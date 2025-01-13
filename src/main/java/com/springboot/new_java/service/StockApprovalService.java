package com.springboot.new_java.service;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.stockApproval.StockApprovalSearchDto;
import com.springboot.new_java.data.entity.StockApproval;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StockApprovalService {

    List<StockApproval> getStockApproval(CommonInfoSearchDto commonInfoSearchDto);

    List<StockApproval> getTotalStockApproval(CommonSearchDto commonSearchDto);


    List<StockApproval> getWorkTaskUidSelect(StockApprovalSearchDto stockApprovalSearchDto);


}
