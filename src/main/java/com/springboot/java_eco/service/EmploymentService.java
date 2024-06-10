package com.springboot.java_eco.service;


import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.employment.EmploymentDto;
import com.springboot.java_eco.data.entity.Employment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmploymentService {

    List<Employment> getTotalEmployment(CommonInfoSearchDto commonInfoSearchDto);

    List<Employment> getEmployment(CommonInfoSearchDto commonInfoSearchDto);


    Employment saveEmployment(EmploymentDto employmentDto) throws Exception;

    Employment updateEmployment(EmploymentDto employmentDto) throws Exception;

    void deleteEmployment(List<Long> uid) throws Exception;


}
