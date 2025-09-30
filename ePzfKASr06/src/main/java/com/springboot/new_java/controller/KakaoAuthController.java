package com.springboot.new_java.controller;

import com.springboot.new_java.data.dto.SignInResultDto;
import com.springboot.new_java.service.KakaoAuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/auth")
public class KakaoAuthController {

    @Autowired
    private KakaoAuthService kakaoAuthService;

    @Value("${kakao.frontend.success-redirect-url}")
    private String frontendSuccessUrl;

    @Value("${kakao.frontend.error-redirect-url}")
    private String frontendErrorUrl;

    /**
     * 카카오 로그인 콜백 처리 - 승인 체크 추가
     */
    @GetMapping("/kakao/callback")
    public ResponseEntity<?> kakaoCallback(@RequestParam String code, HttpServletRequest request) {
        String clientIp = getClientIp(request);
        SignInResultDto result = kakaoAuthService.kakaoSignIn(code, clientIp);

        if (result.isSuccess()) {
            String redirectUrl;

            if (result.getProfile_completed() != null && !result.getProfile_completed()) {
                // 신규 사용자 - 온보딩 필요
                redirectUrl = frontendErrorUrl + "?onboarding=true&token=" + result.getToken();
            } else {
                // 기존 사용자 - 대시보드로
                redirectUrl = frontendSuccessUrl + "?token=" + result.getToken() +
                        "&name=" + URLEncoder.encode(result.getName(), StandardCharsets.UTF_8);
            }

            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(redirectUrl))
                    .build();
        } else {
            // 실패 처리
            String errorType;

            // code -2는 승인 거부
            if (result.getCode() != null && result.getCode() == -2) {
                errorType = "not_approved";
            } else {
                errorType = "kakao_login_failed";
            }

            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(frontendErrorUrl + "?error=" + errorType))
                    .build();
        }
    }

    /**
     * API 테스트용 - JSON 응답만 반환 (기존 로직)
     */
    @GetMapping("/kakao/callback/json")
    public ResponseEntity<SignInResultDto> kakaoCallbackJson(
            @RequestParam String code,
            HttpServletRequest request) {

        String clientIp = getClientIp(request);
        SignInResultDto result = kakaoAuthService.kakaoSignIn(code, clientIp);

        return ResponseEntity.ok(result);
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}