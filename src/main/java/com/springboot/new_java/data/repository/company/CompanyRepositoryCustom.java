package com.springboot.new_java.data.repository.company;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.entity.Company;

import java.util.List;

public interface CompanyRepositoryCustom {
    List<Company> findAll(CommonInfoSearchDto commonInfoSearchDto);
    List<Company> findInfo(CommonInfoSearchDto commonInfoSearchDto);
    List<Company> findInfoCustomer(CommonInfoSearchDto commonInfoSearchDto);


}
