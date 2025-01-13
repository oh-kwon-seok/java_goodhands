package com.springboot.new_java.data.dto;


import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SignInResultDto extends SignUpResultDto{
    private String token;
    private Long company_uid;
    private String name;
    private String email;
    private String phone;
    private String menu;


    @Builder
    public SignInResultDto(boolean success, int code, String msg, String token,Long company_uid, String name, String email, String phone, String menu){
        super(success,code,msg);
        this.token = token;
        this.company_uid = company_uid;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.menu = menu;
    }

}
