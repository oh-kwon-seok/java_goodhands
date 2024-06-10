package com.springboot.java_eco.data.dao;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.stock.LotSearchDto;
import com.springboot.java_eco.data.dto.stock.StockDto;
import com.springboot.java_eco.data.entity.Stock;

import java.util.List;


public interface StockDAO {


    List<Stock> selectStock(CommonInfoSearchDto commonInfoSearchDto);
    List<Stock> selectTotalStock(CommonSearchDto commonSearchDto);


    List<Stock> selectLotStock(LotSearchDto lotSearchDto);

    List<Stock> selectPackingLotStock(LotSearchDto lotSearchDto);
     CommonResultDto insertStock(StockDto stockDto)  throws Exception;




    String deleteStock(List<Long> uid) throws Exception;



}
