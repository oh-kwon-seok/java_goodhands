package com.springboot.new_java.service.impl;

import com.springboot.new_java.data.dao.EmploymentDAO;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.employment.EmploymentDto;
import com.springboot.new_java.data.entity.Employment;
import com.springboot.new_java.service.EmploymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmploymentServiceImpl implements EmploymentService {
    private final EmploymentDAO employmentDAO;

    @Autowired
    public EmploymentServiceImpl(@Qualifier("employmentDAOImpl") EmploymentDAO employmentDAO){
        this.employmentDAO = employmentDAO;
    }


    @Override
    public List<Employment> getTotalEmployment(CommonInfoSearchDto commonInfoSearchDto){
        return employmentDAO.selectTotalEmployment(commonInfoSearchDto);
    }

    @Override
    public List<Employment> getEmployment(CommonInfoSearchDto commonInfoSearchDto){
        return employmentDAO.selectEmployment(commonInfoSearchDto);
    }
    @Override
    public Employment saveEmployment(EmploymentDto employmentDto) throws Exception {

        return employmentDAO.insertEmployment(employmentDto);

    }
    @Override
    public Employment updateEmployment(EmploymentDto employmentDto) throws Exception {
        return employmentDAO.updateEmployment(employmentDto);
    }
    @Override
    public void deleteEmployment(List<Long> uid) throws Exception {
        employmentDAO.deleteEmployment(uid);
    }

}
