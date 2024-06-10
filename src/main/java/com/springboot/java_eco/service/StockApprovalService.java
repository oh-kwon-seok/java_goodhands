package com.springboot.java_eco.service;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.stockApproval.StockApprovalSearchDto;
import com.springboot.java_eco.data.entity.StockApproval;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StockApprovalService {

    List<StockApproval> getStockApproval(CommonInfoSearchDto commonInfoSearchDto);

    List<StockApproval> getTotalStockApproval(CommonSearchDto commonSearchDto);


    List<StockApproval> getWorkTaskUidSelect(StockApprovalSearchDto stockApprovalSearchDto);


}
