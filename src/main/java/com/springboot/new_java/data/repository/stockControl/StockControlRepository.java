package com.springboot.new_java.data.repository.stockControl;


import com.springboot.new_java.data.entity.StockControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("stockControlRepositorySupport")
public interface StockControlRepository extends JpaRepository<StockControl,String>, StockControlRepositoryCustom {

    StockControl findByUid(Long uid);


}
