package com.springboot.java_eco.data.dto.stock;

import lombok.*;

@Getter
@Setter

@Data
@NoArgsConstructor
@ToString

public class StockDto {
    private Long uid;
    private String lot;
    private Long item_uid;
    private Long company_uid;

    private Long factory_uid;
    private Long factory_sub_uid;
    private String user_id;

    private Double qty;
    private String unit;
    private String status;
    private String token;

    public StockDto(
            Long uid,
            String lot,
            Long item_uid,
            Long company_uid,
            Long factory_uid,
            Long factory_sub_uid,
            String user_id,
            Double qty,
            String unit,
            String status,
            String token
    ){
        this.uid = uid;
        this.lot = lot;
        this.item_uid = item_uid;
        this.company_uid = company_uid;
        this.factory_uid = factory_uid;
        this.factory_sub_uid = factory_sub_uid;
        this.user_id = user_id;
        this.qty = qty;
        this.unit = unit;
        this.status = status;

        this.token = token;

    }

}
