package com.springboot.new_java.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.new_java.common.CommonResponse;
import com.springboot.new_java.config.security.JwtTokenProvider;
import com.springboot.new_java.data.dto.SignInResultDto;
import com.springboot.new_java.data.dto.kakao.KakaoLogoutRequestDto;
import com.springboot.new_java.data.dto.kakao.KakaoUserInfoDto;
import com.springboot.new_java.data.dto.user.OnboardingDto;
import com.springboot.new_java.data.entity.History;
import com.springboot.new_java.data.entity.User;
import com.springboot.new_java.data.repository.history.HistoryRepository;
import com.springboot.new_java.data.repository.user.UserRepository;
import jakarta.transaction.Transactional;
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
                user = existingUser.get();

                // ✅ 탈퇴한 회원인지 체크 (deleted가 null이 아니면 탈퇴)
                if (user.getDeleted() != null) {
                    LOGGER.info("[kakaoSignIn] 탈퇴한 회원 - 복구 필요: kakaoId={}, deletedAt={}",
                            user.getKakaoId(), user.getDeleted());

                    SignInResultDto result = new SignInResultDto();
                    result.setSuccess(false);
                    result.setCode(-4); // 탈퇴 상태 코드
                    result.setMsg("탈퇴한 계정입니다. 계정을 복구하시겠습니까?");
                    result.setKakao_id(user.getKakaoId());
                    result.setName(user.getName());
                    return result;
                }

                LOGGER.info("[kakaoSignIn] 기존 카카오 회원 로그인: {}", user.getName());
            } else {
                // 신규 회원 자동 가입
                user = performKakaoSignUp(kakaoUserInfo);
                LOGGER.info("[kakaoSignIn] 카카오 신규 회원 가입 완료 - 온보딩으로 이동");

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

            // 5. 정상 로그인
            LOGGER.info("[kakaoSignIn] 로그인 성공: userId={}, auth={}", user.getId(), user.getAuth());

            // 6. 로그인 이력 저장
            History history = new History();
            history.setName("카카오 로그인");
            history.setIp(clientIp);
            history.setUser(user);
            history.setCreated(LocalDateTime.now());
            historyRepository.save(history);

            // 7. JWT 토큰 생성 및 응답
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
        User kakaoUser = User.builder()
                .id(null)
                .kakaoId(kakaoUserInfo.getId())
                .name(null)
                .email(kakaoUserInfo.getEmail())
                .phone(null)
                .password(null)
                .auth("WAIT")
                .birth_date("")
                .profile_completed(false)
                .deleted(null)  // ✅ 신규 가입 시 deleted = null (정상 상태)
                .created(LocalDateTime.now())
                .used(true)
                .build();

        return userRepository.save(kakaoUser);
    }


    /**
     * 회원 복구 처리
     */
    @Transactional
    public Map<String, Object> restoreAccount(Long kakaoId, String clientIp) {
        try {
            LOGGER.info("[restoreAccount] 회원 복구 시작 - kakaoId: {}", kakaoId);

            if (kakaoId == null) {
                return Map.of("success", false, "message", "카카오 ID가 제공되지 않았습니다.");
            }

            Optional<User> userOptional = userRepository.findByKakaoId(kakaoId);
            if (!userOptional.isPresent()) {
                return Map.of("success", false, "message", "사용자를 찾을 수 없습니다.");
            }

            User user = userOptional.get();

            if (user.getDeleted() == null) {
                return Map.of("success", false, "message", "이미 활성화된 계정입니다.");
            }

            user.setDeleted(null);
            user.setUsed(true);
            user.setUpdated(LocalDateTime.now());

            userRepository.save(user);

            History history = new History();
            history.setName("계정 복구");
            history.setIp(clientIp);
            history.setUser(user);
            history.setCreated(LocalDateTime.now());
            historyRepository.save(history);

            LOGGER.info("[restoreAccount] 회원 복구 완료 - userId: {}, name: {}",
                    user.getId(), user.getName());

            return Map.of(
                    "success", true,
                    "message", "계정이 복구되었습니다."
            );

        } catch (Exception e) {
            LOGGER.error("[restoreAccount] 회원 복구 실패: {}", e.getMessage(), e);
            return Map.of("success", false, "message", "계정 복구 중 오류가 발생했습니다.");
        }
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
                .kakao_id(user.getKakaoId())
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


    @Transactional
    public Map<String, Object> logout(String token, Long kakaoId, String clientIp) {
        try {
            LOGGER.info("[logout] 로그아웃 시작 - kakaoId: {}", kakaoId);

            if (token == null || token.isEmpty()) {
                LOGGER.warn("[logout] 토큰이 제공되지 않음");
            }

            User user = null;
            if (kakaoId != null) {
                Optional<User> userOptional = userRepository.findByKakaoId(kakaoId);
                if (userOptional.isPresent()) {
                    user = userOptional.get();
                }
            }

            if (user != null) {
                History history = new History();
                history.setName("카카오 로그아웃");
                history.setIp(clientIp);
                history.setUser(user);
                history.setCreated(LocalDateTime.now());
                historyRepository.save(history);

                LOGGER.info("[logout] 로그아웃 완료 - userId: {}, name: {}", user.getId(), user.getName());
            } else {
                LOGGER.info("[logout] 로그아웃 완료 - 사용자 정보 없음 (kakaoId: {})", kakaoId);
            }

            return Map.of(
                    "success", true,
                    "message", "로그아웃되었습니다."
            );

        } catch (Exception e) {
            LOGGER.error("[logout] 로그아웃 실패: {}", e.getMessage(), e);
            return Map.of(
                    "success", false,
                    "message", "로그아웃 처리 중 오류가 발생했습니다."
            );
        }
    }

    /**
     * 카카오 연결 끊기 (회원 탈퇴)
     */
    @Transactional
    public Map<String, Object> unlinkKakao(String token, Long kakaoId, String clientIp) {
        try {
            LOGGER.info("[unlinkKakao] 회원 탈퇴 시작 - kakaoId: {}", kakaoId);

            if (token == null || token.isEmpty()) {
                return Map.of("success", false, "message", "인증 토큰이 없습니다.");
            }

            if (kakaoId == null) {
                return Map.of("success", false, "message", "카카오 ID가 제공되지 않았습니다.");
            }

            Optional<User> userOptional = userRepository.findByKakaoId(kakaoId);
            if (!userOptional.isPresent()) {
                return Map.of("success", false, "message", "사용자를 찾을 수 없습니다.");
            }

            User user = userOptional.get();

            if (user.getDeleted() != null) {
                return Map.of("success", false, "message", "이미 탈퇴한 계정입니다.");
            }

            History history = new History();
            history.setName("회원 탈퇴");
            history.setIp(clientIp);
            history.setUser(user);
            history.setCreated(LocalDateTime.now());
            historyRepository.save(history);

            user.setDeleted(LocalDateTime.now());
            user.setUsed(false);
            user.setUpdated(LocalDateTime.now());

            userRepository.save(user);

            LOGGER.info("[unlinkKakao] 회원 탈퇴 완료 - userId: {}, deletedAt: {}",
                    user.getId(), user.getDeleted());

            return Map.of(
                    "success", true,
                    "message", "회원탈퇴가 완료되었습니다. 언제든지 재로그인하여 복구할 수 있습니다."
            );

        } catch (Exception e) {
            LOGGER.error("[unlinkKakao] 회원 탈퇴 실패: {}", e.getMessage(), e);
            return Map.of("success", false, "message", "회원탈퇴 처리 중 오류가 발생했습니다.");
        }
    }


    /**
     * 카카오 계정 연결 끊기 API 호출 (선택사항)
     */
    private void unlinkKakaoAccount(String accessToken) {
        try {
            String url = "https://kapi.kakao.com/v1/user/unlink";

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + accessToken);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            LOGGER.info("[unlinkKakaoAccount] 카카오 API 연결 끊기 완료");

        } catch (Exception e) {
            LOGGER.error("[unlinkKakaoAccount] 카카오 API 연결 끊기 실패: {}", e.getMessage(), e);
            throw new RuntimeException("카카오 연결 끊기 실패", e);
        }
    }
    /**
     * 결과 설정 메서드들 - 올바른 타입 사용
     */
    private void setSuccessResult(SignInResultDto result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }

    private void setFailResult(SignInResultDto result) {
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
    }
}