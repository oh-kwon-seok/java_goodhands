package com.springboot.new_java.data.dao;


import com.springboot.new_java.data.dto.estimateSub.EstimateSubSearchDto;

import com.springboot.new_java.data.entity.EstimateSub;

import java.util.List;


public interface EstimateSubDAO {


    List<EstimateSub> selectTotalEstimateSub(EstimateSubSearchDto estimateSubSearchDto);


    List<EstimateSub> selectEstimateSub(EstimateSubSearchDto estimateSubSearchDto);

    List<EstimateSub> selectEstimateUidSelect(EstimateSubSearchDto estimateSubSearchDto);



}
