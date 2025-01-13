package com.springboot.new_java.service;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;

import com.springboot.new_java.data.dto.stockInout.StockInoutDto;
import com.springboot.new_java.data.entity.StockInout;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StockInoutService {

    List<StockInout> getStockInout(CommonInfoSearchDto commonInfoSearchDto);

    List<StockInout> getTotalStockInout(CommonSearchDto commonSearchDto);


    CommonResultDto saveStockInout(StockInoutDto orderDto) throws Exception;

    CommonResultDto updateStockInout(StockInoutDto orderDto) throws Exception;

    void deleteStockInout(List<Long> uid) throws Exception;


}
