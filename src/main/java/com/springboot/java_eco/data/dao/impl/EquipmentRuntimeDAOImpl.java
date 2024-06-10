package com.springboot.java_eco.data.dao.impl;

import ch.qos.logback.classic.Logger;
import com.springboot.java_eco.common.CommonResponse;
import com.springboot.java_eco.data.dao.EquipmentRuntimeDAO;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.equipmentRuntime.EquipmentRuntimeDto;
import com.springboot.java_eco.data.entity.*;
import com.springboot.java_eco.data.repository.company.CompanyRepository;
import com.springboot.java_eco.data.repository.equipment.EquipmentRepository;
import com.springboot.java_eco.data.repository.equipmentRuntime.EquipmentRuntimeRepository;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class EquipmentRuntimeDAOImpl implements EquipmentRuntimeDAO {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(EquipmentRuntimeDAOImpl.class);

    private final EquipmentRuntimeRepository equipmentRuntimeRepository;
    private final EquipmentRepository equipmentRepository;


    private final CompanyRepository companyRepository;


    @Autowired
    public EquipmentRuntimeDAOImpl(EquipmentRuntimeRepository equipmentRuntimeRepository,
                                   EquipmentRepository equipmentRepository,
                                   CompanyRepository companyRepository
    ) {
        this.equipmentRuntimeRepository = equipmentRuntimeRepository;
        this.equipmentRepository = equipmentRepository;
        this.companyRepository = companyRepository;


    }

    @Override
    public CommonResultDto insertEquipmentRuntime(EquipmentRuntimeDto equipmentRuntimeDto) throws Exception {

        Company company = companyRepository.findByUid(equipmentRuntimeDto.getCompany_uid());
        Equipment equipment = equipmentRepository.findByUid(equipmentRuntimeDto.getEquipment_uid());


        EquipmentRuntime equipmentRuntime = new EquipmentRuntime();

        equipmentRuntime.setCompany(company);
        equipmentRuntime.setEquipment(equipment);

        equipmentRuntime.setStart_time(equipmentRuntimeDto.getStart_time());
        equipmentRuntime.setEnd_time(equipmentRuntimeDto.getEnd_time());
        equipmentRuntime.setRuntime_second(equipmentRuntimeDto.getRuntime_second());

        equipmentRuntime.setUsed(1L);

        equipmentRuntime.setCreated(LocalDateTime.now());

        EquipmentRuntime insertEquipmentRuntime = equipmentRuntimeRepository.save(equipmentRuntime);

        Long uid = insertEquipmentRuntime.getUid();
        CommonResultDto CommonResultDto = new CommonResultDto();
        if (uid != null) {
            setSuccessResult(CommonResultDto);
            return CommonResultDto;
        } else {
            setFailResult(CommonResultDto);
            return CommonResultDto;
        }
    }


    @Override
    public CommonResultDto updateEquipmentRuntime(EquipmentRuntimeDto equipmentRuntimeDto) throws Exception {


        Company company = companyRepository.findByUid(equipmentRuntimeDto.getCompany_uid());


        Optional<EquipmentRuntime> selectedEquipmentRuntime = equipmentRuntimeRepository.findById(String.valueOf(equipmentRuntimeDto.getUid()));

        EquipmentRuntime updatedEquipmentRuntime;
        CommonResultDto CommonResultDto = new CommonResultDto();
        if (selectedEquipmentRuntime.isPresent()) {
            EquipmentRuntime equipmentRuntime = selectedEquipmentRuntime.get();
            equipmentRuntime.setCompany(company);
            equipmentRuntime.setStart_time(equipmentRuntimeDto.getStart_time());
            equipmentRuntime.setEnd_time(equipmentRuntimeDto.getEnd_time());
            equipmentRuntime.setRuntime_second(equipmentRuntimeDto.getRuntime_second());

            equipmentRuntime.setUsed(equipmentRuntimeDto.getUsed());
            equipmentRuntime.setUpdated(LocalDateTime.now());
            updatedEquipmentRuntime = equipmentRuntimeRepository.save(equipmentRuntime);
            Long uid = updatedEquipmentRuntime.getUid();
            if (uid != null) {
                setSuccessResult(CommonResultDto);
                return CommonResultDto;
            } else {
                setFailResult(CommonResultDto);
                return CommonResultDto;
            }


            }else{
            setFailResult(CommonResultDto);
            return CommonResultDto;

        }

    }


    @Override
    public List<EquipmentRuntime> selectEquipmentRuntime(CommonInfoSearchDto commonInfoSearchDto) {
        return equipmentRuntimeRepository.findInfo(commonInfoSearchDto);

    }

    @Override
    public List<EquipmentRuntime> selectTotalEquipmentRuntime(CommonSearchDto commonSearchDto) {
        return equipmentRuntimeRepository.findAll(commonSearchDto);

    }

    @Override
    public String deleteEquipmentRuntime(List<Long> uids) throws Exception {

        for (Long uid : uids) {
            Optional<EquipmentRuntime> selectedEquipmentRuntime = Optional.ofNullable(equipmentRuntimeRepository.findByUid(uid));
            if (selectedEquipmentRuntime.isPresent()) {
                EquipmentRuntime equipmentRuntime = selectedEquipmentRuntime.get();
                equipmentRuntime.setUsed(0L);
                equipmentRuntime.setDeleted(LocalDateTime.now());
                equipmentRuntimeRepository.save(equipmentRuntime);
            } else {
                throw new Exception("EquipmentRuntime with UID " + uid + " not found.");
            }
        }
        return "EquipmentRuntimes deleted successfully";
    }


    private void setSuccessResult(CommonResultDto result){
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }
    private void setFailResult(CommonResultDto result){
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
    }

}