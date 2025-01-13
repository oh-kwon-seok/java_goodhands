package com.springboot.new_java.service;



import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.factorySub.FactorySubSearchDto;
import com.springboot.new_java.data.entity.FactorySub;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FactorySubService {

    List<FactorySub> getTotalFactorySub(FactorySubSearchDto factorySubSearchDto);

    List<FactorySub> getFactorySub(FactorySubSearchDto factorySubSearchDto);

    List<FactorySub> getTotalInfoFactorySub(CommonInfoSearchDto commonInfoSearchDto);

}
