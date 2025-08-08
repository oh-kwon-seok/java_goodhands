package com.springboot.new_java.data.dto.department;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@ToString

public class DepartmentDto {
    private Long uid;
    private String user_id;

    private String name;

    private Boolean used;
    private String token;

    public DepartmentDto(Long uid,String user_id, String name, Boolean used, String token){
        this.user_id = user_id;
        this.uid = uid;
        this.name = name;
        this.used = used;
        this.token = token;

    }

}
