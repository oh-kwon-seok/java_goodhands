package com.springboot.new_java.service;



import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.factory.FactoryDto;
import com.springboot.new_java.data.entity.Factory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FactoryService {

    List<Factory> getFactory(CommonInfoSearchDto commonInfoSearchDto);

    List<Factory> getTotalFactory(CommonInfoSearchDto commonInfoSearchDto);


    CommonResultDto saveFactory(FactoryDto factoryDto) throws Exception;

    CommonResultDto updateFactory(FactoryDto factoryDto) throws Exception;

    void deleteFactory(List<Long> uid) throws Exception;


}
