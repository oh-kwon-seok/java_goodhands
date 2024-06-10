package com.springboot.java_eco.data.repository.equipmentRuntime;


import com.springboot.java_eco.data.entity.Company;
import com.springboot.java_eco.data.entity.EquipmentRuntime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("equipmentRuntimeRepositorySupport")
public interface EquipmentRuntimeRepository extends JpaRepository<EquipmentRuntime,String>, EquipmentRuntimeRepositoryCustom {

    EquipmentRuntime findByUid(Long uid);


}
