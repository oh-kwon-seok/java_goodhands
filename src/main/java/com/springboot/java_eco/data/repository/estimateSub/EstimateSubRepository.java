package com.springboot.java_eco.data.repository.estimateSub;


import com.springboot.java_eco.data.entity.EstimateSub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("estimateSubRepositorySupport")
public interface EstimateSubRepository extends JpaRepository<EstimateSub,String>, EstimateSubRepositoryCustom {

    EstimateSub getById(String user_id);
    EstimateSub findByUid(Long uid);

    List<EstimateSub> findByEstimateUid(Long uid);


}
