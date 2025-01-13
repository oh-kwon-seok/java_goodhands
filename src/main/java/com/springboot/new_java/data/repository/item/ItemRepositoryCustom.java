package com.springboot.new_java.data.repository.item;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.entity.Item;

import java.util.List;

public interface ItemRepositoryCustom {
    List<Item> findAll(CommonInfoSearchDto commonInfoSearchDto);
    List<Item> findInfo(CommonInfoSearchDto commonInfoSearchDto);
    List<Item> findMaterial(CommonInfoSearchDto commonInfoSearchDto);

}
