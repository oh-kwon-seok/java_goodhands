package com.springboot.new_java.data.dao;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.stockRequest.StockRequestSearchDto;
import com.springboot.new_java.data.entity.StockRequest;

import java.util.List;


public interface StockRequestDAO {


    List<StockRequest> selectStockRequest(CommonInfoSearchDto commonInfoSearchDto);
    List<StockRequest> selectTotalStockRequest(CommonSearchDto commonSearchDto);

    List<StockRequest> selectWorkTaskUidSelect(StockRequestSearchDto stockRequestSearchDto);

}
