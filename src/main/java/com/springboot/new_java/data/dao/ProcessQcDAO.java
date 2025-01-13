package com.springboot.new_java.data.dao;



import com.springboot.new_java.data.dto.processQc.ProcessQcSearchDto;
import com.springboot.new_java.data.entity.ProcessQc;

import java.util.List;
import java.util.Map;


public interface ProcessQcDAO {


    List<ProcessQc> selectTotalProcessQc(ProcessQcSearchDto processQcSearchDto);


    List<ProcessQc> selectProcessQc(ProcessQcSearchDto processQcSearchDto);
    String excelUploadProcessQc(List<Map<String, Object>> requestList) throws Exception;


}
