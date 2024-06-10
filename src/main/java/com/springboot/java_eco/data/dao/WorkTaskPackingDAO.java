package com.springboot.java_eco.data.dao;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.workTaskPacking.WorkTaskPackingSearchDto;
import com.springboot.java_eco.data.entity.WorkTaskPacking;

import java.util.List;


public interface WorkTaskPackingDAO {


    List<WorkTaskPacking> selectWorkTaskPacking(CommonInfoSearchDto commonInfoSearchDto);
    List<WorkTaskPacking> selectTotalWorkTaskPacking(CommonSearchDto commonSearchDto);

    List<WorkTaskPacking> selectWorkTaskUidSelect(WorkTaskPackingSearchDto workTaskPackingSearchDto);

}
