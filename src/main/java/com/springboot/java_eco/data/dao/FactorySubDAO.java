package com.springboot.java_eco.data.dao;


import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.factorySub.FactorySubSearchDto;

import com.springboot.java_eco.data.entity.FactorySub;
import java.util.List;


public interface FactorySubDAO {


    List<FactorySub> selectTotalFactorySub(FactorySubSearchDto factorySubSearchDto);


    List<FactorySub> selectFactorySub(FactorySubSearchDto factorySubSearchDto);

    List<FactorySub> selectTotalInfoFactorySub(CommonInfoSearchDto commonInfoSearchDto);

}
