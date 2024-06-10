package com.springboot.java_eco.data.dao;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.costmeticMaterial.CosmeticMaterialDto;

import com.springboot.java_eco.data.entity.CosmeticMaterial;

import java.util.List;


public interface CosmeticMaterialDAO {



    CommonResultDto insertCosmeticMaterial(CosmeticMaterialDto cosmeticMaterialDto, String clientIp) throws Exception;


    List<CosmeticMaterial> selectTotalCosmeticMaterial(CommonInfoSearchDto commonInfoSearchDto);

    List<CosmeticMaterial> selectCosmeticMaterial(CommonInfoSearchDto commonInfoSearchDto);


    String deleteCosmeticMaterial(List<Long> uid) throws Exception;






}
