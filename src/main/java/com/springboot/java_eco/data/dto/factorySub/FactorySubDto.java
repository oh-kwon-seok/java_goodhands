package com.springboot.java_eco.data.dto.factorySub;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter

@Data
@NoArgsConstructor
@ToString

public class FactorySubDto {
    private Long uid;
    private Long factory_uid;
    private String name;
    private String status;
    private String description;
    private Long used;
    private String token;


    public FactorySubDto(
                Long uid,
                Long factory_uid,
                String name,
                String status,
                String description,
                Long used,
                String token
    ){
        this.uid = uid;
        this.factory_uid = factory_uid;
        this.name = name;
        this.status = status;
        this.description = description;
        this.used = used;
        this.token = token;

    }

}
