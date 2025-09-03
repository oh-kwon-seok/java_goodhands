package com.springboot.new_java.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.ArrayList;
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

    public void invalidateSearchCache(CommonInfoSearchDto searchDto) {
        String cacheKey = searchDto.toCacheKey(getEntityType());
        redisTemplate.delete(cacheKey);
        log.info("캐시 무효화: {}", cacheKey);
    }

    /**
     * 캐시 무효화 - 해당 엔티티의 모든 캐시
     */
    public void invalidateAllCaches() {
        String pattern = getEntityType().toLowerCase() + ":*";
        Set<String> keys = redisTemplate.keys(pattern);
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
            log.info("모든 {} 캐시 무효화: {}개 키", getEntityType(), keys.size());
        }
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
}
