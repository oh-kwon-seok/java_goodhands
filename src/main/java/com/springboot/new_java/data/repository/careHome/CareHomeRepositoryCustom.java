package com.springboot.new_java.data.repository.careHome;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;

import com.springboot.new_java.data.entity.care.CareHome;

import java.util.List;

public interface CareHomeRepositoryCustom {
    List<CareHome> findAll(CommonInfoSearchDto commonInfoSearchDto);
    List<CareHome> findInfo(CommonInfoSearchDto commonInfoSearchDto);

}
