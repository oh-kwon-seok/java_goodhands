package com.springboot.java_eco.data.repository.user;

import com.springboot.java_eco.data.entity.Company;
import com.springboot.java_eco.data.entity.Department;
import com.springboot.java_eco.data.entity.Employment;
import com.springboot.java_eco.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepositorySupport")
public interface UserRepository extends JpaRepository<User,String>, UserRepositoryCustom {

    User getById(String id);

    User findByIdAndCompanyAndEmploymentAndDepartment(String id, Company company, Employment employment, Department department);



}
