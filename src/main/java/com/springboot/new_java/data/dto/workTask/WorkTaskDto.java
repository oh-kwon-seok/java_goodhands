package com.springboot.new_java.data.dto.workTask;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter

@Data
@NoArgsConstructor
@ToString

public class WorkTaskDto {
    private Long uid;
    private Long work_plan_uid;
    private Long bom_uid;
    private Long company_uid;
    private String user_id;
    private String code;

    private Double task_qty;
    private Double success_qty;
    private Double fail_qty;

    private String work_start_date;
    private String work_end_date;


    private String unit;
    private String status;


    private Long material_order;
    private Long measure_order;
    private Long production_order;
    private Long packing_order;

    private String token;

    private List<Map<String, Object>> stock_approval;
    private List<Map<String, Object>> work_task_product;
    private List<Map<String, Object>> work_task_packing;



    public WorkTaskDto(
             Long uid,
             Long work_plan_uid,
             String code,
             Long bom_uid,
             Long company_uid,
             String user_id,
             Double task_qty,
             Double success_qty,
             Double fail_qty,
             String work_start_date,
             String work_end_date,
             String unit,
             String status,
             Long material_order,
             Long measure_order,
             Long production_order,
             Long packing_order,
             String token,
             List<Map<String, Object>> stock_approval,
             List<Map<String, Object>> work_task_product,
             List<Map<String, Object>> work_task_packing

             ){
        this.uid = uid;
        this.code = code;
        this.work_plan_uid = work_plan_uid;
        this.company_uid = company_uid;
        this.bom_uid = bom_uid;
        this.user_id = user_id;

        this.task_qty = task_qty;
        this.success_qty = success_qty;
        this.fail_qty = fail_qty;
        this.work_start_date = work_start_date;
        this.work_end_date = work_end_date;

        this.unit = unit;
        this.status = status;
        this.material_order = material_order;
        this.measure_order = measure_order;
        this.production_order = production_order;
        this.packing_order = packing_order;

        this.token = token;
        this.stock_approval = stock_approval;
        this.work_task_product = work_task_product;
        this.work_task_packing = work_task_packing;

    }

}
