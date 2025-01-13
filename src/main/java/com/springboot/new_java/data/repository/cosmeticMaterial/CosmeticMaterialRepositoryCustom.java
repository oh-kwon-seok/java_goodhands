package com.springboot.new_java.data.repository.cosmeticMaterial;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.entity.CosmeticMaterial;

import java.util.List;

public interface CosmeticMaterialRepositoryCustom {
    List<CosmeticMaterial> findAll(CommonInfoSearchDto commonInfoSearchDto);
    List<CosmeticMaterial> findInfo(CommonInfoSearchDto commonInfoSearchDto);


}
