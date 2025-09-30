package com.springboot.new_java.data.repository.senior;


import com.springboot.new_java.data.entity.senior.Senior;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("SeniorRepositorySupport")
public interface SeniorRepository extends JpaRepository<Senior,Long>, SeniorRepositoryCustom {

    Senior findByUid(Long uid);


    Senior findByNameAndUsed(String name, Boolean used);

}
