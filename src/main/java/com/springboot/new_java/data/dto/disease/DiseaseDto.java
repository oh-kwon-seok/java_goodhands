package com.springboot.new_java.data.dto.disease;

import lombok.*;

import javax.swing.text.StyledEditorKit;

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
    private String user_id;
    private String disease_checklist;
    private Boolean is_chronic;
    private Boolean is_disease_checklist;


    private Boolean used;
    private String token;


    public DiseaseDto(Long uid, String name,String code, String description, Long disease_category_uid,  String user_id, String disease_checklist,  Boolean used, Boolean is_disease_checklist, Boolean is_chronic, String token) {
        this.user_id = user_id;
        this.code = code;
        this.description = description;
        this.disease_category_uid = disease_category_uid;
        this.disease_checklist = disease_checklist;
        this.is_chronic = is_chronic;
        this.is_disease_checklist = is_disease_checklist;
        this.used = used;
        this.uid = uid;
        this.name = name;

        this.token = token;
    }
}
