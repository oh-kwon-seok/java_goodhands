package com.springboot.java_eco.data.repository.type;

import com.springboot.java_eco.data.entity.Company;
import com.springboot.java_eco.data.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("typeRepositorySupport")
public interface TypeRepository extends JpaRepository<Type,Long>, TypeRepositoryCustom {

    Type findByUid(Long uid);

    Type findByNameAndCompanyAndUsed(String name, Company company, Long used);
}
