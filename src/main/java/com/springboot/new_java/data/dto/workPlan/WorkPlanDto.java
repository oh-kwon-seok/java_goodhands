package com.springboot.new_java.data.dto.workPlan;

import lombok.*;

@Getter
@Setter

@Data
@NoArgsConstructor
@ToString

public class WorkPlanDto {
    private Long uid;
    private Long company_uid;
    private String user_id;
    private Long bom_uid;
    private Double qty;
    private String unit;
    private String startDate;
    private String endDate;
    private String status;

    private String token;
    private Long used;


    public WorkPlanDto(
             Long uid,
             Long company_uid,
             String user_id,
             Long bom_uid,
             Double qty,
             String unit,
             String startDate,
             String endDate,
             String status,
             String token,
             Long used
    ){
        this.uid = uid;
        this.company_uid = company_uid;
        this.user_id = user_id;
        this.bom_uid = bom_uid;
        this.qty = qty;
        this.unit = unit;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.token = token;
        this.used = used;

    }

}
