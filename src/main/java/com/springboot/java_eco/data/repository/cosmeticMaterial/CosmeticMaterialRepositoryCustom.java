package com.springboot.java_eco.data.repository.cosmeticMaterial;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.entity.CosmeticMaterial;

import java.util.List;

public interface CosmeticMaterialRepositoryCustom {
    List<CosmeticMaterial> findAll(CommonInfoSearchDto commonInfoSearchDto);
    List<CosmeticMaterial> findInfo(CommonInfoSearchDto commonInfoSearchDto);


}
