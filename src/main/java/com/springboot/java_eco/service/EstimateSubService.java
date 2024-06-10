package com.springboot.java_eco.service;


import com.springboot.java_eco.data.dto.estimateSub.EstimateSubSearchDto;

import com.springboot.java_eco.data.entity.EstimateSub;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface EstimateSubService {

    List<EstimateSub> getTotalEstimateSub(EstimateSubSearchDto estimateSubSearchDto);

    List<EstimateSub> getEstimateSub(EstimateSubSearchDto estimateSubSearchDto);

    List<EstimateSub> getEstimateUidSelect(EstimateSubSearchDto estimateSubSearchDto);




}
