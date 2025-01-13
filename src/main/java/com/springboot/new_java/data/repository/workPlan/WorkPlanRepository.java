package com.springboot.new_java.data.repository.workPlan;


import com.springboot.new_java.data.entity.Bom;
import com.springboot.new_java.data.entity.Company;
import com.springboot.new_java.data.entity.WorkPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("workPlanRepositorySupport")
public interface WorkPlanRepository extends JpaRepository<WorkPlan,Long>, WorkPlanRepositoryCustom {

    WorkPlan findByUid(Long uid);
    Long countByBomAndCompanyAndStartDateAndUsed(Bom bom, Company company, String start_date,Long used);


}
