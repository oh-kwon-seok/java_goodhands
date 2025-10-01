package com.springboot.new_java.data.dto.disease;

import com.springboot.new_java.data.entity.disease.DiseaseCategory;
import lombok.*;

import javax.swing.text.StyledEditorKit;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Data
@NoArgsConstructor
@ToString
public class DiseaseDto {

    private Long uid;
    private String name;
    private String code;
    private String description;
    private Long disease_category_uid;
    private DiseaseCategory diseaseCategory;
    private Long user_id;
    private String disease_checklist;
    private Boolean use_chronic;
    private Boolean use_disease_checklist;
    private Boolean used;
    private String token;


    public DiseaseDto(Long uid, String name,String code, String description,DiseaseCategory diseaseCategory, Long disease_category_uid,  Long user_id,  String disease_checklist,  Boolean used, Boolean use_disease_checklist, Boolean use_chronic, String token) {
        this.user_id = user_id;
        this.code = code;
        this.description = description;
        this.disease_category_uid = disease_category_uid;
        this.diseaseCategory = diseaseCategory;
        this.disease_checklist = disease_checklist;
        this.use_chronic = use_chronic;
        this.use_disease_checklist = use_disease_checklist;
        this.used = used;
        this.uid = uid;
        this.name = name;

        this.token = token;
    }
}
