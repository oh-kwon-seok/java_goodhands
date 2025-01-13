package com.springboot.new_java.data.repository.stockRecord;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;

import com.springboot.new_java.data.entity.StockRecord;

import java.util.List;

public interface StockRecordRepositoryCustom {


    List<StockRecord> findAll(CommonSearchDto commonSearchDto);
    List<StockRecord> findInfo(CommonInfoSearchDto commonInfoSearchDto);




}
