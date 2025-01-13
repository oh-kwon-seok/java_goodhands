package com.springboot.new_java.data.dto.stockInoutSub;

import lombok.*;

@Getter
@Setter

@Data
@NoArgsConstructor
@ToString

public class StockInoutSubDto {
    private Long uid;
    private Long stock_inout_uid;
    private Long factory_uid;
    private Long factory_sub_uid;
    private Long item_uid;
    private String lot;
    private Double qty;
    private String unit;
    private String status;
    private String token;

    public StockInoutSubDto(
            Long uid,
            Long stock_inout_uid,
            Long factory_uid,
            Long factory_sub_uid,
            Long item_uid,
            String lot,
            Double qty,
            String unit,
            String status,
            String token
            ){
        this.uid = uid;
        this.stock_inout_uid = stock_inout_uid;
        this.factory_uid = factory_uid;
        this.factory_sub_uid = factory_sub_uid;
        this.item_uid = item_uid;
        this.lot = lot;
        this.qty = qty;
        this.unit = unit;
        this.status = status;
        this.token = token;

    }

}
