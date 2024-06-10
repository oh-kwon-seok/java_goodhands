package com.springboot.java_eco.data.dao.impl;

import ch.qos.logback.classic.Logger;
import com.springboot.java_eco.common.CommonResponse;
import com.springboot.java_eco.data.dao.ProcessDAO;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.process.ProcessDto;
import com.springboot.java_eco.data.entity.*;
import com.springboot.java_eco.data.entity.Process;
import com.springboot.java_eco.data.repository.company.CompanyRepository;
import com.springboot.java_eco.data.repository.process.ProcessRepository;
import com.springboot.java_eco.data.repository.processQc.ProcessQcRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class ProcessDAOImpl implements ProcessDAO {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(ProcessDAOImpl.class);

    private final ProcessRepository processRepository;
    private final ProcessQcRepository processQcRepository;
    private final CompanyRepository companyRepository;

    
    @Autowired
    public ProcessDAOImpl(ProcessRepository processRepository,
                          ProcessQcRepository processQcRepository,
                          CompanyRepository companyRepository
                          ) {
        this.processRepository = processRepository;
        this.processQcRepository = processQcRepository;
        this.companyRepository = companyRepository;

     
    }

    @Override
    public CommonResultDto insertProcess(ProcessDto processDto) throws Exception {

        Company company = companyRepository.findByUid(processDto.getCompany_uid());

        Process process = new Process();
        process.setCompany(company);
        process.setName(processDto.getName());
        process.setStatus(processDto.getStatus());
        process.setProcess_qc_array(String.valueOf(processDto.getProcess_qc_array()));
        process.setDescription(processDto.getDescription());
        process.setUsed(Math.toIntExact(processDto.getUsed()));
        process.setCreated(LocalDateTime.now());

        Process insertProcess = processRepository.save(process);

        Long uid = insertProcess.getUid();

        LOGGER.info("[uid] : {}",uid);
        Process selectedProcess = processRepository.findByUid(uid);

        List<Map<String, Object>> processQcList = processDto.getProcess_qc();

        LOGGER.info("[Process] : {}",selectedProcess);
        CommonResultDto CommonResultDto = new CommonResultDto();
        LOGGER.info("[processQcList] : {}",processQcList);
        if (processQcList != null) {

            for (Map<String, Object> processQcData : processQcList) {
                ProcessQc processQc = new ProcessQc();
                processQc.setProcess(selectedProcess);
                processQc.setName(processQcData.get("name").toString());
                processQc.setType(Integer.valueOf(processQcData.get("type").toString()));
                processQc.setDescription(processQcData.get("description").toString());
                processQc.setCreated(LocalDateTime.now());
                processQc.setUsed(1);

                processQcRepository.save(processQc);
            }



            setSuccessResult(CommonResultDto);
            return CommonResultDto;

        }else {
            setFailResult(CommonResultDto);
            return CommonResultDto;

        }
    }


    @Override
    public CommonResultDto updateProcess(ProcessDto processDto) throws Exception {


        Company company = companyRepository.findByUid(processDto.getCompany_uid());

        Optional<Process> selectedProcess = processRepository.findById(String.valueOf(processDto.getUid()));

        Process updatedProcess;

        if (selectedProcess.isPresent()) {
            Process process = selectedProcess.get();
            process.setCompany(company);
            process.setName(processDto.getName());
            process.setStatus(processDto.getStatus());
            process.setProcess_qc_array(String.valueOf(processDto.getProcess_qc_array()));
            process.setDescription(processDto.getDescription());
            process.setUsed(Math.toIntExact(processDto.getUsed()));
            process.setCreated(LocalDateTime.now());
            updatedProcess = processRepository.save(process);



        } else {
            throw new Exception();
        }


        List<Map<String, Object>> processQcList = processDto.getProcess_qc();

        LOGGER.info("[Process] : {}",selectedProcess);
        CommonResultDto CommonResultDto = new CommonResultDto();

        if (processQcList != null) {

            List<ProcessQc> deletedData = processQcRepository.findByProcessUid(processDto.getUid());
            processQcRepository.deleteAll(deletedData);



            for (Map<String, Object> processQcData : processQcList) {
                ProcessQc processQc = new ProcessQc();
                processQc.setProcess(updatedProcess);

                processQc.setName(processQcData.get("name").toString());
                processQc.setType(Integer.valueOf(processQcData.get("type").toString()));
                processQc.setDescription(processQcData.get("description").toString());
                processQc.setUsed(1);


                processQc.setCreated(LocalDateTime.now());
                processQc.setUpdated(LocalDateTime.now());

                processQcRepository.save(processQc);
            }



            setSuccessResult(CommonResultDto);
            return CommonResultDto;

        }else {
            setFailResult(CommonResultDto);
            return CommonResultDto;

        }
    }


    @Override
    public List<Process> selectProcess(CommonInfoSearchDto commonInfoSearchDto) {
        return processRepository.findInfo(commonInfoSearchDto);

    }

    @Override
    public List<Process> selectTotalProcess(CommonInfoSearchDto commonInfoSearchDto) {
        return processRepository.findAll(commonInfoSearchDto);

    }

    @Override
    public String deleteProcess(List<Long> uids) throws Exception {

        for (Long uid : uids) {
            Optional<Process> selectedProcess = Optional.ofNullable(processRepository.findByUid(uid));
            if (selectedProcess.isPresent()) {
                Process process = selectedProcess.get();
                process.setUsed(0);
                process.setDeleted(LocalDateTime.now());
                processRepository.save(process);
            } else {
                throw new Exception("Process with UID " + uid + " not found.");
            }
        }
        return "Processs deleted successfully";
    }

    @Override
    public String excelUploadProcess(List<Map<String, Object>> requestList) throws Exception {

        for (Map<String, Object> data : requestList) {

            String name = String.valueOf(data.get("name"));
            String status = String.valueOf(data.get("status"));
            String description = String.valueOf(data.get("description"));


            Long company_uid = Long.valueOf((String) data.get("company"));

            Company company = companyRepository.findByUid(company_uid);

            LOGGER.info("company111:{}",company);

            if (company != null) {

                Optional<Process> selectedProcess = Optional.ofNullable(processRepository.findByNameAndCompanyAndUsed(name, company, 1L));

                if (selectedProcess.isPresent()) {
                    Process process = selectedProcess.get();

                    process.setCompany(company);
                    process.setName(name);
                    process.setStatus(status);
                    process.setDescription(description);
                    process.setUsed(1);

                    process.setUpdated(LocalDateTime.now());
                    processRepository.save(process);
                    LOGGER.info("process111:{}",process);
                } else {
                    Process process = new Process();

                    process.setCompany(company);
                    process.setName(name);
                    process.setStatus(status);
                    process.setDescription(description);
                    process.setUsed(1);
                    process.setCreated(LocalDateTime.now());
                    processRepository.save(process);

                    LOGGER.info("process222:{}",process);
                }

            }


        }
        return "Process uploaded successfully";
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