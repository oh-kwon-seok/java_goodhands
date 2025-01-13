package com.springboot.new_java.data.dao;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.stock.LotSearchDto;
import com.springboot.new_java.data.dto.stock.StockDto;
import com.springboot.new_java.data.entity.Stock;

import java.util.List;


public interface StockDAO {


    List<Stock> selectStock(CommonInfoSearchDto commonInfoSearchDto);
    List<Stock> selectTotalStock(CommonSearchDto commonSearchDto);


    List<Stock> selectLotStock(LotSearchDto lotSearchDto);

    List<Stock> selectPackingLotStock(LotSearchDto lotSearchDto);
     CommonResultDto insertStock(StockDto stockDto)  throws Exception;




    String deleteStock(List<Long> uid) throws Exception;



}
