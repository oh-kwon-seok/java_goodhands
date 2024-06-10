package com.springboot.java_eco.data.repository.stockInout;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.entity.StockInout;

import java.util.List;

public interface StockInoutRepositoryCustom {


    List<StockInout> findAll(CommonSearchDto commonSearchDto);
    List<StockInout> findInfo(CommonInfoSearchDto commonInfoSearchDto);




}
