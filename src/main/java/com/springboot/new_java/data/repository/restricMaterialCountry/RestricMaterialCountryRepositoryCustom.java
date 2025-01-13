package com.springboot.new_java.data.repository.restricMaterialCountry;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;

import com.springboot.new_java.data.entity.RestricMaterialCountry;

import java.util.List;

public interface RestricMaterialCountryRepositoryCustom {
    List<RestricMaterialCountry> findAll(CommonInfoSearchDto commonInfoSearchDto);
    List<RestricMaterialCountry> findInfo(CommonInfoSearchDto commonInfoSearchDto);


}
