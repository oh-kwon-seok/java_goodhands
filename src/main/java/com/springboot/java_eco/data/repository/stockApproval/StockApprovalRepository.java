package com.springboot.java_eco.data.repository.stockApproval;


import com.springboot.java_eco.data.entity.StockApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("stockApprovalRepositorySupport")
public interface StockApprovalRepository extends JpaRepository<StockApproval,String>, StockApprovalRepositoryCustom {

    StockApproval findByUid(Long uid);

}
