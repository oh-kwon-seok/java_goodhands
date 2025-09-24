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
    private String name;
    private String email;
    private String phone;
    private String menu;

    @Builder
    public SignInResultDto(boolean success, int code, String msg, String token, String name, String email, String phone, String menu){
        super(success,code,msg);
        this.token = token;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.menu = menu;
    }

}
