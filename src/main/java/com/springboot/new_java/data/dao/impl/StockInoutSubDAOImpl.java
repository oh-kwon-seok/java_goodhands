package com.springboot.new_java.data.dao.impl;

import com.springboot.new_java.data.dao.StockInoutSubDAO;
import com.springboot.new_java.data.dto.stockInoutSub.StockInoutSubSearchDto;
import com.springboot.new_java.data.entity.StockInoutSub;
import com.springboot.new_java.data.repository.company.CompanyRepository;
import com.springboot.new_java.data.repository.factory.FactoryRepository;
import com.springboot.new_java.data.repository.factorySub.FactorySubRepository;
import com.springboot.new_java.data.repository.item.ItemRepository;

import com.springboot.new_java.data.repository.stockInout.StockInoutRepository;
import com.springboot.new_java.data.repository.stockInoutSub.StockInoutSubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StockInoutSubDAOImpl implements StockInoutSubDAO {

    private final StockInoutSubRepository stockInoutSubRepository;

    private final StockInoutRepository stockInoutRepository;

    private final ItemRepository itemRepository;

    private final CompanyRepository companyRepository;

    private final FactoryRepository factoryRepository;

    private final FactorySubRepository factorySubRepository;



    @Autowired
    public StockInoutSubDAOImpl(StockInoutSubRepository stockInoutSubRepository,
                                StockInoutRepository stockInoutRepository,
                                FactoryRepository factoryRepository,
                                FactorySubRepository factorySubRepository,
                                CompanyRepository companyRepository,
                                ItemRepository itemRepository){
        this.stockInoutSubRepository = stockInoutSubRepository;
        this.stockInoutRepository = stockInoutRepository;
        this.factoryRepository = factoryRepository;
        this.factorySubRepository = factorySubRepository;
        this.companyRepository = companyRepository;
        this.itemRepository = itemRepository;

    }

    @Override
    public List<StockInoutSub> selectTotalStockInoutSub(StockInoutSubSearchDto stockInoutSubSearchDto) {
        return stockInoutSubRepository.findAll(stockInoutSubSearchDto);

    }


    @Override
    public List<StockInoutSub> selectStockInoutSub(StockInoutSubSearchDto stockInoutSubSearchDto) {
        return stockInoutSubRepository.findInfo(stockInoutSubSearchDto);

    }
    @Override
    public List<StockInoutSub> selectStockInoutUidSelect(StockInoutSubSearchDto stockInoutSubSearchDto) {
        return stockInoutSubRepository.findByStockInoutUidSelect(stockInoutSubSearchDto);

    }



}
