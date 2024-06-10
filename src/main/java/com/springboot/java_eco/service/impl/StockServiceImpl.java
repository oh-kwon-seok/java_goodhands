package com.springboot.java_eco.service.impl;

import com.springboot.java_eco.data.dao.StockDAO;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.stock.LotSearchDto;
import com.springboot.java_eco.data.dto.stock.StockDto;
import com.springboot.java_eco.data.entity.Stock;
import com.springboot.java_eco.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImpl implements StockService {
    private final StockDAO stockDAO;

    @Autowired
    public StockServiceImpl(@Qualifier("stockDAOImpl") StockDAO stockDAO){
        this.stockDAO = stockDAO;
    }

    @Override
    public List<Stock> getStock(CommonInfoSearchDto commonInfoSearchDto){
        return stockDAO.selectStock(commonInfoSearchDto);
    }
    @Override
    public List<Stock> getTotalStock(CommonSearchDto commonSearchDto){
        return stockDAO.selectTotalStock(commonSearchDto);
    }
    @Override
    public List<Stock> getLotStock(LotSearchDto lotSearchDto){
        return stockDAO.selectLotStock(lotSearchDto);
    }

    @Override
    public List<Stock> getPackingLotStock(LotSearchDto lotSearchDto){
        return stockDAO.selectPackingLotStock(lotSearchDto);
    }



    @Override
    public CommonResultDto saveStock(StockDto stockDto) throws Exception {

        return stockDAO.insertStock(stockDto);

    }

    @Override
    public void deleteStock(List<Long> uid) throws Exception {
        stockDAO.deleteStock(uid);
    }


}
