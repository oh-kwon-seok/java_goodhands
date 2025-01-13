package com.springboot.new_java.data.repository.estimateSub;

import com.springboot.new_java.data.dto.estimateSub.EstimateSubSearchDto;
import com.springboot.new_java.data.entity.EstimateSub;

import java.util.List;

public interface EstimateSubRepositoryCustom {


    List<EstimateSub> findAll(EstimateSubSearchDto estimateSubSearchDto);
    List<EstimateSub> findInfo(EstimateSubSearchDto estimateSubSearchDto);


    List<EstimateSub> findByEstimateUidSelect(EstimateSubSearchDto estimateSubSearchDto);


}
