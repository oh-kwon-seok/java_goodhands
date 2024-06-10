package com.springboot.java_eco.service.impl;

import com.springboot.java_eco.data.dao.StockInoutDAO;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.stockInout.StockInoutDto;
import com.springboot.java_eco.data.entity.StockInout;
import com.springboot.java_eco.service.StockInoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockInoutServiceImpl implements StockInoutService {
    private final StockInoutDAO stockInoutDAO;

    @Autowired
    public StockInoutServiceImpl(@Qualifier("stockInoutDAOImpl") StockInoutDAO stockInoutDAO){
        this.stockInoutDAO = stockInoutDAO;
    }

    @Override
    public List<StockInout> getStockInout(CommonInfoSearchDto commonInfoSearchDto){
        return stockInoutDAO.selectStockInout(commonInfoSearchDto);
    }
    @Override
    public List<StockInout> getTotalStockInout(CommonSearchDto commonSearchDto){
        return stockInoutDAO.selectTotalStockInout(commonSearchDto);
    }
    @Override
    public CommonResultDto saveStockInout(StockInoutDto stockInoutDto) throws Exception {

        return stockInoutDAO.insertStockInout(stockInoutDto);

    }
    @Override
    public CommonResultDto updateStockInout(StockInoutDto stockInoutDto) throws Exception {
        return stockInoutDAO.updateStockInout(stockInoutDto);
    }
    @Override
    public void deleteStockInout(List<Long> uid) throws Exception {
        stockInoutDAO.deleteStockInout(uid);
    }


}
