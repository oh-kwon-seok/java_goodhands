package com.springboot.java_eco.service;


import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.restricMaterial.RestricMaterialDto;
import com.springboot.java_eco.data.entity.RestricMaterial;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RestricMaterialService {

    List<RestricMaterial> getTotalRestricMaterial(CommonInfoSearchDto commonInfoSearchDto);

    List<RestricMaterial> getRestricMaterial(CommonInfoSearchDto commonInfoSearchDto);


    CommonResultDto saveRestricMaterial(RestricMaterialDto restricMaterialDto, String clientIp) throws Exception;

    void deleteRestricMaterial(List<Long> uid) throws Exception;




}
