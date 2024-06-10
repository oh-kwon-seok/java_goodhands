package com.springboot.java_eco.data.repository.type;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.entity.Type;

import java.util.List;

public interface TypeRepositoryCustom {
    List<Type> findAll(CommonInfoSearchDto typeSearchDto);
    List<Type> findInfo(CommonInfoSearchDto typeSearchDto);

}
