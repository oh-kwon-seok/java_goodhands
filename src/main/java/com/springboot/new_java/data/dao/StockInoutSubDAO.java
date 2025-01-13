package com.springboot.new_java.data.dao;


import com.springboot.new_java.data.dto.stockInoutSub.StockInoutSubSearchDto;
import com.springboot.new_java.data.entity.StockInoutSub;

import java.util.List;


public interface StockInoutSubDAO {


    List<StockInoutSub> selectTotalStockInoutSub(StockInoutSubSearchDto stockInoutSearchDto);


    List<StockInoutSub> selectStockInoutSub(StockInoutSubSearchDto stockInoutSearchDto);

    List<StockInoutSub> selectStockInoutUidSelect(StockInoutSubSearchDto stockInoutSearchDto);



}
