package com.springboot.new_java.data.repository.equipment;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.entity.Equipment;

import java.util.List;

public interface EquipmentRepositoryCustom {
    List<Equipment> findAll(CommonInfoSearchDto commonInfoSearchDto);
    List<Equipment> findInfo(CommonInfoSearchDto commonInfoSearchDto);

}
