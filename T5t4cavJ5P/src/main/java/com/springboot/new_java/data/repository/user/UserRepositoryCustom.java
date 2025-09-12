package com.springboot.new_java.data.repository.user;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.entity.User;

import java.util.List;

public interface UserRepositoryCustom {



    List<User> findAll(CommonInfoSearchDto commonInfoSearchDto);
    List<User> findInfo(CommonInfoSearchDto commonInfoSearchDto);



}
