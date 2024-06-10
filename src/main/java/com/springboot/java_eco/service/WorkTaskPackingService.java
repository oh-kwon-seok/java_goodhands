package com.springboot.java_eco.service;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.workTaskPacking.WorkTaskPackingSearchDto;
import com.springboot.java_eco.data.entity.WorkTaskPacking;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WorkTaskPackingService {

    List<WorkTaskPacking> getWorkTaskPacking(CommonInfoSearchDto commonInfoSearchDto);

    List<WorkTaskPacking> getTotalWorkTaskPacking(CommonSearchDto commonSearchDto);


    List<WorkTaskPacking> getWorkTaskUidSelect(WorkTaskPackingSearchDto workTaskPackingSearchDto);


}
