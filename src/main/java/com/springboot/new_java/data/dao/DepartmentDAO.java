package com.springboot.new_java.data.dao;


import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.department.DepartmentDto;
import com.springboot.new_java.data.entity.Department;

import java.util.List;


public interface DepartmentDAO {
    Department insertDepartment(DepartmentDto departmentDto);

    List<Department> selectTotalDepartment(CommonInfoSearchDto commonInfoSearchDto);
    List<Department> selectDepartment(CommonInfoSearchDto departmentSearchDto);

    Department updateDepartment(DepartmentDto departmentDto) throws Exception;

    String deleteDepartment(List<Long> uid) throws Exception;


}
