package com.springboot.new_java.data.repository.equipment;

import com.springboot.new_java.data.entity.Company;
import com.springboot.new_java.data.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("equipmentRepositorySupport")
public interface EquipmentRepository extends JpaRepository<Equipment,Long>, EquipmentRepositoryCustom {

    Equipment findByUid(Long uid);


    Equipment findByNameAndCompanyAndUsed(String name, Company company, Long used);

}
