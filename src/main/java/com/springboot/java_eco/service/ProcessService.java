package com.springboot.java_eco.service;

import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.process.ProcessDto;
import com.springboot.java_eco.data.entity.Process;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ProcessService {

    List<Process> getProcess(CommonInfoSearchDto commonInfoSearchDto);

    List<Process> getTotalProcess(CommonInfoSearchDto commonInfoSearchDto);


    CommonResultDto saveProcess(ProcessDto processDto) throws Exception;

    CommonResultDto updateProcess(ProcessDto processDto) throws Exception;

    void deleteProcess(List<Long> uid) throws Exception;

    void excelUploadProcess(List<Map<String, Object>> requestList) throws Exception;

}
