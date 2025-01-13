package com.springboot.new_java.data.repository.equipmentRuntime;


import com.springboot.new_java.data.entity.EquipmentRuntime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("equipmentRuntimeRepositorySupport")
public interface EquipmentRuntimeRepository extends JpaRepository<EquipmentRuntime,String>, EquipmentRuntimeRepositoryCustom {

    EquipmentRuntime findByUid(Long uid);


}
