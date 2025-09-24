package com.springboot.new_java.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.service.AbstractCacheableSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractSearchController<T, D> {

    protected final AbstractCacheableSearchService<T, D> searchService;
    protected final RedisTemplate<String, Object> redisTemplate;
    protected final ObjectMapper objectMapper;

    // 기본 생성자 추가
    protected AbstractSearchController() {
        this.searchService = null;
        this.redisTemplate = null;
        this.objectMapper = null;
    }

//    protected AbstractSearchController(AbstractCacheableSearchService<T, D> searchService,
//                                       RedisTemplate<String, Object> redisTemplate,
//                                       ObjectMapper objectMapper) {
//        this.searchService = searchService;
//        this.redisTemplate = redisTemplate;
//        this.objectMapper = objectMapper;
//    }

    /**
     * 엔티티별 기본 매핑 경로 반환
     */
    protected abstract String getEntityPath();

    /**
     * POST 방식 검색
     */
    @PostMapping("/search")
    public ResponseEntity<List<D>> search(@RequestBody CommonInfoSearchDto searchDto) {
        long startTime = System.currentTimeMillis();

        try {
            log.info("=== [START] {} 검색 API 호출 ===", searchService.getEntityType());
            log.info("검색 조건: {}", objectMapper.writeValueAsString(searchDto));

            // 캐시 상태 확인
            String cacheKey = searchDto.toCacheKey(searchService.getEntityType());
            Boolean hasKey = redisTemplate.hasKey(cacheKey);
            Long ttl = hasKey ? redisTemplate.getExpire(cacheKey) : -1;

            log.info("캐시 상태 - 존재여부: {}, 남은 TTL: {}초", hasKey, ttl);

            List<D> results = searchService.getDataWithCache(searchDto);

            log.info("검색 결과 - 총 {}개의 {} 데이터", results.size(), searchService.getEntityType());

            long endTime = System.currentTimeMillis();
            log.info("API 처리 시간: {}ms", (endTime - startTime));
            log.info("=== [END] {} 검색 API 완료 ===\n", searchService.getEntityType());

            return ResponseEntity.ok(results);

        } catch (Exception e) {
            long endTime = System.currentTimeMillis();
            log.error("{} 검색 API 처리 중 오류 발생 (처리시간: {}ms)",
                    searchService.getEntityType(), (endTime - startTime));
            log.error("오류 상세: {}", e.getMessage(), e);
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    /**
     * GET 방식 검색
     */
    @GetMapping("/search")
    public ResponseEntity<List<D>> searchGet(
            @RequestParam(required = false, defaultValue = "all") String filter_title,
            @RequestParam(required = false, defaultValue = "") String search_text) {

        CommonInfoSearchDto searchDto = new CommonInfoSearchDto();
        searchDto.setFilter_title(filter_title);
        searchDto.setSearch_text(search_text);

        return search(searchDto);
    }

    /**
     * 캐시 상태 확인
     */
    @GetMapping("/cache-status")
    public ResponseEntity<Map<String, Object>> getCacheStatus(@ModelAttribute CommonInfoSearchDto searchDto) {
        Map<String, Object> status = new HashMap<>();

        String cacheKey = searchDto.toCacheKey(searchService.getEntityType());
        Boolean hasKey = redisTemplate.hasKey(cacheKey);
        Long ttl = redisTemplate.getExpire(cacheKey);

        status.put("entityType", searchService.getEntityType());
        status.put("cacheKey", cacheKey);
        status.put("cacheExists", hasKey);
        status.put("ttlSeconds", ttl);
        status.put("ttlMinutes", ttl > 0 ? ttl / 60.0 : 0);
        status.put("searchCondition", searchDto);
        status.put("checkTime", LocalDateTime.now());

        if (ttl > 0) {
            status.put("expiresAt", LocalDateTime.now().plusSeconds(ttl));
        }

        return ResponseEntity.ok(status);
    }

    /**
     * 캐시 삭제
     */
    @DeleteMapping("/cache")
    public ResponseEntity<Map<String, Object>> clearCache(@RequestBody CommonInfoSearchDto searchDto) {
        try {
            searchService.invalidateSearchCache(searchDto);

            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("message", "캐시가 삭제되었습니다");
            result.put("entityType", searchService.getEntityType());
            result.put("cacheKey", searchDto.toCacheKey(searchService.getEntityType()));
            result.put("deletedAt", LocalDateTime.now());

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            log.error("캐시 삭제 실패: {}", e.getMessage(), e);

            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "캐시 삭제에 실패했습니다: " + e.getMessage());

            return ResponseEntity.status(500).body(result);
        }
    }

    /**
     * 전체 캐시 삭제
     */
    @DeleteMapping("/cache/all")
    public ResponseEntity<Map<String, Object>> clearAllCache() {
        try {
            searchService.invalidateAllCaches();

            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("message", String.format("모든 %s 캐시가 삭제되었습니다", searchService.getEntityType()));
            result.put("entityType", searchService.getEntityType());
            result.put("deletedAt", LocalDateTime.now());

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            log.error("전체 캐시 삭제 실패: {}", e.getMessage(), e);

            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "전체 캐시 삭제에 실패했습니다: " + e.getMessage());

            return ResponseEntity.status(500).body(result);
        }
    }
}