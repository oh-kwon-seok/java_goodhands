package com.springboot.new_java.data.repository.stockInout;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.entity.StockInout;

import java.util.List;

public interface StockInoutRepositoryCustom {


    List<StockInout> findAll(CommonSearchDto commonSearchDto);
    List<StockInout> findInfo(CommonInfoSearchDto commonInfoSearchDto);




}
