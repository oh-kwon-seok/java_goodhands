package com.springboot.new_java.data.dto.stockApproval;

import lombok.*;

@Getter
@Setter

@Data
@NoArgsConstructor
@ToString

public class StockApprovalDto {
    private Long uid;
    private String lot;

    private Long item_uid;
    private Long company_uid;
    private String user_id;
    private Long work_task_uid;

    private Double prev_qty;
    private Double out_qty;
    private Double measure_qty;


    private String unit;
    private String status;
    private String token;



    public StockApprovalDto(
             Long uid,
             String lot,
             Long item_uid,
             Long company_uid,
             String user_id,
             Long work_task_uid,
             Double prev_qty,
             Double out_qty,
             Double measure_qty,
             String unit,
             String status,
             String token

    ){
        this.uid = uid;
        this.lot = lot;
        this.item_uid = item_uid;
        this.company_uid = company_uid;
        this.user_id = user_id;
        this.work_task_uid = work_task_uid;
        this.prev_qty = prev_qty;
        this.out_qty = out_qty;
        this.measure_qty = measure_qty;
        this.unit = unit;
        this.status = status;
        this.token = token;

    }

}
