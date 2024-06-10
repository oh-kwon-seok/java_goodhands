package com.springboot.java_eco.data.dto.restricMaterialCountry;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter

@Data
@NoArgsConstructor
@ToString

public class RestricMaterialCountryDto {

    private Long uid;

    private String regulate_type;
    private String regl_code;
    private String ingr_code;
    private String country_name;
    private String notice_ingr_name;
    private String provis_atrcl;
    private String id;
    private String limit_cond;

    private List<Map<String, Object>> restric_material_country_array;


    private Long used;
    private String token;

    public RestricMaterialCountryDto(
                Long uid,
                String regulate_type,
                String regl_code,
                String ingr_code,
                String country_name,
                String notice_ingr_name,
                String provis_atrcl,
                String limit_cond,
                String id,
                List<Map<String, Object>> restric_material_country_array,
                Long used,
                String token
    ){
        this.uid = uid;
        this.regulate_type = regulate_type;
        this.regl_code = regl_code;
        this.ingr_code = ingr_code;

        this.country_name = country_name;
        this.notice_ingr_name = notice_ingr_name;
        this.provis_atrcl = provis_atrcl;
        this.limit_cond = limit_cond;
        this.id = id;

        this.restric_material_country_array = restric_material_country_array;
        this.used = used;
        this.token = token;

    }

}
