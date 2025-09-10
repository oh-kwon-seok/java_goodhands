package com.springboot.new_java.data.dto.user;

import com.springboot.new_java.data.entity.Department;
import com.springboot.new_java.data.entity.Employment;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@Data
@NoArgsConstructor
@ToString
public class UserDto {

    private String id;

    private Long employment_uid;
    private Long department_uid;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String auth;
    private Long used;
    private String token;
    private Department department;
    private Employment employment;


    private Map<String, Permission> menu; // 추가된 변수

    public UserDto(String id, Long employment_uid, Long department_uid, String password, String name, String email, String phone, String auth, Long used, String token, Map<String, Permission> menu, Department department, Employment employment) {
        this.id = id;

        this.employment_uid = employment_uid;
        this.department_uid = department_uid;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.auth = auth;
        this.used = used;
        this.token = token;
        this.menu = menu;
        this.department = department;
        this.employment = employment;
    }
}
