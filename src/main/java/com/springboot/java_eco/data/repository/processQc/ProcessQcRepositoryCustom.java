package com.springboot.java_eco.data.repository.processQc;


import com.springboot.java_eco.data.dto.processQc.ProcessQcSearchDto;
import com.springboot.java_eco.data.entity.ProcessQc;

import java.util.List;

public interface ProcessQcRepositoryCustom {



    List<ProcessQc> findAll(ProcessQcSearchDto processQcSearchDto);


    List<ProcessQc> findInfo(ProcessQcSearchDto processQcSearchDto);

}
