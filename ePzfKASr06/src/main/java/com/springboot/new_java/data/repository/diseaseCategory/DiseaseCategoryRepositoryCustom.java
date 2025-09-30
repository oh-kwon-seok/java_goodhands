package com.springboot.new_java.data.repository.diseaseCategory;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;

import com.springboot.new_java.data.entity.disease.DiseaseCategory;

import java.util.List;

public interface DiseaseCategoryRepositoryCustom {
    List<DiseaseCategory> findAll(CommonInfoSearchDto commonInfoSearchDto);
    List<DiseaseCategory> findInfo(CommonInfoSearchDto commonInfoSearchDto);

}
