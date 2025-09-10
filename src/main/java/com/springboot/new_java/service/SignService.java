package com.springboot.new_java.service;


import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.new_java.common.CommonResponse;
import com.springboot.new_java.config.security.JwtTokenProvider;

import com.springboot.new_java.data.dto.SignInResultDto;
import com.springboot.new_java.data.dto.SignUpResultDto;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.department.DepartmentDto;
import com.springboot.new_java.data.dto.user.Permission;
import com.springboot.new_java.data.dto.user.UserDto;
import com.springboot.new_java.data.entity.*;

import com.springboot.new_java.data.repository.department.DepartmentRepository;
import com.springboot.new_java.data.repository.employment.EmploymentRepository;
import com.springboot.new_java.data.repository.history.HistoryRepository;
import com.springboot.new_java.data.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service

public class SignService extends AbstractCacheableSearchService<User, UserDto>{
    private final Logger LOGGER = (Logger)LoggerFactory.getLogger(SignService.class);


    public UserRepository userRepository;
    public EmploymentRepository employmentRepository;
    public DepartmentRepository departmentRepository;


    public HistoryRepository historyRepository;
    public JwtTokenProvider jwtTokenProvider;
    public PasswordEncoder passwordEncoder;


    public SignService(UserRepository userRepository, EmploymentRepository employmentRepository, DepartmentRepository departmentRepository,
                       JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder, HistoryRepository historyRepository, RedisTemplate<String, Object> redisTemplate,
                       ObjectMapper objectMapper){
        super(redisTemplate, objectMapper);
        this.userRepository = userRepository;

        this.employmentRepository = employmentRepository;
        this.departmentRepository = departmentRepository;

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
    public String excelUploadUser(List<Map<String, Object>> requestList) throws Exception {
        try {
            String result = performExcelUploadUser(requestList);

            // 대량 처리 후 캐시 무효화
            invalidateCachesAfterDataChange();
            LOGGER.info("Excel 업로드 후 캐시 무효화 완료 - 처리된 사용자 수: {}", requestList.size());

            return result;
        } catch (Exception e) {
            LOGGER.error("Excel 업로드 중 오류 발생: {}", e.getMessage(), e);
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


        Employment employment = employmentRepository.findByUid(userDto.getEmployment_uid());
        Department department = departmentRepository.findByUid(userDto.getDepartment_uid());
        String id = userDto.getId();
        String name = userDto.getName();
        String password = userDto.getPassword();
        String email = userDto.getEmail();
        String phone = userDto.getPhone();
        String auth = userDto.getAuth();
        Optional<User> selectedUser = userRepository.findById(userDto.getId());
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

            if (auth.equalsIgnoreCase("admin")) {
                user = User.builder()
                        .id(id)
                        .name(name)
                        .employment(employment)
                        .department(department)
                        .password(passwordEncoder.encode(password))
                        .email(email)
                        .phone(phone)
                        .auth(Collections.singletonList("ROLE_ADMIN"))
                        .menu(menuJson) // menu 데이터 설정
                        .created(LocalDateTime.now())
                        .used(true)
                        .build();
                userRepository.save(user);

                setSuccessResult(signUpResultDto);
                return signUpResultDto;

            } else if (auth.equalsIgnoreCase("user")) {
                user = User.builder()
                        .id(id)
                        .name(name)

                        .employment(employment)
                        .department(department)
                        .password(passwordEncoder.encode(password))
                        .email(email)
                        .phone(phone)
                        .auth(Collections.singletonList("ROLE_USER"))
                        .menu(menuJson) // menu 데이터 설정
                        .created(LocalDateTime.now())
                        .used(true)
                        .build();
                userRepository.save(user);

                setSuccessResult(signUpResultDto);
                return signUpResultDto;
            } else {
                throw new RuntimeException();
            }
        }
    }


    public  SignUpResultDto performUpdate(UserDto userDto) throws RuntimeException{

        Employment employment = employmentRepository.findByUid(userDto.getEmployment_uid());
        Department department = departmentRepository.findByUid(userDto.getDepartment_uid());

        String id = userDto.getId();
        String name = userDto.getName();
        String password = userDto.getPassword();


        String email = userDto.getEmail();
        String phone = userDto.getPhone();
        String auth = userDto.getAuth();


        Optional<User> selectedUser = Optional.ofNullable(userRepository.getById(userDto.getId()));

        Map<String, Permission> menu = userDto.getMenu(); // Permission 데이터 구조

        User user;
        SignUpResultDto signUpResultDto = new SignUpResultDto();

        if(selectedUser.isPresent()){
            ObjectMapper objectMapper = new ObjectMapper();
            String menuJson = ""; // menu 데이터를 JSON 문자열로 변환
            try {
                menuJson = objectMapper.writeValueAsString(menu);
            } catch (JsonProcessingException e) {
                LOGGER.error("Error serializing menu data: {}", e.getMessage());
                throw new RuntimeException("Failed to serialize menu data", e);
            }

            if (auth.equalsIgnoreCase("admin")) {
                user = User.builder()
                        .id(id)

                        .employment(employment)
                        .department(department)
                        .name(name)
                        .password(passwordEncoder.encode(password))
                        .email(email)
                        .phone(phone)
                        .auth(Collections.singletonList("ROLE_ADMIN"))
                        .menu(menuJson)
                        .updated(LocalDateTime.now())
                        .used(true)
                        .build();
                userRepository.save(user);
                // UserItem 저장

                setSuccessResult(signUpResultDto);
                return signUpResultDto;

            } else if(auth.equalsIgnoreCase("user")){
                user = User.builder()
                        .id(id)
                        .employment(employment)
                        .department(department)
                        .name(name)
                        .password(passwordEncoder.encode(password))
                        .email(email)
                        .phone(phone)
                        .auth(Collections.singletonList("ROLE_USER"))
                        .menu(menuJson)
                        .updated(LocalDateTime.now())
                        .used(true)
                        .build();
                userRepository.save(user);

                setSuccessResult(signUpResultDto);
                return signUpResultDto;
            }else{
                throw new RuntimeException();
            }

        }else{
            setFailResult(signUpResultDto);
            return signUpResultDto;


        }

    }


    public String performExcelUploadUser(List<Map<String, Object>> requestList) throws Exception {

        for (Map<String, Object> data : requestList) {

            String id = String.valueOf(data.get("id"));
            String name = String.valueOf(data.get("name"));
            String companyCode = String.valueOf(data.get("company_code"));
            String employmentName = String.valueOf(data.get("employment_name"));
            String departmentName = String.valueOf(data.get("department_name"));

            String email = String.valueOf(data.get("email"));
            String phone = String.valueOf(data.get("phone"));
            String password = String.valueOf(data.get("password"));
            LOGGER.info("이름 : {}", name);
            LOGGER.info("ID라능 : {}", id);

            Employment employment = employmentRepository.findByNameAndUsed(employmentName, true);
            Department department = departmentRepository.findByNameAndUsed(departmentName, true);

            LOGGER.info("임플 : {}", employment);
            LOGGER.info("디파트 : {}", department);
            if (employment != null && department != null) {
                Optional<User> selectedUser = Optional.ofNullable(userRepository.findByIdAndEmploymentAndDepartment(id, employment, department));

                if (selectedUser.isPresent()) { // 해당 데이터가 있으면 수정
                    LOGGER.info("데이터있음 ");
//                        User user = selectedUser.get();
                    User user;
                    user = User.builder()
                            .id(id)
                            .name(name)
                            .employment(employment)
                            .department(department)
                            .password(passwordEncoder.encode(password))
                            .email(email)
                            .phone(phone)
                            .auth(Collections.singletonList("ROLE_USER"))
                            .updated(LocalDateTime.now())
                            .used(true)
                            .build();

                    userRepository.save(user);
                } else {  // 해당 데이터가 없으면 추가
                    LOGGER.info("데이터없음 ");
                    User user;

                    user = User.builder()
                            .id(id)
                            .name(name)
                            .employment(employment)
                            .department(department)
                            .password(passwordEncoder.encode(password))
                            .email(email)
                            .phone(phone)
                            .auth(Collections.singletonList("ROLE_USER"))
                            .created(LocalDateTime.now())
                            .used(true)
                            .build();
                    userRepository.save(user);
                }


                // 예시로 이름과 수량이 모두 일치하는 Product를 찾는 메서드를 가정


            }

        }
        return null;
    }




    public SignInResultDto signIn(String id,String password,String clientIp) throws RuntimeException{
        LOGGER.info("[getSignInResult] signDateHandler로 회원 정보 요청");
        User user = userRepository.getById(id);
        LOGGER.info("[getSignInResult] Id : {}",user);
        LOGGER.info("[getByID] id: {}",user);

        LOGGER.info("[String clientIp] String clientIp: {}",clientIp);
        LOGGER.info("[getSignInResult] 패스워드 비교 수행 : {}",passwordEncoder.matches(password, user.getPassword()));

        if(user == null || !passwordEncoder.matches(password, user.getPassword()))  {
            SignInResultDto signInResultDto = new SignInResultDto();

            LOGGER.info("[getSignInResult] in : {}",signInResultDto);
            setFailResult(signInResultDto);
            return signInResultDto;
        }else if(user != null && (passwordEncoder.matches(password, user.getPassword()))){
            LOGGER.info("[getSignInResult] 패스워드 일치");
            LOGGER.info("[getSignInResult] SignInResultDto 객체 생성");
            History history = new History();
            history.setName("로그인");
            history.setIp(clientIp);
            history.setUser(user);

            history.setCreated(LocalDateTime.now());

            History insertHistory = historyRepository.save(history);

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
                    .token(jwtTokenProvider.createToken(String.valueOf(user.getId()),user.getAuth()))
                    .menu(user.getMenu())
                    .name(user.getName())
                    .email(user.getEmail())
                    .phone(user.getPhone())
                    .build();

            LOGGER.info("[getSignInResult] SignInResultDto 객체에 값 주입 lgoger: {}",signInResultDto);
            setSuccessResult(signInResultDto);
            return signInResultDto;
        }else{
            throw new RuntimeException();
        }

    }






    public String performDelete(List<String> ids) throws Exception {

        for (String id : ids) {
            Optional<User> selectedUser = userRepository.findById(id);
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
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        Department department = departmentRepository.findByUid(user.getDepartment().getUid());
        dto.setDepartment(department);
        Employment employment = employmentRepository.findByUid(user.getEmployment().getUid());
        dto.setEmployment(employment);


        return dto;
    }



}
