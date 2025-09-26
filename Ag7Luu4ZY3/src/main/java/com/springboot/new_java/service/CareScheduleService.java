package com.springboot.new_java.service;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.new_java.data.dto.care.CareScheduleDto;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.entity.User;
import com.springboot.new_java.data.entity.care.CareSchedule;

import com.springboot.new_java.data.entity.senior.Senior;
import com.springboot.new_java.data.repository.careSchedule.CareScheduleRepository;

import com.springboot.new_java.data.repository.senior.SeniorRepository;
import com.springboot.new_java.data.repository.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CareScheduleService extends AbstractCacheableSearchService<CareSchedule, CareScheduleDto>{
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(CareScheduleService.class);

    private final CareScheduleRepository careScheduleRepository;
    private final UserRepository userRepository;
    private final SeniorRepository seniorRepository;




    public CareScheduleService(CareScheduleRepository careScheduleRepository, UserRepository userRepository, SeniorRepository seniorRepository,  RedisTemplate<String,Object> redisTemplate, ObjectMapper objectMapper) {

        super(redisTemplate,objectMapper);
        this.careScheduleRepository = careScheduleRepository;
        this.userRepository = userRepository;
        this.seniorRepository = seniorRepository;

    }

    @Override
    public String getEntityType() {
        return "CareSchedule";
    }

    @Override
    public List<CareSchedule> findAllBySearchCondition(CommonInfoSearchDto searchDto) {
        return careScheduleRepository.findAll(searchDto);
    }

    @Transactional
    public CareSchedule save(CareScheduleDto careScheduleDto) {
        try {
            // 1. CareSchedule 생성
            CareSchedule savedCareSchedule = performSave(careScheduleDto);
            LOGGER.info("CareSchedule 생성 완료: ID={}", savedCareSchedule.getUid());

            // 2. 캐시 무효화
            invalidateCachesAfterDataChange();
            LOGGER.info("CareSchedule 생성 후 캐시 무효화 완료: {}", savedCareSchedule.getUid());

            return savedCareSchedule;

        } catch (Exception e) {
            LOGGER.error("CareSchedule 생성 (캐시 무효화 포함) 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("CareSchedule 생성에 실패했습니다: " + e.getMessage(), e);
        }
    }

    @Transactional
    public CareSchedule update(CareScheduleDto careScheduleDto) {
        try {
            // 1. CareSchedule 수정
            CareSchedule updatedCareSchedule = performUpdate(careScheduleDto);

            if (updatedCareSchedule == null) {
                LOGGER.warn("수정할 CareSchedule를 찾을 수 없습니다: ID={}", careScheduleDto.getUid());
                return null;
            }

            LOGGER.info("CareSchedule 수정 완료: ID={}", updatedCareSchedule.getUid());

            // 2. 캐시 무효화
            invalidateCachesAfterDataChange();
            LOGGER.info("CareSchedule 수정 후 캐시 무효화 완료: {}", updatedCareSchedule.getUid());

            return updatedCareSchedule;

        } catch (Exception e) {
            LOGGER.error("CareSchedule 수정 (캐시 무효화 포함) 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("CareSchedule 수정에 실패했습니다: " + e.getMessage(), e);
        }
    }

    public CareSchedule performSave(CareScheduleDto careScheduleDto) {
        CareSchedule careSchedule = new CareSchedule();
        Senior senior = seniorRepository.findByUid(careScheduleDto.getSenior_uid());
        User user = userRepository.getById(careScheduleDto.getUser_id());
        User caregiver = userRepository.getById(careScheduleDto.getCaregiver_id());


        careSchedule.setCare_real_date(careScheduleDto.getCare_real_date());
        careSchedule.setCareReserveDate(careScheduleDto.getCare_reserve_date());
        careSchedule.setSenior(senior);
        careSchedule.setUser(user);
        careSchedule.setCaregiver(caregiver);
        careSchedule.setCreated(LocalDateTime.now());
        careSchedule.setUsed(careScheduleDto.getUsed());
        return careScheduleRepository.save(careSchedule);
    }


    public CareSchedule performUpdate(CareScheduleDto careScheduleDto) {
        CareSchedule careSchedule = careScheduleRepository.findById(careScheduleDto.getUid())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 시설입니다."));

        Senior senior = seniorRepository.findByUid(careScheduleDto.getSenior_uid());
        User user = userRepository.getById(careScheduleDto.getUser_id());
        User caregiver = userRepository.getById(careScheduleDto.getCaregiver_id());


        careSchedule.setCare_real_date(careScheduleDto.getCare_real_date());
        careSchedule.setCareReserveDate(careScheduleDto.getCare_reserve_date());
        careSchedule.setSenior(senior);
        careSchedule.setUser(user);
        careSchedule.setCaregiver(caregiver);
        careSchedule.setUpdated(LocalDateTime.now());
        careSchedule.setUsed(careScheduleDto.getUsed());
        return careScheduleRepository.save(careSchedule);
    }

    @Transactional
    public int delete(List<Long> uids) {
        try {
            int deletedCount = 0;

            for (Long uid : uids) {
                Optional<CareSchedule> existingCareSchedule = careScheduleRepository.findById(uid);
                if (existingCareSchedule.isPresent()) {
                    CareSchedule careSchedule = existingCareSchedule.get();
                    careSchedule.setUsed(false);
                    careSchedule.setDeleted(LocalDateTime.now());
                    careScheduleRepository.save(careSchedule);
                    deletedCount++;
                    LOGGER.debug("CareSchedule 삭제: ID={}", uid);
                } else {
                    LOGGER.warn("삭제할 CareSchedule를 찾을 수 없습니다: ID={}", uid);
                }
            }

            LOGGER.info("CareSchedule 삭제 완료: {}개 삭제", deletedCount);

            // 캐시 무효화 (삭제된 항목이 있을 때만)
            if (deletedCount > 0) {
                invalidateCachesAfterDataChange();
                LOGGER.info("CareSchedule 삭제 후 캐시 무효화 완료");
            }

            return deletedCount;

        } catch (Exception e) {
            LOGGER.error("CareSchedule 삭제 (캐시 무효화 포함) 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("CareSchedule 삭제에 실패했습니다: " + e.getMessage(), e);
        }
    }


    @Override
    public CareScheduleDto convertToDto(CareSchedule careSchedule) {

        CareScheduleDto dto = new CareScheduleDto();
        dto.setUid(careSchedule.getUid());
        dto.setCare_real_date(careSchedule.getCare_real_date());
        dto.setCare_reserve_date(careSchedule.getCareReserveDate());
        dto.setSenior(seniorRepository.findByUid(careSchedule.getSenior().getUid()));
        dto.setCaregiver(userRepository.getById(careSchedule.getCaregiver().getId()));
        dto.setUsed(careSchedule.getUsed());
        dto.setUser(careSchedule.getUser());
        dto.setCreated(careSchedule.getCreated());

        return dto;
    }
}