package com.springboot.java_eco.data.dao.impl;

import ch.qos.logback.classic.Logger;
import com.springboot.java_eco.common.CommonResponse;
import com.springboot.java_eco.data.dao.CosmeticMaterialDAO;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;

import com.springboot.java_eco.data.dto.costmeticMaterial.CosmeticMaterialDto;
import com.springboot.java_eco.data.entity.History;
import com.springboot.java_eco.data.entity.CosmeticMaterial;
import com.springboot.java_eco.data.entity.User;
import com.springboot.java_eco.data.repository.history.HistoryRepository;
import com.springboot.java_eco.data.repository.cosmeticMaterial.CosmeticMaterialRepository;
import com.springboot.java_eco.data.repository.user.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class CosmeticMaterialDAOImpl implements CosmeticMaterialDAO {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(CosmeticMaterialDAOImpl.class);

    private final CosmeticMaterialRepository cosmeticMaterialRepository;
    private final UserRepository userRepository;
    private final HistoryRepository historyRepository;


    @Autowired
    public CosmeticMaterialDAOImpl(CosmeticMaterialRepository cosmeticMaterialRepository,
                                   UserRepository userRepository,
                                   HistoryRepository historyRepository
                      ) {
        this.cosmeticMaterialRepository = cosmeticMaterialRepository;
        this.userRepository = userRepository;
        this.historyRepository = historyRepository;
    }

    @Override
    public CommonResultDto insertCosmeticMaterial(CosmeticMaterialDto cosmeticMaterialDto, String clientIp) throws Exception {
        User user = userRepository.getById(cosmeticMaterialDto.getId());
        List<Map<String, Object>> cosmeticMaterialList = cosmeticMaterialDto.getCosmetic_material_array();


        LOGGER.info("로그객체 : {}",user);
        LOGGER.info("사용자ID,{}",cosmeticMaterialDto.getId());

        CommonResultDto CommonResultDto = new CommonResultDto();

        if (cosmeticMaterialList != null) {
            cosmeticMaterialRepository.deleteAll();

            if(user != null){
                History history = new History();

                history.setName("사용제한 원료정보 업데이트");
                history.setIp(clientIp);
                history.setUser(user);
                history.setCreated(LocalDateTime.now());
                historyRepository.save(history);
            }



            for (Map<String, Object> cosmeticMaterialData : cosmeticMaterialList) {


                CosmeticMaterial cosmeticMaterial = new CosmeticMaterial();




                cosmeticMaterial.setIngr_kor_name(cosmeticMaterialData.get("ingr_kor_name").toString());
                cosmeticMaterial.setIngr_eng_name(cosmeticMaterialData.get("ingr_eng_name").toString());
                cosmeticMaterial.setCas_no(cosmeticMaterialData.get("cas_no").toString());

                cosmeticMaterial.setIngr_synonym(cosmeticMaterialData.get("ingr_synonym").toString());


                cosmeticMaterial.setOrigin_major_kor_name(cosmeticMaterialData.get("origin_major_kor_name").toString());

                cosmeticMaterial.setCreated(LocalDateTime.now());
                cosmeticMaterial.setUsed(1);
                cosmeticMaterialRepository.save(cosmeticMaterial);
            }
            setSuccessResult(CommonResultDto);
            return CommonResultDto;
        }else {
            setFailResult(CommonResultDto);
            return CommonResultDto;

        }

    }

    @Override
    public List<CosmeticMaterial> selectTotalCosmeticMaterial(CommonInfoSearchDto commonInfoSearchDto) {
        return cosmeticMaterialRepository.findAll(commonInfoSearchDto);

    }  @Override
    public List<CosmeticMaterial> selectCosmeticMaterial(CommonInfoSearchDto commonInfoSearchDto) {
        return cosmeticMaterialRepository.findInfo(commonInfoSearchDto);

    }


    @Override
    public String deleteCosmeticMaterial(List<Long> uids) throws Exception {

        for (Long uid : uids) {
            Optional<CosmeticMaterial> selectedCosmeticMaterial = cosmeticMaterialRepository.findById(uid);
            if (selectedCosmeticMaterial.isPresent()) {
                CosmeticMaterial cosmeticMaterial = selectedCosmeticMaterial.get();

                cosmeticMaterialRepository.delete(cosmeticMaterial);
            } else {
                throw new Exception("CosmeticMaterial with UID " + uid + " not found.");
            }
        }
        return "CosmeticMaterials deleted successfully";
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