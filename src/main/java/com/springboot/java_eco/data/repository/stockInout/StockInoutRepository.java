package com.springboot.java_eco.data.repository.stockInout;


import com.springboot.java_eco.data.entity.Company;
import com.springboot.java_eco.data.entity.StockInout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("stockInoutRepositorySupport")
public interface StockInoutRepository extends JpaRepository<StockInout,String>, StockInoutRepositoryCustom {

    StockInout getById(String user_id);
    StockInout findByUid(Long uid);


}
