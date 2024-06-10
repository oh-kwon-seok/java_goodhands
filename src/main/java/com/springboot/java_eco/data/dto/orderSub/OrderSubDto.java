package com.springboot.java_eco.data.dto.orderSub;

import lombok.*;

@Getter
@Setter

@Data
@NoArgsConstructor
@ToString

public class OrderSubDto {
    private Long uid;
    private Long order_uid;
    private Long item_uid;

    private Double qty;
    private String unit;
    private Integer price;
    private Integer buy_price;

    private Integer supply_price;
    private Integer vat_price;

    private Long used;
    private String token;


    public OrderSubDto(
                Long uid,
                Long order_uid,
                Long item_uid,
                Double qty,

                String unit,
                Integer price,
                Integer buy_price,
                Integer supply_price,
                Integer vat_price,
                Long used,
                String token
    ){
        this.uid = uid;
        this.order_uid = order_uid;
        this.item_uid = item_uid;
        this.qty = qty;
        this.unit = unit;
        this.price = price;
        this.buy_price = buy_price;
        this.supply_price = supply_price;
        this.vat_price = vat_price;
        this.used = used;
        this.token = token;

    }

}
