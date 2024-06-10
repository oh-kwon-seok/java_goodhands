package com.springboot.java_eco.data.dao;

import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.factory.FactoryDto;
import com.springboot.java_eco.data.entity.Factory;


import java.util.List;


public interface FactoryDAO {


    List<Factory> selectFactory(CommonInfoSearchDto commonInfoSearchDto);
    List<Factory> selectTotalFactory(CommonInfoSearchDto commonInfoSearchDto);


     CommonResultDto insertFactory(FactoryDto factoryDto)  throws Exception;

    CommonResultDto updateFactory(FactoryDto factoryDto) throws Exception;


    String deleteFactory(List<Long> uid) throws Exception;


}
