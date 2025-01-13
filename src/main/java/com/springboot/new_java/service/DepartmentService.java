package com.springboot.new_java.service;


import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.department.DepartmentDto;
import com.springboot.new_java.data.entity.Department;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DepartmentService {

    List<Department> getTotalDepartment(CommonInfoSearchDto commonInfoSearchDto);

    List<Department> getDepartment(CommonInfoSearchDto commonInfoSearchDto);


    Department saveDepartment(DepartmentDto departmentDto) throws Exception;

    Department updateDepartment(DepartmentDto departmentDto) throws Exception;

    void deleteDepartment(List<Long> uid) throws Exception;


}
