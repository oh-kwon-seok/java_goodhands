package com.springboot.java_eco.service;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.stock.LotSearchDto;
import com.springboot.java_eco.data.dto.stock.StockDto;
import com.springboot.java_eco.data.dto.stock.StockDto;
import com.springboot.java_eco.data.entity.Stock;
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
