package com.springboot.java_eco.data.repository.restricMaterialCountry;


import com.springboot.java_eco.data.entity.RestricMaterialCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("restricMaterialCountryRepositorySupport")
public interface RestricMaterialCountryRepository extends JpaRepository<RestricMaterialCountry,Long>, RestricMaterialCountryRepositoryCustom {





}
