package com.springboot.new_java.service;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.workTaskPacking.WorkTaskPackingSearchDto;
import com.springboot.new_java.data.entity.WorkTaskPacking;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WorkTaskPackingService {

    List<WorkTaskPacking> getWorkTaskPacking(CommonInfoSearchDto commonInfoSearchDto);

    List<WorkTaskPacking> getTotalWorkTaskPacking(CommonSearchDto commonSearchDto);


    List<WorkTaskPacking> getWorkTaskUidSelect(WorkTaskPackingSearchDto workTaskPackingSearchDto);


}
