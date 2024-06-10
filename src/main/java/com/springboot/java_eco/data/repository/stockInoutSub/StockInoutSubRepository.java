package com.springboot.java_eco.data.repository.stockInoutSub;


import com.springboot.java_eco.data.entity.StockInoutSub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("stockInoutSubRepositorySupport")
public interface StockInoutSubRepository extends JpaRepository<StockInoutSub,String>, StockInoutSubRepositoryCustom {

    StockInoutSub getById(String user_id);
    StockInoutSub findByUid(Long uid);

    List<StockInoutSub> findByStockInoutUid(Long uid);


}
