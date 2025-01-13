package com.springboot.new_java.data.dao.impl;

import ch.qos.logback.classic.Logger;
import com.springboot.new_java.common.CommonResponse;
import com.springboot.new_java.data.dao.WorkPlanDAO;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.workPlan.WorkPlanDto;
import com.springboot.new_java.data.entity.*;
import com.springboot.new_java.data.repository.bom.BomRepository;
import com.springboot.new_java.data.repository.company.CompanyRepository;
import com.springboot.new_java.data.repository.workPlan.WorkPlanRepository;
import com.springboot.new_java.data.repository.user.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class WorkPlanDAOImpl implements WorkPlanDAO {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(WorkPlanDAOImpl.class);

    private final WorkPlanRepository workPlanRepository;


    private final BomRepository bomRepository;

    private final CompanyRepository companyRepository;

    private final UserRepository userRepository;

    @Autowired
    public WorkPlanDAOImpl(WorkPlanRepository workPlanRepository,
                           BomRepository bomRepository,
                           CompanyRepository companyRepository,
                           UserRepository userRepository

                          ) {
        this.workPlanRepository = workPlanRepository;
        this.bomRepository = bomRepository;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CommonResultDto insertWorkPlan(WorkPlanDto workPlanDto) throws Exception {

        Company company = companyRepository.findByUid(workPlanDto.getCompany_uid());
        Bom bom = bomRepository.findByUid(workPlanDto.getBom_uid());
        User user = userRepository.getById(workPlanDto.getUser_id());
        Long countWorkPlan = workPlanRepository.countByBomAndCompanyAndStartDateAndUsed(bom,company,workPlanDto.getStartDate(),1L);



        String code = bom.getCode().toString() +workPlanDto.getStartDate().replace("-", "") + "-"+(countWorkPlan + 1);
        LOGGER.info("생산계획갯수 : {}", countWorkPlan);
        LOGGER.info("날짜 : {}", workPlanDto.getStartDate());
        LOGGER.info("생산계획코드 : {}", code);
        WorkPlan workPlan = new WorkPlan();

        workPlan.setBom(bom);
        workPlan.setCode(code);
        workPlan.setCompany(company);
        workPlan.setUser(user);

        workPlan.setQty(workPlanDto.getQty());
        workPlan.setUnit(workPlanDto.getUnit());
        workPlan.setStatus("준비");
        workPlan.setStartDate(workPlanDto.getStartDate());
        workPlan.setEndDate(workPlanDto.getEndDate());

        workPlan.setCreated(LocalDateTime.now());
        workPlan.setUsed(1L);

        WorkPlan insertWorkPlan = workPlanRepository.save(workPlan);

        Long uid = insertWorkPlan.getUid();
        CommonResultDto CommonResultDto = new CommonResultDto();

        if(uid != null){
            setSuccessResult(CommonResultDto);
            return CommonResultDto;


            }else{
                setFailResult(CommonResultDto);
                return CommonResultDto;
            }
    }

    @Override
    public CommonResultDto updateWorkPlan(WorkPlanDto workPlanDto) throws Exception {

        Company company = companyRepository.findByUid(workPlanDto.getCompany_uid());
        Bom bom = bomRepository.findByUid(workPlanDto.getBom_uid());
        User user = userRepository.getById(workPlanDto.getUser_id());


        Optional<WorkPlan> selectedWorkPlan = workPlanRepository.findById(workPlanDto.getUid());

        WorkPlan updatedWorkPlan;
        CommonResultDto CommonResultDto = new CommonResultDto();
        if (selectedWorkPlan.isPresent()) {
            WorkPlan workPlan = selectedWorkPlan.get();
            workPlan.setBom(bom);
            workPlan.setCompany(company);
            workPlan.setUser(user);
            workPlan.setQty(workPlanDto.getQty());
            workPlan.setUnit(workPlanDto.getUnit());
            workPlan.setStatus("준비");
            workPlan.setStartDate(workPlanDto.getStartDate());
            workPlan.setEndDate(workPlanDto.getEndDate());

            workPlan.setCreated(LocalDateTime.now());
            workPlan.setUsed(workPlanDto.getUsed());
            workPlan.setUpdated(LocalDateTime.now());
            updatedWorkPlan = workPlanRepository.save(workPlan);

            Long uid = updatedWorkPlan.getUid();

            setSuccessResult(CommonResultDto);
            return CommonResultDto;

        } else {

            setFailResult(CommonResultDto);
            return CommonResultDto;
        }

    }

    @Override
    public List<WorkPlan> selectWorkPlan(CommonInfoSearchDto commonInfoSearchDto) {
        return workPlanRepository.findInfo(commonInfoSearchDto);

    }

    @Override
    public List<WorkPlan> selectTotalWorkPlan(CommonSearchDto commonSearchDto) {
        return workPlanRepository.findAll(commonSearchDto);

    }

    @Override
    public String deleteWorkPlan(List<Long> uids) throws Exception {

        for (Long uid : uids) {
            Optional<WorkPlan> selectedWorkPlan = Optional.ofNullable(workPlanRepository.findByUid(uid));
            if (selectedWorkPlan.isPresent()) {
                WorkPlan workPlan = selectedWorkPlan.get();
                workPlan.setUsed(0L);
                workPlan.setDeleted(LocalDateTime.now());

                workPlanRepository.save(workPlan);
            } else {
                throw new Exception("WORKPLAN with UID " + uid + " not found.");
            }
        }
        return "WORKPLAN deleted successfully";
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