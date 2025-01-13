package com.springboot.new_java.data.dao.impl;

import ch.qos.logback.classic.Logger;
import com.springboot.new_java.common.CommonResponse;
import com.springboot.new_java.data.dao.RestricMaterialCountryDAO;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.restricMaterialCountry.RestricMaterialCountryDto;
import com.springboot.new_java.data.entity.History;
import com.springboot.new_java.data.entity.RestricMaterialCountry;
import com.springboot.new_java.data.entity.User;
import com.springboot.new_java.data.repository.history.HistoryRepository;
import com.springboot.new_java.data.repository.restricMaterialCountry.RestricMaterialCountryRepository;
import com.springboot.new_java.data.repository.user.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class RestricMaterialCountryDAOImpl implements RestricMaterialCountryDAO {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(RestricMaterialCountryDAOImpl.class);

    private final RestricMaterialCountryRepository restricMaterialCountryRepository;
    private final UserRepository userRepository;
    private final HistoryRepository historyRepository;


    @Autowired
    public RestricMaterialCountryDAOImpl(RestricMaterialCountryRepository restricMaterialCountryRepository,
                                         UserRepository userRepository,
                                         HistoryRepository historyRepository
                      ) {
        this.restricMaterialCountryRepository = restricMaterialCountryRepository;
        this.userRepository = userRepository;
        this.historyRepository = historyRepository;
    }

    @Override
    public CommonResultDto insertRestricMaterialCountry(RestricMaterialCountryDto restricMaterialCountryDto,String clientIp) throws Exception {
        User user = userRepository.getById(restricMaterialCountryDto.getId());
        List<Map<String, Object>> restricMaterialCountryList = restricMaterialCountryDto.getRestric_material_country_array();


        LOGGER.info("로그객체 : {}",user);
        LOGGER.info("사용자ID,{}",restricMaterialCountryDto.getId());

        CommonResultDto CommonResultDto = new CommonResultDto();

        if (restricMaterialCountryList != null) {
            restricMaterialCountryRepository.deleteAll();

            if(user != null){
                History history = new History();

                history.setName("사용제한 배합금지 국가정보 업데이트");
                history.setIp(clientIp);
                history.setUser(user);
                history.setCreated(LocalDateTime.now());
                historyRepository.save(history);
            }



            for (Map<String, Object> restricMaterialCountryData : restricMaterialCountryList) {


                RestricMaterialCountry restricMaterialCountry = new RestricMaterialCountry();


                if (!restricMaterialCountryData.get("regulate_type").toString().isEmpty()) {
                    try {
                        restricMaterialCountry.setRegulate_type(restricMaterialCountryData.get("regulate_type").toString());
                    } catch (Exception e) {
                        LOGGER.info("Exception : {}",e);
                    }
                }

                restricMaterialCountry.setRegl_code(restricMaterialCountryData.get("regl_code").toString());
                restricMaterialCountry.setIngr_code(restricMaterialCountryData.get("ingr_code").toString());


                if (!restricMaterialCountryData.get("country_name").toString().isEmpty()) {
                    try {
                        restricMaterialCountry.setCountry_name(restricMaterialCountryData.get("country_name").toString());
                    } catch (Exception e) {
                        LOGGER.info("Exception : {}",e);
                    }
                }
                restricMaterialCountry.setNotice_ingr_name(restricMaterialCountryData.get("notice_ingr_name").toString());
                restricMaterialCountry.setProvis_atrcl(restricMaterialCountryData.get("provis_atrcl").toString());
                restricMaterialCountry.setLimit_cond(restricMaterialCountryData.get("limit_cond").toString());

                restricMaterialCountry.setCreated(LocalDateTime.now());
                restricMaterialCountry.setUsed(1);
                restricMaterialCountryRepository.save(restricMaterialCountry);
            }
            setSuccessResult(CommonResultDto);
            return CommonResultDto;
        }else {
            setFailResult(CommonResultDto);
            return CommonResultDto;

        }

    }

    @Override
    public List<RestricMaterialCountry> selectTotalRestricMaterialCountry(CommonInfoSearchDto commonInfoSearchDto) {
        return restricMaterialCountryRepository.findAll(commonInfoSearchDto);

    }  @Override
    public List<RestricMaterialCountry> selectRestricMaterialCountry(CommonInfoSearchDto commonInfoSearchDto) {
        return restricMaterialCountryRepository.findInfo(commonInfoSearchDto);

    }


    @Override
    public String deleteRestricMaterialCountry(List<Long> uids) throws Exception {

        for (Long uid : uids) {
            Optional<RestricMaterialCountry> selectedRestricMaterialCountry = restricMaterialCountryRepository.findById(uid);
            if (selectedRestricMaterialCountry.isPresent()) {
                RestricMaterialCountry restricMaterialCountry = selectedRestricMaterialCountry.get();

                restricMaterialCountryRepository.delete(restricMaterialCountry);
            } else {
                throw new Exception("RestricMaterialCountry with UID " + uid + " not found.");
            }
        }
        return "RestricMaterialCountrys deleted successfully";
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