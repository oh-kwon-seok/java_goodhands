package com.springboot.new_java.data.repository.stockApproval;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.stockApproval.StockApprovalSearchDto;
import com.springboot.new_java.data.entity.StockApproval;

import java.util.List;

public interface StockApprovalRepositoryCustom {


    List<StockApproval> findAll(CommonSearchDto commonSearchDto);
    List<StockApproval> findInfo(CommonInfoSearchDto commonInfoSearchDto);


    List<StockApproval> findByWorkTaskUidSelect(StockApprovalSearchDto stockApprovalSearchDto);

}
