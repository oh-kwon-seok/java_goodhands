package com.springboot.new_java.service;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;

import com.springboot.new_java.data.dto.stockRecord.StockRecordDto;
import com.springboot.new_java.data.entity.StockRecord;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StockRecordService {

    List<StockRecord> getStockRecord(CommonInfoSearchDto commonInfoSearchDto);

    List<StockRecord> getTotalStockRecord(CommonSearchDto commonSearchDto);


    CommonResultDto saveStockRecord(StockRecordDto stockDto) throws Exception;


    void deleteStockRecord(List<Long> uid) throws Exception;


}
