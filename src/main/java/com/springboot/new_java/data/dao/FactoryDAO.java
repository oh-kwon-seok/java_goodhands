package com.springboot.new_java.data.dao;

import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.factory.FactoryDto;
import com.springboot.new_java.data.entity.Factory;


import java.util.List;


public interface FactoryDAO {


    List<Factory> selectFactory(CommonInfoSearchDto commonInfoSearchDto);
    List<Factory> selectTotalFactory(CommonInfoSearchDto commonInfoSearchDto);


     CommonResultDto insertFactory(FactoryDto factoryDto)  throws Exception;

    CommonResultDto updateFactory(FactoryDto factoryDto) throws Exception;


    String deleteFactory(List<Long> uid) throws Exception;


}
