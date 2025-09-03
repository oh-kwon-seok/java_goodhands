package com.springboot.new_java.service;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.department.DepartmentDto;
import com.springboot.new_java.data.dto.disease.DiseaseCategoryDto;
import com.springboot.new_java.data.dto.disease.DiseaseDto;

import com.springboot.new_java.data.entity.Department;
import com.springboot.new_java.data.entity.User;
import com.springboot.new_java.data.entity.disease.Disease;
import com.springboot.new_java.data.entity.disease.DiseaseCategory;
import com.springboot.new_java.data.repository.disease.DiseaseRepository;
import com.springboot.new_java.data.repository.diseaseCategory.DiseaseCategoryRepository;
import com.springboot.new_java.data.repository.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiseaseService extends AbstractCacheableSearchService<Disease, DiseaseDto> {

    private final DiseaseRepository diseaseRepository;
    private final DiseaseCategoryRepository diseaseCategoryRepository;
    private final UserRepository userRepository;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(DiseaseService.class);


    public DiseaseService(DiseaseRepository diseaseRepository,DiseaseCategoryRepository diseaseCategoryRepository, UserRepository userRepository, RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        super(redisTemplate, objectMapper);
        this.diseaseRepository = diseaseRepository;
        this.diseaseCategoryRepository = diseaseCategoryRepository;
        this.userRepository = userRepository;


    }
    @Override
    public String getEntityType() {
        return "Disease";
    }

    @Override
    public List<Disease> findAllBySearchCondition(CommonInfoSearchDto searchDto) {
        return diseaseRepository.findAll(searchDto);
    }

    public Disease insertDisease(DiseaseDto diseaseDto) {
        String diseaseName = diseaseDto.getName();
        // 기존에 동일한 이름이 사용중인지 체크
        Disease existing = diseaseRepository.findByNameAndUsed(diseaseName, true);
        if (existing != null) {
            throw new IllegalArgumentException("이미 등록된 시설 유형입니다: " + diseaseName);
        }
        User user = userRepository.getById(diseaseDto.getUser_id());
        DiseaseCategory diseaseCategory = diseaseCategoryRepository.getById(diseaseDto.getDisease_category_uid());
        Disease disease = new Disease();
        disease.setCode(diseaseDto.getCode());
        disease.setName(diseaseDto.getName());
        disease.setUser(user);
        disease.setDiseaseCategory(diseaseCategory);

        disease.setCreated(LocalDateTime.now());

        return diseaseRepository.save(disease);
    }

    public Disease updateDisease(DiseaseDto diseaseDto) {
        Disease disease = diseaseRepository.findById(diseaseDto.getUid())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 부서입니다."));

        User user = userRepository.getById(diseaseDto.getUser_id());
        DiseaseCategory diseaseCategory = diseaseCategoryRepository.getById(diseaseDto.getDisease_category_uid());
        disease.setCode(diseaseDto.getCode());
        disease.setName(diseaseDto.getName());
        disease.setUser(user);
        disease.setDiseaseCategory(diseaseCategory);
        disease.setUpdated(LocalDateTime.now());

        return diseaseRepository.save(disease);
    }

    public String deleteDisease(List<Long> uids) {
        for (Long uid : uids) {
            Disease disease = diseaseRepository.findById(uid)
                    .orElseThrow(() -> new EntityNotFoundException("Disease with UID " + uid + " not found."));

            disease.setUsed(false);
            disease.setDeleted(LocalDateTime.now());
            diseaseRepository.save(disease);
        }
        return "Diseases deleted successfully";
    }

    public DiseaseDto convertToDto(Disease disease) {
        DiseaseDto dto = new DiseaseDto();
        dto.setUid(disease.getUid());
        dto.setName(disease.getName());

        return dto;
    }
}
