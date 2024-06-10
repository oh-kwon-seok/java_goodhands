package com.springboot.java_eco.data.repository.restricMaterialCountry;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;

import com.springboot.java_eco.data.entity.RestricMaterialCountry;

import java.util.List;

public interface RestricMaterialCountryRepositoryCustom {
    List<RestricMaterialCountry> findAll(CommonInfoSearchDto commonInfoSearchDto);
    List<RestricMaterialCountry> findInfo(CommonInfoSearchDto commonInfoSearchDto);


}
