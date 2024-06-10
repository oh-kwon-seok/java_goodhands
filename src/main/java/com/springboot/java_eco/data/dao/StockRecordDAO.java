package com.springboot.java_eco.data.dao;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.stock.StockDto;
import com.springboot.java_eco.data.dto.stockRecord.StockRecordDto;
import com.springboot.java_eco.data.entity.Stock;
import com.springboot.java_eco.data.entity.StockRecord;

import java.util.List;


public interface StockRecordDAO {


    List<StockRecord> selectStockRecord(CommonInfoSearchDto commonInfoSearchDto);
    List<StockRecord> selectTotalStockRecord(CommonSearchDto commonSearchDto);


     CommonResultDto insertStockRecord(StockRecordDto stockRecordDto)  throws Exception;


    String deleteStockRecord(List<Long> uid) throws Exception;



}
