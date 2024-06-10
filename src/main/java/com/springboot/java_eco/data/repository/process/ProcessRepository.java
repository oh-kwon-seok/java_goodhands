package com.springboot.java_eco.data.repository.process;


import com.springboot.java_eco.data.entity.Company;
import com.springboot.java_eco.data.entity.Process;
import com.springboot.java_eco.data.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("processRepositorySupport")
public interface ProcessRepository extends JpaRepository<Process,String>, ProcessRepositoryCustom {

    Process getById(String user_id);
    Process findByUid(Long uid);
    Process findByNameAndCompanyAndUsed(String name, Company company, Long used);

}
