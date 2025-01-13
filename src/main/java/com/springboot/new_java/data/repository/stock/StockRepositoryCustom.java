package com.springboot.new_java.data.repository.stock;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.stock.LotSearchDto;
import com.springboot.new_java.data.entity.Stock;

import java.util.List;

public interface StockRepositoryCustom {


    List<Stock> findAll(CommonSearchDto commonSearchDto);
    List<Stock> findInfo(CommonInfoSearchDto commonInfoSearchDto);

    List<Stock> findLotStock(LotSearchDto lotSearchDto);
    List<Stock> findPackingLotStock(LotSearchDto lotSearchDto);


}
