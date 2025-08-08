package com.springboot.new_java.data.dto.care;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@Data
@NoArgsConstructor
@ToString
public class CareHomeDto {

    private Long uid;
    private String user_id;
    private Long care_type_uid;
    private String name;

    private Boolean used;
    private String token;


    public CareHomeDto(Long uid,Long care_type_uid, String user_id, String name, Boolean used, String token) {
        this.user_id = user_id;
        this.care_type_uid = care_type_uid;
        this.uid = uid;
        this.name = name;
        this.used = used;
        this.token = token;
    }
}
