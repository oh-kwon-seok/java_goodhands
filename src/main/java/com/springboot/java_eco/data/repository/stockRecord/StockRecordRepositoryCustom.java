package com.springboot.java_eco.data.repository.stockRecord;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;

import com.springboot.java_eco.data.entity.StockRecord;

import java.util.List;

public interface StockRecordRepositoryCustom {


    List<StockRecord> findAll(CommonSearchDto commonSearchDto);
    List<StockRecord> findInfo(CommonInfoSearchDto commonInfoSearchDto);




}
