package com.springboot.java_eco.data.dao.impl;

import ch.qos.logback.classic.Logger;
import com.springboot.java_eco.data.dao.ItemDAO;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.item.ItemDto;
import com.springboot.java_eco.data.entity.*;
import com.springboot.java_eco.data.repository.company.CompanyRepository;
import com.springboot.java_eco.data.repository.item.ItemRepository;
import com.springboot.java_eco.data.repository.type.TypeRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class ItemDAOImpl implements ItemDAO {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(ItemDAOImpl.class);

    private final ItemRepository itemRepository;
    private final CompanyRepository companyRepository;
    private final TypeRepository typeRepository;





    @Autowired
    public ItemDAOImpl(ItemRepository itemRepository,
                       CompanyRepository companyRepository,
                       TypeRepository typeRepository
                       ) {
        this.itemRepository = itemRepository;
        this.companyRepository = companyRepository;
        this.typeRepository = typeRepository;

    }

    @Override
    public Item insertItem(ItemDto itemDto) throws Exception {

        Company company = companyRepository.findByUid(itemDto.getCompany_uid());
        Type type = typeRepository.findByUid(itemDto.getType_uid());

        Optional<Item> selectedItem = Optional.ofNullable(itemRepository.findByCodeAndUsed(itemDto.getCode(),1L));



        if (selectedItem.isPresent()) {
            Item item = selectedItem.get();

            item.setCode(itemDto.getCode());
            item.setSimple_code(itemDto.getSimple_code());
            item.setIngr_kor_name(itemDto.getIngr_kor_name());
            item.setIngr_eng_name(itemDto.getIngr_eng_name());
            item.setInout_unit(itemDto.getInout_unit());
            item.setInout_type(itemDto.getInout_type());
            item.setCurrency_unit(itemDto.getCurrency_unit());
            item.setBuy_type(itemDto.getBuy_type());
            item.setType_code(itemDto.getType_code());
            item.setClassify_code(itemDto.getClassify_code());
            item.setComponent_code(itemDto.getComponent_code());
            item.setHs_code(itemDto.getHs_code());
            item.setNts_code(itemDto.getNts_code());
            item.setDescription(itemDto.getDescription());
            item.setType(type);

            item.setImage_url(String.valueOf(itemDto.getImage_url()));

            item.setUsed(1);
            item.setCompany(company);
            item.setUpdated(LocalDateTime.now());

            return itemRepository.save(item);
        } else {
            Item item = new Item();

            item.setCode(itemDto.getCode());
            item.setSimple_code(itemDto.getSimple_code());
            item.setIngr_kor_name(itemDto.getIngr_kor_name());
            item.setIngr_eng_name(itemDto.getIngr_eng_name());
            item.setInout_unit(itemDto.getInout_unit());
            item.setInout_type(itemDto.getInout_type());
            item.setCurrency_unit(itemDto.getCurrency_unit());
            item.setBuy_type(itemDto.getBuy_type());
            item.setType_code(itemDto.getType_code());
            item.setClassify_code(itemDto.getClassify_code());
            item.setComponent_code(itemDto.getComponent_code());
            item.setHs_code(itemDto.getHs_code());
            item.setNts_code(itemDto.getNts_code());
            item.setDescription(itemDto.getDescription());
            item.setType(type);

            item.setImage_url(String.valueOf(itemDto.getImage_url()));

            item.setUsed(1);
            item.setCompany(company);
            item.setCreated(LocalDateTime.now());

            return itemRepository.save(item);

        }

    }

    @Override
    public List<Item> selectTotalItem(CommonInfoSearchDto commonInfoSearchDto) {
        return itemRepository.findAll(commonInfoSearchDto);

    }
    @Override
    public List<Item> selectItem(CommonInfoSearchDto commonInfoSearchDto) {
        return itemRepository.findInfo(commonInfoSearchDto);

    }
    @Override
    public List<Item> selectMaterial(CommonInfoSearchDto commonInfoSearchDto) {
        return itemRepository.findMaterial(commonInfoSearchDto);

    }

    @Override
    public Item updateItem(ItemDto itemDto) throws Exception {

        Company company = companyRepository.findByUid(itemDto.getCompany_uid());
        Type type = typeRepository.findByUid(itemDto.getType_uid());


        Optional<Item> selectedItem = itemRepository.findById(itemDto.getUid());

        Item updatedItem;

        if (selectedItem.isPresent()) {
            Item item = selectedItem.get();
            item.setCode(itemDto.getCode());
            item.setSimple_code(itemDto.getSimple_code());
            item.setIngr_kor_name(itemDto.getIngr_kor_name());
            item.setIngr_eng_name(itemDto.getIngr_eng_name());
            item.setInout_unit(itemDto.getInout_unit());
            item.setInout_type(itemDto.getInout_type());
            item.setCurrency_unit(itemDto.getCurrency_unit());
            item.setBuy_type(itemDto.getBuy_type());
            item.setType_code(itemDto.getType_code());
            item.setClassify_code(itemDto.getClassify_code());
            item.setComponent_code(itemDto.getComponent_code());
            item.setHs_code(itemDto.getHs_code());
            item.setNts_code(itemDto.getNts_code());
            item.setDescription(itemDto.getDescription());
            item.setType(type);
            item.setCompany(company);

            item.setImage_url(String.valueOf(itemDto.getImage_url()));

            item.setUsed(Math.toIntExact(itemDto.getUsed()));
            item.setUpdated(LocalDateTime.now());
            updatedItem = itemRepository.save(item);
        } else {
            throw new Exception();
        }
        return updatedItem;
    }

    @Override
    public String deleteItem(List<Long> uids) throws Exception {

        for (Long uid : uids) {
            Optional<Item> selectedItem = itemRepository.findById(uid);
            if (selectedItem.isPresent()) {
                Item item = selectedItem.get();
                item.setUsed(0);
                item.setDeleted(LocalDateTime.now());
                itemRepository.save(item);
            } else {
                throw new Exception("Item with UID " + uid + " not found.");
            }
        }
        return "Items deleted successfully";
    }

    @Override
    public String excelUploadItem(List<Map<String, Object>> requestList) throws Exception {

        for (Map<String, Object> data : requestList) {

            String code = String.valueOf(data.get("code"));
            String simple_code = String.valueOf(data.get("simple_code"));
            String ingr_kor_name = String.valueOf(data.get("ingr_kor_name"));
            String ingr_eng_name = String.valueOf(data.get("ingr_eng_name"));
            String inout_unit = String.valueOf(data.get("inout_unit"));
            String inout_type = String.valueOf(data.get("inout_type"));
            String currency_unit = String.valueOf(data.get("currency_unit"));
            String buy_type = String.valueOf(data.get("buy_type"));
            String type_code = String.valueOf(data.get("type_code"));
            String classify_code = String.valueOf(data.get("classify_code"));
            String component_code = String.valueOf(data.get("component_code"));
            String hs_code = String.valueOf(data.get("hs_code"));
            String nts_code = String.valueOf(data.get("nts_code"));
            String description = String.valueOf(data.get("description"));

            String companyCode = String.valueOf(data.get("company_code"));

            String typeName = String.valueOf(data.get("type_name"));

            Company company = companyRepository.findByCode(companyCode);


            if (company != null) {
                Type type = typeRepository.findByNameAndCompanyAndUsed(typeName, company, 1L);

                if (type != null) {
                    Optional<Item> selectedItem = Optional.ofNullable(itemRepository.findByCodeAndUsed(code, 1L));

                    if (selectedItem.isPresent()) {
                        Item item = selectedItem.get();

                        item.setSimple_code(simple_code);
                        item.setIngr_kor_name(ingr_kor_name);
                        item.setIngr_eng_name(ingr_eng_name);
                        item.setInout_unit(inout_unit);
                        item.setInout_type(inout_type);
                        item.setCurrency_unit(currency_unit);
                        item.setBuy_type(buy_type);
                        item.setType_code(type_code);
                        item.setClassify_code(classify_code);
                        item.setComponent_code(component_code);
                        item.setHs_code(hs_code);
                        item.setNts_code(nts_code);
                        item.setDescription(description);
                        item.setType(type);
                        item.setCompany(company);
                        item.setUsed(1);
                        item.setUpdated(LocalDateTime.now());
                        itemRepository.save(item);
                    } else {
                        Item item = new Item();
                        item.setCode(code);
                        item.setSimple_code(simple_code);
                        item.setIngr_kor_name(ingr_kor_name);
                        item.setIngr_eng_name(ingr_eng_name);
                        item.setInout_unit(inout_unit);
                        item.setInout_type(inout_type);
                        item.setCurrency_unit(currency_unit);
                        item.setBuy_type(buy_type);
                        item.setType_code(type_code);
                        item.setClassify_code(classify_code);
                        item.setComponent_code(component_code);
                        item.setHs_code(hs_code);
                        item.setNts_code(nts_code);
                        item.setDescription(description);
                        item.setType(type);
                        item.setCompany(company);
                        item.setUsed(1);
                        item.setCreated(LocalDateTime.now());
                        itemRepository.save(item);


                    }


                }


            }

        }
        return "Items uploaded successfully";
    }
}