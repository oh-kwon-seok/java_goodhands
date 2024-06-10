package com.springboot.java_eco.service;



import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.factorySub.FactorySubSearchDto;
import com.springboot.java_eco.data.entity.FactorySub;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FactorySubService {

    List<FactorySub> getTotalFactorySub(FactorySubSearchDto factorySubSearchDto);

    List<FactorySub> getFactorySub(FactorySubSearchDto factorySubSearchDto);

    List<FactorySub> getTotalInfoFactorySub(CommonInfoSearchDto commonInfoSearchDto);

}
