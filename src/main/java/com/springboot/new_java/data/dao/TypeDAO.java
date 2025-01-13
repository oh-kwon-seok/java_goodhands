package com.springboot.new_java.data.dao;


import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.type.TypeDto;
import com.springboot.new_java.data.entity.Type;

import java.util.List;


public interface TypeDAO {
    Type insertType(TypeDto typeDto);

    List<Type> selectTotalType(CommonInfoSearchDto commonInfoSearchDto);
    List<Type> selectType(CommonInfoSearchDto typeSearchDto);

    Type updateType(TypeDto typeDto) throws Exception;

    String deleteType(List<Long> uid) throws Exception;


}
