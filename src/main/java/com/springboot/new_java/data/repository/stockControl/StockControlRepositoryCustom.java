package com.springboot.new_java.data.repository.stockControl;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.entity.StockControl;

import java.util.List;

public interface StockControlRepositoryCustom {


    List<StockControl> findAll(CommonSearchDto commonSearchDto);
    List<StockControl> findInfo(CommonInfoSearchDto commonInfoSearchDto);




}
