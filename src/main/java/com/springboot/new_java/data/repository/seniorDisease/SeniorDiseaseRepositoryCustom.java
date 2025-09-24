package com.springboot.new_java.data.repository.seniorDisease;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.entity.senior.Senior;
import com.springboot.new_java.data.entity.senior.SeniorDisease;

import java.util.List;

public interface SeniorDiseaseRepositoryCustom {
    List<SeniorDisease> findAll(CommonInfoSearchDto commonInfoSearchDto);


}
