package com.springboot.java_eco.data.repository.employment;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.entity.Employment;

import java.util.List;

public interface EmploymentRepositoryCustom {
    List<Employment> findAll(CommonInfoSearchDto commonInfoSearchDto);
    List<Employment> findInfo(CommonInfoSearchDto commonInfoSearchDto);

}
