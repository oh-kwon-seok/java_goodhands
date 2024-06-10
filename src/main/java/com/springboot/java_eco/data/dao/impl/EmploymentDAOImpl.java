package com.springboot.java_eco.data.dao.impl;

import com.springboot.java_eco.data.dao.EmploymentDAO;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.employment.EmploymentDto;
import com.springboot.java_eco.data.entity.Company;
import com.springboot.java_eco.data.entity.Employment;
import com.springboot.java_eco.data.repository.company.CompanyRepository;
import com.springboot.java_eco.data.repository.employment.EmploymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class EmploymentDAOImpl implements EmploymentDAO {

    private final EmploymentRepository employmentRepository;
    private final CompanyRepository companyRepository;

    @Autowired
    public EmploymentDAOImpl(EmploymentRepository employmentRepository,CompanyRepository companyRepository){

        this.employmentRepository = employmentRepository;
        this.companyRepository = companyRepository;
    }

    public Employment insertEmployment(EmploymentDto employmentDto) {

        Company company = companyRepository.findByUid(employmentDto.getCompany_uid());

        Employment employment = new Employment();

        employment.setCompany(company);
        employment.setName(employmentDto.getName());
        employment.setName2(employmentDto.getName2());


        employment.setUsed(Math.toIntExact(employmentDto.getUsed()));

        employment.setCreated(LocalDateTime.now());

        return employmentRepository.save(employment);

    }
    @Override
    public List<Employment> selectTotalEmployment(CommonInfoSearchDto commonInfoSearchDto) {
        return employmentRepository.findAll(commonInfoSearchDto);

    }

    @Override
    public List<Employment> selectEmployment(CommonInfoSearchDto commonInfoSearchDto) {
        return employmentRepository.findInfo(commonInfoSearchDto);

    }


    @Override
    public Employment updateEmployment(EmploymentDto employmentDto) throws Exception {

        Company company = companyRepository.findByUid(employmentDto.getCompany_uid());
        Optional<Employment> selectedEmployment = employmentRepository.findById(employmentDto.getUid());

        Employment updatedEmployment;

        if (selectedEmployment.isPresent()) {
            Employment employment = selectedEmployment.get();

            employment.setCompany(company);
            employment.setName(employmentDto.getName());
            employment.setName2(employmentDto.getName2());

            employment.setUsed(Math.toIntExact(employmentDto.getUsed()));

            employment.setUpdated(LocalDateTime.now());
            updatedEmployment = employmentRepository.save(employment);
        } else {
            throw new Exception();
        }
        return updatedEmployment;
    }
    @Override
    public String deleteEmployment(List<Long> uids) throws Exception {

        for (Long uid : uids) {
            Optional<Employment> selectedEmployment = employmentRepository.findById(uid);
            if (selectedEmployment.isPresent()) {
                Employment employment = selectedEmployment.get();
                employment.setUsed(0);
                employment.setDeleted(LocalDateTime.now());
                employmentRepository.save(employment);
            } else {
                throw new Exception("Employment with UID " + uid + " not found.");
            }
        }
        return "Employments deleted successfully";
    }
}
