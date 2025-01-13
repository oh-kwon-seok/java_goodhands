package com.springboot.new_java.data.repository.equipmentRuntime;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.entity.EquipmentRuntime;

import java.util.List;

public interface EquipmentRuntimeRepositoryCustom {


    List<EquipmentRuntime> findAll(CommonSearchDto commonSearchDto);
    List<EquipmentRuntime> findInfo(CommonInfoSearchDto commonInfoSearchDto);




}
