package com.springboot.new_java.data.dto.care;

import com.springboot.new_java.data.entity.User;
import com.springboot.new_java.data.entity.care.CareHomeType;
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
    private Long care_home_type_uid;
    private CareHomeType careHomeType;
    private User user;
    private String name;

    private Boolean used;
    private String token;


    public CareHomeDto(Long uid,Long care_home_type_uid, String user_id, String name, CareHomeType careHomeType, User user, Boolean used, String token) {
        this.user_id = user_id;
        this.care_home_type_uid = care_home_type_uid;
        this.careHomeType = careHomeType;
        this.user = user;
        this.uid = uid;
        this.name = name;
        this.used = used;
        this.token = token;
    }
}
