package com.springboot.new_java.data.repository.restricMaterial;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.entity.RestricMaterial;

import java.util.List;

public interface RestricMaterialRepositoryCustom {
    List<RestricMaterial> findAll(CommonInfoSearchDto commonInfoSearchDto);
    List<RestricMaterial> findInfo(CommonInfoSearchDto commonInfoSearchDto);


}
