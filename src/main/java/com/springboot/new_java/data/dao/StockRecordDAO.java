package com.springboot.new_java.data.dao;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.stockRecord.StockRecordDto;
import com.springboot.new_java.data.entity.StockRecord;

import java.util.List;


public interface StockRecordDAO {


    List<StockRecord> selectStockRecord(CommonInfoSearchDto commonInfoSearchDto);
    List<StockRecord> selectTotalStockRecord(CommonSearchDto commonSearchDto);


     CommonResultDto insertStockRecord(StockRecordDto stockRecordDto)  throws Exception;


    String deleteStockRecord(List<Long> uid) throws Exception;



}
