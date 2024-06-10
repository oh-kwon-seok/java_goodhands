package com.springboot.java_eco.data.dao;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.stockApproval.StockApprovalSearchDto;
import com.springboot.java_eco.data.entity.StockApproval;

import java.util.List;


public interface StockApprovalDAO {


    List<StockApproval> selectStockApproval(CommonInfoSearchDto commonInfoSearchDto);
    List<StockApproval> selectTotalStockApproval(CommonSearchDto commonSearchDto);

    List<StockApproval> selectWorkTaskUidSelect(StockApprovalSearchDto stockApprovalSearchDto);

}
