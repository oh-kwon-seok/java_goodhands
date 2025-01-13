package com.springboot.new_java.data.repository.employment;

import com.springboot.new_java.data.entity.Company;
import com.springboot.new_java.data.entity.Employment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("employmentRepositorySupport")
public interface EmploymentRepository extends JpaRepository<Employment,Long>, EmploymentRepositoryCustom {

    Employment findByUid(Long uid);


    Employment findByNameAndCompanyAndUsed(String name, Company company, Long used);

}
