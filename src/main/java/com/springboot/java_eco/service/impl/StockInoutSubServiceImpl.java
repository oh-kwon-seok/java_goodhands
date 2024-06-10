package com.springboot.java_eco.service.impl;

import com.springboot.java_eco.data.dao.StockInoutSubDAO;
import com.springboot.java_eco.data.dto.stockInoutSub.StockInoutSubSearchDto;
import com.springboot.java_eco.data.entity.StockInoutSub;
import com.springboot.java_eco.service.StockInoutSubService;
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
