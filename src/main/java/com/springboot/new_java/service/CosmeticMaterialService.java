package com.springboot.new_java.service;


import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;

import com.springboot.new_java.data.dto.costmeticMaterial.CosmeticMaterialDto;
import com.springboot.new_java.data.entity.CosmeticMaterial;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CosmeticMaterialService {

    List<CosmeticMaterial> getTotalCosmeticMaterial(CommonInfoSearchDto commonInfoSearchDto);

    List<CosmeticMaterial> getCosmeticMaterial(CommonInfoSearchDto commonInfoSearchDto);


    CommonResultDto saveCosmeticMaterial(CosmeticMaterialDto cosmeticMaterialDto, String clientIp) throws Exception;

    void deleteCosmeticMaterial(List<Long> uid) throws Exception;




}
