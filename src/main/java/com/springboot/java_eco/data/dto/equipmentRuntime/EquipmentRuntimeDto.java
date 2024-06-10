package com.springboot.java_eco.data.dto.equipmentRuntime;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@NoArgsConstructor
@ToString

public class EquipmentRuntimeDto {
    private Long uid;
    private Long company_uid;
    private Long equipment_uid;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private Integer runtime_second;
    private String token;
    private Long used;

    public EquipmentRuntimeDto(Long uid, Long company_uid, Long equipment_uid, LocalDateTime start_time, LocalDateTime end_time, Integer runtime_second, Long used, String token){
        this.uid = uid;
        this.company_uid = company_uid;
        this.equipment_uid = equipment_uid;
        this.start_time = start_time;
        this.end_time = end_time;
        this.runtime_second = runtime_second;
        this.used = used;
        this.token = token;

    }

}
