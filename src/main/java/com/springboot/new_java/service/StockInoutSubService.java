package com.springboot.new_java.service;


import com.springboot.new_java.data.dto.stockInoutSub.StockInoutSubSearchDto;
import com.springboot.new_java.data.entity.StockInoutSub;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StockInoutSubService {

    List<StockInoutSub> getTotalStockInoutSub(StockInoutSubSearchDto stockInoutSubSearchDto);

    List<StockInoutSub> getStockInoutSub(StockInoutSubSearchDto stockInoutSubSearchDto);

    List<StockInoutSub> getStockInoutUidSelect(StockInoutSubSearchDto stockInoutSubSearchDto);




}
