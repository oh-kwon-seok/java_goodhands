package com.springboot.new_java.service;

import com.springboot.new_java.data.dto.SignInResultDto;
import com.springboot.new_java.data.dto.SignUpResultDto;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.user.UserDto;
import com.springboot.new_java.data.entity.User;

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
