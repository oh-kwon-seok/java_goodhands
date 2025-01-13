package com.springboot.new_java.data.dao.impl;

import com.springboot.new_java.data.dao.CompanyDAO;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.company.CompanyDto;
import com.springboot.new_java.data.entity.Company;
import com.springboot.new_java.data.repository.company.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class CompanyDAOImpl implements CompanyDAO {
    
    private final CompanyRepository companyRepository;
    @Autowired
    public CompanyDAOImpl(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;

    }

    public Company insertCompany(CompanyDto companyDto) {



        Company company = new Company();
        company.setCode(companyDto.getCode());

        company.setName(companyDto.getName());
        company.setEmail(companyDto.getEmail());

        company.setOwner_name(companyDto.getOwner_name());
        company.setOwner_phone(companyDto.getOwner_phone());


        company.setEmp_name(companyDto.getEmp_name());

        company.setEmp_phone(companyDto.getEmp_phone());
        company.setFax(companyDto.getFax());




        company.setType(companyDto.getType());

        company.setUsed(Math.toIntExact(companyDto.getUsed()));

        company.setCreated(LocalDateTime.now());

        Company insertCompany = companyRepository.save(company);
        return insertCompany;

    }
    @Override
    public List<Company> selectTotalCompany(CommonInfoSearchDto commonInfoSearchDto) {
        return companyRepository.findAll(commonInfoSearchDto);

    }

    @Override
    public List<Company> selectCompany(CommonInfoSearchDto commonInfoSearchDto) {
        return companyRepository.findInfo(commonInfoSearchDto);

    }
    @Override
    public List<Company> selectCustomer(CommonInfoSearchDto commonInfoSearchDto) {
        return companyRepository.findInfoCustomer(commonInfoSearchDto);

    }


    @Override
    public Company updateCompany(CompanyDto companyDto) throws Exception {


        Optional<Company> selectedCompany = companyRepository.findById(companyDto.getUid());

        Company updatedCompany;

        if (selectedCompany.isPresent()) {
            Company company = selectedCompany.get();

            company.setCode(companyDto.getCode());
            company.setName(companyDto.getName());
            company.setEmail(companyDto.getEmail());
            company.setOwner_name(companyDto.getOwner_name());
            company.setOwner_phone(companyDto.getOwner_phone());


            company.setEmp_name(companyDto.getEmp_name());

            company.setEmp_phone(companyDto.getEmp_phone());
            company.setFax(companyDto.getFax());


            company.setType(companyDto.getType());
            company.setUsed(Math.toIntExact(companyDto.getUsed()));

            company.setUpdated(LocalDateTime.now());
            updatedCompany = companyRepository.save(company);
        } else {
            throw new Exception();
        }
        return updatedCompany;
    }
    @Override
    public String deleteCompany(List<Long> uids) throws Exception {

        for (Long uid : uids) {
            Optional<Company> selectedCompany = companyRepository.findById(uid);
            if (selectedCompany.isPresent()) {
                Company company = selectedCompany.get();
                company.setUsed(0);
                company.setDeleted(LocalDateTime.now());
                companyRepository.save(company);
            } else {
                throw new Exception("Company with UID " + uid + " not found.");
            }
        }
        return "Companys deleted successfully";
    }

    @Override
    public String excelUploadCompany(List<Map<String, Object>> requestList) throws Exception {

        for (Map<String, Object> data : requestList) {
            String code = (String) data.get("code");
            String name = (String) data.get("name");
            String owner_name = (String) data.get("owner_name");
            String owner_phone = (String) data.get("owner_phone");
            String emp_name = (String) data.get("emp_name");
            String emp_phone = (String) data.get("emp_phone");
            String fax = (String) data.get("fax");
            String email = (String) data.get("email");
            String type = (String) data.get("type");



            // 예시로 이름과 수량이 모두 일치하는 Product를 찾는 메서드를 가정
            Optional<Company> selectedCompany = Optional.ofNullable(companyRepository.findByCode(code));

            if (selectedCompany.isPresent()) { // 해당 데이터가 있으면 수정
                Company company = selectedCompany.get();

                company.setCode(code);
                company.setName(name);
                company.setOwner_name(owner_name);
                company.setOwner_phone(owner_phone);
                company.setEmp_name(emp_name);
                company.setEmp_phone(emp_phone);
                company.setFax(fax);
                company.setEmail(email);
                company.setType(type);
                company.setUsed(1);
                company.setUpdated(LocalDateTime.now());
                companyRepository.save(company);
            } else {  // 해당 데이터가 없으면 추가
                Company company = new Company();

                company.setCode(code);
                company.setName(name);
                company.setOwner_name(owner_name);
                company.setOwner_phone(owner_phone);
                company.setEmp_name(emp_name);
                company.setEmp_phone(emp_phone);
                company.setFax(fax);
                company.setEmail(email);
                company.setType(type);
                company.setUsed(1);

                company.setCreated(LocalDateTime.now());
                companyRepository.save(company);


            }
        }
        return "Company excelupload successfully";
    }
}
