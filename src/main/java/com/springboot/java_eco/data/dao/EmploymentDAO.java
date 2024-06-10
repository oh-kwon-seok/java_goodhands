package com.springboot.java_eco.data.dao;


import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.employment.EmploymentDto;
import com.springboot.java_eco.data.entity.Employment;

import java.util.List;


public interface EmploymentDAO {
    Employment insertEmployment(EmploymentDto employmentDto);

    List<Employment> selectTotalEmployment(CommonInfoSearchDto commonInfoSearchDto);
    List<Employment> selectEmployment(CommonInfoSearchDto commonInfoSearchDto);

    Employment updateEmployment(EmploymentDto employmentDto) throws Exception;

    String deleteEmployment(List<Long> uid) throws Exception;


}
