package com.springboot.java_eco.data.repository.bom;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.entity.Bom;

import java.util.List;

public interface BomRepositoryCustom {
    List<Bom> findAll(CommonInfoSearchDto commonInfoSearchDto);
    List<Bom> findInfo(CommonInfoSearchDto commonInfoSearchDto);

}
