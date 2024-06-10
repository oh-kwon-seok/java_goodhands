package com.springboot.java_eco.data.repository.workTaskPacking;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.workTaskPacking.WorkTaskPackingSearchDto;
import com.springboot.java_eco.data.entity.WorkTaskPacking;

import java.util.List;

public interface WorkTaskPackingRepositoryCustom {


    List<WorkTaskPacking> findAll(CommonSearchDto commonSearchDto);
    List<WorkTaskPacking> findInfo(CommonInfoSearchDto commonInfoSearchDto);


    List<WorkTaskPacking> findByWorkTaskUidSelect(WorkTaskPackingSearchDto workTaskPackingSearchDto);

}
