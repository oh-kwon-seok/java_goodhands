package com.springboot.new_java.data.repository.process;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.entity.Process;

import java.util.List;

public interface ProcessRepositoryCustom {


    List<Process> findAll(CommonInfoSearchDto commonInfoSearchDto);
    List<Process> findInfo(CommonInfoSearchDto commonInfoSearchDto);




}
