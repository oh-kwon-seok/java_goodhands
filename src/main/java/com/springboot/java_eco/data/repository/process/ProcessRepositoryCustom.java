package com.springboot.java_eco.data.repository.process;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.entity.Process;

import java.util.List;

public interface ProcessRepositoryCustom {


    List<Process> findAll(CommonInfoSearchDto commonInfoSearchDto);
    List<Process> findInfo(CommonInfoSearchDto commonInfoSearchDto);




}
