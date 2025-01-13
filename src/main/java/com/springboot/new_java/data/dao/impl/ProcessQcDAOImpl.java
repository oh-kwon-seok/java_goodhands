package com.springboot.new_java.data.dao.impl;

import com.springboot.new_java.data.dao.ProcessQcDAO;
import com.springboot.new_java.data.dto.processQc.ProcessQcSearchDto;
import com.springboot.new_java.data.entity.Company;
import com.springboot.new_java.data.entity.Process;
import com.springboot.new_java.data.entity.ProcessQc;
import com.springboot.new_java.data.repository.company.CompanyRepository;
import com.springboot.new_java.data.repository.process.ProcessRepository;
import com.springboot.new_java.data.repository.processQc.ProcessQcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class ProcessQcDAOImpl implements ProcessQcDAO {
    
    private final ProcessQcRepository processQcRepository;
    private final ProcessRepository processRepository;

    private final CompanyRepository companyRepository;


    @Autowired
    public ProcessQcDAOImpl(ProcessQcRepository processQcRepository, ProcessRepository processRepository,CompanyRepository companyRepository){
        this.processQcRepository = processQcRepository;
        this.processRepository = processRepository;
        this.companyRepository = companyRepository;

    }

    @Override
    public List<ProcessQc> selectTotalProcessQc(ProcessQcSearchDto processQcSearchDto) {
        return processQcRepository.findAll(processQcSearchDto);

    }


    @Override
    public List<ProcessQc> selectProcessQc(ProcessQcSearchDto processQcSearchDto) {
        return processQcRepository.findInfo(processQcSearchDto);

    }

    @Override
    public String excelUploadProcessQc(List<Map<String, Object>> requestList) throws Exception {

        for (Map<String, Object> data : requestList) {

            String process_name = String.valueOf(data.get("process_name"));

            String name = String.valueOf(data.get("name"));

            Integer type = Integer.valueOf(String.valueOf(data.get("type")));

            String description = String.valueOf(data.get("description"));


            Long company_uid = Long.valueOf((String) data.get("company"));

            Company company = companyRepository.findByUid(company_uid);


            if (company != null) {

                Optional<Process> selectedProcess = Optional.ofNullable(processRepository.findByNameAndCompanyAndUsed(process_name, company, 1L));

                if (selectedProcess.isPresent()) {
                    Process process = selectedProcess.get();

                    ProcessQc processQc = new ProcessQc();
                    processQc.setProcess(process);
                    processQc.setName(name);
                    processQc.setType(type);
                    processQc.setDescription(description);

                    processQc.setUsed(1);
                    processQc.setCreated(LocalDateTime.now());
                    processQcRepository.save(processQc);

                }

            }


        }
        return "Process uploaded successfully";
    }
}
