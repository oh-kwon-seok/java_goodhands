package com.springboot.new_java.data.dto.workTaskPacking;

import lombok.*;

@Getter
@Setter

@Data
@NoArgsConstructor
@ToString

public class WorkTaskPackingDto {
    private Long uid;
    private String lot;
    private Long work_task_uid;
    private Long work_task_product_uid;
    private Long bom_uid;
    private Long factory_uid;
    private Long factory_sub_uid;
    private Integer inbox_qty;
    private Integer box_qty;
    private Integer etc_qty;


    private String token;

    public WorkTaskPackingDto(
         Long uid,
        String lot,
        Long work_task_uid,
        Long work_task_product_uid,
        Long bom_uid,
        Long factory_uid,
        Long factory_sub_uid,
        Integer inbox_qty,
        Integer box_qty,
        Integer etc_qty,
         String token

    ){
        this.uid = uid;
        this.lot = lot;
        this.work_task_uid = work_task_uid;
        this.work_task_product_uid = work_task_product_uid;
        this.bom_uid = bom_uid;
        this.factory_uid = factory_uid;
        this.factory_sub_uid = factory_sub_uid;
        this.inbox_qty = inbox_qty;
        this.box_qty = box_qty;
        this.etc_qty = etc_qty;
        this.token = token;

    }

}
