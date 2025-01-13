package com.springboot.new_java.data.repository.cosmeticMaterial;

import com.springboot.new_java.data.entity.CosmeticMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("cosmeticMaterialRepositorySupport")
public interface CosmeticMaterialRepository extends JpaRepository<CosmeticMaterial,Long>, CosmeticMaterialRepositoryCustom {





}
