package com.springboot.java_eco.data.repository.stockControl;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.entity.StockControl;

import java.util.List;

public interface StockControlRepositoryCustom {


    List<StockControl> findAll(CommonSearchDto commonSearchDto);
    List<StockControl> findInfo(CommonInfoSearchDto commonInfoSearchDto);




}
