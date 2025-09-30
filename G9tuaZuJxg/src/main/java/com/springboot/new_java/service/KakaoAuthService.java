package com.springboot.new_java.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.new_java.common.CommonResponse;
import com.springboot.new_java.config.security.JwtTokenProvider;
import com.springboot.new_java.data.dto.SignInResultDto;

import com.springboot.new_java.data.dto.SignUpResultDto;
import com.springboot.new_java.data.dto.kakao.KakaoTokenResponseDto;
import com.springboot.new_java.data.dto.kakao.KakaoUserInfoDto;

import com.springboot.new_java.data.dto.user.OnboardingDto;
import com.springboot.new_java.data.entity.History;
import com.springboot.new_java.data.entity.User;
import com.springboot.new_java.data.repository.history.HistoryRepository;
import com.springboot.new_java.data.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class KakaoAuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KakaoAuthService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String kakaoClientSecret;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUri;

    /**
     * 카카오 인가 코드로 액세스 토큰 발급
     */
    private String getKakaoAccessToken(String code) {
        LOGGER.info("[getKakaoAccessToken] 카카오 액세스 토큰 발급 시작");

        try {
            String tokenUrl = "https://kauth.kakao.com/oauth/token";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "authorization_code");
            params.add("client_id", kakaoClientId);
            params.add("redirect_uri", kakaoRedirectUri);
            params.add("code", code);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, request, String.class);

            // JSON 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            String accessToken = jsonNode.get("access_token").asText();

            LOGGER.info("[getKakaoAccessToken] 액세스 토큰 발급 완료");
            return accessToken;

        } catch (HttpClientErrorException.TooManyRequests e) {
            LOGGER.error("[getKakaoAccessToken] 카카오 API rate limit 초과: {}", e.getMessage());
            throw new RuntimeException("RATE_LIMIT_EXCEEDED");

        } catch (HttpClientErrorException e) {
            LOGGER.error("[getKakaoAccessToken] 카카오 토큰 발급 HTTP 에러: status={}, body={}",
                    e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("KAKAO_TOKEN_ERROR");

        } catch (Exception e) {
            LOGGER.error("[getKakaoAccessToken] 카카오 토큰 발급 실패: {}", e.getMessage(), e);
            throw new RuntimeException("TOKEN_REQUEST_FAILED");
        }
    }

    /**
     * 액세스 토큰으로 카카오 사용자 정보 조회
     */
    public KakaoUserInfoDto getKakaoUserInfo(String accessToken) {
        String requestURL = "https://kapi.kakao.com/v2/user/me";

        try {
            RestTemplate restTemplate = new RestTemplate();

            // Header 설정
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<String> request = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    requestURL, HttpMethod.POST, request, String.class);

            // JSON 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            LOGGER.info("카카오 API 응답: {}", response.getBody()); // 디버깅용 로그

            // 안전한 데이터 추출
            Long kakaoId = jsonNode.has("id") ? jsonNode.get("id").asLong() : null;

            String nickname = "카카오 사용자"; // 기본값
            String profileImage = null;
            if (jsonNode.has("properties") && jsonNode.get("properties") != null) {
                JsonNode properties = jsonNode.get("properties");
                if (properties.has("nickname") && properties.get("nickname") != null) {
                    nickname = properties.get("nickname").asText();
                }
                if (properties.has("profile_image") && properties.get("profile_image") != null) {
                    profileImage = properties.get("profile_image").asText();
                }
            }

            String email = null; // 기본값을 null로 설정
            if (jsonNode.has("kakao_account") && jsonNode.get("kakao_account") != null) {
                JsonNode kakaoAccount = jsonNode.get("kakao_account");
                if (kakaoAccount.has("email") && kakaoAccount.get("email") != null) {
                    email = kakaoAccount.get("email").asText();
                }
            }

            return KakaoUserInfoDto.builder()
                    .id(kakaoId)
                    .nickname(nickname)
                    .email(email)
                    .profileImage(profileImage)
                    .build();

        } catch (Exception e) {
            LOGGER.error("카카오 사용자 정보 조회 실패: {}", e.getMessage(), e);
            throw new RuntimeException("카카오 사용자 정보 조회 실패", e);
        }
    }

    /**
     * 카카오 로그인 처리 (로그인 + 자동 회원가입)
     */
    public SignInResultDto kakaoSignIn(String code, String clientIp) throws RuntimeException {
        try {
            LOGGER.info("[kakaoSignIn] 카카오 로그인 시작");

            // 1. 인가 코드로 액세스 토큰 발급
            String accessToken = getKakaoAccessToken(code);
            LOGGER.info("[kakaoSignIn] 액세스 토큰 발급 완료");

            // 2. 액세스 토큰으로 사용자 정보 조회
            KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);
            LOGGER.info("[kakaoSignIn] 카카오 사용자 정보: {}", kakaoUserInfo.getId());

            // 3. 기존 회원 확인
            Optional<User> existingUser = userRepository.findByKakaoId(kakaoUserInfo.getId());

            User user;

            if (existingUser.isPresent()) {
                // 기존 회원 로그인
                user = existingUser.get();
                LOGGER.info("[kakaoSignIn] 기존 카카오 회원 로그인: {}", user.getName());
            } else {
                // 신규 회원 자동 가입
                user = performKakaoSignUp(kakaoUserInfo);
                LOGGER.info("[kakaoSignIn] 카카오 신규 회원 가입 완료 - 온보딩으로 이동");

                // 신규 회원은 무조건 온보딩으로 이동
                SignInResultDto onboardingResult = new SignInResultDto();
                onboardingResult.setSuccess(true);
                onboardingResult.setToken(jwtTokenProvider.createToken(
                        String.valueOf(user.getKakaoId()),
                        "WAIT"
                ));
                onboardingResult.setProfile_completed(false);
                onboardingResult.setCode(0);
                onboardingResult.setMsg("온보딩이 필요합니다");
                return onboardingResult;
            }

            // 4. 기존 회원 - 온보딩 완료 여부 체크
            if (user.getProfile_completed() == null || !user.getProfile_completed()) {
                LOGGER.info("[kakaoSignIn] 온보딩 미완료 사용자: userId={}", user.getId());

                SignInResultDto onboardingResult = new SignInResultDto();
                onboardingResult.setSuccess(true);
                onboardingResult.setToken(jwtTokenProvider.createToken(
                        String.valueOf(user.getKakaoId()),
                        user.getAuth() != null ? user.getAuth() : "WAIT"
                ));
                onboardingResult.setProfile_completed(false);
                onboardingResult.setCode(0);
                onboardingResult.setMsg("온보딩이 필요합니다");
                return onboardingResult;
            }

            // 5. 온보딩 완료된 기존 회원 - 승인 여부 관계없이 로그인 성공
            // 승인 대기 중이어도 로그인은 허용하고, 대시보드에서 안내
            LOGGER.info("[kakaoSignIn] 로그인 성공: userId={}, auth={}", user.getId(), user.getAuth());

            // 6. 로그인 이력 저장
            History history = new History();
            history.setName("카카오 로그인");
            history.setIp(clientIp);
            history.setUser(user);
            history.setCreated(LocalDateTime.now());
            historyRepository.save(history);

            // 7. JWT 토큰 생성 및 응답 (auth가 WAIT여도 성공 처리)
            return createSuccessSignInResult(user);

        } catch (Exception e) {
            LOGGER.error("[kakaoSignIn] 카카오 로그인 실패: {}", e.getMessage(), e);
            SignInResultDto failResult = new SignInResultDto();
            setFailResult(failResult);
            return failResult;
        }
    }

    /**
     * 카카오 신규 회원 가입 처리
     */
    private User performKakaoSignUp(KakaoUserInfoDto kakaoUserInfo) {
        // 카카오 사용자 기본 메뉴 설정


        User kakaoUser = User.builder()
                .id(null)
                .kakaoId(kakaoUserInfo.getId())

                .name(null)  // 온보딩에서 받을 예정
                .email(kakaoUserInfo.getEmail())
                .phone(null)  // 온보딩에서 받을 예정
                .password(null)
                .auth("WAIT")
                .birth_date("")
                .profile_completed(false)
                .created(LocalDateTime.now())
                .used(true)
                .build();

        return userRepository.save(kakaoUser);
    }


    /**
     * 로그인 성공 결과 생성
     */
    private SignInResultDto createSuccessSignInResult(User user) {
        String tokenSubject = user.getKakaoId() != null ?
                String.valueOf(user.getKakaoId()) : user.getId();

        // 프로필 완성 여부에 따라 리다이렉트 결정
        String redirectPath = user.getProfile_completed() ? "/client/care" : "/client/login/auth/onboarding";

        SignInResultDto signInResultDto = SignInResultDto.builder()
                .token(jwtTokenProvider.createToken(tokenSubject, Collections.singletonList(user.getAuth())))
                .menu(user.getMenu())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .auth(user.getAuth())
                .profile_completed(user.getProfile_completed())
                .redirectPath(redirectPath)  // 리다이렉트 경로 추가
                .build();

        setSuccessResult(signInResultDto);
        return signInResultDto;
    }

    @Transactional
    public Map<String, Object> completeOnboarding(String token, OnboardingDto onboardingDto) throws RuntimeException {
        try {
            LOGGER.info("[completeOnboarding] 온보딩 처리 시작");

            // JWT에서 사용자 확인
            User user = jwtTokenProvider.getUserFromToken(token);

            if (user == null) {
                LOGGER.error("[completeOnboarding] 유효하지 않은 토큰");
                return Map.of("success", false, "message", "유효하지 않은 토큰입니다.");
            }

            if (user.getProfile_completed() != null && user.getProfile_completed()) {
                LOGGER.error("[completeOnboarding] 이미 완료된 프로필");
                return Map.of("success", false, "message", "이미 완료된 프로필입니다.");
            }

            // 전화번호 중복 체크
            if (userRepository.existsByPhone(onboardingDto.getPhone())) {
                LOGGER.error("[completeOnboarding] 중복된 전화번호: {}", onboardingDto.getPhone());
                return Map.of("success", false, "message", "이미 등록된 전화번호입니다.");
            }

            // 사용자 정보 업데이트
            user.setName(onboardingDto.getName());
            user.setBirth_date(onboardingDto.getBirthDate());
            user.setPhone(onboardingDto.getPhone());
            user.setProfile_completed(true);
            user.setAuth("WAIT");
            user.setUpdated(LocalDateTime.now());

            userRepository.save(user);



            LOGGER.info("[completeOnboarding] 온보딩 완료: {}", user.getName());

            return Map.of("success", true, "message", "온보딩이 완료되었습니다.");

        } catch (Exception e) {
            LOGGER.error("[completeOnboarding] 온보딩 처리 실패: {}", e.getMessage(), e);
            return Map.of("success", false, "message", "온보딩 처리 중 오류가 발생했습니다.");
        }
    }




    /**
     * 결과 설정 메서드들 - 올바른 타입 사용
     */
    private void setSuccessResult(SignInResultDto result) {  // SignUpResultDto -> SignInResultDto
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }

    private void setFailResult(SignInResultDto result) {  // SignUpResultDto -> SignInResultDto
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
    }
}