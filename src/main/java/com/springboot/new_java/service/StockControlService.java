package com.springboot.new_java.service;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.stockControl.StockControlDto;

import com.springboot.new_java.data.entity.StockControl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StockControlService {

    List<StockControl> getStockControl(CommonInfoSearchDto commonInfoSearchDto);

    List<StockControl> getTotalStockControl(CommonSearchDto commonSearchDto);


    CommonResultDto saveStockControl(StockControlDto stockDto) throws Exception;


    void deleteStockControl(List<Long> uid) throws Exception;


}
