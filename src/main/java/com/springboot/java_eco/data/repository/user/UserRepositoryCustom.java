package com.springboot.java_eco.data.repository.user;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.entity.User;

import java.util.List;

public interface UserRepositoryCustom {



    List<User> findAll(CommonInfoSearchDto commonInfoSearchDto);
    List<User> findInfo(CommonInfoSearchDto commonInfoSearchDto);



}
