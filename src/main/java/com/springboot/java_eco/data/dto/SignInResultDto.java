package com.springboot.java_eco.data.dto;


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

    @Builder
    public SignInResultDto(boolean success, int code, String msg, String token,Long company_uid){
        super(success,code,msg);
        this.token = token;
        this.company_uid = company_uid;
    }

}
