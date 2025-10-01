package com.springboot.new_java.data.dto.kakao;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KakaoUserInfoDto {
    private Long id;
    private String nickname;
    private String email;
    private String profileImage;
}