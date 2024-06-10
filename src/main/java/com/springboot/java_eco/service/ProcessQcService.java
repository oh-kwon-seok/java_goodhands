package com.springboot.java_eco.service;



import com.springboot.java_eco.data.dto.processQc.ProcessQcSearchDto;
import com.springboot.java_eco.data.entity.ProcessQc;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ProcessQcService {

    List<ProcessQc> getTotalProcessQc(ProcessQcSearchDto processQcSearchDto);

    List<ProcessQc> getProcessQc(ProcessQcSearchDto processQcSearchDto);

    void excelUploadProcessQc(List<Map<String, Object>> requestList) throws Exception;

}
