package com.springboot.java_eco.data.repository.stockRequest;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.stockRequest.StockRequestSearchDto;
import com.springboot.java_eco.data.entity.StockRequest;

import java.util.List;

public interface StockRequestRepositoryCustom {


    List<StockRequest> findAll(CommonSearchDto commonSearchDto);
    List<StockRequest> findInfo(CommonInfoSearchDto commonInfoSearchDto);


    List<StockRequest> findByWorkTaskUidSelect(StockRequestSearchDto stockRequestSearchDto);

}
