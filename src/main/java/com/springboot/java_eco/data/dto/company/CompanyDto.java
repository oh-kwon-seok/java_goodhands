package com.springboot.java_eco.data.dto.company;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@ToString

public class CompanyDto {
    private Long uid;
    private String code;
    private String name;
    private String owner_name;
    private String owner_phone;
    private String emp_name;
    private String emp_phone;
    private String fax;

    private String email;
    private String type;

    private Long used;
    private String token;

    public CompanyDto(Long uid,String code,String name,String owner_name,String owner_phone,String emp_name,String emp_phone, String fax,  String email,String type, Long used, String token){
        this.uid = uid;
        this.code = code;
        this.name = name;
        this.owner_name = owner_name;
        this.owner_phone = owner_phone;
        this.emp_name = emp_name;
        this.emp_phone = emp_phone;
        this.fax = fax;

        this.email = email;
        this.type = type;
        this.used = used;
        this.token = token;

    }

}
