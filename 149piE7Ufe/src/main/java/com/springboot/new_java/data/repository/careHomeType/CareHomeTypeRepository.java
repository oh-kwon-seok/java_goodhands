package com.springboot.new_java.data.repository.careHomeType;


import com.springboot.new_java.data.entity.care.CareHome;
import com.springboot.new_java.data.entity.care.CareHomeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("careHomeTypeRepositorySupport")
public interface CareHomeTypeRepository extends JpaRepository<CareHomeType,Long>, CareHomeTypeRepositoryCustom {

    CareHomeType findByUid(Long uid);


    CareHomeType findByNameAndUsed(String name, Boolean used);

}
