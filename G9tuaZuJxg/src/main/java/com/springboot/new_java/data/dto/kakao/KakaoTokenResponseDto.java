package com.springboot.new_java.data.dto.kakao;

import lombok.Data;

@Data
public class KakaoTokenResponseDto {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private String expires_in;
    private String scope;
}