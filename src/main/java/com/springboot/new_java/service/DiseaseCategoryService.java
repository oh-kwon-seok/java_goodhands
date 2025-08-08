package com.springboot.new_java.service;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.disease.DiseaseCategoryDto;
import com.springboot.new_java.data.entity.User;

import com.springboot.new_java.data.entity.disease.DiseaseCategory;

import com.springboot.new_java.data.repository.diseaseCategory.DiseaseCategoryRepository;
import com.springboot.new_java.data.repository.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DiseaseCategoryService {


    private final UserRepository userRepository;
    private final DiseaseCategoryRepository diseaseCategoryRepository;

    @Autowired
    public DiseaseCategoryService(UserRepository userRepository, DiseaseCategoryRepository diseaseCategoryRepository) {
      
        this.userRepository = userRepository;
        this.diseaseCategoryRepository = diseaseCategoryRepository;
    }

    public DiseaseCategory insertDiseaseCategory(DiseaseCategoryDto diseaseCategoryDto) {
        String diseaseCategoryName = diseaseCategoryDto.getName();
        // 기존에 동일한 이름이 사용중인지 체크
        DiseaseCategory existing = diseaseCategoryRepository.findByNameAndUsed(diseaseCategoryName, true);
        if (existing != null) {
            throw new IllegalArgumentException("이미 등록된 시설 유형입니다: " + diseaseCategoryName);
        }
        User user = userRepository.getById(diseaseCategoryDto.getUser_id());
        DiseaseCategory diseaseCategory = new DiseaseCategory();
        diseaseCategory.setCode(diseaseCategoryDto.getCode());
        diseaseCategory.setName(diseaseCategoryDto.getName());
        diseaseCategory.setUser(user);
        diseaseCategory.setDescription(diseaseCategoryDto.getDescription());
        diseaseCategory.setUsed(diseaseCategoryDto.getUsed());
        diseaseCategory.setCreated(LocalDateTime.now());

        return diseaseCategoryRepository.save(diseaseCategory);
    }


    public List<DiseaseCategory> getTotalDiseaseCategory(CommonInfoSearchDto commonInfoSearchDto) {
        return diseaseCategoryRepository.findAll(commonInfoSearchDto);
    }

    public List<DiseaseCategory> getDiseaseCategory(CommonInfoSearchDto commonInfoSearchDto) {
        return diseaseCategoryRepository.findInfo(commonInfoSearchDto);
    }

    public DiseaseCategory updateDiseaseCategory(DiseaseCategoryDto diseaseCategoryDto) {
        DiseaseCategory diseaseCategory = diseaseCategoryRepository.findById(diseaseCategoryDto.getUid())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 시설입니다."));

        String diseaseCategoryName = diseaseCategoryDto.getName();
        DiseaseCategory existing = diseaseCategoryRepository.findByNameAndUsed(diseaseCategoryName, true);

        // 다른 UID에서 동일한 이름이 사용 중이면 예외 처리
        if (existing != null && !existing.getUid().equals(diseaseCategoryDto.getUid())) {
            throw new IllegalArgumentException("이미 등록된 질병유형입니다: " + diseaseCategoryName);
        }
        User user = userRepository.getById(diseaseCategoryDto.getUser_id());
        diseaseCategory.setCode(diseaseCategoryDto.getCode());
        diseaseCategory.setName(diseaseCategoryDto.getName());
        diseaseCategory.setUser(user);
        diseaseCategory.setDescription(diseaseCategoryDto.getDescription());
        diseaseCategory.setUsed(diseaseCategoryDto.getUsed());
        diseaseCategory.setUpdated(LocalDateTime.now());

        return diseaseCategoryRepository.save(diseaseCategory);
    }


    public String deleteDiseaseCategory(List<Long> uids) {
        for (Long uid : uids) {
            DiseaseCategory careHome = diseaseCategoryRepository.findById(uid)
                    .orElseThrow(() -> new EntityNotFoundException("DiseaseCategory with UID " + uid + " not found."));

            careHome.setUsed(false);
            careHome.setDeleted(LocalDateTime.now());
            diseaseCategoryRepository.save(careHome);
        }
        return "DiseaseCategorys deleted successfully";
    }
}