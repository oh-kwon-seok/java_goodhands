package com.springboot.java_eco.data.dao.impl;

import com.springboot.java_eco.data.dao.EstimateSubDAO;
import com.springboot.java_eco.data.dao.EstimateSubDAO;
import com.springboot.java_eco.data.dto.estimateSub.EstimateSubSearchDto;
import com.springboot.java_eco.data.entity.Estimate;
import com.springboot.java_eco.data.entity.EstimateSub;
import com.springboot.java_eco.data.entity.Company;
import com.springboot.java_eco.data.entity.Item;
import com.springboot.java_eco.data.repository.estimate.EstimateRepository;
import com.springboot.java_eco.data.repository.estimateSub.EstimateSubRepository;
import com.springboot.java_eco.data.repository.company.CompanyRepository;
import com.springboot.java_eco.data.repository.estimateSub.EstimateSubRepository;
import com.springboot.java_eco.data.repository.item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class EstimateSubDAOImpl implements EstimateSubDAO {

    private final EstimateSubRepository estimateSubRepository;

    private final EstimateRepository estimateRepository;

    private final ItemRepository itemRepository;

    private final CompanyRepository companyRepository;


    @Autowired
    public EstimateSubDAOImpl(EstimateSubRepository estimateSubRepository, EstimateRepository estimateRepository, CompanyRepository companyRepository, ItemRepository itemRepository){
        this.estimateSubRepository = estimateSubRepository;
        this.estimateRepository = estimateRepository;
        this.companyRepository = companyRepository;
        this.itemRepository = itemRepository;

    }

    @Override
    public List<EstimateSub> selectTotalEstimateSub(EstimateSubSearchDto estimateSubSearchDto) {
        return estimateSubRepository.findAll(estimateSubSearchDto);

    }


    @Override
    public List<EstimateSub> selectEstimateSub(EstimateSubSearchDto estimateSubSearchDto) {
        return estimateSubRepository.findInfo(estimateSubSearchDto);

    }
    @Override
    public List<EstimateSub> selectEstimateUidSelect(EstimateSubSearchDto estimateSubSearchDto) {
        return estimateSubRepository.findByEstimateUidSelect(estimateSubSearchDto);

    }



}
