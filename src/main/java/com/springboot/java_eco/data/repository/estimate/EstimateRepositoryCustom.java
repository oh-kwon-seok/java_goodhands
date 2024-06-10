package com.springboot.java_eco.data.repository.estimate;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.entity.Estimate;

import java.util.List;

public interface EstimateRepositoryCustom {


    List<Estimate> findAll(CommonSearchDto commonSearchDto);
    List<Estimate> findInfo(CommonInfoSearchDto commonInfoSearchDto);




}
