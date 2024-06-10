package com.springboot.java_eco.data.dto.processQc;

import lombok.*;

@Getter
@Setter

@Data
@NoArgsConstructor
@ToString

public class ProcessQcDto {
    private Long uid;
    private Long process_uid;
    private String name;
    private Integer type;
    private String description;
    private Long used;
    private String token;


    public ProcessQcDto(
                Long uid,
                Long process_uid,
                String name,
                Integer type,
                String description,
                Long used,
                String token
    ){
        this.uid = uid;
        this.process_uid = process_uid;
        this.name = name;
        this.type = type;
        this.description = description;
        this.used = used;
        this.token = token;

    }

}
