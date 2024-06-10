package com.springboot.java_eco.data.dao.impl;

import ch.qos.logback.classic.Logger;
import com.springboot.java_eco.common.CommonResponse;
import com.springboot.java_eco.data.dao.EstimateDAO;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.estimate.EstimateDto;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.entity.*;
import com.springboot.java_eco.data.repository.estimate.EstimateRepository;
import com.springboot.java_eco.data.repository.estimateSub.EstimateSubRepository;
import com.springboot.java_eco.data.repository.company.CompanyRepository;
import com.springboot.java_eco.data.repository.item.ItemRepository;
import com.springboot.java_eco.data.repository.user.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class EstimateDAOImpl implements EstimateDAO {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(EstimateDAOImpl.class);

    private final EstimateRepository estimateRepository;
    private final EstimateSubRepository estimateSubRepository;
    private final UserRepository userRepository;

    private final ItemRepository itemRepository;

    private final CompanyRepository companyRepository;


    @Autowired
    public EstimateDAOImpl(EstimateRepository estimateRepository,
                           EstimateSubRepository estimateSubRepository,
                           UserRepository userRepository,
                           ItemRepository itemRepository,
                           CompanyRepository companyRepository
                          ) {
        this.estimateRepository = estimateRepository;
        this.estimateSubRepository = estimateSubRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.companyRepository = companyRepository;

     
    }

    @Override
    public CommonResultDto insertEstimate(EstimateDto estimateDto) throws Exception {

        Company company = companyRepository.findByUid(estimateDto.getCompany_uid());
        Company customer = companyRepository.findByUid(estimateDto.getCustomer_uid());

        User user = userRepository.getById(estimateDto.getUser_id());


        Estimate estimate = new Estimate();

        estimate.setUser(user);
        estimate.setCompany(company);
        estimate.setCustomer(customer);

        estimate.setCode(estimateDto.getCode());
        estimate.setName(estimateDto.getName());
        estimate.setProduct_spec(estimateDto.getProduct_spec());
        estimate.setShip_place(estimateDto.getShip_place());

        estimate.setDescription(estimateDto.getDescription());
        estimate.setUsed(Math.toIntExact(estimateDto.getUsed()));
        estimate.setCreated(LocalDateTime.now());

        estimate.setEstimate_date(estimateDto.getEstimate_date());
        estimate.setExpire(estimateDto.getExpire());


        Estimate insertEstimate = estimateRepository.save(estimate);

        Long uid = insertEstimate.getUid();

        LOGGER.info("[uid] : {}",uid);
        Estimate selectedEstimate = estimateRepository.findByUid(uid);

        List<Map<String, Object>> estimateSubList = estimateDto.getEstimate_sub();

        CommonResultDto CommonResultDto = new CommonResultDto();
        if (estimateSubList != null) {

            for (Map<String, Object> estimateSubData : estimateSubList) {
                EstimateSub estimateSub = new EstimateSub();

                estimateSub.setEstimate(selectedEstimate);
                if (estimateSubData.containsKey("item_uid")) {
                    Long itemUid = Long.parseLong(estimateSubData.get("item_uid").toString());
                    Item selectedItem = itemRepository.findByUid(itemUid);
                    estimateSub.setItem(selectedItem);
                }
                estimateSub.setUnit(estimateSubData.get("unit").toString());
                estimateSub.setQty(Double.valueOf(estimateSubData.get("qty").toString()));
                estimateSub.setBuy_price(Integer.valueOf(estimateSubData.get("buy_price").toString()));
                estimateSub.setPrice(Integer.valueOf(estimateSubData.get("price").toString()));
                estimateSub.setSupply_price(Integer.valueOf(estimateSubData.get("supply_price").toString()));
                estimateSub.setVat_price(Integer.valueOf(estimateSubData.get("vat_price").toString()));
                estimateSub.setDescription(estimateSubData.get("description").toString());

                estimateSub.setCreated(LocalDateTime.now());


                estimateSubRepository.save(estimateSub);
            }



            setSuccessResult(CommonResultDto);
            return CommonResultDto;

        }else {
            setFailResult(CommonResultDto);
            return CommonResultDto;

        }
    }


    @Override
    public CommonResultDto updateEstimate(EstimateDto estimateDto) throws Exception {


        Company company = companyRepository.findByUid(estimateDto.getCompany_uid());
        Company customer = companyRepository.findByUid(estimateDto.getCustomer_uid());

        User user = userRepository.getById(estimateDto.getUser_id());

        
        Optional<Estimate> selectedEstimate = estimateRepository.findById(String.valueOf(estimateDto.getUid()));

        Estimate updatedEstimate;

        if (selectedEstimate.isPresent()) {
            Estimate estimate = selectedEstimate.get();
            estimate.setCompany(company);
            estimate.setCustomer(customer);

            estimate.setUser(user);

            estimate.setCode(estimateDto.getCode());
            estimate.setName(estimateDto.getName());

            estimate.setProduct_spec(estimateDto.getProduct_spec());
            estimate.setShip_place(estimateDto.getShip_place());

            estimate.setDescription(estimateDto.getDescription());

            estimate.setEstimate_date(estimateDto.getEstimate_date());
            estimate.setExpire(estimateDto.getExpire());


            estimate.setUsed(Math.toIntExact(estimateDto.getUsed()));
            estimate.setCreated(LocalDateTime.now());
            updatedEstimate = estimateRepository.save(estimate);



        } else {
            throw new Exception();
        }


        List<Map<String, Object>> estimateSubList = estimateDto.getEstimate_sub();

        LOGGER.info("[Estimate] : {}",selectedEstimate);
        CommonResultDto CommonResultDto = new CommonResultDto();

        if (estimateSubList != null) {

            List<EstimateSub> deletedData = estimateSubRepository.findByEstimateUid(estimateDto.getUid());
            estimateSubRepository.deleteAll(deletedData);



            for (Map<String, Object> estimateSubData : estimateSubList) {
                EstimateSub estimateSub = new EstimateSub();
                estimateSub.setEstimate(updatedEstimate);

                if (estimateSubData.containsKey("item_uid")) {
                    Long itemUid = Long.parseLong(estimateSubData.get("item_uid").toString());
                    Item selectedItem = itemRepository.findByUid(itemUid);
                    estimateSub.setItem(selectedItem);
                }


                if (estimateSubData.get("unit") != null && !estimateSubData.get("unit").toString().isEmpty()) {
                    estimateSub.setUnit(estimateSubData.get("unit").toString());
                } else {
                    estimateSub.setUnit("");
                }
                if (!estimateSubData.get("qty").toString().isEmpty()) {
                    try {
                        estimateSub.setQty(Double.valueOf(estimateSubData.get("qty").toString()));
                    } catch (NumberFormatException e) {
                        estimateSub.setQty((double) 0L);
                    }
                }
                if (!estimateSubData.get("buy_price").toString().isEmpty()) {
                    try {
                        estimateSub.setBuy_price(Integer.valueOf(estimateSubData.get("buy_price").toString()));
                    } catch (NumberFormatException e) {
                        estimateSub.setBuy_price(0);
                    }
                }
                if (!estimateSubData.get("price").toString().isEmpty()) {
                    try {
                        estimateSub.setPrice(Integer.valueOf(estimateSubData.get("price").toString()));
                    } catch (NumberFormatException e) {
                        estimateSub.setPrice(0);
                    }
                }
                if (!estimateSubData.get("supply_price").toString().isEmpty()) {
                    try {
                        estimateSub.setSupply_price(Integer.valueOf(estimateSubData.get("supply_price").toString()));
                    } catch (NumberFormatException e) {
                        estimateSub.setSupply_price(0);
                    }
                }
                if (!estimateSubData.get("vat_price").toString().isEmpty()) {
                    try {
                        estimateSub.setVat_price(Integer.valueOf(estimateSubData.get("vat_price").toString()));
                    } catch (NumberFormatException e) {
                        estimateSub.setVat_price(0);
                    }
                }
                if (estimateSubData.get("description") != null && !estimateSubData.get("description").toString().isEmpty()) {
                    estimateSub.setDescription(estimateSubData.get("description").toString());
                } else {
                    estimateSub.setDescription("");
                }





                estimateSub.setCreated(LocalDateTime.now());
                estimateSub.setUpdated(LocalDateTime.now());

                estimateSubRepository.save(estimateSub);
            }



            setSuccessResult(CommonResultDto);
            return CommonResultDto;

        }else {
            setFailResult(CommonResultDto);
            return CommonResultDto;

        }
    }


    @Override
    public List<Estimate> selectEstimate(CommonInfoSearchDto commonInfoSearchDto) {
        return estimateRepository.findInfo(commonInfoSearchDto);

    }

    @Override
    public List<Estimate> selectTotalEstimate(CommonSearchDto commonSearchDto) {
        return estimateRepository.findAll(commonSearchDto);

    }

    @Override
    public String deleteEstimate(List<Long> uids) throws Exception {

        for (Long uid : uids) {
            Optional<Estimate> selectedEstimate = Optional.ofNullable(estimateRepository.findByUid(uid));
            if (selectedEstimate.isPresent()) {
                Estimate estimate = selectedEstimate.get();
                estimate.setUsed(0);
                estimate.setDeleted(LocalDateTime.now());
                estimateRepository.save(estimate);
            } else {
                throw new Exception("Estimate with UID " + uid + " not found.");
            }
        }
        return "Estimates deleted successfully";
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