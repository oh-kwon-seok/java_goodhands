package com.springboot.java_eco.data.dao;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.stockControl.StockControlDto;
import com.springboot.java_eco.data.entity.StockControl;

import java.util.List;


public interface StockControlDAO {


    List<StockControl> selectStockControl(CommonInfoSearchDto commonInfoSearchDto);
    List<StockControl> selectTotalStockControl(CommonSearchDto commonSearchDto);

     CommonResultDto insertStockControl(StockControlDto stockControlDto)  throws Exception;


    String deleteStockControl(List<Long> uid) throws Exception;



}
