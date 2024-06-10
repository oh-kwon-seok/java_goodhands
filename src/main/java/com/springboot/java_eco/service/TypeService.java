package com.springboot.java_eco.service;


import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.type.TypeDto;

import com.springboot.java_eco.data.entity.Type;
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
