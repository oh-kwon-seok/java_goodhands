package com.springboot.new_java.data.dto.workTaskProduct;

import lombok.*;

@Getter
@Setter

@Data
@NoArgsConstructor
@ToString

public class WorkTaskProductDto {
    private Long uid;
    private String lot;
    private Long work_task_uid;
    private Long bom_uid;

    private Double product_qty;
    private String unit;
    private String status;
    private String token;

    public WorkTaskProductDto(
             Long uid,
             String lot,
             Long work_task_uid,
             Long bom_uid,
             Double product_qty,
             String unit,
             String status,
             String token

    ){
        this.uid = uid;
        this.lot = lot;
        this.work_task_uid = work_task_uid;
        this.bom_uid = bom_uid;

        this.product_qty = product_qty;
        this.unit = unit;
        this.status = status;
        this.token = token;

    }

}
