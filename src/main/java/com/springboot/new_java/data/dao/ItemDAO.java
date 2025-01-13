package com.springboot.new_java.data.dao;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.item.ItemDto;
import com.springboot.new_java.data.entity.Item;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.Map;


public interface ItemDAO {



    Item insertItem(ItemDto itemDto, MultipartFile file_url) throws Exception;


    List<Item> selectTotalItem(CommonInfoSearchDto commonInfoSearchDto);

    List<Item> selectItem(CommonInfoSearchDto commonInfoSearchDto);

    List<Item> selectMaterial(CommonInfoSearchDto commonInfoSearchDto);

    Item updateItem(ItemDto itemDto, MultipartFile file_url) throws Exception;

    String deleteItem(List<Long> uid) throws Exception;


    String excelUploadItem(List<Map<String, Object>> requestList) throws Exception;



}
