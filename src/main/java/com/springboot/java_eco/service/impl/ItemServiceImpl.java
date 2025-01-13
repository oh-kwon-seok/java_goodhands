package com.springboot.java_eco.service.impl;

import com.springboot.java_eco.data.dao.ItemDAO;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.item.ItemDto;
import com.springboot.java_eco.data.entity.Item;
import com.springboot.java_eco.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemDAO ItemDAO;

    @Autowired
    public ItemServiceImpl(@Qualifier("itemDAOImpl") ItemDAO ItemDAO){
        this.ItemDAO = ItemDAO;
    }


    @Override
    public List<Item> getTotalItem(CommonInfoSearchDto commonInfoSearchDto){
        return ItemDAO.selectTotalItem(commonInfoSearchDto);
    }

    @Override
    public List<Item> getItem(CommonInfoSearchDto commonInfoSearchDto){
        return ItemDAO.selectItem(commonInfoSearchDto);
    }
    @Override
    public List<Item> getMaterial(CommonInfoSearchDto commonInfoSearchDto){
        return ItemDAO.selectMaterial(commonInfoSearchDto);
    }

    @Override
    public Item saveItem(ItemDto ItemDto, MultipartFile file_url) throws Exception {

        return ItemDAO.insertItem(ItemDto,file_url);

    }
    @Override
    public Item updateItem(ItemDto ItemDto,MultipartFile file_url) throws Exception {
        return ItemDAO.updateItem(ItemDto,file_url);
    }
    @Override
    public void deleteItem(List<Long> uid) throws Exception {
        ItemDAO.deleteItem(uid);
    }

    @Override
    public void excelUploadItem(List<Map<String, Object>> requestList) throws Exception {
        ItemDAO.excelUploadItem(requestList);
    }

}
