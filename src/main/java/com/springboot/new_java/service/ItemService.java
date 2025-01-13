package com.springboot.new_java.service;


import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.item.ItemDto;

import com.springboot.new_java.data.entity.Item;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public interface ItemService {

    List<Item> getTotalItem(CommonInfoSearchDto commonInfoSearchDto);
    List<Item> getItem(CommonInfoSearchDto commonInfoSearchDto);
    List<Item> getMaterial(CommonInfoSearchDto commonInfoSearchDto);
    Item saveItem(ItemDto ItemDto, MultipartFile file_url) throws Exception;

    Item updateItem(ItemDto ItemDto, MultipartFile file_url) throws Exception;

    void deleteItem(List<Long> uid) throws Exception;

    void excelUploadItem(List<Map<String, Object>> requestList) throws Exception;


}
