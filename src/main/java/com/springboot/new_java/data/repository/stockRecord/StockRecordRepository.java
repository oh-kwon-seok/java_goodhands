package com.springboot.new_java.data.repository.stockRecord;


import com.springboot.new_java.data.entity.StockRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("stockRecordRepositorySupport")
public interface StockRecordRepository extends JpaRepository<StockRecord,String>, StockRecordRepositoryCustom {

    StockRecord findByUid(Long uid);


}
