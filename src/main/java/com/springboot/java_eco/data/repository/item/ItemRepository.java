package com.springboot.java_eco.data.repository.item;

import com.springboot.java_eco.data.entity.Company;
import com.springboot.java_eco.data.entity.Factory;
import com.springboot.java_eco.data.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("itemRepositorySupport")
public interface ItemRepository extends JpaRepository<Item,Long>, ItemRepositoryCustom {


    Item findByUid(Long uid);

    Item findByCodeAndUsed(String code,Long used);

    Item findByCodeAndUsedAndCompany(String code, Long used, Company company);



}
