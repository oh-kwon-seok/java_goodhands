package com.springboot.java_eco.data.dao.impl;

import com.springboot.java_eco.data.dao.DepartmentDAO;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.department.DepartmentDto;
import com.springboot.java_eco.data.entity.Company;
import com.springboot.java_eco.data.entity.Department;
import com.springboot.java_eco.data.repository.company.CompanyRepository;
import com.springboot.java_eco.data.repository.department.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class DepartmentDAOImpl implements DepartmentDAO {
    
    private final DepartmentRepository departmentRepository;
    private final CompanyRepository companyRepository;
    @Autowired
    public DepartmentDAOImpl(DepartmentRepository departmentRepository, CompanyRepository companyRepository){
        this.departmentRepository = departmentRepository;
        this.companyRepository = companyRepository;

    }

    public Department insertDepartment(DepartmentDto departmentDto) {


        Company company = companyRepository.findByUid(departmentDto.getCompany_uid());
        Department department = new Department();

        department.setCompany(company);
        department.setName(departmentDto.getName());

        department.setUsed(Math.toIntExact(departmentDto.getUsed()));

        department.setCreated(LocalDateTime.now());

        return departmentRepository.save(department);

    }
    @Override
    public List<Department> selectTotalDepartment(CommonInfoSearchDto commonInfoSearchDto) {
        return departmentRepository.findAll(commonInfoSearchDto);

    }

    @Override
    public List<Department> selectDepartment(CommonInfoSearchDto commonInfoSearchDto) {
        return departmentRepository.findInfo(commonInfoSearchDto);

    }


    @Override
    public Department updateDepartment(DepartmentDto departmentDto) throws Exception {

        Company company = companyRepository.findByUid(departmentDto.getCompany_uid());
        Optional<Department> selectedDepartment = departmentRepository.findById(departmentDto.getUid());

        Department updatedDepartment;

        if (selectedDepartment.isPresent()) {
            Department department = selectedDepartment.get();

            department.setCompany(company);

            department.setName(departmentDto.getName());

            department.setUsed(Math.toIntExact(departmentDto.getUsed()));

            department.setUpdated(LocalDateTime.now());
            updatedDepartment = departmentRepository.save(department);
        } else {
            throw new Exception();
        }
        return updatedDepartment;
    }
    @Override
    public String deleteDepartment(List<Long> uids) throws Exception {

        for (Long uid : uids) {
            Optional<Department> selectedDepartment = departmentRepository.findById(uid);
            if (selectedDepartment.isPresent()) {
                Department department = selectedDepartment.get();
                department.setUsed(0);
                department.setDeleted(LocalDateTime.now());
                departmentRepository.save(department);
            } else {
                throw new Exception("Department with UID " + uid + " not found.");
            }
        }
        return "Departments deleted successfully";
    }
}
