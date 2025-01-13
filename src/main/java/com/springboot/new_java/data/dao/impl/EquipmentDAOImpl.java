package com.springboot.new_java.data.dao.impl;

import com.springboot.new_java.data.dao.EquipmentDAO;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.equipment.EquipmentDto;
import com.springboot.new_java.data.entity.Company;
import com.springboot.new_java.data.entity.Equipment;
import com.springboot.new_java.data.repository.company.CompanyRepository;
import com.springboot.new_java.data.repository.equipment.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class EquipmentDAOImpl implements EquipmentDAO {

    private final EquipmentRepository equipmentRepository;
    private final CompanyRepository companyRepository;

    @Autowired
    public EquipmentDAOImpl(EquipmentRepository equipmentRepository, CompanyRepository companyRepository){

        this.equipmentRepository = equipmentRepository;
        this.companyRepository = companyRepository;
    }

    public Equipment insertEquipment(EquipmentDto equipmentDto) {

        Company company = companyRepository.findByUid(equipmentDto.getCompany_uid());


        Optional<Equipment> selectedEquipment = Optional.ofNullable(equipmentRepository.findByNameAndCompanyAndUsed(equipmentDto.getName(), company, 1L));


        if (selectedEquipment.isPresent()) {
            Equipment equipment = selectedEquipment.get();
            equipment.setCompany(company);
            equipment.setName(equipmentDto.getName());
            equipment.setPurpose(equipmentDto.getPurpose());
            equipment.setDescription(equipmentDto.getDescription());
            equipment.setUsed(Math.toIntExact(equipmentDto.getUsed()));
            equipment.setUpdated(LocalDateTime.now());
            return equipmentRepository.save(equipment);
        } else {
            Equipment equipment = new Equipment();
            equipment.setCompany(company);
            equipment.setName(equipmentDto.getName());
            equipment.setPurpose(equipmentDto.getPurpose());
            equipment.setDescription(equipmentDto.getDescription());
            equipment.setUsed(Math.toIntExact(equipmentDto.getUsed()));
            equipment.setCreated(LocalDateTime.now());

            return equipmentRepository.save(equipment);

        }

    }
    @Override
    public List<Equipment> selectTotalEquipment(CommonInfoSearchDto commonInfoSearchDto) {
        return equipmentRepository.findAll(commonInfoSearchDto);

    }

    @Override
    public List<Equipment> selectEquipment(CommonInfoSearchDto commonInfoSearchDto) {
        return equipmentRepository.findInfo(commonInfoSearchDto);

    }


    @Override
    public Equipment updateEquipment(EquipmentDto equipmentDto) throws Exception {

        Company company = companyRepository.findByUid(equipmentDto.getCompany_uid());
        Optional<Equipment> selectedEquipment = equipmentRepository.findById(equipmentDto.getUid());

        Equipment updatedEquipment;

        if (selectedEquipment.isPresent()) {
            Equipment equipment = selectedEquipment.get();

            equipment.setCompany(company);
            equipment.setName(equipmentDto.getName());
            equipment.setPurpose(equipmentDto.getPurpose());
            equipment.setDescription(equipmentDto.getDescription());
            equipment.setUsed(Math.toIntExact(equipmentDto.getUsed()));

            equipment.setUpdated(LocalDateTime.now());
            updatedEquipment = equipmentRepository.save(equipment);
        } else {
            throw new Exception();
        }
        return updatedEquipment;
    }
    @Override
    public String deleteEquipment(List<Long> uids) throws Exception {

        for (Long uid : uids) {
            Optional<Equipment> selectedEquipment = equipmentRepository.findById(uid);
            if (selectedEquipment.isPresent()) {
                Equipment equipment = selectedEquipment.get();
                equipment.setUsed(0);
                equipment.setDeleted(LocalDateTime.now());
                equipmentRepository.save(equipment);
            } else {
                throw new Exception("Equipment with UID " + uid + " not found.");
            }
        }
        return "Equipments deleted successfully";
    }
    @Override
    public String excelUploadEquipment(List<Map<String, Object>> requestList) throws Exception {

        for (Map<String, Object> data : requestList) {

            String name = String.valueOf(data.get("name"));
            String purpose = String.valueOf(data.get("purpose"));
            String description = String.valueOf(data.get("description"));


            String companyCode = String.valueOf(data.get("company_code"));

            Company company = companyRepository.findByCode(companyCode);

            // 예시로 이름과 수량이 모두 일치하는 Equipment를 찾는 메서드를 가정
            Optional<Equipment> selectedEquipment = Optional.ofNullable(equipmentRepository.findByNameAndCompanyAndUsed(name, company,1L));
            if (company != null) {
                if (selectedEquipment.isPresent()) {
                    Equipment equipment = selectedEquipment.get();
                    equipment.setCompany(company);
                    equipment.setName(name);
                    equipment.setPurpose(purpose);
                    equipment.setDescription(description);
                    equipment.setUsed(1);
                    equipment.setUpdated(LocalDateTime.now());
                    equipmentRepository.save(equipment);
                } else {
                    Equipment equipment = new Equipment();

                    equipment.setCompany(company);
                    equipment.setName(name);
                    equipment.setPurpose(purpose);
                    equipment.setDescription(description);
                    equipment.setUsed(1);

                    equipment.setCreated(LocalDateTime.now());
                    equipmentRepository.save(equipment);


                }

            }


        }
        return "Equipments deleted successfully";
    }
}
