package com.springboot.new_java.data.repository.workTaskPacking;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.workTaskPacking.WorkTaskPackingSearchDto;
import com.springboot.new_java.data.entity.WorkTaskPacking;

import java.util.List;

public interface WorkTaskPackingRepositoryCustom {


    List<WorkTaskPacking> findAll(CommonSearchDto commonSearchDto);
    List<WorkTaskPacking> findInfo(CommonInfoSearchDto commonInfoSearchDto);


    List<WorkTaskPacking> findByWorkTaskUidSelect(WorkTaskPackingSearchDto workTaskPackingSearchDto);

}
