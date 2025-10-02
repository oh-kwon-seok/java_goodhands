package com.springboot.new_java.controller;

import com.springboot.new_java.data.dto.SignInResultDto;

import com.springboot.new_java.data.dto.kakao.KakaoLogoutRequestDto;
import com.springboot.new_java.service.KakaoAuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class KakaoAuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(KakaoAuthController.class);

    @Autowired
    private KakaoAuthService kakaoAuthService;

    @Value("${kakao.frontend.success-redirect-url}")
    private String frontendSuccessUrl;

    @Value("${kakao.frontend.error-redirect-url}")
    private String frontendErrorUrl;

    /**
     * 카카오 로그인 콜백 처리
     */
    @GetMapping("/kakao/callback")
    public ResponseEntity<?> kakaoCallback(@RequestParam String code, HttpServletRequest request) {
        String clientIp = getClientIp(request);
        SignInResultDto result = kakaoAuthService.kakaoSignIn(code, clientIp);

        if (result.isSuccess()) {
            String redirectUrl;

            if (result.getProfile_completed() != null && !result.getProfile_completed()) {
                // 신규 사용자 또는 온보딩 미완료 - 온보딩 페이지로
                redirectUrl = frontendErrorUrl + "?token=" + result.getToken() + "&onboarding=true";
            } else {
                // 기존 사용자 (온보딩 완료) - 대시보드로
                StringBuilder urlBuilder = new StringBuilder(frontendSuccessUrl);
                urlBuilder.append("?token=").append(result.getToken());

                if (result.getName() != null && !result.getName().isEmpty()) {
                    urlBuilder.append("&name=").append(URLEncoder.encode(result.getName(), StandardCharsets.UTF_8));
                }
                if (result.getKakao_id() != null ) {
                    urlBuilder.append("&kakao_id=").append(result.getKakao_id());
                }
                if (result.getAuth() != null && !result.getAuth().isEmpty()) {
                    urlBuilder.append("&auth=").append(result.getAuth());
                }

                redirectUrl = urlBuilder.toString();
            }

            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(redirectUrl))
                    .build();

        } else {
            // 탈퇴한 계정 처리 (code = -4)
            if (result.getCode() != null && result.getCode() == -4) {
                LOGGER.info("[kakaoCallback] 탈퇴한 계정 - 로그인 페이지로 리다이렉트: kakaoId={}", result.getKakao_id());

                // frontendSuccessUrl에서 로그인 페이지 URL 생성
                String loginPageUrl = frontendSuccessUrl.replace("/client/care", "/client/login");

                StringBuilder urlBuilder = new StringBuilder(loginPageUrl);
                urlBuilder.append("?withdrawn_account=true");

                if (result.getKakao_id() != null) {
                    urlBuilder.append("&kakao_id=").append(result.getKakao_id());
                }
                if (result.getName() != null && !result.getName().isEmpty()) {
                    urlBuilder.append("&name=").append(URLEncoder.encode(result.getName(), StandardCharsets.UTF_8));
                }

                String redirectUrl = urlBuilder.toString();
                LOGGER.info("[kakaoCallback] 리다이렉트 URL: {}", redirectUrl);

                return ResponseEntity.status(HttpStatus.FOUND)
                        .location(URI.create(redirectUrl))
                        .build();
            }

            // 기타 실패 처리
            String errorType = (result.getCode() != null && result.getCode() == -2)
                    ? "not_approved"
                    : "kakao_login_failed";

            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(frontendErrorUrl + "?error=" + errorType))
                    .build();
        }
    }

    /**
     * 로그아웃 처리
     */
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(
            @RequestHeader(value = "X-AUTH-TOKEN", required = false) String token,
            @RequestBody KakaoLogoutRequestDto logoutRequest,
            HttpServletRequest request) {

        try {
            String clientIp = getClientIp(request);

            Map<String, Object> logoutResult = kakaoAuthService.logout(
                    token,
                    logoutRequest.getKakaoId(),
                    clientIp
            );

            return ResponseEntity.ok(logoutResult);

        } catch (Exception e) {
            LOGGER.error("[logout] 로그아웃 처리 중 오류: {}", e.getMessage(), e);
            Map<String, Object> response = Map.of(
                    "success", false,
                    "message", "로그아웃 처리 중 오류가 발생했습니다: " + e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 카카오 연결 끊기 (회원 탈퇴)
     */
    @PostMapping("/kakao/unlink")
    public ResponseEntity<Map<String, Object>> kakaoUnlink(
            @RequestHeader(value = "X-AUTH-TOKEN", required = false) String token,
            @RequestBody KakaoLogoutRequestDto unlinkRequest,
            HttpServletRequest request) {

        try {
            String clientIp = getClientIp(request);

            if (token == null || token.isEmpty()) {
                Map<String, Object> response = Map.of(
                        "success", false,
                        "message", "인증 토큰이 없습니다."
                );
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            if (unlinkRequest.getKakaoId() == null) {
                Map<String, Object> response = Map.of(
                        "success", false,
                        "message", "카카오 ID가 제공되지 않았습니다."
                );
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Map<String, Object> unlinkResult = kakaoAuthService.unlinkKakao(
                    token,
                    unlinkRequest.getKakaoId(),
                    clientIp
            );

            if ((boolean) unlinkResult.get("success")) {
                return ResponseEntity.ok(unlinkResult);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(unlinkResult);
            }

        } catch (Exception e) {
            LOGGER.error("[kakaoUnlink] 회원탈퇴 처리 중 오류: {}", e.getMessage(), e);
            Map<String, Object> response = Map.of(
                    "success", false,
                    "message", "회원탈퇴 처리 중 오류가 발생했습니다: " + e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 계정 복구
     */
    @PostMapping("/kakao/restore")
    public ResponseEntity<Map<String, Object>> restoreAccount(
            @RequestBody KakaoLogoutRequestDto restoreRequest,
            HttpServletRequest request) {

        try {
            LOGGER.info("[restoreAccount] 요청 받음 - kakaoId: {}", restoreRequest.getKakaoId());

            String clientIp = getClientIp(request);

            Map<String, Object> restoreResult = kakaoAuthService.restoreAccount(
                    restoreRequest.getKakaoId(),
                    clientIp
            );

            LOGGER.info("[restoreAccount] 결과: {}", restoreResult);

            if ((boolean) restoreResult.get("success")) {
                return ResponseEntity.ok(restoreResult);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restoreResult);
            }

        } catch (Exception e) {
            LOGGER.error("[restoreAccount] 계정 복구 중 오류: {}", e.getMessage(), e);
            Map<String, Object> response = Map.of(
                    "success", false,
                    "message", "계정 복구 중 오류가 발생했습니다."
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * API 테스트용 - JSON 응답만 반환
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