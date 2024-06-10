package com.springboot.java_eco.data.dao;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.stockInout.StockInoutDto;
import com.springboot.java_eco.data.dto.stockInout.StockInoutDto;
import com.springboot.java_eco.data.entity.StockInout;

import java.util.List;


public interface StockInoutDAO {


    List<StockInout> selectStockInout(CommonInfoSearchDto commonInfoSearchDto);
    List<StockInout> selectTotalStockInout(CommonSearchDto commonSearchDto);


     CommonResultDto insertStockInout(StockInoutDto stockInoutDto)  throws Exception;

    CommonResultDto updateStockInout(StockInoutDto stockInoutDto) throws Exception;


    String deleteStockInout(List<Long> uid) throws Exception;



}
