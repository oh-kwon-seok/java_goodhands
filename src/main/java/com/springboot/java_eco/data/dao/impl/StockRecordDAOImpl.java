package com.springboot.java_eco.data.dao.impl;

import ch.qos.logback.classic.Logger;
import com.springboot.java_eco.common.CommonResponse;
import com.springboot.java_eco.data.dao.StockRecordDAO;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;

import com.springboot.java_eco.data.dto.stockRecord.StockRecordDto;
import com.springboot.java_eco.data.entity.*;
import com.springboot.java_eco.data.repository.company.CompanyRepository;
import com.springboot.java_eco.data.repository.factory.FactoryRepository;
import com.springboot.java_eco.data.repository.factorySub.FactorySubRepository;
import com.springboot.java_eco.data.repository.item.ItemRepository;

import com.springboot.java_eco.data.repository.stockRecord.StockRecordRepository;

import com.springboot.java_eco.data.repository.stockInout.StockInoutRepository;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class StockRecordDAOImpl implements StockRecordDAO {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(StockRecordDAOImpl.class);

    private final StockRecordRepository stockRecordRepository;

    private final StockInoutRepository stockInoutRepository;

    private final ItemRepository itemRepository;

    private final CompanyRepository companyRepository;

    private final FactoryRepository factoryRepository;

    private final FactorySubRepository factorySubRepository;

    @Autowired
    public StockRecordDAOImpl(StockRecordRepository stockRecordRepository,
                              StockInoutRepository stockInoutRepository,
                              FactoryRepository factoryRepository,
                              FactorySubRepository factorySubRepository,
                              ItemRepository itemRepository,
                              CompanyRepository companyRepository

                          ) {
        this.stockRecordRepository = stockRecordRepository;
        this.factoryRepository = factoryRepository;
        this.factorySubRepository = factorySubRepository;
        this.stockInoutRepository = stockInoutRepository;
        this.itemRepository = itemRepository;

        this.companyRepository = companyRepository;
    }

    @Override
    public CommonResultDto insertStockRecord(StockRecordDto stockRecordDto) throws Exception {

        Company company = companyRepository.findByUid(stockRecordDto.getCompany_uid());
        Item item = itemRepository.findByUid(stockRecordDto.getItem_uid());
        Factory outFactory = factoryRepository.findByUid(stockRecordDto.getOut_factory_uid());
        FactorySub outFactorySub = factorySubRepository.findByUid(stockRecordDto.getOut_factory_sub_uid());

        Factory inFactory = factoryRepository.findByUid(stockRecordDto.getIn_factory_uid());
        FactorySub inFactorySub = factorySubRepository.findByUid(stockRecordDto.getIn_factory_sub_uid());


        StockRecord stockRecord = new StockRecord();

        stockRecord.setItem(item);
        stockRecord.setCompany(company);

        stockRecord.setOutFactory(outFactory);
        stockRecord.setOutFactorySub(outFactorySub);

        stockRecord.setInFactory(inFactory);
        stockRecord.setInFactorySub(inFactorySub);

        stockRecord.setLot(stockRecordDto.getLot());
        stockRecord.setQty(stockRecordDto.getQty());
        stockRecord.setUnit(stockRecordDto.getUnit());
        stockRecord.setType(stockRecordDto.getType());
        stockRecord.setStatus(stockRecordDto.getStatus());
        stockRecord.setReason(stockRecordDto.getReason());
        stockRecord.setCreated(LocalDateTime.now());

        StockRecord insertStockRecord = stockRecordRepository.save(stockRecord);

        Long uid = insertStockRecord.getUid();

        StockRecord selectedStockRecord = stockRecordRepository.findByUid(uid);

        CommonResultDto CommonResultDto = new CommonResultDto();
        if (selectedStockRecord != null) {

            setSuccessResult(CommonResultDto);
            return CommonResultDto;

        }else {
            setFailResult(CommonResultDto);
            return CommonResultDto;

        }
    }

    @Override
    public List<StockRecord> selectStockRecord(CommonInfoSearchDto commonInfoSearchDto) {
        return stockRecordRepository.findInfo(commonInfoSearchDto);

    }

    @Override
    public List<StockRecord> selectTotalStockRecord(CommonSearchDto commonSearchDto) {
        return stockRecordRepository.findAll(commonSearchDto);

    }

    @Override
    public String deleteStockRecord(List<Long> uids) throws Exception {

        for (Long uid : uids) {
            Optional<StockRecord> selectedStockRecord = Optional.ofNullable(stockRecordRepository.findByUid(uid));
            if (selectedStockRecord.isPresent()) {
                StockRecord stockRecord = selectedStockRecord.get();
                stockRecordRepository.delete(stockRecord);
            } else {
                throw new Exception("Stock with UID " + uid + " not found.");
            }
        }
        return "Stocks deleted successfully";
    }


    private void setSuccessResult(CommonResultDto result){
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }
    private void setFailResult(CommonResultDto result){
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
    }

}