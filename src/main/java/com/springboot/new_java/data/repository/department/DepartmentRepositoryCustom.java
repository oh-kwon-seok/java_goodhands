package com.springboot.new_java.data.repository.department;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.entity.Department;

import java.util.List;

public interface DepartmentRepositoryCustom {
    List<Department> findAll(CommonInfoSearchDto departmentSearchDto);
    List<Department> findInfo();

}
