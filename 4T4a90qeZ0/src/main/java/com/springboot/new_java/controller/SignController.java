package com.springboot.new_java.controller;


import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.new_java.common.CommonApiResponse;
import com.springboot.new_java.config.security.JwtTokenProvider;
import com.springboot.new_java.data.dto.SignInResultDto;
import com.springboot.new_java.data.dto.SignUpResultDto;

import com.springboot.new_java.data.dto.user.OnboardingDto;
import com.springboot.new_java.data.dto.user.UserDto;
import com.springboot.new_java.data.dto.user.UserProfileDto;
import com.springboot.new_java.data.entity.User;

import com.springboot.new_java.data.repository.user.UserRepository;
import com.springboot.new_java.service.KakaoAuthService;
import com.springboot.new_java.service.SignService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController


@RequestMapping("/user")
public class SignController extends AbstractSearchController<User, UserDto> {
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(SignController.class);
    private final SignService signService;
    private final KakaoAuthService kakaoAuthService;
    private final UserRepository userRepository;


//    @Autowired
//    public SignController(SignService signService){
//        this.signService = signService;
//    }

    public SignController(SignService signService,
                          KakaoAuthService kakaoAuthService,
                          UserRepository userRepository,
                                RedisTemplate<String, Object> redisTemplate,
                                ObjectMapper objectMapper) {
        super(signService, redisTemplate, objectMapper); // 이 줄이 필수!

        this.signService = signService;
        this.kakaoAuthService = kakaoAuthService;
        this.userRepository =userRepository;
    }

    @Override
    protected String getEntityPath() {
        return "users";
    }



    @PostMapping(value= "/save",consumes = "application/json", produces = "application/json")
    public SignUpResultDto save(@RequestBody UserDto userDto) throws RuntimeException {
        long currentTime = System.currentTimeMillis();

        SignUpResultDto signUpResultDto = signService.save(userDto);

        return signUpResultDto;
    }

    @PostMapping(value= "/update",consumes = "application/json", produces = "application/json")
    public SignUpResultDto update(@RequestBody UserDto userDto) throws RuntimeException {
        long currentTime = System.currentTimeMillis();

        SignUpResultDto signUpResultDto = signService.update(userDto);

        return signUpResultDto;
    }


    @PostMapping(value= "/sign-in", consumes = "application/json", produces = "application/json")
    public SignInResultDto signIn(
            @RequestBody User user, HttpServletRequest request
    ) throws RuntimeException {

        LOGGER.info("[signIn] 로그인을 시도하고 있습니다.id: {}, pw: ****", user.getId());
        LOGGER.info("[IP정보] 로그인을 시도하고 있습니다.IP: {}", request.getRemoteAddr());
        LOGGER.info("[user] 로그인을 시도하고 있습니다. AUTH: {}", user.getAuth());
        SignInResultDto signInResultDto = signService.signIn(user.getAuth(), user.getId(), user.getPassword(),request.getRemoteAddr());
        if(signInResultDto.getCode() == 0){
            LOGGER.info("[signIn] 정상적으로 로그인되었습니다.id: {}, token : {}",user.getId(), signInResultDto.getToken());
        }
        return signInResultDto;
    }




    @PostMapping(value= "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> delete(@RequestBody Map<String, List<String>> requestMap) throws Exception {
        List<String> id = requestMap.get("id");
        signService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }





    @GetMapping(value= "/exception")
    public void exceptionTest() throws RuntimeException {
        throw new RuntimeException("접근이 금지되었습니다.");

    }


    @ExceptionHandler(value= RuntimeException.class)
    public ResponseEntity<Map<String,String>>  ExceptionHandler(RuntimeException e){
        HttpHeaders responseHeaders = new HttpHeaders();
//        responseHeaders.add(HttpHeaders.CONTENT_TYPE, "appication/json");
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        LOGGER.error("ExceptionHandler 호출,{},{}", e.getCause(),e.getMessage());
        Map<String,String> map = new HashMap<>();
        map.put("error type",httpStatus.getReasonPhrase());
        map.put("code", "400");
        map.put("message","에러 발생");

        return new ResponseEntity<>(map,responseHeaders,httpStatus);

    }

    @GetMapping(value = "/profile", produces = "application/json")
    public ResponseEntity<CommonApiResponse<UserProfileDto>> getUserProfile(HttpServletRequest request) {
        long start = System.currentTimeMillis();
        LOGGER.info("[getUserProfile] 사용자 프로필 조회 시작");

        try {
            String token = JwtTokenProvider.resolveToken(request);
            User user = JwtTokenProvider.getUserFromToken(token);

            if (user == null) {
                LOGGER.error("[getUserProfile] 사용자를 찾을 수 없습니다");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                        CommonApiResponse.unauthorized("USER_NOT_FOUND", "사용자를 찾을 수 없습니다")
                );
            }

            // 민감한 정보는 제외하고 반환
            UserProfileDto profileDto = UserProfileDto.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .phone(user.getPhone())
                    .birth_date(user.getBirth_date())
                    .profileCompleted(user.getProfile_completed())
                    .build();

            LOGGER.info("[getUserProfile] 프로필 조회 완료: userId={}, response time: {}ms",
                    user.getId(), System.currentTimeMillis() - start);

            return ResponseEntity.ok(
                    CommonApiResponse.success(profileDto, "프로필 조회가 완료되었습니다")
            );

        } catch (Exception e) {
            LOGGER.error("[getUserProfile] 프로필 조회 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    CommonApiResponse.serverError("프로필 조회 중 오류가 발생했습니다: " + e.getMessage())
            );
        }
    }


    @PostMapping(value = "/onboarding", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommonApiResponse<UserProfileDto>> completeOnboarding(
            @RequestBody OnboardingDto onboardingDto,
            HttpServletRequest request) {

        long start = System.currentTimeMillis();
        LOGGER.info("[completeOnboarding] 온보딩 처리 시작");
        LOGGER.info("[onboardingDto] : {}", onboardingDto);

        try {
            String token = JwtTokenProvider.resolveToken(request);
            User user = JwtTokenProvider.getUserFromToken(token);

            if (user == null) {
                LOGGER.error("[completeOnboarding] 사용자를 찾을 수 없습니다");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                        CommonApiResponse.unauthorized("USER_NOT_FOUND", "사용자를 찾을 수 없습니다")
                );
            }

            // 이미 온보딩 완료된 사용자인지 체크
            if (user.getProfile_completed() != null && user.getProfile_completed()) {
                LOGGER.warn("[completeOnboarding] 이미 온보딩 완료된 사용자: {}", user.getId());

                UserProfileDto profileDto = UserProfileDto.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .phone(user.getPhone())
                        .birth_date(user.getBirth_date())
                        .profileCompleted(true)
                        .build();

                return ResponseEntity.ok(
                        CommonApiResponse.success(profileDto, "이미 온보딩이 완료되었습니다")
                );
            }

            // 유효성 검사
            if (onboardingDto.getName() == null || onboardingDto.getName().trim().length() < 2) {
                return ResponseEntity.badRequest().body(
                        CommonApiResponse.badRequest("INVALID_NAME", "이름을 2글자 이상 입력해주세요")
                );
            }

            if (onboardingDto.getBirthDate() == null || onboardingDto.getBirthDate().isEmpty()) {
                return ResponseEntity.badRequest().body(
                        CommonApiResponse.badRequest("INVALID_BIRTH", "생년월일을 입력해주세요")
                );
            }

            if (onboardingDto.getPhone() == null || !onboardingDto.getPhone().matches("^010\\d{8}$")) {
                return ResponseEntity.badRequest().body(
                        CommonApiResponse.badRequest("INVALID_PHONE", "올바른 전화번호 형식이 아닙니다 (예: 01012345678)")
                );
            }

            // 사용자 정보 업데이트
            user.setName(onboardingDto.getName().trim());
            user.setBirth_date(onboardingDto.getBirthDate());
            user.setPhone(onboardingDto.getPhone());
            user.setProfile_completed(true); // 중요: 온보딩 완료 표시

            User savedUser = userRepository.save(user);

            if (savedUser == null) {
                return ResponseEntity.badRequest().body(
                        CommonApiResponse.error("온보딩 처리에 실패했습니다")
                );
            }

            UserProfileDto profileDto = UserProfileDto.builder()
                    .id(savedUser.getId())
                    .name(savedUser.getName())
                    .email(savedUser.getEmail())
                    .phone(savedUser.getPhone())
                    .birth_date(savedUser.getBirth_date())
                    .profileCompleted(savedUser.getProfile_completed())
                    .build();

            LOGGER.info("[completeOnboarding] 온보딩 완료: userId={}, name={}, response time: {}ms",
                    savedUser.getId(), savedUser.getName(), System.currentTimeMillis() - start);

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    CommonApiResponse.success(profileDto, "온보딩이 성공적으로 완료되었습니다")
            );

        } catch (Exception e) {
            LOGGER.error("[completeOnboarding] 온보딩 처리 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    CommonApiResponse.serverError("온보딩 처리 중 오류가 발생했습니다: " + e.getMessage())
            );
        }
    }

    // Authorization 헤더에서 토큰 추출하는 헬퍼 메서드 추가
    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }



}
