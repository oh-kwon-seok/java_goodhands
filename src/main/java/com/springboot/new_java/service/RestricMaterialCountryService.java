package com.springboot.new_java.service;


import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;

import com.springboot.new_java.data.dto.restricMaterialCountry.RestricMaterialCountryDto;

import com.springboot.new_java.data.entity.RestricMaterialCountry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RestricMaterialCountryService {

    List<RestricMaterialCountry> getTotalRestricMaterialCountry(CommonInfoSearchDto commonInfoSearchDto);

    List<RestricMaterialCountry> getRestricMaterialCountry(CommonInfoSearchDto commonInfoSearchDto);


    CommonResultDto saveRestricMaterialCountry(RestricMaterialCountryDto restricMaterialCountryDto, String clientIp) throws Exception;

    void deleteRestricMaterialCountry(List<Long> uid) throws Exception;




}
