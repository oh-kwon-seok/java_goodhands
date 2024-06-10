package com.springboot.java_eco.data.dao;

import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.estimate.EstimateDto;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.entity.Estimate;

import java.util.List;


public interface EstimateDAO {


    List<Estimate> selectEstimate(CommonInfoSearchDto commonInfoSearchDto);
    List<Estimate> selectTotalEstimate(CommonSearchDto commonSearchDto);


     CommonResultDto insertEstimate(EstimateDto estimateDto)  throws Exception;

    CommonResultDto updateEstimate(EstimateDto estimateDto) throws Exception;


    String deleteEstimate(List<Long> uid) throws Exception;



}
