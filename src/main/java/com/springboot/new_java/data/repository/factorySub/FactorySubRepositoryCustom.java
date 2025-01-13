package com.springboot.new_java.data.repository.factorySub;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.factorySub.FactorySubSearchDto;
import com.springboot.new_java.data.entity.FactorySub;


import java.util.List;

public interface FactorySubRepositoryCustom {



    List<FactorySub> findAll(FactorySubSearchDto factorySubSearchDto);


    List<FactorySub> findInfo(FactorySubSearchDto factorySubSearchDto);


    List<FactorySub> findInfoFactorySub(CommonInfoSearchDto commonInfoSearchDto);

}
