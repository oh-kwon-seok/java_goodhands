package com.springboot.java_eco.data.repository.restricMaterial;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.entity.RestricMaterial;

import java.util.List;

public interface RestricMaterialRepositoryCustom {
    List<RestricMaterial> findAll(CommonInfoSearchDto commonInfoSearchDto);
    List<RestricMaterial> findInfo(CommonInfoSearchDto commonInfoSearchDto);


}
