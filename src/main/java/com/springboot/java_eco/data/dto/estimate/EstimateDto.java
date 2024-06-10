package com.springboot.java_eco.data.dto.estimate;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter

@Data
@NoArgsConstructor
@ToString

public class EstimateDto {

    private Long uid;
    private Long company_uid;
    private Long customer_uid;
    private String user_id;
    private String code;
    private String name;

    private String product_spec;
    private String ship_place;
    private String description;
    private String estimate_date;
    private String expire;

    private Long used;
    private String token;

    private List<Map<String, Object>> estimate_sub;


    public EstimateDto(
                Long uid,
                Long company_uid,
                Long customer_uid,
                String user_id,
                String code,
                String name,
                String product_spec,
                String ship_place,
                String description,
                String estimate_date,
                String expire,
                Long used,
                String token,
                List<Map<String, Object>> estimate_sub
    ){
        this.uid = uid;
        this.company_uid = company_uid;
        this.customer_uid = customer_uid;
        this.user_id = user_id;
        this.code = code;
        this.name = name;
        this.product_spec = product_spec;
        this.ship_place = ship_place;
        this.description = description;
        this.estimate_date = estimate_date;
        this.expire = expire;
        this.used = used;
        this.token = token;
        this.estimate_sub = estimate_sub;
    }

}
