package com.springboot.java_eco.service;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;

import com.springboot.java_eco.data.dto.stockRecord.StockRecordDto;
import com.springboot.java_eco.data.entity.StockRecord;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StockRecordService {

    List<StockRecord> getStockRecord(CommonInfoSearchDto commonInfoSearchDto);

    List<StockRecord> getTotalStockRecord(CommonSearchDto commonSearchDto);


    CommonResultDto saveStockRecord(StockRecordDto stockDto) throws Exception;


    void deleteStockRecord(List<Long> uid) throws Exception;


}
