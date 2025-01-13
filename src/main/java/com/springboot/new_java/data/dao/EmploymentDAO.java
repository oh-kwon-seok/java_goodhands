package com.springboot.new_java.data.dao;


import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.employment.EmploymentDto;
import com.springboot.new_java.data.entity.Employment;

import java.util.List;


public interface EmploymentDAO {
    Employment insertEmployment(EmploymentDto employmentDto);

    List<Employment> selectTotalEmployment(CommonInfoSearchDto commonInfoSearchDto);
    List<Employment> selectEmployment(CommonInfoSearchDto commonInfoSearchDto);

    Employment updateEmployment(EmploymentDto employmentDto) throws Exception;

    String deleteEmployment(List<Long> uid) throws Exception;


}
