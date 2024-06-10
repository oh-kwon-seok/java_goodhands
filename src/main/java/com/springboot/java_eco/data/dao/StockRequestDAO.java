package com.springboot.java_eco.data.dao;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.stockRequest.StockRequestDto;
import com.springboot.java_eco.data.dto.stockRequest.StockRequestSearchDto;
import com.springboot.java_eco.data.entity.StockRequest;

import java.util.List;


public interface StockRequestDAO {


    List<StockRequest> selectStockRequest(CommonInfoSearchDto commonInfoSearchDto);
    List<StockRequest> selectTotalStockRequest(CommonSearchDto commonSearchDto);

    List<StockRequest> selectWorkTaskUidSelect(StockRequestSearchDto stockRequestSearchDto);

}
