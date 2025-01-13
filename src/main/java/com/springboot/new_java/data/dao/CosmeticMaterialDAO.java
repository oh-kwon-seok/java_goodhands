package com.springboot.new_java.data.dao;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.costmeticMaterial.CosmeticMaterialDto;

import com.springboot.new_java.data.entity.CosmeticMaterial;

import java.util.List;


public interface CosmeticMaterialDAO {



    CommonResultDto insertCosmeticMaterial(CosmeticMaterialDto cosmeticMaterialDto, String clientIp) throws Exception;


    List<CosmeticMaterial> selectTotalCosmeticMaterial(CommonInfoSearchDto commonInfoSearchDto);

    List<CosmeticMaterial> selectCosmeticMaterial(CommonInfoSearchDto commonInfoSearchDto);


    String deleteCosmeticMaterial(List<Long> uid) throws Exception;






}
