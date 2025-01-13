package com.springboot.new_java.data.dao;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.restricMaterial.RestricMaterialDto;

import com.springboot.new_java.data.entity.RestricMaterial;

import java.util.List;


public interface RestricMaterialDAO {



    CommonResultDto insertRestricMaterial(RestricMaterialDto restricMaterialDto,String clientIp) throws Exception;


    List<RestricMaterial> selectTotalRestricMaterial(CommonInfoSearchDto commonInfoSearchDto);

    List<RestricMaterial> selectRestricMaterial(CommonInfoSearchDto commonInfoSearchDto);


    String deleteRestricMaterial(List<Long> uid) throws Exception;






}
