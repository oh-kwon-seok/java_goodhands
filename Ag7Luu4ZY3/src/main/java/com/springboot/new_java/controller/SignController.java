package com.springboot.new_java.controller;


import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.new_java.data.dto.SignInResultDto;
import com.springboot.new_java.data.dto.SignUpResultDto;

import com.springboot.new_java.data.dto.user.OnboardingDto;
import com.springboot.new_java.data.dto.user.UserDto;
import com.springboot.new_java.data.entity.User;

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


//    @Autowired
//    public SignController(SignService signService){
//        this.signService = signService;
//    }

    public SignController(SignService signService,
                          KakaoAuthService kakaoAuthService,

                                RedisTemplate<String, Object> redisTemplate,
                                ObjectMapper objectMapper) {
        super(signService, redisTemplate, objectMapper); // 이 줄이 필수!

        this.signService = signService;
        this.kakaoAuthService = kakaoAuthService;
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

    @PostMapping("/onboarding")
    public ResponseEntity<Map<String, Object>> completeOnboarding(@RequestBody OnboardingDto onboardingDto,
                                                                  HttpServletRequest request) {
        try {
            // Authorization 헤더에서 토큰 추출
            String token = extractTokenFromRequest(request);

            if (token == null || token.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("success", false, "message", "토큰이 필요합니다."));
            }

            // 서비스 호출
            Map<String, Object> result = kakaoAuthService.completeOnboarding(token, onboardingDto);

            Boolean success = (Boolean) result.get("success");
            if (success) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.badRequest().body(result);
            }

        } catch (Exception e) {
            LOGGER.error("온보딩 컨트롤러 에러: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "온보딩 처리 실패: " + e.getMessage()));
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
