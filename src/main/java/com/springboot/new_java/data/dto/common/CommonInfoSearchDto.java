package com.springboot.new_java.data.dto.common;


import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommonInfoSearchDto {
    private String filter_title;
    private String search_text;

    // 엔티티 타입을 받아서 캐시 키 생성 (범용)
    public String toCacheKey(String entityType) {
        if (search_text == null || search_text.trim().isEmpty()) {
            return entityType.toLowerCase() + ":all";
        }

        String cleanSearchText = search_text.trim().toLowerCase();
        String cleanFilterTitle = filter_title != null ? filter_title : "all";

        return String.format("%s:search:%s:%s", entityType.toLowerCase(), cleanFilterTitle, cleanSearchText);
    }

    // 검색 조건이 있는지 확인
    public boolean hasSearchCondition() {
        return search_text != null && !search_text.trim().isEmpty();
    }

    // 해시코드 생성 (캐시 키 대체용)
    public String toHashKey(String entityType) {
        int hash = Objects.hash(entityType, filter_title, search_text);
        return entityType.toLowerCase() + ":hash:" + Integer.toHexString(Math.abs(hash));
    }
}

