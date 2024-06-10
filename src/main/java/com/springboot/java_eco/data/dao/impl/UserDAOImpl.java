package com.springboot.java_eco.data.dao.impl;

import ch.qos.logback.classic.Logger;
import com.springboot.java_eco.data.dao.UserDAO;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.entity.*;
import com.springboot.java_eco.data.repository.company.CompanyRepository;
import com.springboot.java_eco.data.repository.department.DepartmentRepository;
import com.springboot.java_eco.data.repository.employment.EmploymentRepository;
import com.springboot.java_eco.data.repository.user.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class UserDAOImpl implements UserDAO {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(UserDAOImpl.class);

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final EmploymentRepository employmentRepository;
    private final DepartmentRepository departmentRepository;
    public PasswordEncoder passwordEncoder;



    @Autowired
    public UserDAOImpl(UserRepository userRepository,
                       CompanyRepository companyRepository,
                       EmploymentRepository employmentRepository,
                       DepartmentRepository departmentRepository,
                       PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.employmentRepository = employmentRepository;
        this.departmentRepository = departmentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> selectTotalUser(CommonInfoSearchDto commonInfoSearchDto) {
        return userRepository.findAll(commonInfoSearchDto);
    }
    public List<User> selectUser(CommonInfoSearchDto commonInfoSearchDto) {
        return userRepository.findInfo(commonInfoSearchDto);
    }

    @Override
    public String excelUploadUser(List<Map<String, Object>> requestList) throws Exception {

        for (Map<String, Object> data : requestList) {

            String id = String.valueOf(data.get("id"));
            String name = String.valueOf(data.get("name"));
            String companyCode = String.valueOf(data.get("company_code"));
            String employmentName = String.valueOf(data.get("employment_name"));
            String departmentName = String.valueOf(data.get("department_name"));

            String email = String.valueOf(data.get("email"));
            String phone = String.valueOf(data.get("phone"));
            String password = String.valueOf(data.get("password"));

            Company company = companyRepository.findByCode(companyCode);

            LOGGER.info("흐흐흐 : {}",name);
            LOGGER.info("ID라능 : {}",id);
            if (company != null) {
                Employment employment = employmentRepository.findByNameAndCompanyAndUsed(employmentName, company,1L);
                Department department = departmentRepository.findByNameAndCompanyAndUsed(departmentName, company, 1L);
                if(employment != null && department != null){
                    Optional<User> selectedUser = Optional.ofNullable(userRepository.findByIdAndCompanyAndEmploymentAndDepartment(id,company,employment,department));

                    if (selectedUser.isPresent()) { // 해당 데이터가 있으면 수정
                        LOGGER.info("데이터있음 ");
//                        User user = selectedUser.get();
                        User user;
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
                                .updated(LocalDateTime.now())
                                .used(1)
                                .build();


//                        user.setId(id);
//                        user.setCompany(company);
//                        user.setEmployment(employment);
//                        user.setDepartment(department);
//                        user.setName(name);
//                        user.setEmail(email);
//                        user.setPhone(phone);
//                        user.setPassword(passwordEncoder.encode(password));
//
//                        user.setUsed(1);
//                        user.setUpdated(LocalDateTime.now());
                        userRepository.save(user);
                    } else {  // 해당 데이터가 없으면 추가
                        LOGGER.info("데이터없음 ");
                        User user ;

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
                    }

                }else {
                    return "User excelupload Fail";
                }



            } else {
                // Company가 존재하지 않을 경우 처리
                return "User excelupload Fail";
            }

            // 예시로 이름과 수량이 모두 일치하는 Product를 찾는 메서드를 가정


        }
        return "Company excelupload successfully";
    }
}

