package com.springboot.new_java.service;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.care.CareHomeDto;
import com.springboot.new_java.data.entity.User;
import com.springboot.new_java.data.entity.care.CareHome;
import com.springboot.new_java.data.entity.care.CareHomeType;
import com.springboot.new_java.data.repository.careHome.CareHomeRepository;
import com.springboot.new_java.data.repository.careHomeType.CareHomeTypeRepository;
import com.springboot.new_java.data.repository.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CareHomeService {

    private final CareHomeRepository careHomeRepository;
    private final UserRepository userRepository;
    private final CareHomeTypeRepository careHomeTypeRepository;

    @Autowired
    public CareHomeService(CareHomeRepository careHomeRepository, UserRepository userRepository, CareHomeTypeRepository careHomeTypeRepository) {
        this.careHomeRepository = careHomeRepository;
        this.userRepository = userRepository;
        this.careHomeTypeRepository = careHomeTypeRepository;
    }

    public CareHome insertCareHome(CareHomeDto careHomeDto) {
        CareHome careHome = new CareHome();
        String careHomeTypeName = careHomeDto.getName();
        CareHomeType careHomeType = careHomeTypeRepository.findByNameAndUsed(careHomeTypeName, true);
        User user = userRepository.getById(careHomeDto.getUser_id());
        careHome.setName(careHomeDto.getName());
        careHome.setCareHomeType(careHomeType);
        careHome.setUser(user);

        careHome.setUsed(careHomeDto.getUsed());
        careHome.setCreated(LocalDateTime.now());
        return careHomeRepository.save(careHome);
    }

    public List<CareHome> getTotalCareHome(CommonInfoSearchDto commonInfoSearchDto) {
        return careHomeRepository.findAll(commonInfoSearchDto);
    }

    public List<CareHome> getCareHome(CommonInfoSearchDto commonInfoSearchDto) {
        return careHomeRepository.findInfo(commonInfoSearchDto);
    }

    public CareHome updateCareHome(CareHomeDto careHomeDto) {
        CareHome careHome = careHomeRepository.findById(careHomeDto.getUid())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 시설입니다."));

        String careHomeTypeName = careHomeDto.getName();
        CareHomeType careHomeType = careHomeTypeRepository.findByNameAndUsed(careHomeTypeName, true);
        careHome.setCareHomeType(careHomeType);

        careHome.setName(careHomeDto.getName());
        careHome.setUsed(careHomeDto.getUsed());
        careHome.setUpdated(LocalDateTime.now());

        return careHomeRepository.save(careHome);
    }

    public String deleteCareHome(List<Long> uids) {
        for (Long uid : uids) {
            CareHome careHome = careHomeRepository.findById(uid)
                    .orElseThrow(() -> new EntityNotFoundException("CareHome with UID " + uid + " not found."));

            careHome.setUsed(false);
            careHome.setDeleted(LocalDateTime.now());
            careHomeRepository.save(careHome);
        }
        return "CareHomes deleted successfully";
    }
}