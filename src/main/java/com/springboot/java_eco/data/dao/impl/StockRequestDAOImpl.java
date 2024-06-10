package com.springboot.java_eco.data.dao.impl;

import ch.qos.logback.classic.Logger;
import com.springboot.java_eco.common.CommonResponse;
import com.springboot.java_eco.data.dao.StockRequestDAO;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;

import com.springboot.java_eco.data.dto.stockRequest.StockRequestSearchDto;
import com.springboot.java_eco.data.entity.*;
import com.springboot.java_eco.data.repository.company.CompanyRepository;

import com.springboot.java_eco.data.repository.item.ItemRepository;
import com.springboot.java_eco.data.repository.stockRequest.StockRequestRepository;
import com.springboot.java_eco.data.repository.user.UserRepository;
import com.springboot.java_eco.data.repository.workTask.WorkTaskRepository;
import org.hibernate.jdbc.Work;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class StockRequestDAOImpl implements StockRequestDAO {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(StockRequestDAOImpl.class);

    private final StockRequestRepository stockRequestRepository;

    private final UserRepository userRepository;

    private final ItemRepository itemRepository;

    private final CompanyRepository companyRepository;

    private final WorkTaskRepository workTaskRepository;



    @Autowired
    public StockRequestDAOImpl(StockRequestRepository stockRequestRepository,
                               WorkTaskRepository workTaskRepository,
                               UserRepository userRepository,
                               ItemRepository itemRepository,
                               CompanyRepository companyRepository

                          ) {
        this.stockRequestRepository = stockRequestRepository;
        this.workTaskRepository = workTaskRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.companyRepository = companyRepository;
    }


    @Override
    public List<StockRequest> selectStockRequest(CommonInfoSearchDto commonInfoSearchDto) {
        return stockRequestRepository.findInfo(commonInfoSearchDto);

    }

    @Override
    public List<StockRequest> selectTotalStockRequest(CommonSearchDto commonSearchDto) {
        return stockRequestRepository.findAll(commonSearchDto);

    }
    @Override
    public List<StockRequest> selectWorkTaskUidSelect(StockRequestSearchDto stockRequestSearchDto) {
        return stockRequestRepository.findByWorkTaskUidSelect(stockRequestSearchDto);

    }



}