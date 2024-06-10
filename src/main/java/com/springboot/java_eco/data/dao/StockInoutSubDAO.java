package com.springboot.java_eco.data.dao;


import com.springboot.java_eco.data.dto.stockInout.StockInoutSearchDto;
import com.springboot.java_eco.data.dto.stockInoutSub.StockInoutSubSearchDto;
import com.springboot.java_eco.data.entity.StockInout;
import com.springboot.java_eco.data.entity.StockInoutSub;

import java.util.List;


public interface StockInoutSubDAO {


    List<StockInoutSub> selectTotalStockInoutSub(StockInoutSubSearchDto stockInoutSearchDto);


    List<StockInoutSub> selectStockInoutSub(StockInoutSubSearchDto stockInoutSearchDto);

    List<StockInoutSub> selectStockInoutUidSelect(StockInoutSubSearchDto stockInoutSearchDto);



}
