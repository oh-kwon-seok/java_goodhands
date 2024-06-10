package com.springboot.java_eco.data.dao;


import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.type.TypeDto;
import com.springboot.java_eco.data.entity.Type;

import java.util.List;


public interface TypeDAO {
    Type insertType(TypeDto typeDto);

    List<Type> selectTotalType(CommonInfoSearchDto commonInfoSearchDto);
    List<Type> selectType(CommonInfoSearchDto typeSearchDto);

    Type updateType(TypeDto typeDto) throws Exception;

    String deleteType(List<Long> uid) throws Exception;


}
