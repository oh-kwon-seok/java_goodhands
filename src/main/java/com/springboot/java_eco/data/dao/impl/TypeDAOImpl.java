package com.springboot.java_eco.data.dao.impl;

import com.springboot.java_eco.data.dao.TypeDAO;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.type.TypeDto;
import com.springboot.java_eco.data.entity.Company;
import com.springboot.java_eco.data.entity.Type;

import com.springboot.java_eco.data.repository.company.CompanyRepository;
import com.springboot.java_eco.data.repository.type.TypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class TypeDAOImpl implements TypeDAO {
    
    private final TypeRepository typeRepository;
    private final CompanyRepository companyRepository;
    @Autowired
    public TypeDAOImpl(TypeRepository typeRepository, CompanyRepository companyRepository){
        this.typeRepository = typeRepository;
        this.companyRepository = companyRepository;

    }

    public Type insertType(TypeDto typeDto) {


        Company company = companyRepository.findByUid(typeDto.getCompany_uid());


        Optional<Type> selectedType = Optional.ofNullable(typeRepository.findByNameAndCompanyAndUsed(typeDto.getName(), company, 1L));


        if (selectedType.isPresent()) {
            Type type = selectedType.get();
            type.setCompany(company);
            type.setName(typeDto.getName());
            type.setUsed(Math.toIntExact(typeDto.getUsed()));
            type.setUpdated(LocalDateTime.now());
            return typeRepository.save(type);
        } else {
            Type type = new Type();
            type.setCompany(company);
            type.setName(typeDto.getName());
            type.setUsed(Math.toIntExact(typeDto.getUsed()));
            type.setCreated(LocalDateTime.now());

            return typeRepository.save(type);

        }

    }
    @Override
    public List<Type> selectTotalType(CommonInfoSearchDto commonInfoSearchDto) {
        return typeRepository.findAll(commonInfoSearchDto);

    }

    @Override
    public List<Type> selectType(CommonInfoSearchDto commonInfoSearchDto) {
        return typeRepository.findInfo(commonInfoSearchDto);

    }


    @Override
    public Type updateType(TypeDto typeDto) throws Exception {

        Company company = companyRepository.findByUid(typeDto.getCompany_uid());
        Optional<Type> selectedType = typeRepository.findById(typeDto.getUid());

        Type updatedType;

        if (selectedType.isPresent()) {
            Type type = selectedType.get();

            type.setCompany(company);

            type.setName(typeDto.getName());

            type.setUsed(Math.toIntExact(typeDto.getUsed()));

            type.setUpdated(LocalDateTime.now());
            updatedType = typeRepository.save(type);
        } else {
            throw new Exception();
        }
        return updatedType;
    }
    @Override
    public String deleteType(List<Long> uids) throws Exception {

        for (Long uid : uids) {
            Optional<Type> selectedType = typeRepository.findById(uid);
            if (selectedType.isPresent()) {
                Type type = selectedType.get();
                type.setUsed(0);
                type.setDeleted(LocalDateTime.now());
                typeRepository.save(type);
            } else {
                throw new Exception("Type with UID " + uid + " not found.");
            }
        }
        return "Types deleted successfully";
    }
}
