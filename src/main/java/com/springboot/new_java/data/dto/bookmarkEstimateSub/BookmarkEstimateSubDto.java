package com.springboot.new_java.data.dto.bookmarkEstimateSub;

import lombok.*;

@Getter
@Setter

@Data
@NoArgsConstructor
@ToString

public class BookmarkEstimateSubDto {
    private Long uid;
    private Long bookmark_estimate_uid;
    private Long item_uid;

    private Double qty;
    private String unit;
    private Integer price;
    private Integer buy_price;
    private Integer supply_price;
    private Integer vat_price;
    private String description;


    private Long used;
    private String token;


    public BookmarkEstimateSubDto(
                Long uid,
                Long bookmark_estimate_uid,
                Long item_uid,
                Double qty,

                String unit,
                Integer price,
                Integer buy_price,
                Integer supply_price,
                Integer vat_price,
                String description,
                Long used,
                String token
    ){
        this.uid = uid;
        this.bookmark_estimate_uid = bookmark_estimate_uid;
        this.item_uid = item_uid;
        this.qty = qty;
        this.unit = unit;
        this.price = price;
        this.buy_price = buy_price;
        this.supply_price = supply_price;
        this.vat_price = vat_price;
        this.description = description;
        this.used = used;
        this.token = token;

    }

}
