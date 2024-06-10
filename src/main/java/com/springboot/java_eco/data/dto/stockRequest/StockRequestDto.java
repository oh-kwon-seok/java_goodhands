package com.springboot.java_eco.data.dto.stockRequest;

import lombok.*;

@Getter
@Setter

@Data
@NoArgsConstructor
@ToString

public class StockRequestDto {
    private Long uid;

    private Long item_uid;
    private Long company_uid;
    private String user_id;
    private Long work_task_uid;


    private Double req_qty;
    private String unit;
    private String status;
    private String token;



    public StockRequestDto(
             Long uid,
             Long item_uid,
             Long company_uid,
             String user_id,
             Long work_task_uid,
             Double req_qty,
             String unit,
             String status,
             String token

    ){
        this.uid = uid;
        this.item_uid = item_uid;
        this.company_uid = company_uid;
        this.user_id = user_id;
        this.work_task_uid = work_task_uid;
        this.req_qty = req_qty;
        this.unit = unit;
        this.status = status;
        this.token = token;

    }

}
