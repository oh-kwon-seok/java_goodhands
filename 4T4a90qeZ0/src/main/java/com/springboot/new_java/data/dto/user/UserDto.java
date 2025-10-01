package com.springboot.new_java.data.dto.user;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@Data
@NoArgsConstructor
@ToString
public class UserDto {


    private Long uid;
    private String id;

    private String password;
    private String name;
    private String email;
    private String phone;
    private String auth;
    private String birth_date;
    private Boolean used;
    private String token;
    private LocalDateTime created;


    private Map<String, Permission> menu; // 추가된 변수

    public UserDto(Long uid, String id, String password, String name, String email, String phone, String auth, Boolean used, String token, Map<String, Permission> menu,  LocalDateTime created,String birth_date) {
        this.uid = uid;
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.auth = auth;
        this.used = used;
        this.token = token;
        this.menu = menu;
        this.birth_date = birth_date;
        this.created = created;
    }
}
