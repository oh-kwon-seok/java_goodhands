package com.springboot.new_java.service;


import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.type.TypeDto;

import com.springboot.new_java.data.entity.Type;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TypeService {

    List<Type> getTotalType(CommonInfoSearchDto commonInfoSearchDto);

    List<Type> getType(CommonInfoSearchDto commonInfoSearchDto);


    Type saveType(TypeDto typeDto) throws Exception;

    Type updateType(TypeDto typeDto) throws Exception;

    void deleteType(List<Long> uid) throws Exception;


}
