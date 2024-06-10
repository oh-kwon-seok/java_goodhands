package com.springboot.java_eco.data.dao.impl;

import ch.qos.logback.classic.Logger;
import com.springboot.java_eco.common.CommonResponse;
import com.springboot.java_eco.data.dao.RestricMaterialDAO;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.restricMaterial.RestricMaterialDto;
import com.springboot.java_eco.data.entity.*;
import com.springboot.java_eco.data.repository.history.HistoryRepository;
import com.springboot.java_eco.data.repository.restricMaterial.RestricMaterialRepository;
import com.springboot.java_eco.data.repository.user.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class RestricMaterialDAOImpl implements RestricMaterialDAO {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(RestricMaterialDAOImpl.class);

    private final RestricMaterialRepository restricMaterialRepository;
    private final UserRepository userRepository;
    private final HistoryRepository historyRepository;


    @Autowired
    public RestricMaterialDAOImpl(RestricMaterialRepository restricMaterialRepository,
                                  UserRepository userRepository,
                                  HistoryRepository historyRepository
                      ) {
        this.restricMaterialRepository = restricMaterialRepository;
        this.userRepository = userRepository;
        this.historyRepository = historyRepository;
    }

    @Override
    public CommonResultDto insertRestricMaterial(RestricMaterialDto restricMaterialDto,String clientIp) throws Exception {
        User user = userRepository.getById(restricMaterialDto.getId());
        List<Map<String, Object>> restricMaterialList = restricMaterialDto.getRestric_material_array();


        LOGGER.info("로그객체 : {}",user);
        LOGGER.info("사용자ID,{}",restricMaterialDto.getId());

        CommonResultDto CommonResultDto = new CommonResultDto();

        if (restricMaterialList != null) {
            restricMaterialRepository.deleteAll();

            if(user != null){
                History history = new History();

                history.setName("사용제한 원료정보 업데이트");
                history.setIp(clientIp);
                history.setUser(user);
                history.setCreated(LocalDateTime.now());
                historyRepository.save(history);
            }



            for (Map<String, Object> restricMaterialData : restricMaterialList) {


                RestricMaterial restricMaterial = new RestricMaterial();


                if (!restricMaterialData.get("regulate_type").toString().isEmpty()) {
                    try {
                        restricMaterial.setRegulate_type(restricMaterialData.get("regulate_type").toString());
                    } catch (Exception e) {
                        LOGGER.info("Exception : {}",e);
                    }
                }
                restricMaterial.setIngr_std_name(restricMaterialData.get("ingr_std_name").toString());
                restricMaterial.setIngr_eng_name(restricMaterialData.get("ingr_eng_name").toString());
                restricMaterial.setCas_no(restricMaterialData.get("cas_no").toString());

                restricMaterial.setIngr_synonym(restricMaterialData.get("ingr_synonym").toString());

                if (!restricMaterialData.get("country_name").toString().isEmpty()) {
                    try {
                        restricMaterial.setCountry_name(restricMaterialData.get("country_name").toString());
                    } catch (Exception e) {
                        LOGGER.info("Exception : {}",e);
                    }
                }
                restricMaterial.setNotice_ingr_name(restricMaterialData.get("notice_ingr_name").toString());
                restricMaterial.setProvis_atrcl(restricMaterialData.get("provis_atrcl").toString());
                restricMaterial.setLimit_cond(restricMaterialData.get("limit_cond").toString());

                restricMaterial.setCreated(LocalDateTime.now());
                restricMaterial.setUsed(1);
                restricMaterialRepository.save(restricMaterial);
            }
            setSuccessResult(CommonResultDto);
            return CommonResultDto;
        }else {
            setFailResult(CommonResultDto);
            return CommonResultDto;

        }

    }

    @Override
    public List<RestricMaterial> selectTotalRestricMaterial(CommonInfoSearchDto commonInfoSearchDto) {
        return restricMaterialRepository.findAll(commonInfoSearchDto);

    }  @Override
    public List<RestricMaterial> selectRestricMaterial(CommonInfoSearchDto commonInfoSearchDto) {
        return restricMaterialRepository.findInfo(commonInfoSearchDto);

    }


    @Override
    public String deleteRestricMaterial(List<Long> uids) throws Exception {

        for (Long uid : uids) {
            Optional<RestricMaterial> selectedRestricMaterial = restricMaterialRepository.findById(uid);
            if (selectedRestricMaterial.isPresent()) {
                RestricMaterial restricMaterial = selectedRestricMaterial.get();

                restricMaterialRepository.delete(restricMaterial);
            } else {
                throw new Exception("RestricMaterial with UID " + uid + " not found.");
            }
        }
        return "RestricMaterials deleted successfully";
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