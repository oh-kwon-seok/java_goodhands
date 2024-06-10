package com.springboot.java_eco.service;

import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.estimate.EstimateDto;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;

import com.springboot.java_eco.data.entity.Estimate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface EstimateService {

    List<Estimate> getEstimate(CommonInfoSearchDto commonInfoSearchDto);

    List<Estimate> getTotalEstimate(CommonSearchDto commonSearchDto);


    CommonResultDto saveEstimate(EstimateDto estimateDto) throws Exception;

    CommonResultDto updateEstimate(EstimateDto estimateDto) throws Exception;

    void deleteEstimate(List<Long> uid) throws Exception;


}
