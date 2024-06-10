package com.springboot.java_eco.service.impl;

import com.springboot.java_eco.data.dao.EstimateSubDAO;
import com.springboot.java_eco.data.dto.estimateSub.EstimateSubSearchDto;
import com.springboot.java_eco.data.entity.EstimateSub;
import com.springboot.java_eco.service.EstimateSubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EstimateSubServiceImpl implements EstimateSubService {
    private final EstimateSubDAO estimateSubDAO;

    @Autowired
    public EstimateSubServiceImpl(@Qualifier("estimateSubDAOImpl") EstimateSubDAO estimateSubDAO){
        this.estimateSubDAO = estimateSubDAO;
    }

    @Override
    public List<EstimateSub> getTotalEstimateSub(EstimateSubSearchDto estimateSubSearchDto){
        return estimateSubDAO.selectTotalEstimateSub(estimateSubSearchDto);
    }

  

    @Override
    public List<EstimateSub> getEstimateSub(EstimateSubSearchDto estimateSubSearchDto){
        return estimateSubDAO.selectEstimateSub(estimateSubSearchDto);
    }


    @Override
    public List<EstimateSub> getEstimateUidSelect(EstimateSubSearchDto estimateSubSearchDto){
        return estimateSubDAO.selectEstimateUidSelect(estimateSubSearchDto);
    }




}
