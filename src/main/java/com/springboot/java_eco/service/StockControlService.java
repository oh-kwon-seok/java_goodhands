package com.springboot.java_eco.service;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.stockControl.StockControlDto;

import com.springboot.java_eco.data.entity.StockControl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StockControlService {

    List<StockControl> getStockControl(CommonInfoSearchDto commonInfoSearchDto);

    List<StockControl> getTotalStockControl(CommonSearchDto commonSearchDto);


    CommonResultDto saveStockControl(StockControlDto stockDto) throws Exception;


    void deleteStockControl(List<Long> uid) throws Exception;


}
