package com.springboot.java_eco.service;


import com.springboot.java_eco.data.dto.stockInoutSub.StockInoutSubSearchDto;
import com.springboot.java_eco.data.dto.stockInoutSub.StockInoutSubSearchDto;
import com.springboot.java_eco.data.entity.StockInoutSub;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StockInoutSubService {

    List<StockInoutSub> getTotalStockInoutSub(StockInoutSubSearchDto stockInoutSubSearchDto);

    List<StockInoutSub> getStockInoutSub(StockInoutSubSearchDto stockInoutSubSearchDto);

    List<StockInoutSub> getStockInoutUidSelect(StockInoutSubSearchDto stockInoutSubSearchDto);




}
