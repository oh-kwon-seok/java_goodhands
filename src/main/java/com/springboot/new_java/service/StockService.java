package com.springboot.new_java.service;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.stock.LotSearchDto;
import com.springboot.new_java.data.dto.stock.StockDto;
import com.springboot.new_java.data.entity.Stock;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StockService {

    List<Stock> getStock(CommonInfoSearchDto commonInfoSearchDto);

    List<Stock> getTotalStock(CommonSearchDto commonSearchDto);

    List<Stock> getLotStock(LotSearchDto lotSearchDto);
    List<Stock> getPackingLotStock(LotSearchDto lotSearchDto);


    CommonResultDto saveStock(StockDto stockDto) throws Exception;


    void deleteStock(List<Long> uid) throws Exception;


}
