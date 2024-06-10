package com.springboot.java_eco.service.impl;

import com.springboot.java_eco.data.dao.TypeDAO;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.type.TypeDto;
import com.springboot.java_eco.data.entity.Type;
import com.springboot.java_eco.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {
    private final TypeDAO typeDAO;

    @Autowired
    public TypeServiceImpl(@Qualifier("typeDAOImpl") TypeDAO typeDAO){
        this.typeDAO = typeDAO;
    }


    @Override
    public List<Type> getTotalType(CommonInfoSearchDto commonInfoSearchDto){
        return typeDAO.selectTotalType(commonInfoSearchDto);
    }

    @Override
    public List<Type> getType(CommonInfoSearchDto commonInfoSearchDto){
        return typeDAO.selectType(commonInfoSearchDto);
    }
    @Override
    public Type saveType(TypeDto typeDto) throws Exception {

        return typeDAO.insertType(typeDto);

    }
    @Override
    public Type updateType(TypeDto typeDto) throws Exception {
        return typeDAO.updateType(typeDto);
    }
    @Override
    public void deleteType(List<Long> uid) throws Exception {
        typeDAO.deleteType(uid);
    }

}
