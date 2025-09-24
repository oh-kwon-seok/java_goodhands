package com.springboot.new_java.data.dto.employment;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@ToString

public class EmploymentDto {
    private Long uid;
    private String user_id;

    private String name;
    private String name2;

    private Boolean used;
    private String token;

    public EmploymentDto(Long uid, String user_id,String name, String name2, Boolean used, String token){
        this.uid = uid;
        this.user_id = user_id;
        this.name = name;
        this.name2 = name2;
        this.used = used;
        this.token = token;

    }

}
