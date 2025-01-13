package com.springboot.new_java.service.impl;

import com.springboot.new_java.data.dao.CompanyDAO;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.company.CompanyDto;
import com.springboot.new_java.data.entity.Company;
import com.springboot.new_java.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyDAO companyDAO;

    @Autowired
    public CompanyServiceImpl(@Qualifier("companyDAOImpl") CompanyDAO companyDAO){
        this.companyDAO = companyDAO;
    }


    @Override
    public List<Company> getTotalCompany(CommonInfoSearchDto commonInfoSearchDto){
        return companyDAO.selectTotalCompany(commonInfoSearchDto);
    }

    @Override
    public List<Company> getCompany(CommonInfoSearchDto commonInfoSearchDto){
        return companyDAO.selectCompany(commonInfoSearchDto);
    }
    @Override
    public List<Company> getCustomer(CommonInfoSearchDto commonInfoSearchDto){
        return companyDAO.selectCustomer(commonInfoSearchDto);
    }
    @Override
    public Company saveCompany(CompanyDto companyDto) throws Exception {

        return companyDAO.insertCompany(companyDto);

    }
    @Override
    public Company updateCompany(CompanyDto companyDto) throws Exception {
        return companyDAO.updateCompany(companyDto);
    }
    @Override
    public void deleteCompany(List<Long> uid) throws Exception {
        companyDAO.deleteCompany(uid);
    }
    @Override
    public void excelUploadCompany(List<Map<String, Object>> requestList) throws Exception {
        companyDAO.excelUploadCompany(requestList);
    }

}
