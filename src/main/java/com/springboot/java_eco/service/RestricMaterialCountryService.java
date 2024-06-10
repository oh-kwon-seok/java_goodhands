package com.springboot.java_eco.service;


import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;

import com.springboot.java_eco.data.dto.restricMaterialCountry.RestricMaterialCountryDto;

import com.springboot.java_eco.data.entity.RestricMaterialCountry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RestricMaterialCountryService {

    List<RestricMaterialCountry> getTotalRestricMaterialCountry(CommonInfoSearchDto commonInfoSearchDto);

    List<RestricMaterialCountry> getRestricMaterialCountry(CommonInfoSearchDto commonInfoSearchDto);


    CommonResultDto saveRestricMaterialCountry(RestricMaterialCountryDto restricMaterialCountryDto, String clientIp) throws Exception;

    void deleteRestricMaterialCountry(List<Long> uid) throws Exception;




}
