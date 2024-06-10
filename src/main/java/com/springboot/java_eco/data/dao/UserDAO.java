package com.springboot.java_eco.data.dao;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.entity.User;

import java.util.List;
import java.util.Map;

public interface UserDAO {

    List<User> selectTotalUser(CommonInfoSearchDto userSearchDto);

    List<User> selectUser(CommonInfoSearchDto userSearchDto);

    String excelUploadUser(List<Map<String, Object>> requestList) throws Exception;

}
