package com.springboot.java_eco.data.repository.bom;

import com.springboot.java_eco.data.entity.Company;
import com.springboot.java_eco.data.entity.Bom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("bomRepositorySupport")
public interface BomRepository extends JpaRepository<Bom,Long>, BomRepositoryCustom {

    Bom findByUid(Long uid);

   List<Bom> findByMain(Long uid);

    Bom findByCodeAndMainAndCompanyAndUsed(String code, Long main, Company company, Long used);
    Bom findByCodeAndCompanyAndUsed(String code, Company company, Long used);

}
