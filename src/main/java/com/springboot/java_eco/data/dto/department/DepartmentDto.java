package com.springboot.java_eco.data.dto.department;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@ToString

public class DepartmentDto {
    private Long uid;
    private Long company_uid;

    private String name;

    private Long used;
    private String token;

    public DepartmentDto(Long uid,Long company_uid, String name, Long used, String token){
        this.company_uid = company_uid;
        this.uid = uid;
        this.name = name;
        this.used = used;
        this.token = token;

    }

}
