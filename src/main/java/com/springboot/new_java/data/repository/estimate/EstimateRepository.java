package com.springboot.new_java.data.repository.estimate;


import com.springboot.new_java.data.entity.Estimate;
import com.springboot.new_java.data.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("estimateRepositorySupport")
public interface EstimateRepository extends JpaRepository<Estimate,String>, EstimateRepositoryCustom {

    Estimate getById(String user_id);
    Estimate findByUid(Long uid);
    Estimate findByNameAndCompanyAndUsed(String name, Company company, Long used);

}
