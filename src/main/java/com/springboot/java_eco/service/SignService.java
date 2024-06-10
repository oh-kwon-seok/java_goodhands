package com.springboot.java_eco.service;

import com.springboot.java_eco.data.dto.SignInResultDto;
import com.springboot.java_eco.data.dto.SignUpResultDto;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.user.UserDto;
import com.springboot.java_eco.data.entity.User;

import java.util.List;
import java.util.Map;

public interface SignService {


    SignUpResultDto save(UserDto userDto);

    SignUpResultDto update(UserDto userDto);


    List<User> getTotalUser(CommonInfoSearchDto commonInfoSearchDto);
    List<User> getUser(CommonInfoSearchDto commonInfoSearchDto);

    String delete(List<String> id) throws Exception;
    SignInResultDto signIn(String userId, String password,String clientIp) throws RuntimeException;

    void excelUploadUser(List<Map<String, Object>> requestList) throws Exception;

}
