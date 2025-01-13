package com.springboot.new_java.data.dto.employment;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@ToString

public class EmploymentDto {
    private Long uid;
    private Long company_uid;

    private String name;
    private String name2;

    private Long used;
    private String token;

    public EmploymentDto(Long uid, Long company_uid,String name, String name2, Long used, String token){
        this.uid = uid;
        this.company_uid = company_uid;
        this.name = name;
        this.name2 = name2;


        this.used = used;
        this.token = token;

    }

}
