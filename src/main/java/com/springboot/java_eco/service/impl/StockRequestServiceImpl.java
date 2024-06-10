package com.springboot.java_eco.service.impl;

import com.springboot.java_eco.data.dao.StockRequestDAO;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.stockRequest.StockRequestDto;
import com.springboot.java_eco.data.dto.stockRequest.StockRequestSearchDto;
import com.springboot.java_eco.data.dto.workTask.WorkTaskSearchDto;
import com.springboot.java_eco.data.entity.StockRequest;
import com.springboot.java_eco.service.StockRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockRequestServiceImpl implements StockRequestService {
    private final StockRequestDAO stockRequestDAO;

    @Autowired
    public StockRequestServiceImpl(@Qualifier("stockRequestDAOImpl") StockRequestDAO stockRequestDAO){
        this.stockRequestDAO = stockRequestDAO;
    }

    @Override
    public List<StockRequest> getStockRequest(CommonInfoSearchDto commonInfoSearchDto){
        return stockRequestDAO.selectStockRequest(commonInfoSearchDto);
    }
    @Override
    public List<StockRequest> getTotalStockRequest(CommonSearchDto commonSearchDto){
        return stockRequestDAO.selectTotalStockRequest(commonSearchDto);
    }

    @Override
    public List<StockRequest> getWorkTaskUidSelect(StockRequestSearchDto stockRequestSearchDto){
        return stockRequestDAO.selectWorkTaskUidSelect(stockRequestSearchDto);
    }



}
