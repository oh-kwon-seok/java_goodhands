package com.springboot.java_eco.service;


import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;

import com.springboot.java_eco.data.dto.costmeticMaterial.CosmeticMaterialDto;
import com.springboot.java_eco.data.entity.CosmeticMaterial;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CosmeticMaterialService {

    List<CosmeticMaterial> getTotalCosmeticMaterial(CommonInfoSearchDto commonInfoSearchDto);

    List<CosmeticMaterial> getCosmeticMaterial(CommonInfoSearchDto commonInfoSearchDto);


    CommonResultDto saveCosmeticMaterial(CosmeticMaterialDto cosmeticMaterialDto, String clientIp) throws Exception;

    void deleteCosmeticMaterial(List<Long> uid) throws Exception;




}
