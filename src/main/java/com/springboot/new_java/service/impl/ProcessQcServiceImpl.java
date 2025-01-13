package com.springboot.new_java.service.impl;

import com.springboot.new_java.data.dao.ProcessQcDAO;
import com.springboot.new_java.data.dto.processQc.ProcessQcSearchDto;
import com.springboot.new_java.data.entity.ProcessQc;
import com.springboot.new_java.service.ProcessQcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProcessQcServiceImpl implements ProcessQcService {
    private final ProcessQcDAO processQcDAO;

    @Autowired
    public ProcessQcServiceImpl(@Qualifier("processQcDAOImpl") ProcessQcDAO processQcDAO){
        this.processQcDAO = processQcDAO;
    }

    @Override
    public List<ProcessQc> getTotalProcessQc(ProcessQcSearchDto processQcSearchDto){
        return processQcDAO.selectTotalProcessQc(processQcSearchDto);
    }

  

    @Override
    public List<ProcessQc> getProcessQc(ProcessQcSearchDto processQcSearchDto){
        return processQcDAO.selectProcessQc(processQcSearchDto);
    }
    @Override
    public void excelUploadProcessQc(List<Map<String, Object>> requestList) throws Exception {
        processQcDAO.excelUploadProcessQc(requestList);
    }



}
