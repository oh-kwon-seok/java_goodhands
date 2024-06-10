package com.springboot.java_eco.data.repository.item;

import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.entity.Item;

import java.util.List;

public interface ItemRepositoryCustom {
    List<Item> findAll(CommonInfoSearchDto commonInfoSearchDto);
    List<Item> findInfo(CommonInfoSearchDto commonInfoSearchDto);
    List<Item> findMaterial(CommonInfoSearchDto commonInfoSearchDto);

}
