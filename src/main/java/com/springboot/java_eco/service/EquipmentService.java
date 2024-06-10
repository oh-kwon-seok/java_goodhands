package com.springboot.java_eco.service;


import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.equipment.EquipmentDto;
import com.springboot.java_eco.data.entity.Equipment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface EquipmentService {

    List<Equipment> getTotalEquipment(CommonInfoSearchDto commonInfoSearchDto);

    List<Equipment> getEquipment(CommonInfoSearchDto commonInfoSearchDto);


    Equipment saveEquipment(EquipmentDto equipmentDto) throws Exception;

    Equipment updateEquipment(EquipmentDto equipmentDto) throws Exception;

    void deleteEquipment(List<Long> uid) throws Exception;

    void excelUploadEquipment(List<Map<String, Object>> requestList) throws Exception;


}
