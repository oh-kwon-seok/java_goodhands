package com.springboot.new_java.data.repository.factory;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.entity.Factory;


import java.util.List;

public interface FactoryRepositoryCustom {


    List<Factory> findAll(CommonInfoSearchDto commonInfoSearchDto);
    List<Factory> findInfo(CommonInfoSearchDto commonInfoSearchDto);




}
