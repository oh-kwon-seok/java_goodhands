package com.springboot.new_java.data.dao;


import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.equipment.EquipmentDto;
import com.springboot.new_java.data.entity.Equipment;

import java.util.List;
import java.util.Map;


public interface EquipmentDAO {
    Equipment insertEquipment(EquipmentDto equipmentDto);

    List<Equipment> selectTotalEquipment(CommonInfoSearchDto commonInfoSearchDto);
    List<Equipment> selectEquipment(CommonInfoSearchDto commonInfoSearchDto);

    Equipment updateEquipment(EquipmentDto equipmentDto) throws Exception;

    String deleteEquipment(List<Long> uid) throws Exception;

    String excelUploadEquipment(List<Map<String, Object>> requestList) throws Exception;


}
