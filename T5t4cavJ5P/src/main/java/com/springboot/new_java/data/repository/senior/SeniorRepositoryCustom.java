package com.springboot.new_java.data.repository.senior;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.entity.senior.Senior;

import java.util.List;

public interface SeniorRepositoryCustom {
    List<Senior> findAll(CommonInfoSearchDto commonInfoSearchDto);
    List<Senior> findInfo();

}
