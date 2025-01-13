package com.springboot.new_java.data.dao;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.workTaskPacking.WorkTaskPackingSearchDto;
import com.springboot.new_java.data.entity.WorkTaskPacking;

import java.util.List;


public interface WorkTaskPackingDAO {


    List<WorkTaskPacking> selectWorkTaskPacking(CommonInfoSearchDto commonInfoSearchDto);
    List<WorkTaskPacking> selectTotalWorkTaskPacking(CommonSearchDto commonSearchDto);

    List<WorkTaskPacking> selectWorkTaskUidSelect(WorkTaskPackingSearchDto workTaskPackingSearchDto);

}
