package com.springboot.new_java.data.repository.disease;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;

import com.springboot.new_java.data.entity.disease.Disease;

import java.util.List;

public interface DiseaseRepositoryCustom {
    List<Disease> findAll(CommonInfoSearchDto diseaseSearchDto);
    List<Disease> findInfo();

}
