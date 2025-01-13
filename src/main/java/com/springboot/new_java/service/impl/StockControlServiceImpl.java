package com.springboot.new_java.service.impl;

import com.springboot.new_java.data.dao.StockControlDAO;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.stockControl.StockControlDto;
import com.springboot.new_java.data.entity.StockControl;
import com.springboot.new_java.service.StockControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockControlServiceImpl implements StockControlService {
    private final StockControlDAO stockControlDAO;

    @Autowired
    public StockControlServiceImpl(@Qualifier("stockControlDAOImpl") StockControlDAO stockControlDAO){
        this.stockControlDAO = stockControlDAO;
    }

    @Override
    public List<StockControl> getStockControl(CommonInfoSearchDto commonInfoSearchDto){
        return stockControlDAO.selectStockControl(commonInfoSearchDto);
    }
    @Override
    public List<StockControl> getTotalStockControl(CommonSearchDto commonSearchDto){
        return stockControlDAO.selectTotalStockControl(commonSearchDto);
    }
    @Override
    public CommonResultDto saveStockControl(StockControlDto stockDto) throws Exception {

        return stockControlDAO.insertStockControl(stockDto);

    }

    @Override
    public void deleteStockControl(List<Long> uid) throws Exception {
        stockControlDAO.deleteStockControl(uid);
    }


}
