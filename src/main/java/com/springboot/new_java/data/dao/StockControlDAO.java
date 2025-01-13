package com.springboot.new_java.data.dao;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.stockControl.StockControlDto;
import com.springboot.new_java.data.entity.StockControl;

import java.util.List;


public interface StockControlDAO {


    List<StockControl> selectStockControl(CommonInfoSearchDto commonInfoSearchDto);
    List<StockControl> selectTotalStockControl(CommonSearchDto commonSearchDto);

     CommonResultDto insertStockControl(StockControlDto stockControlDto)  throws Exception;


    String deleteStockControl(List<Long> uid) throws Exception;



}
