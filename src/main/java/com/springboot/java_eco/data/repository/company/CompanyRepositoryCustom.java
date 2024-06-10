package com.springboot.java_eco.data.repository.company;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.entity.Company;

import java.util.List;

public interface CompanyRepositoryCustom {
    List<Company> findAll(CommonInfoSearchDto commonInfoSearchDto);
    List<Company> findInfo(CommonInfoSearchDto commonInfoSearchDto);
    List<Company> findInfoCustomer(CommonInfoSearchDto commonInfoSearchDto);


}
