package com.springboot.new_java.service.impl;

import com.springboot.new_java.data.dao.StockRecordDAO;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;

import com.springboot.new_java.data.dto.stockRecord.StockRecordDto;
import com.springboot.new_java.data.entity.StockRecord;
import com.springboot.new_java.service.StockRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockRecordServiceImpl implements StockRecordService {
    private final StockRecordDAO stockRecordDAO;

    @Autowired
    public StockRecordServiceImpl(@Qualifier("stockRecordDAOImpl") StockRecordDAO stockRecordDAO){
        this.stockRecordDAO = stockRecordDAO;
    }

    @Override
    public List<StockRecord> getStockRecord(CommonInfoSearchDto commonInfoSearchDto){
        return stockRecordDAO.selectStockRecord(commonInfoSearchDto);
    }
    @Override
    public List<StockRecord> getTotalStockRecord(CommonSearchDto commonSearchDto){
        return stockRecordDAO.selectTotalStockRecord(commonSearchDto);
    }
    @Override
    public CommonResultDto saveStockRecord(StockRecordDto stockDto) throws Exception {

        return stockRecordDAO.insertStockRecord(stockDto);

    }

    @Override
    public void deleteStockRecord(List<Long> uid) throws Exception {
        stockRecordDAO.deleteStockRecord(uid);
    }


}
