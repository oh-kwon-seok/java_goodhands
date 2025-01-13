package com.springboot.new_java.service.impl;

import com.springboot.new_java.data.dao.EstimateDAO;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.estimate.EstimateDto;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.entity.Estimate;
import com.springboot.new_java.service.EstimateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstimateServiceImpl implements EstimateService {
    private final EstimateDAO estimateDAO;

    @Autowired
    public EstimateServiceImpl(@Qualifier("estimateDAOImpl") EstimateDAO estimateDAO){
        this.estimateDAO = estimateDAO;
    }

    @Override
    public List<Estimate> getEstimate(CommonInfoSearchDto commonInfoSearchDto){
        return estimateDAO.selectEstimate(commonInfoSearchDto);
    }
    @Override
    public List<Estimate> getTotalEstimate(CommonSearchDto commonSearchDto){
        return estimateDAO.selectTotalEstimate(commonSearchDto);
    }
    @Override
    public CommonResultDto saveEstimate(EstimateDto estimateDto) throws Exception {

        return estimateDAO.insertEstimate(estimateDto);

    }
    @Override
    public CommonResultDto updateEstimate(EstimateDto estimateDto) throws Exception {
        return estimateDAO.updateEstimate(estimateDto);
    }
    @Override
    public void deleteEstimate(List<Long> uid) throws Exception {
        estimateDAO.deleteEstimate(uid);
    }


}
