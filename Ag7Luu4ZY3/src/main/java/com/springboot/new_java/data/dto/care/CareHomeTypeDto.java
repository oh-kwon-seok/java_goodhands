package com.springboot.new_java.data.dto.care;

import com.springboot.new_java.data.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@NoArgsConstructor
@ToString
public class CareHomeTypeDto {

    private Long uid;
    private String user_id;
    private String name;
    private User user;
    private Boolean used;
    private String token;
    private LocalDateTime created;


    public CareHomeTypeDto(Long uid, String user_id, User user, String name, Boolean used, LocalDateTime created, String token) {
        this.user_id = user_id;
        this.uid = uid;
        this.name = name;
        this.user = user;
        this.used = used;
        this.created = created;
        this.token = token;
    }
}
