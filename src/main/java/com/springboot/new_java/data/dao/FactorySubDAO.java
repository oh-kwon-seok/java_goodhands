package com.springboot.new_java.data.dao;


import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.factorySub.FactorySubSearchDto;

import com.springboot.new_java.data.entity.FactorySub;
import java.util.List;


public interface FactorySubDAO {


    List<FactorySub> selectTotalFactorySub(FactorySubSearchDto factorySubSearchDto);


    List<FactorySub> selectFactorySub(FactorySubSearchDto factorySubSearchDto);

    List<FactorySub> selectTotalInfoFactorySub(CommonInfoSearchDto commonInfoSearchDto);

}
