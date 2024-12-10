package com.springboot.java_eco.data.dao.impl;

import ch.qos.logback.classic.Logger;
import com.springboot.java_eco.common.CommonResponse;
import com.springboot.java_eco.data.dao.StockInoutDAO;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.stockInout.StockInoutDto;
import com.springboot.java_eco.data.entity.*;
import com.springboot.java_eco.data.repository.company.CompanyRepository;
import com.springboot.java_eco.data.repository.estimate.EstimateRepository;
import com.springboot.java_eco.data.repository.factory.FactoryRepository;
import com.springboot.java_eco.data.repository.factorySub.FactorySubRepository;
import com.springboot.java_eco.data.repository.item.ItemRepository;
import com.springboot.java_eco.data.repository.stock.StockRepository;
import com.springboot.java_eco.data.repository.stockInout.StockInoutRepository;
import com.springboot.java_eco.data.repository.stockInout.StockInoutRepository;
import com.springboot.java_eco.data.repository.stockInoutSub.StockInoutSubRepository;
import com.springboot.java_eco.data.repository.stockRecord.StockRecordRepository;
import com.springboot.java_eco.data.repository.user.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class StockInoutDAOImpl implements StockInoutDAO {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(StockInoutDAOImpl.class);

    private final StockInoutRepository stockInoutRepository;
    private final StockInoutSubRepository stockInoutSubRepository;

    private final StockRepository stockRepository;
    private final UserRepository userRepository;

    private final ItemRepository itemRepository;

    private final CompanyRepository companyRepository;
    
    private final FactoryRepository factoryRepository;
    private final FactorySubRepository factorySubRepository;
    private final StockRecordRepository stockRecordRepository;
    
    @Autowired
    public StockInoutDAOImpl(
                            StockInoutRepository stockInoutRepository,
                             StockInoutSubRepository stockInoutSubRepository,
                             StockRepository stockRepository,
                             StockRecordRepository stockRecordRepository,
                             UserRepository userRepository,
                             ItemRepository itemRepository,
                             CompanyRepository companyRepository,
                             FactoryRepository factoryRepository,
                             FactorySubRepository factorySubRepository
                          ) {
        this.stockInoutRepository = stockInoutRepository;
        this.stockInoutSubRepository = stockInoutSubRepository;
        this.stockRepository = stockRepository;
        this.stockRecordRepository = stockRecordRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.companyRepository = companyRepository;
        this.factoryRepository = factoryRepository;
        this.factorySubRepository = factorySubRepository;
        
    }

    @Override
    public CommonResultDto insertStockInout(StockInoutDto stockInoutDto) throws Exception {

        Company company = companyRepository.findByUid(stockInoutDto.getCompany_uid());


        User user = userRepository.getById(stockInoutDto.getUser_id());


        StockInout stockInout = new StockInout();

        stockInout.setUser(user);
        stockInout.setCompany(company);
        stockInout.setDoc_type(stockInoutDto.getDoc_type());
        stockInout.setStatus(stockInoutDto.getStatus());
        stockInout.setCreated(LocalDateTime.now());
        StockInout insertStockInout = stockInoutRepository.save(stockInout);
        Long uid = insertStockInout.getUid();


        LOGGER.info("[uid] : {}",uid);
        StockInout selectedStockInout = stockInoutRepository.findByUid(uid);

        List<Map<String, Object>> stockInoutSubList = stockInoutDto.getStock_inout_sub();

        CommonResultDto CommonResultDto = new CommonResultDto();
        if (stockInoutSubList != null) {

            for (Map<String, Object> stockInoutSubData : stockInoutSubList) {
                StockInoutSub stockInoutSub = new StockInoutSub();

                stockInoutSub.setStockInout(selectedStockInout);
                if (stockInoutSubData.containsKey("item_uid")) {
                    Long itemUid = Long.parseLong(stockInoutSubData.get("item_uid").toString());
                    Item selectedItem = itemRepository.findByUid(itemUid);
                    stockInoutSub.setItem(selectedItem);
                }
                if (stockInoutSubData.containsKey("factory")) {
                    Long factoryUid = Long.parseLong(stockInoutSubData.get("factory").toString());
                    Factory selectedFactory = factoryRepository.findByUid(factoryUid);
                    stockInoutSub.setFactory(selectedFactory);
                }
                if (stockInoutSubData.containsKey("factory_sub")) {
                    Long factorySubUid = Long.parseLong(stockInoutSubData.get("factory_sub").toString());
                    FactorySub selectedFactorySub = factorySubRepository.findByUid(factorySubUid);
                    stockInoutSub.setFactorySub(selectedFactorySub);
                }


                stockInoutSub.setLot(stockInoutSubData.get("lot").toString());
                stockInoutSub.setQty(Double.valueOf(stockInoutSubData.get("qty").toString()));
                stockInoutSub.setUnit(stockInoutSubData.get("unit").toString());
                stockInoutSub.setStatus("가용");
                stockInoutSub.setCreated(LocalDateTime.now());
                StockInoutSub insertStockInoutSub = stockInoutSubRepository.save(stockInoutSub);
                Long stockInoutSubUid = insertStockInoutSub.getUid();
                if(stockInoutSubUid != null){
                    if("입고".equals(stockInoutDto.getDoc_type())){
                        String lot = stockInoutSubData.get("lot").toString();
                        Optional<Stock> selectedStock = Optional.ofNullable(stockRepository.findByLotAndItemAndCompanyAndFactoryAndFactorySub(lot, stockInoutSub.getItem(), company, stockInoutSub.getFactory(), stockInoutSub.getFactorySub()));
                        if(selectedStock.isPresent()){ // 기존 재고에 해당 LOT 및 공장,랙까지 동일하다면 해당 재고에 + 해서 업데이트 해줌
                            Stock stock = selectedStock.get();

                            Double updateQty = stock.getQty() +  stockInoutSub.getQty();
                            stock.setQty(updateQty);
                            Stock updateStock = stockRepository.save(stock);
                            Long stockUid = updateStock.getUid();

                            if(stockUid != null) {
                                StockRecord stockRecord = new StockRecord();

                                stockRecord.setItem(stockInoutSub.getItem());
                                stockRecord.setCompany(company);

                                stockRecord.setOutFactory(stockInoutSub.getFactory());
                                stockRecord.setOutFactorySub(stockInoutSub.getFactorySub());

                                stockRecord.setInFactory(stockInoutSub.getFactory());
                                stockRecord.setInFactorySub(stockInoutSub.getFactorySub());

                                stockRecord.setLot(stockInoutSub.getLot());
                                stockRecord.setQty(stockInoutSub.getQty());
                                stockRecord.setUnit(stockInoutSub.getUnit());
                                stockRecord.setType("입고");
                                stockRecord.setStatus("가용");
                                stockRecord.setReason("");
                                stockRecord.setCreated(LocalDateTime.now());

                                StockRecord insertStockRecord = stockRecordRepository.save(stockRecord);
                                Long stockRecordUid = insertStockRecord.getUid();

                                if(stockRecordUid == null) {
                                    setFailResult(CommonResultDto);
                                    return CommonResultDto;

                                }

                            }else{
                                setFailResult(CommonResultDto);
                                return CommonResultDto;
                            }
                        }else{ // 기존재고가 없을 시

                            Stock stock = new Stock();
                            stock.setItem(stockInoutSub.getItem());
                            stock.setFactory(stockInoutSub.getFactory());
                            stock.setFactorySub(stockInoutSub.getFactorySub());
                            stock.setUser(user);
                            stock.setCompany(company);
                            stock.setLot(stockInoutSub.getLot());
                            stock.setQty(stockInoutSub.getQty());
                            stock.setUnit(stockInoutSub.getUnit());
                            stock.setStatus(stockInoutSub.getStatus());
                            Stock insertStock = stockRepository.save(stock);
                            Long stockUid = insertStock.getUid();

                            if(stockUid != null) {
                                StockRecord stockRecord = new StockRecord();

                                stockRecord.setItem(stockInoutSub.getItem());
                                stockRecord.setCompany(company);

                                stockRecord.setOutFactory(stockInoutSub.getFactory());
                                stockRecord.setOutFactorySub(stockInoutSub.getFactorySub());

                                stockRecord.setInFactory(stockInoutSub.getFactory());
                                stockRecord.setInFactorySub(stockInoutSub.getFactorySub());

                                stockRecord.setLot(stockInoutSub.getLot());
                                stockRecord.setQty(stockInoutSub.getQty());
                                stockRecord.setUnit(stockInoutSub.getUnit());
                                stockRecord.setType("입고");
                                stockRecord.setStatus("가용");
                                stockRecord.setReason("");
                                stockRecord.setCreated(LocalDateTime.now());

                                StockRecord insertStockRecord = stockRecordRepository.save(stockRecord);
                                Long stockRecordUid = insertStockRecord.getUid();

                                if(stockRecordUid == null) {
                                    setFailResult(CommonResultDto);
                                    return CommonResultDto;

                                }

                            }else{
                                setFailResult(CommonResultDto);
                                return CommonResultDto;
                            }



                        }


                    }

                }


            }



            setSuccessResult(CommonResultDto);
            return CommonResultDto;

        }else {
            setFailResult(CommonResultDto);
            return CommonResultDto;

        }
    }


    @Override
    public CommonResultDto updateStockInout(StockInoutDto stockInoutDto) throws Exception {


        Company company = companyRepository.findByUid(stockInoutDto.getCompany_uid());

        User user = userRepository.getById(stockInoutDto.getUser_id());

        
        Optional<StockInout> selectedStockInout = stockInoutRepository.findById(String.valueOf(stockInoutDto.getUid()));

        StockInout updatedStockInout;
        CommonResultDto CommonResultDto = new CommonResultDto();
        if (selectedStockInout.isPresent()) {
            StockInout stockInout = selectedStockInout.get();
            stockInout.setCompany(company);
            stockInout.setUser(user);

            stockInout.setDoc_type(stockInoutDto.getDoc_type());
            stockInout.setStatus(stockInoutDto.getStatus());
            stockInout.setCreated(LocalDateTime.now());
            updatedStockInout = stockInoutRepository.save(stockInout);
            setSuccessResult(CommonResultDto);
            return CommonResultDto;
        } else {
            setFailResult(CommonResultDto);
            return CommonResultDto;
        }


    }


    @Override
    public List<StockInout> selectStockInout(CommonInfoSearchDto commonInfoSearchDto) {
        return stockInoutRepository.findInfo(commonInfoSearchDto);

    }

    @Override
    public List<StockInout> selectTotalStockInout(CommonSearchDto commonSearchDto) {
        return stockInoutRepository.findAll(commonSearchDto);

    }

    @Override
    public String deleteStockInout(List<Long> uids) throws Exception {

        for (Long uid : uids) {
            Optional<StockInout> selectedStockInout = Optional.ofNullable(stockInoutRepository.findByUid(uid));
            if (selectedStockInout.isPresent()) {
                StockInout stockInout = selectedStockInout.get();


                stockInoutRepository.delete(stockInout);
            } else {
                throw new Exception("StockInout with UID " + uid + " not found.");
            }
        }
        return "StockInouts deleted successfully";
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