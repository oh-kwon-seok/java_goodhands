package com.springboot.java_eco.data.repository.stockApproval;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.stockApproval.StockApprovalSearchDto;
import com.springboot.java_eco.data.entity.StockApproval;

import java.util.List;

public interface StockApprovalRepositoryCustom {


    List<StockApproval> findAll(CommonSearchDto commonSearchDto);
    List<StockApproval> findInfo(CommonInfoSearchDto commonInfoSearchDto);


    List<StockApproval> findByWorkTaskUidSelect(StockApprovalSearchDto stockApprovalSearchDto);

}
