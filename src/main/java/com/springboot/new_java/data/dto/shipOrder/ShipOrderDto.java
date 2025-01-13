package com.springboot.new_java.data.dto.shipOrder;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter

@Data
@NoArgsConstructor
@ToString

public class ShipOrderDto {

    private Long uid;
    private Long company_uid;
    private Long customer_uid;
    private Long order_uid;
    private String user_id;
    private String code;
    private String name;

    private String product_spec;
    private String ship_place;
    private String ship_date;
    private Integer order_count;
    private String description;
    private String status;


    private Long used;
    private String token;

    private List<Map<String, Object>> ship_order_sub;


    public ShipOrderDto(
                Long uid,
                Long company_uid,
                Long customer_uid,
                Long estimate_uid,
                String user_id,
                String code,
                String name,
                String product_spec,
                String ship_place,
                String description,
                String ship_date,
                Integer order_count,
                String status,
                Long used,
                String token,
                List<Map<String, Object>> ship_order_sub
    ){
        this.uid = uid;
        this.company_uid = company_uid;
        this.customer_uid = customer_uid;
        this.order_uid = estimate_uid;
        this.user_id = user_id;
        this.code = code;
        this.name = name;
        this.product_spec = product_spec;
        this.ship_place = ship_place;
        this.description = description;
        this.ship_date = ship_date;
        this.order_count = order_count;
        this.status = status;
        this.used = used;
        this.token = token;
        this.ship_order_sub = ship_order_sub;
    }

}
