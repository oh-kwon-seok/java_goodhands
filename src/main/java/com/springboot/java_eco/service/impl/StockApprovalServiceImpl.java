package com.springboot.java_eco.service.impl;

import com.springboot.java_eco.data.dao.StockApprovalDAO;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.stockApproval.StockApprovalSearchDto;
import com.springboot.java_eco.data.entity.StockApproval;
import com.springboot.java_eco.service.StockApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockApprovalServiceImpl implements StockApprovalService {
    private final StockApprovalDAO stockApprovalDAO;

    @Autowired
    public StockApprovalServiceImpl(@Qualifier("stockApprovalDAOImpl") StockApprovalDAO stockApprovalDAO){
        this.stockApprovalDAO = stockApprovalDAO;
    }

    @Override
    public List<StockApproval> getStockApproval(CommonInfoSearchDto commonInfoSearchDto){
        return stockApprovalDAO.selectStockApproval(commonInfoSearchDto);
    }
    @Override
    public List<StockApproval> getTotalStockApproval(CommonSearchDto commonSearchDto){
        return stockApprovalDAO.selectTotalStockApproval(commonSearchDto);
    }

    @Override
    public List<StockApproval> getWorkTaskUidSelect(StockApprovalSearchDto stockApprovalSearchDto){
        return stockApprovalDAO.selectWorkTaskUidSelect(stockApprovalSearchDto);
    }



}
