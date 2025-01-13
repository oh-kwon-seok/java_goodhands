package com.springboot.new_java.data.repository.bom;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.entity.Bom;

import java.util.List;

public interface BomRepositoryCustom {
    List<Bom> findAll(CommonInfoSearchDto commonInfoSearchDto);
    List<Bom> findInfo(CommonInfoSearchDto commonInfoSearchDto);

}
