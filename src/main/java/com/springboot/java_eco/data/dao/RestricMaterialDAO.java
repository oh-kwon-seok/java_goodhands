package com.springboot.java_eco.data.dao;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.restricMaterial.RestricMaterialDto;

import com.springboot.java_eco.data.entity.RestricMaterial;

import java.util.List;


public interface RestricMaterialDAO {



    CommonResultDto insertRestricMaterial(RestricMaterialDto restricMaterialDto,String clientIp) throws Exception;


    List<RestricMaterial> selectTotalRestricMaterial(CommonInfoSearchDto commonInfoSearchDto);

    List<RestricMaterial> selectRestricMaterial(CommonInfoSearchDto commonInfoSearchDto);


    String deleteRestricMaterial(List<Long> uid) throws Exception;






}
