package com.springboot.java_eco.data.repository.stockRecord;


import com.springboot.java_eco.data.entity.Company;
import com.springboot.java_eco.data.entity.Stock;
import com.springboot.java_eco.data.entity.StockRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("stockRecordRepositorySupport")
public interface StockRecordRepository extends JpaRepository<StockRecord,String>, StockRecordRepositoryCustom {

    StockRecord findByUid(Long uid);


}
