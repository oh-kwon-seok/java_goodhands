package com.springboot.new_java.service;


import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.new_java.common.CommonResponse;
import com.springboot.new_java.config.security.JwtTokenProvider;

import com.springboot.new_java.data.dto.SignInResultDto;
import com.springboot.new_java.data.dto.SignUpResultDto;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;

import com.springboot.new_java.data.dto.user.Permission;
import com.springboot.new_java.data.dto.user.UserDto;
import com.springboot.new_java.data.entity.*;


import com.springboot.new_java.data.repository.history.HistoryRepository;
import com.springboot.new_java.data.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class SignService extends AbstractCacheableSearchService<User, UserDto>{
    private final Logger LOGGER = (Logger)LoggerFactory.getLogger(SignService.class);


    public UserRepository userRepository;

    public HistoryRepository historyRepository;
    public JwtTokenProvider jwtTokenProvider;
    public PasswordEncoder passwordEncoder;


    public SignService(UserRepository userRepository,
                       JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder, HistoryRepository historyRepository, RedisTemplate<String, Object> redisTemplate,
                       ObjectMapper objectMapper){
        super(redisTemplate, objectMapper);
        this.userRepository = userRepository;
        this.historyRepository = historyRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public String getEntityType() {
        return "User";
    }
    @Override
    public List<User> findAllBySearchCondition(CommonInfoSearchDto searchDto) {
        return userRepository.findAll(searchDto);
    }
    @Override
    protected String[] getRelatedEntityTypes() {
        return new String[]{"Department", "Employment"};
    }


    @Transactional
    public SignUpResultDto save(UserDto userDto) throws RuntimeException {
        try {
            SignUpResultDto result = performSave(userDto);

            // 성공한 경우에만 캐시 무효화
            if (result.isSuccess()) {
                invalidateCachesAfterDataChange();
                LOGGER.info("사용자 생성 후 캐시 무효화 완료: {}", userDto.getId());
            }

            return result;
        } catch (Exception e) {
            LOGGER.error("사용자 생성 중 오류 발생: {}", e.getMessage(), e);
            throw e;
        }
    }
    @Transactional
    public SignUpResultDto update(UserDto userDto) throws RuntimeException {
        try {
            SignUpResultDto result = performUpdate(userDto);

            // 성공한 경우에만 캐시 무효화
            if (result.isSuccess()) {
                invalidateCachesAfterDataChange();
                LOGGER.info("사용자 수정 후 캐시 무효화 완료: {}", userDto.getId());
            }

            return result;
        } catch (Exception e) {
            LOGGER.error("사용자 수정 중 오류 발생: {}", e.getMessage(), e);
            throw e;
        }
    }



    @Transactional
    public String delete(List<String> ids) throws Exception {
        try {
            String result = performDelete(ids);

            // 삭제 후 캐시 무효화
            invalidateCachesAfterDataChange();
            LOGGER.info("사용자 삭제 후 캐시 무효화 완료 - 삭제된 사용자 수: {}", ids.size());

            return result;
        } catch (Exception e) {
            LOGGER.error("사용자 삭제 중 오류 발생: {}", e.getMessage(), e);
            throw e;
        }
    }






    public SignUpResultDto performSave(UserDto userDto) throws RuntimeException {

        String id = userDto.getId();
        String name = userDto.getName();
        String password = userDto.getPassword();
        String email = userDto.getEmail();
        String phone = userDto.getPhone();
        String auth = userDto.getAuth();
        Optional<User> selectedUser = userRepository.findByAdminId(userDto.getId());
        Map<String, Permission> menu = userDto.getMenu(); // Permission 데이터 구조

        LOGGER.info("[selectUser] : {}", selectedUser);

        User user;
        SignUpResultDto signUpResultDto = new SignUpResultDto();

        if (selectedUser.isPresent()) {
            setFailResult(signUpResultDto);
            return signUpResultDto;
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            String menuJson = ""; // menu 데이터를 JSON 문자열로 변환
            try {
                menuJson = objectMapper.writeValueAsString(menu);
            } catch (JsonProcessingException e) {
                LOGGER.error("Error serializing menu data: {}", e.getMessage());
                throw new RuntimeException("Failed to serialize menu data", e);
            }

                user = User.builder()
                        .id(id)
                        .name(name)

                        .password(passwordEncoder.encode(password))
                        .email(email)
                        .phone(phone)
                        .auth(auth)
                        .menu(menuJson) // menu 데이터 설정
                        .created(LocalDateTime.now())
                        .used(true)
                        .build();
                userRepository.save(user);

                setSuccessResult(signUpResultDto);
                return signUpResultDto;

        }
    }


    public SignUpResultDto performUpdate(UserDto userDto) throws RuntimeException {

        String id = userDto.getId();
        String name = userDto.getName();
        String password = userDto.getPassword();
        String email = userDto.getEmail();
        String phone = userDto.getPhone();
        String auth = userDto.getAuth();

        Optional<User> selectedUser = userRepository.findByUid(userDto.getUid());

        Map<String, Permission> menu = userDto.getMenu();
        SignUpResultDto signUpResultDto = new SignUpResultDto();

        if (selectedUser.isPresent()) {
            // ✅ 기존 User 엔티티를 가져와서 수정
            User user = selectedUser.get();

            ObjectMapper objectMapper = new ObjectMapper();
            String menuJson = "";
            try {
                menuJson = objectMapper.writeValueAsString(menu);
            } catch (JsonProcessingException e) {
                LOGGER.error("Error serializing menu data: {}", e.getMessage());
                throw new RuntimeException("Failed to serialize menu data", e);
            }

            // ✅ ROLE_ADMIN인 경우만 id와 password 설정
            if ("ROLE_ADMIN".equals(auth)) {
                user.setId(id);
                if (password != null && !password.isEmpty()) {
                    user.setPassword(passwordEncoder.encode(password));
                }
            } else {
                // ROLE_ADMIN이 아니면 id와 password를 null로
                user.setId(null);
                user.setPassword(null);
            }

            // 나머지 필드는 모두 업데이트
            user.setName(name);
            user.setEmail(email);
            user.setPhone(phone);
            user.setAuth(auth);
            user.setMenu(menuJson);
            user.setUpdated(LocalDateTime.now());
            user.setUsed(true);

            userRepository.save(user);

            setSuccessResult(signUpResultDto);
            return signUpResultDto;

        } else {
            setFailResult(signUpResultDto);
            return signUpResultDto;
        }
    }

    public SignInResultDto generalSignIn(String id, String password, String clientIp) throws RuntimeException {
        LOGGER.info("[getSignInResult] 회원 정보 요청 : {}",id);

        // getById 대신 findById 사용
        Optional<User> optionalUser = userRepository.findByAdminId(id);
        LOGGER.info("[optionalUser] 회원 정보 요청 : {}",optionalUser);
        if (!optionalUser.isPresent()) {
            LOGGER.warn("[getSignInResult] 사용자를 찾을 수 없음: {}", id);
            SignInResultDto signInResultDto = new SignInResultDto();
            setFailResult(signInResultDto);
            return signInResultDto;
        }

        User user = optionalUser.get();

        LOGGER.info("[getSignInResult] 패스워드 비교 수행");

        if (!passwordEncoder.matches(password, user.getPassword())) {
            LOGGER.warn("[getSignInResult] 패스워드 불일치");
            SignInResultDto signInResultDto = new SignInResultDto();
            setFailResult(signInResultDto);
            return signInResultDto;
        }

        LOGGER.info("[getSignInResult] 로그인 성공");

        // 로그인 이력 저장
        History history = new History();
        history.setName("로그인");
        history.setIp(clientIp);
        history.setUser(user);
        history.setCreated(LocalDateTime.now());
        historyRepository.save(history);

        // menu를 JSON 문자열로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        String menuAsString;
        try {
            menuAsString = objectMapper.writeValueAsString(user.getMenu());
        } catch (Exception e) {
            throw new RuntimeException("Invalid menu format", e);
        }


        user.setMenu(menuAsString);

        SignInResultDto signInResultDto = SignInResultDto.builder()
                .token(jwtTokenProvider.createToken(
                        String.valueOf(user.getId()),
                        Collections.singletonList(user.getAuth())
                ))
                .menu(user.getMenu())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .auth(user.getAuth())
                .build();

        LOGGER.info("[getSignInResult] SignInResultDto 생성 완료");
        setSuccessResult(signInResultDto);
        return signInResultDto;
    }

    public SignInResultDto signIn(String loginType, String identifier, String password, String clientIp) {
        if ("kakao".equals(loginType)) {
            // 카카오 로그인은 KakaoAuthService에서 처리
            throw new IllegalArgumentException("카카오 로그인은 별도 엔드포인트를 사용하세요");
        } else {
            // 일반 로그인
            return generalSignIn(identifier, password, clientIp);
        }
    }






    public String performDelete(List<String> ids) throws Exception {

        for (String id : ids) {
            Optional<User> selectedUser = userRepository.findByAdminId(id);
            if (selectedUser.isPresent()) {
                User user = selectedUser.get();
                user.setUsed(false);
                user.setDeleted(LocalDateTime.now());
                userRepository.save(user);
            } else {
                throw new Exception("USER with UID " + id + " not found.");
            }
        }
        return "USER deleted successfully";
    }

    private void setSuccessResult(SignUpResultDto result){
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }
    private void setFailResult(SignUpResultDto result){
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
    }

    @Override
    public UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setUid(user.getUid());
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setUsed(user.getUsed());
        dto.setAuth(user.getAuth());
        dto.setCreated(user.getCreated());


        return dto;
    }



}
