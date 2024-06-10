package com.springboot.java_eco.data.dao;

import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.process.ProcessDto;
import com.springboot.java_eco.data.entity.Process;

import java.util.List;
import java.util.Map;


public interface ProcessDAO {


    List<Process> selectProcess(CommonInfoSearchDto commonInfoSearchDto);
    List<Process> selectTotalProcess(CommonInfoSearchDto commonInfoSearchDto);


     CommonResultDto insertProcess(ProcessDto processDto)  throws Exception;

    CommonResultDto updateProcess(ProcessDto processDto) throws Exception;


    String deleteProcess(List<Long> uid) throws Exception;
    String excelUploadProcess(List<Map<String, Object>> requestList) throws Exception;



}
