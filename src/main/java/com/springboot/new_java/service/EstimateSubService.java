package com.springboot.new_java.service;


import com.springboot.new_java.data.dto.estimateSub.EstimateSubSearchDto;

import com.springboot.new_java.data.entity.EstimateSub;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EstimateSubService {

    List<EstimateSub> getTotalEstimateSub(EstimateSubSearchDto estimateSubSearchDto);

    List<EstimateSub> getEstimateSub(EstimateSubSearchDto estimateSubSearchDto);

    List<EstimateSub> getEstimateUidSelect(EstimateSubSearchDto estimateSubSearchDto);




}
