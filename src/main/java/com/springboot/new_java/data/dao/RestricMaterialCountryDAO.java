package com.springboot.new_java.data.dao;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;

import com.springboot.new_java.data.dto.restricMaterialCountry.RestricMaterialCountryDto;
import com.springboot.new_java.data.entity.RestricMaterialCountry;

import java.util.List;


public interface RestricMaterialCountryDAO {



    CommonResultDto insertRestricMaterialCountry(RestricMaterialCountryDto restricMaterialDto, String clientIp) throws Exception;


    List<RestricMaterialCountry> selectTotalRestricMaterialCountry(CommonInfoSearchDto commonInfoSearchDto);

    List<RestricMaterialCountry> selectRestricMaterialCountry(CommonInfoSearchDto commonInfoSearchDto);


    String deleteRestricMaterialCountry(List<Long> uid) throws Exception;






}
