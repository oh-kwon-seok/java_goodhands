package com.springboot.java_eco.data.dto.process;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter

@Data
@NoArgsConstructor
@ToString

public class ProcessDto {

    private Long uid;
    private Long company_uid;

    private String name;
    private String status;
    private String description;
    private String process_qc_array;

    private Long used;
    private String token;

    private List<Map<String, Object>> process_qc;

    public ProcessDto(
                Long uid,
                Long company_uid,
                String name,
                String status,
                String description,
                String process_qc_array,
                Long used,
                String token,
                List<Map<String, Object>> process_qc
    ){
        this.uid = uid;
        this.company_uid = company_uid;
        this.name = name;
        this.status = status;
        this.description = description;
        this.process_qc_array = process_qc_array;
        this.used = used;
        this.token = token;
        this.process_qc = process_qc;
    }

}
