package com.springboot.java_eco.data.repository.restricMaterial;

import com.springboot.java_eco.data.entity.RestricMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("restricMaterialRepositorySupport")
public interface RestricMaterialRepository extends JpaRepository<RestricMaterial,Long>, RestricMaterialRepositoryCustom {





}
