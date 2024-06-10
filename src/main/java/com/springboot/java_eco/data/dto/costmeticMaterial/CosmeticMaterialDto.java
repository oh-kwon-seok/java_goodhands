package com.springboot.java_eco.data.dto.costmeticMaterial;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter

@Data
@NoArgsConstructor
@ToString

public class CosmeticMaterialDto {

    private Long uid;

    private String ingr_kor_name;
    private String ingr_eng_name;
    private String cas_no;
    private String ingr_synonym;
    private String origin_major_kor_name;
    private String id;

    private List<Map<String, Object>> cosmetic_material_array;


    private Long used;
    private String token;

    public CosmeticMaterialDto(
                Long uid,
                String ingr_kor_name,
                String ingr_eng_name,
                String cas_no,
                String ingr_synonym,
                String origin_major_kor_name,
                String id,
                List<Map<String, Object>> cosmetic_material_array,
                Long used,
                String token
    ){
        this.uid = uid;
        this.ingr_kor_name = ingr_kor_name;
        this.ingr_eng_name = ingr_eng_name;
        this.cas_no = cas_no;
        this.ingr_synonym = ingr_synonym;
        this.origin_major_kor_name = origin_major_kor_name;
        this.id = id;

        this.cosmetic_material_array = cosmetic_material_array;
        this.used = used;
        this.token = token;

    }

}
