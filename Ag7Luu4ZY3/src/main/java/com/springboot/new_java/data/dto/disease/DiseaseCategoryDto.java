package com.springboot.new_java.data.dto.disease;

import com.springboot.new_java.data.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@NoArgsConstructor
@ToString
public class DiseaseCategoryDto {

    private Long uid;
    private String name;
    private String code;
    private String description;
    private String user_id;
    private Boolean used;
    private String token;
    private LocalDateTime created;
    private User user;


    public DiseaseCategoryDto(Long uid, String name, String code, String description,  String user_id, User user,LocalDateTime created, Boolean used,   String token) {
        this.user_id = user_id;
        this.code = code;
        this.user = user;
        this.description = description;
         this.used = used;
        this.uid = uid;
        this.name = name;
        this.created = created;
        this.token = token;
    }
}
