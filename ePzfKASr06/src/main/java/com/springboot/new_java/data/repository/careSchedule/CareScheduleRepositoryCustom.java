package com.springboot.new_java.data.repository.careSchedule;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.entity.care.CareSchedule;

import java.util.List;

public interface CareScheduleRepositoryCustom {
    List<CareSchedule> findAll(CommonInfoSearchDto commonInfoSearchDto);
    List<CareSchedule> findInfo();

}
