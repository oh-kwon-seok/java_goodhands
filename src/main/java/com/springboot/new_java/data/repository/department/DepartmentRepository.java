package com.springboot.new_java.data.repository.department;

import com.springboot.new_java.data.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("departmentRepositorySupport")
public interface DepartmentRepository extends JpaRepository<Department,Long>, DepartmentRepositoryCustom {

    Department findByUid(Long uid);

    Department findByNameAndUsed(String name,  Boolean used);
}
