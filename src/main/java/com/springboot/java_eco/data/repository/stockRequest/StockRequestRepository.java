package com.springboot.java_eco.data.repository.stockRequest;


import com.springboot.java_eco.data.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("stockRequestRepositorySupport")
public interface StockRequestRepository extends JpaRepository<StockRequest,String>, StockRequestRepositoryCustom {

    StockRequest findByUid(Long uid);

}
