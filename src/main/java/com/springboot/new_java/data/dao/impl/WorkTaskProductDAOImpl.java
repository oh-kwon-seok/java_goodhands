package com.springboot.new_java.data.dao.impl;

import ch.qos.logback.classic.Logger;
import com.springboot.new_java.data.dao.WorkTaskProductDAO;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.workTaskProduct.WorkTaskProductSearchDto;
import com.springboot.new_java.data.entity.WorkTaskProduct;
import com.springboot.new_java.data.repository.company.CompanyRepository;
import com.springboot.new_java.data.repository.item.ItemRepository;
import com.springboot.new_java.data.repository.workTaskProduct.WorkTaskProductRepository;
import com.springboot.new_java.data.repository.user.UserRepository;
import com.springboot.new_java.data.repository.workTask.WorkTaskRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WorkTaskProductDAOImpl implements WorkTaskProductDAO {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(WorkTaskProductDAOImpl.class);

    private final WorkTaskProductRepository workTaskProductRepository;

    private final UserRepository userRepository;

    private final ItemRepository itemRepository;

    private final CompanyRepository companyRepository;

    private final WorkTaskRepository workTaskRepository;



    @Autowired
    public WorkTaskProductDAOImpl(WorkTaskProductRepository workTaskProductRepository,
                                  WorkTaskRepository workTaskRepository,
                                  UserRepository userRepository,
                                  ItemRepository itemRepository,
                                  CompanyRepository companyRepository

                          ) {
        this.workTaskProductRepository = workTaskProductRepository;
        this.workTaskRepository = workTaskRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.companyRepository = companyRepository;
    }


    @Override
    public List<WorkTaskProduct> selectWorkTaskProduct(CommonInfoSearchDto commonInfoSearchDto) {
        return workTaskProductRepository.findInfo(commonInfoSearchDto);

    }

    @Override
    public List<WorkTaskProduct> selectTotalWorkTaskProduct(CommonSearchDto commonSearchDto) {
        return workTaskProductRepository.findAll(commonSearchDto);

    }
    @Override
    public List<WorkTaskProduct> selectWorkTaskUidSelect(WorkTaskProductSearchDto workTaskProductSearchDto) {
        return workTaskProductRepository.findByWorkTaskUidSelect(workTaskProductSearchDto);

    }



}