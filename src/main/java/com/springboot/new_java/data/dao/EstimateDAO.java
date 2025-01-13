package com.springboot.new_java.data.dao;

import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.estimate.EstimateDto;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.entity.Estimate;

import java.util.List;


public interface EstimateDAO {


    List<Estimate> selectEstimate(CommonInfoSearchDto commonInfoSearchDto);
    List<Estimate> selectTotalEstimate(CommonSearchDto commonSearchDto);


     CommonResultDto insertEstimate(EstimateDto estimateDto)  throws Exception;

    CommonResultDto updateEstimate(EstimateDto estimateDto) throws Exception;


    String deleteEstimate(List<Long> uid) throws Exception;



}
