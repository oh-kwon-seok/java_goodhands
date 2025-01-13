package com.springboot.new_java.service;


import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.company.CompanyDto;
import com.springboot.new_java.data.entity.Company;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CompanyService {

    List<Company> getTotalCompany(CommonInfoSearchDto commonInfoSearchDto);

    List<Company> getCompany(CommonInfoSearchDto commonInfoSearchDto);

    List<Company> getCustomer(CommonInfoSearchDto commonInfoSearchDto);



    Company saveCompany(CompanyDto companyDto) throws Exception;

    Company updateCompany(CompanyDto companyDto) throws Exception;

    void deleteCompany(List<Long> uid) throws Exception;

    void excelUploadCompany(List<Map<String, Object>> requestList) throws Exception;


}
