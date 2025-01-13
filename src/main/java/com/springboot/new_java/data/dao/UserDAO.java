package com.springboot.new_java.data.dao;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.entity.User;

import java.util.List;
import java.util.Map;

public interface UserDAO {

    List<User> selectTotalUser(CommonInfoSearchDto userSearchDto);

    List<User> selectUser(CommonInfoSearchDto userSearchDto);

    String excelUploadUser(List<Map<String, Object>> requestList) throws Exception;

}
