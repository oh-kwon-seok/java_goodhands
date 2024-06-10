package com.springboot.java_eco.service.impl;


import ch.qos.logback.classic.Logger;
import com.springboot.java_eco.common.CommonResponse;
import com.springboot.java_eco.config.security.JwtTokenProvider;
import com.springboot.java_eco.data.dao.UserDAO;
import com.springboot.java_eco.data.dto.SignInResultDto;
import com.springboot.java_eco.data.dto.SignUpResultDto;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.user.UserDto;
import com.springboot.java_eco.data.entity.*;
import com.springboot.java_eco.data.repository.company.CompanyRepository;
import com.springboot.java_eco.data.repository.department.DepartmentRepository;
import com.springboot.java_eco.data.repository.employment.EmploymentRepository;
import com.springboot.java_eco.data.repository.history.HistoryRepository;
import com.springboot.java_eco.data.repository.user.UserRepository;
import com.springboot.java_eco.service.SignService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service

public class SignServiceImpl implements SignService {
    private final Logger LOGGER = (Logger)LoggerFactory.getLogger(SignServiceImpl.class);
    private final UserDAO userDao;


    public UserRepository userRepository;
    public CompanyRepository companyRepository;
    public EmploymentRepository employmentRepository;
    public DepartmentRepository departmentRepository;


    public HistoryRepository historyRepository;
    public JwtTokenProvider jwtTokenProvider;
    public PasswordEncoder passwordEncoder;

    @Autowired
    public SignServiceImpl(@Qualifier("userDAOImpl") UserDAO userDAO, UserRepository userRepository, CompanyRepository companyRepository, EmploymentRepository employmentRepository, DepartmentRepository departmentRepository,
    JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder, HistoryRepository historyRepository){
        this.userDao = userDAO;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.employmentRepository = employmentRepository;
        this.departmentRepository = departmentRepository;

        this.historyRepository = historyRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;

    }



    @Override
    public List<User> getTotalUser(CommonInfoSearchDto commonInfoSearchDto){
        return userDao.selectTotalUser(commonInfoSearchDto);

    }

    @Override
    public List<User> getUser(CommonInfoSearchDto commonInfoSearchDto){
        return userDao.selectUser(commonInfoSearchDto);
    }

    @Override
    public  SignUpResultDto save(UserDto userDto) throws RuntimeException{

        Company company = companyRepository.findByUid(userDto.getCompany_uid());
        Employment employment = employmentRepository.findByUid(userDto.getEmployment_uid());
        Department department = departmentRepository.findByUid(userDto.getDepartment_uid());
        String id = userDto.getId();
        String name = userDto.getName();
        String password = userDto.getPassword();

        String email = userDto.getEmail();
        String phone = userDto.getPhone();
        String auth = userDto.getAuth();
        Optional<User> selectedUser = userRepository.findById(userDto.getId());

        LOGGER.info("[selectUser] : {}",selectedUser);

        User user;
        SignUpResultDto signUpResultDto = new SignUpResultDto();

        if(selectedUser.isPresent()){

            setFailResult(signUpResultDto);
            return signUpResultDto;

        }else{
            if (auth.equalsIgnoreCase("admin")) {
                user = User.builder()
                        .id(id)
                        .name(name)
                        .company(company)
                        .employment(employment)
                        .department(department)
                        .password(passwordEncoder.encode(password))
                        .email(email)
                        .phone(phone)
                        .auth(Collections.singletonList("ROLE_ADMIN"))
                        .created(LocalDateTime.now())
                        .used(1)
                        .build();
                userRepository.save(user);

                setSuccessResult(signUpResultDto);
                return signUpResultDto;

            } else if(auth.equalsIgnoreCase("user")){
                user = User.builder()
                        .id(id)

                        .name(name)
                        .company(company)
                        .employment(employment)
                        .department(department)

                        .password(passwordEncoder.encode(password))
                        .email(email)
                        .phone(phone)


                        .auth(Collections.singletonList("ROLE_USER"))
                        .created(LocalDateTime.now())
                        .used(1)
                        .build();
                userRepository.save(user);
                setSuccessResult(signUpResultDto);
                return signUpResultDto;
            }else{
                throw new RuntimeException();
            }

        }


    }

    @Override
    public  SignUpResultDto update(UserDto userDto) throws RuntimeException{
        Company company = companyRepository.findByUid(userDto.getCompany_uid());
        Employment employment = employmentRepository.findByUid(userDto.getEmployment_uid());
        Department department = departmentRepository.findByUid(userDto.getDepartment_uid());

        String id = userDto.getId();
        String name = userDto.getName();
        String password = userDto.getPassword();


        String email = userDto.getEmail();
        String phone = userDto.getPhone();
        String auth = userDto.getAuth();


        Optional<User> selectedUser = Optional.ofNullable(userRepository.getById(userDto.getId()));

        User user;
        SignUpResultDto signUpResultDto = new SignUpResultDto();

        if(selectedUser.isPresent()){
            if (auth.equalsIgnoreCase("admin")) {
                user = User.builder()
                        .id(id)
                        .company(company)
                        .employment(employment)
                        .department(department)
                        .name(name)
                        .password(passwordEncoder.encode(password))
                        .email(email)
                        .phone(phone)
                        .auth(Collections.singletonList("ROLE_ADMIN"))
                        .updated(LocalDateTime.now())
                        .used(Math.toIntExact(userDto.getUsed()))
                        .build();
                userRepository.save(user);
                // UserItem 저장

                setSuccessResult(signUpResultDto);
                return signUpResultDto;

            } else if(auth.equalsIgnoreCase("user")){
                user = User.builder()
                        .id(id)
                        .company(company)
                        .employment(employment)
                        .department(department)
                        .name(name)
                        .password(passwordEncoder.encode(password))
                        .email(email)
                        .phone(phone)
                        .auth(Collections.singletonList("ROLE_USER"))
                        .updated(LocalDateTime.now())
                        .used(Math.toIntExact(userDto.getUsed()))
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

    @Override
    public void excelUploadUser(List<Map<String, Object>> requestList) throws Exception {
        userDao.excelUploadUser(requestList);
    }



    @Override
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


            SignInResultDto signInResultDto = SignInResultDto.builder()
                    .token(jwtTokenProvider.createToken(String.valueOf(user.getId()),user.getAuth()))
                    .company_uid(user.getCompany().getUid())
                    .build();

            LOGGER.info("[getSignInResult] SignInResultDto 객체에 값 주입 lgoger: {}",signInResultDto);
            setSuccessResult(signInResultDto);
            return signInResultDto;
        }else{
            throw new RuntimeException();
        }

    }





    @Override
    public String delete(List<String> ids) throws Exception {

        for (String id : ids) {
            Optional<User> selectedUser = userRepository.findById(id);
            if (selectedUser.isPresent()) {
                User user = selectedUser.get();
                user.setUsed(0);
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



}
