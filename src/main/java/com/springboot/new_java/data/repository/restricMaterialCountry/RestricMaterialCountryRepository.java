package com.springboot.new_java.data.repository.restricMaterialCountry;


import com.springboot.new_java.data.entity.RestricMaterialCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("restricMaterialCountryRepositorySupport")
public interface RestricMaterialCountryRepository extends JpaRepository<RestricMaterialCountry,Long>, RestricMaterialCountryRepositoryCustom {





}
