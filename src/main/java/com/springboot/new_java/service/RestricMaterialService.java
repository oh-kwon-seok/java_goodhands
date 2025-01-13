package com.springboot.new_java.service;


import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.restricMaterial.RestricMaterialDto;
import com.springboot.new_java.data.entity.RestricMaterial;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RestricMaterialService {

    List<RestricMaterial> getTotalRestricMaterial(CommonInfoSearchDto commonInfoSearchDto);

    List<RestricMaterial> getRestricMaterial(CommonInfoSearchDto commonInfoSearchDto);


    CommonResultDto saveRestricMaterial(RestricMaterialDto restricMaterialDto, String clientIp) throws Exception;

    void deleteRestricMaterial(List<Long> uid) throws Exception;




}
