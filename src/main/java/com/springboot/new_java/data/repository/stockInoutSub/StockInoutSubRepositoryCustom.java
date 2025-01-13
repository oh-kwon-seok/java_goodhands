package com.springboot.new_java.data.repository.stockInoutSub;

import com.springboot.new_java.data.dto.stockInoutSub.StockInoutSubSearchDto;
import com.springboot.new_java.data.entity.StockInoutSub;

import java.util.List;

public interface StockInoutSubRepositoryCustom {


    List<StockInoutSub> findAll(StockInoutSubSearchDto stockInoutSubSearchDto);
    List<StockInoutSub> findInfo(StockInoutSubSearchDto stockInoutSubSearchDto);

    List<StockInoutSub> findByStockInoutUidSelect(StockInoutSubSearchDto stockInoutSubSearchDto);


}
