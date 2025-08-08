package com.springboot.new_java.data.repository.careHomeType;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.entity.care.CareHomeType;

import java.util.List;

public interface CareHomeTypeRepositoryCustom {
    List<CareHomeType> findAll(CommonInfoSearchDto commonInfoSearchDto);
    List<CareHomeType> findInfo(CommonInfoSearchDto commonInfoSearchDto);

}
