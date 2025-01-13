package com.springboot.new_java.data.dto.bookmarkEstimate;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter

@Data
@NoArgsConstructor
@ToString

public class BookmarkEstimateDto {

    private Long uid;
    private Long company_uid;
    private String user_id;
    private String name;

    private String product_spec;
    private String ship_place;
    private String description;


    private Long used;
    private String token;

    private List<Map<String, Object>> bookmark_estimate_sub;


    public BookmarkEstimateDto(
                Long uid,
                Long company_uid,
                String user_id,
                String name,
                String product_spec,
                String ship_place,
                String description,
                Long used,
                String token,
                List<Map<String, Object>> bookmark_estimate_sub
    ){
        this.uid = uid;
        this.company_uid = company_uid;
        this.user_id = user_id;
        this.name = name;
        this.product_spec = product_spec;
        this.ship_place = ship_place;
        this.description = description;

        this.used = used;
        this.token = token;
        this.bookmark_estimate_sub = bookmark_estimate_sub;
    }

}
