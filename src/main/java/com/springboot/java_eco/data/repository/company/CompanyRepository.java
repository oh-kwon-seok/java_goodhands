package com.springboot.java_eco.data.repository.company;

import com.springboot.java_eco.data.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("companyRepositorySupport")
public interface CompanyRepository extends JpaRepository<Company,Long>, CompanyRepositoryCustom {

    Company findByUid(Long uid);
    Company findByCode(String code);


}
