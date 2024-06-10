package com.springboot.java_eco.data.dao;


import com.springboot.java_eco.data.dto.estimateSub.EstimateSubSearchDto;

import com.springboot.java_eco.data.entity.EstimateSub;

import java.util.List;
import java.util.Map;


public interface EstimateSubDAO {


    List<EstimateSub> selectTotalEstimateSub(EstimateSubSearchDto estimateSubSearchDto);


    List<EstimateSub> selectEstimateSub(EstimateSubSearchDto estimateSubSearchDto);

    List<EstimateSub> selectEstimateUidSelect(EstimateSubSearchDto estimateSubSearchDto);



}
