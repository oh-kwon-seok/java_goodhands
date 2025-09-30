package com.springboot.new_java.service;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;

import java.time.Duration;
import java.util.List;

// ==================== 2. 범용 캐시 서비스 인터페이스 ====================
public interface CacheableSearchService<T, D> {

    /**
     * 검색 조건과 캐시를 결합한 데이터 조회
     */
    List<D> getDataWithCache(CommonInfoSearchDto searchDto);

    /**
     * 엔티티 타입명 반환 (캐시 키 생성용)
     */
    String getEntityType();

    /**
     * Entity를 DTO로 변환
     */
    D convertToDto(T entity);

    /**
     * 실제 DB 검색 로직
     */
    List<T> findAllBySearchCondition(CommonInfoSearchDto searchDto);

    /**
     * 캐시 TTL 설정 (기본값 제공, 오버라이드 가능)
     */
    default Duration getCacheTtl(CommonInfoSearchDto searchDto) {
        return searchDto.hasSearchCondition()
                ? Duration.ofSeconds(3)  // 검색 결과(개발용)
                : Duration.ofSeconds(5); // 전체 조회 (개발용)
                // ? Duration.ofMinutes(3)  // 검색 결과(배포용)
                //: Duration.ofofMinutes(5); // 전체 조회 (배포용)

    }
}