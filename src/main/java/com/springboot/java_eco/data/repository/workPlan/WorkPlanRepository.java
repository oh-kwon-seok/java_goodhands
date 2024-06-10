package com.springboot.java_eco.data.repository.workPlan;


import com.springboot.java_eco.data.entity.Bom;
import com.springboot.java_eco.data.entity.Company;
import com.springboot.java_eco.data.entity.WorkPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("workPlanRepositorySupport")
public interface WorkPlanRepository extends JpaRepository<WorkPlan,Long>, WorkPlanRepositoryCustom {

    WorkPlan findByUid(Long uid);
    Long countByBomAndCompanyAndStartDateAndUsed(Bom bom, Company company, String start_date,Long used);


}
