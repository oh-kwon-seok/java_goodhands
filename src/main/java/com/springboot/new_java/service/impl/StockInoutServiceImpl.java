package com.springboot.new_java.service.impl;

import com.springboot.new_java.data.dao.StockInoutDAO;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.stockInout.StockInoutDto;
import com.springboot.new_java.data.entity.StockInout;
import com.springboot.new_java.service.StockInoutService;
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
