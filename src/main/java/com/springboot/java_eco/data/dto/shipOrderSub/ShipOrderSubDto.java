package com.springboot.java_eco.data.dto.shipOrderSub;

import lombok.*;

@Getter
@Setter

@Data
@NoArgsConstructor
@ToString

public class ShipOrderSubDto {
    private Long uid;
    private Long ship_order_uid;
    private Long item_uid;
    private Long stock_uid;

    private Double qty;
    private String unit;
    private Integer price;
    private Integer buy_price;

    private Integer supply_price;
    private Integer vat_price;
    private String token;


    public ShipOrderSubDto(
                Long uid,
                Long ship_order_uid,
                Long item_uid,
                Long stock_uid,
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
        this.ship_order_uid = ship_order_uid;
        this.item_uid = item_uid;
        this.stock_uid = stock_uid;
        this.qty = qty;
        this.unit = unit;
        this.price = price;
        this.buy_price = buy_price;
        this.supply_price = supply_price;
        this.vat_price = vat_price;

        this.token = token;

    }

}
