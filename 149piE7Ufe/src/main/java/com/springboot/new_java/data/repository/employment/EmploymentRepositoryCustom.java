package com.springboot.new_java.data.repository.employment;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.entity.Employment;

import java.util.List;

public interface EmploymentRepositoryCustom {
    List<Employment> findAll(CommonInfoSearchDto commonInfoSearchDto);
    List<Employment> findInfo(CommonInfoSearchDto commonInfoSearchDto);

}
