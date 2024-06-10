package com.springboot.java_eco.data.repository.estimateSub;

import com.springboot.java_eco.data.dto.estimateSub.EstimateSubSearchDto;
import com.springboot.java_eco.data.entity.EstimateSub;

import java.util.List;

public interface EstimateSubRepositoryCustom {


    List<EstimateSub> findAll(EstimateSubSearchDto estimateSubSearchDto);
    List<EstimateSub> findInfo(EstimateSubSearchDto estimateSubSearchDto);


    List<EstimateSub> findByEstimateUidSelect(EstimateSubSearchDto estimateSubSearchDto);


}
