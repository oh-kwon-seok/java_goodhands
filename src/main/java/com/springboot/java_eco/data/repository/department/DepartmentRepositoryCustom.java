package com.springboot.java_eco.data.repository.department;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.entity.Department;

import java.util.List;

public interface DepartmentRepositoryCustom {
    List<Department> findAll(CommonInfoSearchDto departmentSearchDto);
    List<Department> findInfo(CommonInfoSearchDto departmentSearchDto);

}
