package com.springboot.java_eco.data.repository.factory;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.entity.Factory;


import java.util.List;

public interface FactoryRepositoryCustom {


    List<Factory> findAll(CommonInfoSearchDto commonInfoSearchDto);
    List<Factory> findInfo(CommonInfoSearchDto commonInfoSearchDto);




}
