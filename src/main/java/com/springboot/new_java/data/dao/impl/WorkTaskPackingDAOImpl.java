package com.springboot.new_java.data.dao.impl;

import ch.qos.logback.classic.Logger;
import com.springboot.new_java.data.dao.WorkTaskPackingDAO;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.workTaskPacking.WorkTaskPackingSearchDto;
import com.springboot.new_java.data.entity.WorkTaskPacking;
import com.springboot.new_java.data.repository.company.CompanyRepository;
import com.springboot.new_java.data.repository.item.ItemRepository;
import com.springboot.new_java.data.repository.user.UserRepository;
import com.springboot.new_java.data.repository.workTask.WorkTaskRepository;
import com.springboot.new_java.data.repository.workTaskPacking.WorkTaskPackingRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WorkTaskPackingDAOImpl implements WorkTaskPackingDAO {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(WorkTaskPackingDAOImpl.class);

    private final WorkTaskPackingRepository workTaskPackingRepository;

    private final UserRepository userRepository;

    private final ItemRepository itemRepository;

    private final CompanyRepository companyRepository;

    private final WorkTaskRepository workTaskRepository;



    @Autowired
    public WorkTaskPackingDAOImpl(WorkTaskPackingRepository workTaskPackingRepository,
                                  WorkTaskRepository workTaskRepository,
                                  UserRepository userRepository,
                                  ItemRepository itemRepository,
                                  CompanyRepository companyRepository

                          ) {
        this.workTaskPackingRepository = workTaskPackingRepository;
        this.workTaskRepository = workTaskRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.companyRepository = companyRepository;
    }


    @Override
    public List<WorkTaskPacking> selectWorkTaskPacking(CommonInfoSearchDto commonInfoSearchDto) {
        return workTaskPackingRepository.findInfo(commonInfoSearchDto);

    }

    @Override
    public List<WorkTaskPacking> selectTotalWorkTaskPacking(CommonSearchDto commonSearchDto) {
        return workTaskPackingRepository.findAll(commonSearchDto);

    }
    @Override
    public List<WorkTaskPacking> selectWorkTaskUidSelect(WorkTaskPackingSearchDto workTaskPackingSearchDto) {
        return workTaskPackingRepository.findByWorkTaskUidSelect(workTaskPackingSearchDto);

    }



}