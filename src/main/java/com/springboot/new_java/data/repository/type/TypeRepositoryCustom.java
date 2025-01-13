package com.springboot.new_java.data.repository.type;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.entity.Type;

import java.util.List;

public interface TypeRepositoryCustom {
    List<Type> findAll(CommonInfoSearchDto typeSearchDto);
    List<Type> findInfo(CommonInfoSearchDto typeSearchDto);

}
