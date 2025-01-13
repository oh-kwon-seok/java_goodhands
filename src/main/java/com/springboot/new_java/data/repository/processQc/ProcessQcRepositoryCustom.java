package com.springboot.new_java.data.repository.processQc;


import com.springboot.new_java.data.dto.processQc.ProcessQcSearchDto;
import com.springboot.new_java.data.entity.ProcessQc;

import java.util.List;

public interface ProcessQcRepositoryCustom {



    List<ProcessQc> findAll(ProcessQcSearchDto processQcSearchDto);


    List<ProcessQc> findInfo(ProcessQcSearchDto processQcSearchDto);

}
