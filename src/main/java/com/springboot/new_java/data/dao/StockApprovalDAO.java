package com.springboot.new_java.data.dao;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.stockApproval.StockApprovalSearchDto;
import com.springboot.new_java.data.entity.StockApproval;

import java.util.List;


public interface StockApprovalDAO {


    List<StockApproval> selectStockApproval(CommonInfoSearchDto commonInfoSearchDto);
    List<StockApproval> selectTotalStockApproval(CommonSearchDto commonSearchDto);

    List<StockApproval> selectWorkTaskUidSelect(StockApprovalSearchDto stockApprovalSearchDto);

}
