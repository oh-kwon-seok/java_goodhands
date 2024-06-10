package com.springboot.java_eco.data.repository.stockInoutSub;

import com.springboot.java_eco.data.dto.stockInoutSub.StockInoutSubSearchDto;
import com.springboot.java_eco.data.entity.StockInoutSub;

import java.util.List;

public interface StockInoutSubRepositoryCustom {


    List<StockInoutSub> findAll(StockInoutSubSearchDto stockInoutSubSearchDto);
    List<StockInoutSub> findInfo(StockInoutSubSearchDto stockInoutSubSearchDto);

    List<StockInoutSub> findByStockInoutUidSelect(StockInoutSubSearchDto stockInoutSubSearchDto);


}
