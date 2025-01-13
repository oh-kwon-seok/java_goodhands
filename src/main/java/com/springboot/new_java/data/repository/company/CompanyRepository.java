package com.springboot.new_java.data.repository.company;

import com.springboot.new_java.data.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("companyRepositorySupport")
public interface CompanyRepository extends JpaRepository<Company,Long>, CompanyRepositoryCustom {

    Company findByUid(Long uid);
    Company findByCode(String code);


}
