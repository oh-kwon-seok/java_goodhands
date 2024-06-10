package com.springboot.java_eco.data.dto.stockControl;

import lombok.*;

@Getter
@Setter

@Data
@NoArgsConstructor
@ToString

public class StockControlDto {
    private Long uid;
    private String lot;
    private Long item_uid;
    private Long company_uid;

    private Long prev_factory_uid;
    private Long prev_factory_sub_uid;

    private Long after_factory_uid;
    private Long after_factory_sub_uid;



    private String user_id;

    private Double prev_qty;
    private Double after_qty;

    private String control_reason;

    private String unit;
    private String status;

    private String token;


    public StockControlDto(
             Long uid,
            String lot,
            Long item_uid,
            Long company_uid,
            Long prev_factory_uid,
            Long prev_factory_sub_uid,
            Long after_factory_uid,
            Long after_factory_sub_uid,
            String user_id,
            Double prev_qty,
             Double after_qty,
            String unit,
            String status,
            String control_reason,
            String token
    ){
        this.uid = uid;
        this.lot = lot;
        this.item_uid = item_uid;
        this.company_uid = company_uid;
        this.prev_factory_uid = prev_factory_uid;
        this.prev_factory_sub_uid = prev_factory_sub_uid;
        this.after_factory_uid = after_factory_uid;
        this.after_factory_sub_uid = after_factory_sub_uid;
        this.user_id = user_id;
        this.prev_qty = prev_qty;
        this.after_qty = after_qty;
        this.unit = unit;
        this.status = status;
        this.control_reason = control_reason;
        this.token = token;

    }

}
