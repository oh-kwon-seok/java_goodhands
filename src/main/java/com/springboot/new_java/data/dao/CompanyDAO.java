package com.springboot.new_java.data.dao;


import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.company.CompanyDto;
import com.springboot.new_java.data.entity.Company;

import java.util.List;
import java.util.Map;


public interface CompanyDAO {
    Company insertCompany(CompanyDto companyDto);

    List<Company> selectTotalCompany(CommonInfoSearchDto CommonInfoSearchDto);
    List<Company> selectCompany(CommonInfoSearchDto companySearchDto);

    List<Company> selectCustomer(CommonInfoSearchDto companySearchDto);

    Company updateCompany(CompanyDto companyDto) throws Exception;

    String deleteCompany(List<Long> uid) throws Exception;

    String excelUploadCompany(List<Map<String, Object>> requestList) throws Exception;


}
