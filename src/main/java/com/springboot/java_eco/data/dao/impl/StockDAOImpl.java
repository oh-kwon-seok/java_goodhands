package com.springboot.java_eco.data.dao.impl;

import ch.qos.logback.classic.Logger;
import com.springboot.java_eco.common.CommonResponse;
import com.springboot.java_eco.data.dao.StockDAO;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.stock.LotSearchDto;
import com.springboot.java_eco.data.dto.stock.StockDto;
import com.springboot.java_eco.data.entity.*;
import com.springboot.java_eco.data.repository.company.CompanyRepository;
import com.springboot.java_eco.data.repository.estimate.EstimateRepository;
import com.springboot.java_eco.data.repository.factory.FactoryRepository;
import com.springboot.java_eco.data.repository.factorySub.FactorySubRepository;
import com.springboot.java_eco.data.repository.item.ItemRepository;
import com.springboot.java_eco.data.repository.stock.StockRepository;

import com.springboot.java_eco.data.repository.user.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class StockDAOImpl implements StockDAO {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(StockDAOImpl.class);

    private final StockRepository stockRepository;

    private final UserRepository userRepository;

    private final ItemRepository itemRepository;

    private final CompanyRepository companyRepository;

    private final FactoryRepository factoryRepository;

    private final FactorySubRepository factorySubRepository;

    @Autowired
    public StockDAOImpl(StockRepository stockRepository,
                        FactoryRepository factoryRepository,
                        FactorySubRepository factorySubRepository,
                        UserRepository userRepository,
                        ItemRepository itemRepository,
                        CompanyRepository companyRepository

                          ) {
        this.stockRepository = stockRepository;
        this.factoryRepository = factoryRepository;
        this.factorySubRepository = factorySubRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public CommonResultDto insertStock(StockDto stockDto) throws Exception {

        Company company = companyRepository.findByUid(stockDto.getCompany_uid());
        Item item = itemRepository.findByUid(stockDto.getItem_uid());
        Factory factory = factoryRepository.findByUid(stockDto.getFactory_uid());
        FactorySub factorySub = factorySubRepository.findByUid(stockDto.getFactory_sub_uid());
        User user = userRepository.getById(stockDto.getUser_id());

        Stock stock = new Stock();

        stock.setItem(item);
        stock.setFactory(factory);
        stock.setFactorySub(factorySub);
        stock.setUser(user);
        stock.setCompany(company);
        stock.setLot(stockDto.getLot());
        stock.setQty(stockDto.getQty());
        stock.setUnit(stockDto.getUnit());
        stock.setStatus(stockDto.getStatus());


        stock.setCreated(LocalDateTime.now());


        Stock insertStock = stockRepository.save(stock);

        Long uid = insertStock.getUid();


        Stock selectedStock = stockRepository.findByUid(uid);

        CommonResultDto CommonResultDto = new CommonResultDto();
        if (selectedStock != null) {

            setSuccessResult(CommonResultDto);
            return CommonResultDto;

        }else {
            setFailResult(CommonResultDto);
            return CommonResultDto;

        }
    }

    @Override
    public List<Stock> selectStock(CommonInfoSearchDto commonInfoSearchDto) {
        return stockRepository.findInfo(commonInfoSearchDto);

    }

    @Override
    public List<Stock> selectTotalStock(CommonSearchDto commonSearchDto) {
        return stockRepository.findAll(commonSearchDto);

    }
    @Override
    public List<Stock> selectLotStock(LotSearchDto lotSearchDto) {


        return stockRepository.findLotStock(lotSearchDto);

    }
    @Override
    public List<Stock> selectPackingLotStock(LotSearchDto lotSearchDto) {


        return stockRepository.findPackingLotStock(lotSearchDto);

    }


    @Override
    public String deleteStock(List<Long> uids) throws Exception {

        for (Long uid : uids) {
            Optional<Stock> selectedStock = Optional.ofNullable(stockRepository.findByUid(uid));
            if (selectedStock.isPresent()) {
                Stock stock = selectedStock.get();


                stockRepository.delete(stock);
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