package com.springboot.java_eco.data.dao;



import com.springboot.java_eco.data.dto.processQc.ProcessQcSearchDto;
import com.springboot.java_eco.data.entity.ProcessQc;

import java.util.List;
import java.util.Map;


public interface ProcessQcDAO {


    List<ProcessQc> selectTotalProcessQc(ProcessQcSearchDto processQcSearchDto);


    List<ProcessQc> selectProcessQc(ProcessQcSearchDto processQcSearchDto);
    String excelUploadProcessQc(List<Map<String, Object>> requestList) throws Exception;


}
