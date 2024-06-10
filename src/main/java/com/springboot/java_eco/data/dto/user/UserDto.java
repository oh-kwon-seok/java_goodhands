package com.springboot.java_eco.data.dto.user;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter

@Data
@NoArgsConstructor
@ToString

public class UserDto {

    private String id;

    private Long company_uid;
    private Long employment_uid;
    private Long department_uid;

    private String password;
    private String name;
    private String email;
    private String phone;

    private String auth;
    private Long used;
    private String token;

    public UserDto( String id, Long company_uid, Long employment_uid, Long department_uid, String password,String name,String email,String phone, String auth, Long used, String token){
        this.id = id;
        this.company_uid = company_uid;
        this.employment_uid = employment_uid;
        this.department_uid = department_uid;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.auth = auth;
        this.used = used;
        this.token = token;

    }

}
