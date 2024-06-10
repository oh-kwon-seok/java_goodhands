package com.springboot.java_eco.data.dao.impl;

import ch.qos.logback.classic.Logger;
import com.springboot.java_eco.data.dao.StockApprovalDAO;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.stockApproval.StockApprovalSearchDto;
import com.springboot.java_eco.data.entity.StockApproval;
import com.springboot.java_eco.data.repository.company.CompanyRepository;
import com.springboot.java_eco.data.repository.item.ItemRepository;
import com.springboot.java_eco.data.repository.stockApproval.StockApprovalRepository;
import com.springboot.java_eco.data.repository.user.UserRepository;
import com.springboot.java_eco.data.repository.workTask.WorkTaskRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StockApprovalDAOImpl implements StockApprovalDAO {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(StockApprovalDAOImpl.class);

    private final StockApprovalRepository stockApprovalRepository;

    private final UserRepository userRepository;

    private final ItemRepository itemRepository;

    private final CompanyRepository companyRepository;

    private final WorkTaskRepository workTaskRepository;



    @Autowired
    public StockApprovalDAOImpl(StockApprovalRepository stockApprovalRepository,
                                WorkTaskRepository workTaskRepository,
                                UserRepository userRepository,
                                ItemRepository itemRepository,
                                CompanyRepository companyRepository

                          ) {
        this.stockApprovalRepository = stockApprovalRepository;
        this.workTaskRepository = workTaskRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.companyRepository = companyRepository;
    }


    @Override
    public List<StockApproval> selectStockApproval(CommonInfoSearchDto commonInfoSearchDto) {
        return stockApprovalRepository.findInfo(commonInfoSearchDto);

    }

    @Override
    public List<StockApproval> selectTotalStockApproval(CommonSearchDto commonSearchDto) {
        return stockApprovalRepository.findAll(commonSearchDto);

    }
    @Override
    public List<StockApproval> selectWorkTaskUidSelect(StockApprovalSearchDto stockApprovalSearchDto) {
        return stockApprovalRepository.findByWorkTaskUidSelect(stockApprovalSearchDto);

    }



}