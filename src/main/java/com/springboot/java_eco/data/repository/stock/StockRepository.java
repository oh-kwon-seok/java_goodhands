package com.springboot.java_eco.data.repository.stock;


import com.springboot.java_eco.data.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("stockRepositorySupport")
public interface StockRepository extends JpaRepository<Stock,String>, StockRepositoryCustom {

    Stock findByUid(Long uid);
    Stock findByLotAndItemAndCompanyAndFactoryAndFactorySub(String lot, Item item, Company company, Factory factory, FactorySub factorySub);

    Stock findByLotAndItemAndCompany(String lot, Item item, Company company);


}
