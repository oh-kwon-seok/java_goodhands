package com.springboot.new_java.data.dto.factory;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter

@Data
@NoArgsConstructor
@ToString

public class FactoryDto {

    private Long uid;
    private Long company_uid;

    private String name;
    private String status;
    private String description;
    private String factory_sub_array;

    private Long used;
    private String token;

    private List<Map<String, Object>> factory_sub;

    public FactoryDto(
                Long uid,
                Long company_uid,
                String name,
                String status,
                String description,
                String factory_sub_array,
                Long used,
                String token,
                List<Map<String, Object>> factory_sub
    ){
        this.uid = uid;
        this.company_uid = company_uid;
        this.name = name;
        this.status = status;
        this.description = description;
        this.factory_sub_array = factory_sub_array;
        this.used = used;
        this.token = token;
        this.factory_sub = factory_sub;
    }

}
