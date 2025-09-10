package com.springboot.new_java.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractCacheableSearchService<T, D> implements CacheableSearchService<T, D> {

    protected final RedisTemplate<String, Object> redisTemplate;
    protected final ObjectMapper objectMapper;

    @Override
    public List<D> getDataWithCache(CommonInfoSearchDto searchDto) {
        String entityType = getEntityType();
        String cacheKey = searchDto.toCacheKey(entityType);
        long startTime = System.currentTimeMillis();

        try {
            // 1. Redis에서 캐시 확인
            log.debug("Redis 캐시 확인: {} (검색조건: filter='{}', text='{}')",
                    cacheKey, searchDto.getFilter_title(), searchDto.getSearch_text());

            String cachedData = (String) redisTemplate.opsForValue().get(cacheKey);

            if (cachedData != null) {
                log.info("캐시 HIT - Redis에서 데이터 반환 ({}ms)",
                        System.currentTimeMillis() - startTime);

                TypeReference<List<D>> typeRef = new TypeReference<List<D>>() {};
                return objectMapper.readValue(cachedData, typeRef);
            }

            // 2. 캐시 MISS - DB에서 검색
            log.info("캐시 MISS - DB에서 검색 시작 (entity: {}, filter: {}, search: {})",
                    entityType, searchDto.getFilter_title(), searchDto.getSearch_text());

            List<T> entities = findAllBySearchCondition(searchDto);
            List<D> dtos = entities.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());

            // 3. Redis에 새로 캐싱
            try {
                String jsonData = objectMapper.writeValueAsString(dtos);
                Duration ttl = getCacheTtl(searchDto);

                redisTemplate.opsForValue().set(cacheKey, jsonData, ttl);
                log.info("새 데이터를 Redis에 캐싱 완료 (entity: {}, TTL: {}분, 데이터 수: {}개)",
                        entityType, ttl.toMinutes(), dtos.size());
            } catch (Exception e) {
                log.error("Redis 캐싱 실패 (entity: {}): {}", entityType, e.getMessage());
            }

            // 4. 검색 통계 로깅
            logSearchStatistics(searchDto, dtos);

            log.info("DB 검색 완료 (entity: {}, 총 처리시간: {}ms)",
                    entityType, System.currentTimeMillis() - startTime);
            return dtos;

        } catch (Exception e) {
            log.error("{} 검색 처리 중 오류: {}", entityType, e.getMessage(), e);

            // 캐시 실패해도 DB에서라도 조회 시도
            try {
                return findAllBySearchCondition(searchDto).stream()
                        .map(this::convertToDto)
                        .collect(Collectors.toList());
            } catch (Exception dbException) {
                log.error("{} DB 조회도 실패: {}", entityType, dbException.getMessage(), dbException);
                return new ArrayList<>();
            }
        }
    }

    /**
     * 특정 검색 조건의 캐시 무효화
     */
    public void invalidateSearchCache(CommonInfoSearchDto searchDto) {
        try {
            String cacheKey = searchDto.toCacheKey(getEntityType());
            Boolean deleted = redisTemplate.delete(cacheKey);

            if (Boolean.TRUE.equals(deleted)) {
                log.info("특정 캐시 무효화 성공: {}", cacheKey);
            } else {
                log.debug("캐시 키가 존재하지 않음: {}", cacheKey);
            }
        } catch (Exception e) {
            log.error("특정 캐시 무효화 실패: {}", e.getMessage(), e);
        }
    }

    /**
     * 해당 엔티티의 모든 캐시 무효화
     */
    public void invalidateAllCaches() {
        try {
            String pattern = getEntityType().toLowerCase() + ":*";
            Set<String> keys = redisTemplate.keys(pattern);

            if (keys != null && !keys.isEmpty()) {
                Long deletedCount = redisTemplate.delete(keys);
                log.info("모든 {} 캐시 무효화 완료: {}개 키 삭제", getEntityType(), deletedCount);
            } else {
                log.debug("무효화할 {} 캐시가 없습니다", getEntityType());
            }
        } catch (Exception e) {
            log.error("전체 캐시 무효화 실패 (entity: {}): {}", getEntityType(), e.getMessage(), e);
        }
    }

    /**
     * 여러 엔티티 타입의 캐시를 일괄 무효화
     */
    public void invalidateMultipleEntityCaches(String... entityTypes) {
        if (entityTypes == null || entityTypes.length == 0) {
            log.warn("무효화할 엔티티 타입이 지정되지 않았습니다");
            return;
        }

        try {
            List<String> patterns = Arrays.stream(entityTypes)
                    .map(entityType -> entityType.toLowerCase() + ":*")
                    .collect(Collectors.toList());

            int totalDeleted = 0;
            for (String pattern : patterns) {
                Set<String> keys = redisTemplate.keys(pattern);
                if (keys != null && !keys.isEmpty()) {
                    Long deletedCount = redisTemplate.delete(keys);
                    totalDeleted += deletedCount != null ? deletedCount.intValue() : 0;
                    log.debug("엔티티 타입별 캐시 무효화: {} - {}개 키", pattern, deletedCount);
                }
            }

            log.info("다중 엔티티 캐시 무효화 완료: {} 타입, 총 {}개 키 삭제",
                    entityTypes.length, totalDeleted);
        } catch (Exception e) {
            log.error("다중 엔티티 캐시 무효화 실패: {}", e.getMessage(), e);
        }
    }

    /**
     * 관련 엔티티들의 캐시를 무효화 (오버라이드 가능)
     */
    protected void invalidateRelatedCaches() {
        // 하위 클래스에서 필요에 따라 구현
        // 예: User 엔티티가 변경되면 Department, Employment 캐시도 무효화
        String[] relatedEntityTypes = getRelatedEntityTypes();
        if (relatedEntityTypes != null && relatedEntityTypes.length > 0) {
            invalidateMultipleEntityCaches(relatedEntityTypes);
        }
    }

    /**
     * 연관된 엔티티 타입들 반환 (하위 클래스에서 오버라이드)
     */
    protected String[] getRelatedEntityTypes() {
        // 기본적으로는 관련 엔티티 없음
        return new String[0];
    }

    /**
     * 데이터 변경 후 자동 캐시 무효화 (CUD 작업 후 호출)
     */
    public void invalidateCachesAfterDataChange() {
        try {
            log.debug("데이터 변경 후 캐시 무효화 시작: {}", getEntityType());

            // 1. 현재 엔티티의 모든 캐시 무효화
            invalidateAllCaches();

            // 2. 관련 엔티티 캐시 무효화
            invalidateRelatedCaches();

            log.info("데이터 변경 후 캐시 무효화 완료: {}", getEntityType());
        } catch (Exception e) {
            // 캐시 무효화 실패해도 비즈니스 로직에는 영향 없음
            log.error("데이터 변경 후 캐시 무효화 중 오류 발생: {}", e.getMessage(), e);
        }
    }

    /**
     * 캐시 상태 확인
     */
    public CacheStatus getCacheStatus() {
        try {
            String pattern = getEntityType().toLowerCase() + ":*";
            Set<String> keys = redisTemplate.keys(pattern);
            int totalKeys = keys != null ? keys.size() : 0;

            return CacheStatus.builder()
                    .entityType(getEntityType())
                    .totalCacheKeys(totalKeys)
                    .cachePattern(pattern)
                    .build();
        } catch (Exception e) {
            log.error("캐시 상태 확인 실패: {}", e.getMessage(), e);
            return CacheStatus.builder()
                    .entityType(getEntityType())
                    .totalCacheKeys(0)
                    .error(e.getMessage())
                    .build();
        }
    }

    /**
     * 캐시 워밍업 (자주 사용되는 검색 조건들을 미리 캐싱)
     */
    public void warmupCache(List<CommonInfoSearchDto> commonSearchConditions) {
        if (commonSearchConditions == null || commonSearchConditions.isEmpty()) {
            log.debug("워밍업할 검색 조건이 없습니다");
            return;
        }

        log.info("캐시 워밍업 시작: {} 엔티티, {}개 검색 조건",
                getEntityType(), commonSearchConditions.size());

        int successCount = 0;
        for (CommonInfoSearchDto searchDto : commonSearchConditions) {
            try {
                getDataWithCache(searchDto);
                successCount++;
            } catch (Exception e) {
                log.warn("캐시 워밍업 실패 (검색조건: filter='{}', text='{}'): {}",
                        searchDto.getFilter_title(), searchDto.getSearch_text(), e.getMessage());
            }
        }

        log.info("캐시 워밍업 완료: {}/{} 성공", successCount, commonSearchConditions.size());
    }

    /**
     * 검색 통계 로깅
     */
    protected void logSearchStatistics(CommonInfoSearchDto searchDto, List<D> results) {
        if (searchDto.hasSearchCondition()) {
            log.info("검색 통계 - Entity: {}, 필터: '{}', 검색어: '{}', 결과: {}개",
                    getEntityType(), searchDto.getFilter_title(), searchDto.getSearch_text(), results.size());

            if (results.isEmpty()) {
                log.warn("검색 결과 없음 - 검색어 확인 필요 (Entity: {})", getEntityType());
            }
        } else {
            log.info("전체 조회 - Entity: {}, 결과: {}개", getEntityType(), results.size());
        }
    }

    /**
     * 캐시 상태 정보를 담는 클래스
     */
    public static class CacheStatus {
        private String entityType;
        private int totalCacheKeys;
        private String cachePattern;
        private String error;

        // Builder pattern
        public static CacheStatusBuilder builder() {
            return new CacheStatusBuilder();
        }

        // Getters
        public String getEntityType() { return entityType; }
        public int getTotalCacheKeys() { return totalCacheKeys; }
        public String getCachePattern() { return cachePattern; }
        public String getError() { return error; }

        public static class CacheStatusBuilder {
            private String entityType;
            private int totalCacheKeys;
            private String cachePattern;
            private String error;

            public CacheStatusBuilder entityType(String entityType) {
                this.entityType = entityType;
                return this;
            }

            public CacheStatusBuilder totalCacheKeys(int totalCacheKeys) {
                this.totalCacheKeys = totalCacheKeys;
                return this;
            }

            public CacheStatusBuilder cachePattern(String cachePattern) {
                this.cachePattern = cachePattern;
                return this;
            }

            public CacheStatusBuilder error(String error) {
                this.error = error;
                return this;
            }

            public CacheStatus build() {
                CacheStatus cacheStatus = new CacheStatus();
                cacheStatus.entityType = this.entityType;
                cacheStatus.totalCacheKeys = this.totalCacheKeys;
                cacheStatus.cachePattern = this.cachePattern;
                cacheStatus.error = this.error;
                return cacheStatus;
            }
        }
    }
}