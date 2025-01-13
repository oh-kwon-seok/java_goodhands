package com.springboot.new_java.data.repository.stockApproval;


import com.springboot.new_java.data.entity.StockApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("stockApprovalRepositorySupport")
public interface StockApprovalRepository extends JpaRepository<StockApproval,String>, StockApprovalRepositoryCustom {

    StockApproval findByUid(Long uid);

}
