package com.springboot.new_java.service;

import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.estimate.EstimateDto;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;

import com.springboot.new_java.data.entity.Estimate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EstimateService {

    List<Estimate> getEstimate(CommonInfoSearchDto commonInfoSearchDto);

    List<Estimate> getTotalEstimate(CommonSearchDto commonSearchDto);


    CommonResultDto saveEstimate(EstimateDto estimateDto) throws Exception;

    CommonResultDto updateEstimate(EstimateDto estimateDto) throws Exception;

    void deleteEstimate(List<Long> uid) throws Exception;


}
