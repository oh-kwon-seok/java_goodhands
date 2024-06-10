package com.springboot.java_eco.data.repository.stock;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.stock.LotSearchDto;
import com.springboot.java_eco.data.entity.Stock;

import java.util.List;

public interface StockRepositoryCustom {


    List<Stock> findAll(CommonSearchDto commonSearchDto);
    List<Stock> findInfo(CommonInfoSearchDto commonInfoSearchDto);

    List<Stock> findLotStock(LotSearchDto lotSearchDto);
    List<Stock> findPackingLotStock(LotSearchDto lotSearchDto);


}
