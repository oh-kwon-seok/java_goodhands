package com.springboot.java_eco.data.dao.impl;

import ch.qos.logback.classic.Logger;
import com.springboot.java_eco.common.CommonResponse;
import com.springboot.java_eco.data.dao.FactoryDAO;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.factory.FactoryDto;
import com.springboot.java_eco.data.entity.Company;
import com.springboot.java_eco.data.entity.Factory;
import com.springboot.java_eco.data.entity.FactorySub;
import com.springboot.java_eco.data.repository.company.CompanyRepository;
import com.springboot.java_eco.data.repository.factory.FactoryRepository;
import com.springboot.java_eco.data.repository.factorySub.FactorySubRepository;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class FactoryDAOImpl implements FactoryDAO {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(FactoryDAOImpl.class);

    private final FactoryRepository factoryRepository;
    private final FactorySubRepository factorySubRepository;
    private final CompanyRepository companyRepository;

    
    @Autowired
    public FactoryDAOImpl(FactoryRepository factoryRepository,
                          FactorySubRepository factorySubRepository,
                          CompanyRepository companyRepository
                          ) {
        this.factoryRepository = factoryRepository;
        this.factorySubRepository = factorySubRepository;
        this.companyRepository = companyRepository;

     
    }

    @Override
    public CommonResultDto insertFactory(FactoryDto factoryDto) throws Exception {

        Company company = companyRepository.findByUid(factoryDto.getCompany_uid());

        Factory factory = new Factory();
        factory.setCompany(company);
        factory.setName(factoryDto.getName());
        factory.setStatus(factoryDto.getStatus());
        factory.setFactory_sub_array(String.valueOf(factoryDto.getFactory_sub_array()));
        factory.setDescription(factoryDto.getDescription());
        factory.setUsed(Math.toIntExact(factoryDto.getUsed()));
        factory.setCreated(LocalDateTime.now());

        Factory insertFactory = factoryRepository.save(factory);

        Long uid = insertFactory.getUid();

        LOGGER.info("[uid] : {}",uid);
        Factory selectedFactory = factoryRepository.findByUid(uid);

        List<Map<String, Object>> factorySubList = factoryDto.getFactory_sub();

        LOGGER.info("[Factory] : {}",selectedFactory);
        CommonResultDto CommonResultDto = new CommonResultDto();
        LOGGER.info("[factorySubList] : {}",factorySubList);
        if (factorySubList != null) {

            for (Map<String, Object> factorySubData : factorySubList) {
                FactorySub factorySub = new FactorySub();
                factorySub.setFactory(selectedFactory);

                if (!factorySubData.get("name").toString().isEmpty()) {
                    try {
                        factorySub.setName(factorySubData.get("name").toString());
                    } catch (Exception e) {
                        factorySub.setName("");
                        factorySub.setStatus("재등록요청");
                    }
                }
                if (!factorySubData.get("status").toString().isEmpty()) {
                    try {
                        factorySub.setStatus(factorySubData.get("status").toString());
                    } catch (Exception e) {
                        factorySub.setStatus("재등록요청");
                    }
                }
                if (!factorySubData.get("description").toString().isEmpty()) {
                    try {
                        factorySub.setDescription(factorySubData.get("description").toString());
                    } catch (Exception e) {
                        factorySub.setName("재등록바람");
                        factorySub.setStatus("재등록요청");
                    }
                }

                // product_uid 값이 있다면 product를 가져와서 userProduct에 설정

                factorySub.setCreated(LocalDateTime.now());
                factorySub.setUsed(1);

                factorySubRepository.save(factorySub);
            }



            setSuccessResult(CommonResultDto);
            return CommonResultDto;

        }else {
            setFailResult(CommonResultDto);
            return CommonResultDto;

        }
    }


    @Override
    public CommonResultDto updateFactory(FactoryDto factoryDto) throws Exception {


        Company company = companyRepository.findByUid(factoryDto.getCompany_uid());

        Optional<Factory> selectedFactory = factoryRepository.findById(String.valueOf(factoryDto.getUid()));

        Factory updatedFactory;

        if (selectedFactory.isPresent()) {
            Factory factory = selectedFactory.get();
            factory.setCompany(company);
            factory.setName(factoryDto.getName());
            factory.setStatus(factoryDto.getStatus());
            factory.setFactory_sub_array(String.valueOf(factoryDto.getFactory_sub_array()));
            factory.setDescription(factoryDto.getDescription());
            factory.setUsed(Math.toIntExact(factoryDto.getUsed()));
            factory.setCreated(LocalDateTime.now());
            updatedFactory = factoryRepository.save(factory);



        } else {
            throw new Exception();
        }


        List<Map<String, Object>> factorySubList = factoryDto.getFactory_sub();

        LOGGER.info("[Factory] : {}",selectedFactory);
        CommonResultDto CommonResultDto = new CommonResultDto();

        if (factorySubList != null) {

            List<FactorySub> deletedData = factorySubRepository.findByFactoryUid(factoryDto.getUid());
            factorySubRepository.deleteAll(deletedData);



            for (Map<String, Object> factorySubData : factorySubList) {
                FactorySub factorySub = new FactorySub();
                factorySub.setFactory(updatedFactory);

                factorySub.setName(factorySubData.get("name").toString());
                factorySub.setStatus(factorySubData.get("status").toString());
                factorySub.setDescription(factorySubData.get("description").toString());
                factorySub.setUsed(1);


                factorySub.setCreated(LocalDateTime.now());
                factorySub.setUpdated(LocalDateTime.now());

                factorySubRepository.save(factorySub);
            }



            setSuccessResult(CommonResultDto);
            return CommonResultDto;

        }else {
            setFailResult(CommonResultDto);
            return CommonResultDto;

        }
    }


    @Override
    public List<Factory> selectFactory(CommonInfoSearchDto commonInfoSearchDto) {
        return factoryRepository.findInfo(commonInfoSearchDto);

    }

    @Override
    public List<Factory> selectTotalFactory(CommonInfoSearchDto commonInfoSearchDto) {
        return factoryRepository.findAll(commonInfoSearchDto);

    }

    @Override
    public String deleteFactory(List<Long> uids) throws Exception {

        for (Long uid : uids) {
            Optional<Factory> selectedFactory = Optional.ofNullable(factoryRepository.findByUid(uid));
            if (selectedFactory.isPresent()) {
                Factory factory = selectedFactory.get();
                factory.setUsed(0);
                factory.setDeleted(LocalDateTime.now());
                factoryRepository.save(factory);
            } else {
                throw new Exception("Factory with UID " + uid + " not found.");
            }
        }
        return "Factorys deleted successfully";
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