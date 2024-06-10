package com.springboot.java_eco.service.impl;

import com.springboot.java_eco.data.dao.ProcessDAO;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.process.ProcessDto;
import com.springboot.java_eco.data.entity.Process;
import com.springboot.java_eco.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProcessServiceImpl implements ProcessService {
    private final ProcessDAO processDAO;

    @Autowired
    public ProcessServiceImpl(@Qualifier("processDAOImpl") ProcessDAO processDAO){
        this.processDAO = processDAO;
    }

    @Override
    public List<Process> getProcess(CommonInfoSearchDto commonInfoSearchDto){
        return processDAO.selectProcess(commonInfoSearchDto);
    }
    @Override
    public List<Process> getTotalProcess(CommonInfoSearchDto commonInfoSearchDto){
        return processDAO.selectTotalProcess(commonInfoSearchDto);
    }
    @Override
    public CommonResultDto saveProcess(ProcessDto processDto) throws Exception {

        return processDAO.insertProcess(processDto);

    }
    @Override
    public CommonResultDto updateProcess(ProcessDto processDto) throws Exception {
        return processDAO.updateProcess(processDto);
    }
    @Override
    public void deleteProcess(List<Long> uid) throws Exception {
        processDAO.deleteProcess(uid);
    }
    @Override
    public void excelUploadProcess(List<Map<String, Object>> requestList) throws Exception {
        processDAO.excelUploadProcess(requestList);
    }

}
