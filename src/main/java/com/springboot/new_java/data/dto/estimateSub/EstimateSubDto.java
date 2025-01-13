package com.springboot.new_java.data.dto.estimateSub;

import lombok.*;

@Getter
@Setter

@Data
@NoArgsConstructor
@ToString

public class EstimateSubDto {
    private Long uid;
    private Long estimate_uid;
    private Long item_uid;

    private Double qty;
    private String unit;
    private Integer price;
    private Integer buy_price;

    private Integer supply_price;
    private Integer vat_price;

    private Long used;
    private String token;


    public EstimateSubDto(
                Long uid,
                Long estimate_uid,
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
        this.estimate_uid = estimate_uid;
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
