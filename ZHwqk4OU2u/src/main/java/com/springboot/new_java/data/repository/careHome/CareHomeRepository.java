package com.springboot.new_java.data.repository.careHome;


import com.springboot.new_java.data.entity.care.CareHome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("careHomeRepositorySupport")
public interface CareHomeRepository extends JpaRepository<CareHome,Long>, CareHomeRepositoryCustom {

    CareHome findByUid(Long uid);


    CareHome findByNameAndUsed(String name, Boolean used);

}
