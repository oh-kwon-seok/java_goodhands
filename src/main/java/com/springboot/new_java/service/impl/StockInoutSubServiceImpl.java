package com.springboot.new_java.service.impl;

import com.springboot.new_java.data.dao.StockInoutSubDAO;
import com.springboot.new_java.data.dto.stockInoutSub.StockInoutSubSearchDto;
import com.springboot.new_java.data.entity.StockInoutSub;
import com.springboot.new_java.service.StockInoutSubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockInoutSubServiceImpl implements StockInoutSubService {
    private final StockInoutSubDAO stockInoutSubDAO;

    @Autowired
    public StockInoutSubServiceImpl(@Qualifier("stockInoutSubDAOImpl") StockInoutSubDAO stockInoutSubDAO){
        this.stockInoutSubDAO = stockInoutSubDAO;
    }

    @Override
    public List<StockInoutSub> getTotalStockInoutSub(StockInoutSubSearchDto stockInoutSubSearchDto){
        return stockInoutSubDAO.selectTotalStockInoutSub(stockInoutSubSearchDto);
    }

  

    @Override
    public List<StockInoutSub> getStockInoutSub(StockInoutSubSearchDto stockInoutSubSearchDto){
        return stockInoutSubDAO.selectStockInoutSub(stockInoutSubSearchDto);
    }


    @Override
    public List<StockInoutSub> getStockInoutUidSelect(StockInoutSubSearchDto stockInoutSubSearchDto){
        return stockInoutSubDAO.selectStockInoutUidSelect(stockInoutSubSearchDto);
    }




}
