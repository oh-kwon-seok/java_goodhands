package com.springboot.java_eco.data.dto.stockRecord;

import lombok.*;

@Getter
@Setter

@Data
@NoArgsConstructor
@ToString

public class StockRecordDto {
    private Long uid;


    private Long item_uid;
    private Long company_uid;
    private Long out_factory_uid;
    private Long out_factory_sub_uid;

    private Long in_factory_uid;
    private Long in_factory_sub_uid;

    private String lot;

    private Double qty;
    private String unit;
    private String type;
    private String status;
    private String reason;

    private String token;

    public StockRecordDto(
             Long uid,

            Long item_uid,
            Long company_uid,
            Long out_factory_uid,
            Long out_factory_sub_uid,
             Long in_factory_uid,
             Long in_factory_sub_uid,
            String lot,
            Double qty,
            String unit,
             String type,
            String status,
            String reason,
            String token
            ){
        this.uid = uid;

        this.item_uid = item_uid;
        this.company_uid = company_uid;
        this.out_factory_uid = out_factory_uid;
        this.out_factory_sub_uid = out_factory_sub_uid;
        this.in_factory_uid = in_factory_uid;
        this.in_factory_sub_uid = in_factory_sub_uid;

        this.lot = lot;
        this.qty = qty;
        this.unit = unit;
        this.type = type;
        this.status = status;
        this.reason = reason;
        this.token = token;

    }

}
