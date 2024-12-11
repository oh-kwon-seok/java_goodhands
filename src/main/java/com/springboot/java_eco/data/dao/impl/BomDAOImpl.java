package com.springboot.java_eco.data.dao.impl;

import ch.qos.logback.classic.Logger;
import com.springboot.java_eco.common.CommonResponse;
import com.springboot.java_eco.data.dao.BomDAO;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.bom.BomDto;
import com.springboot.java_eco.data.entity.*;
import com.springboot.java_eco.data.repository.company.CompanyRepository;
import com.springboot.java_eco.data.repository.bom.BomRepository;
import com.springboot.java_eco.data.repository.item.ItemRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class BomDAOImpl implements BomDAO {
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(BomDAOImpl.class);
    private final BomRepository bomRepository;
    private final CompanyRepository companyRepository;

    private final ItemRepository itemRepository;


    @Autowired
    public BomDAOImpl(BomRepository bomRepository, CompanyRepository companyRepository, ItemRepository itemRepository){

        this.bomRepository = bomRepository;
        this.companyRepository = companyRepository;
        this.itemRepository = itemRepository;
    }

    public CommonResultDto insertBom(BomDto bomDto) {

        Company company = companyRepository.findByUid(bomDto.getCompany_uid());
        Item item = itemRepository.findByUid(bomDto.getItem_uid());


        Optional<Bom> selectedBom = Optional.ofNullable(bomRepository.findByCodeAndMainAndCompanyAndUsed(bomDto.getCode(), 0L,company, 1L));

        LOGGER.info("test : {}",selectedBom);
        CommonResultDto CommonResultDto = new CommonResultDto();
        if (selectedBom.isPresent()) {


                setFailResult(CommonResultDto,"code");
                return CommonResultDto;


        } else {
            Bom bom = new Bom();
            bom.setParent_uid(0L);
            bom.setMain(0L);
            bom.setCode(bomDto.getCode());
            bom.setCompany(company);
            bom.setItem(item);
            bom.setQty(bomDto.getQty());
            bom.setRate(bomDto.getRate());
            bom.setUsed(Math.toIntExact(bomDto.getUsed()));
            bom.setCreated(LocalDateTime.now());

            Bom insertBom = bomRepository.save(bom);
            Long uid = insertBom.getUid();


            List<Map<String, Object>> bomSubList = bomDto.getBom_data();

            if (bomSubList != null) {

                for (Map<String, Object> bomSubData : bomSubList) {

                    if (bomSubData.containsKey("title")) {

                        String title = (String) bomSubData.get("title");
                        if ("main".equals(title)) {

                            Bom subBom = new Bom();
                            subBom.setParent_uid(uid);
                            subBom.setMain(uid);

                            // item_uid 값이 있다면 item를 가져옴
                            if (bomSubData.containsKey("item_uid")) {
                                Long itemUid = Long.parseLong(bomSubData.get("item_uid").toString());
                                Item selectedItem = itemRepository.findByUid(itemUid);
                                subBom.setItem(selectedItem);
                            }
                            if (bomSubData.containsKey("company_uid")) {
                                Long companyUid = Long.parseLong(bomSubData.get("company_uid").toString());
                                Company selectedCompany = companyRepository.findByUid(companyUid);
                                subBom.setCompany(selectedCompany);
                            }
                            subBom.setCode(bomSubData.get("code").toString());
                            subBom.setQty(Double.valueOf(bomSubData.get("qty").toString()));
                            subBom.setRate(Double.valueOf(bomSubData.get("rate").toString()));
                            // product_uid 값이 있다면 product를 가져와서 userProduct에 설정

                            subBom.setCreated(LocalDateTime.now());
                            subBom.setUsed(1);


                            Bom insertSubBom = bomRepository.save(subBom);
                            Long sub_uid = insertSubBom.getUid();


                            if (bomSubData.containsKey("_children")) {
                                @SuppressWarnings("unchecked")
                                List<Map<String, Object>> children = (List<Map<String, Object>>) bomSubData.get("_children");
                                for (Map<String, Object> child : children) {

                                    Bom sub2Bom = new Bom();
                                    sub2Bom.setMain(uid);
                                    sub2Bom.setParent_uid(sub_uid);
                                    // item_uid 값이 있다면 item를 가져옴
                                    if (child.containsKey("item_uid")) {
                                        Long itemUid = Long.parseLong(child.get("item_uid").toString());
                                        Item selectedItem = itemRepository.findByUid(itemUid);
                                        sub2Bom.setItem(selectedItem);
                                    }
                                    if (child.containsKey("company_uid")) {
                                        Long companyUid = Long.parseLong(child.get("company_uid").toString());
                                        Company selectedCompany = companyRepository.findByUid(companyUid);
                                        sub2Bom.setCompany(selectedCompany);
                                    }
                                    sub2Bom.setCode(child.get("code").toString());
                                    sub2Bom.setQty(Double.valueOf(child.get("qty").toString()));
                                    sub2Bom.setRate(Double.valueOf(child.get("rate").toString()));
                                    // product_uid 값이 있다면 product를 가져와서 userProduct에 설정
                                    sub2Bom.setCreated(LocalDateTime.now());
                                    sub2Bom.setUsed(1);
                                    Bom insertSubBom2 = bomRepository.save(sub2Bom);
                                    Long sub2_uid = insertSubBom2.getUid();


                                    if (child.containsKey("_children")) {
                                        @SuppressWarnings("unchecked")
                                        List<Map<String, Object>> children2 = (List<Map<String, Object>>) child.get("_children");
                                        for (Map<String, Object> child2 : children2) {

                                            Bom sub3Bom = new Bom();
                                            sub3Bom.setMain(uid);
                                            sub3Bom.setParent_uid(sub2_uid);
                                            // item_uid 값이 있다면 item를 가져옴
                                            if (child2.containsKey("item_uid")) {
                                                Long itemUid = Long.parseLong(child2.get("item_uid").toString());
                                                Item selectedItem = itemRepository.findByUid(itemUid);
                                                sub3Bom.setItem(selectedItem);
                                            }
                                            if (child2.containsKey("company_uid")) {
                                                Long companyUid = Long.parseLong(child2.get("company_uid").toString());
                                                Company selectedCompany = companyRepository.findByUid(companyUid);
                                                sub3Bom.setCompany(selectedCompany);
                                            }
                                            sub3Bom.setCode(child2.get("code").toString());
                                            sub3Bom.setQty(Double.valueOf(child2.get("qty").toString()));
                                            sub3Bom.setRate(Double.valueOf(child2.get("rate").toString()));
                                            // product_uid 값이 있다면 product를 가져와서 userProduct에 설정
                                            sub3Bom.setCreated(LocalDateTime.now());
                                            sub3Bom.setUsed(1);
                                            bomRepository.save(sub3Bom);


                                        }
                                    }
                                }
                            }



                        }

                    }



                }




            }
            setSuccessResult(CommonResultDto);
            return CommonResultDto;

        }

    }
    @Override
    public List<Bom> selectTotalBom(CommonInfoSearchDto commonInfoSearchDto) {
        return bomRepository.findAll(commonInfoSearchDto);

    }

    @Override
    public List<Bom> selectBom(CommonInfoSearchDto commonInfoSearchDto) {
        return bomRepository.findInfo(commonInfoSearchDto);

    }


    @Override
    public CommonResultDto updateBom(BomDto bomDto) {

        Company company = companyRepository.findByUid(bomDto.getCompany_uid());
        Item item = itemRepository.findByUid(bomDto.getItem_uid());

        Optional<Bom> selectedBom = Optional.ofNullable(bomRepository.findByUid(bomDto.getUid()));


        Bom updatedBom;
        CommonResultDto CommonResultDto = new CommonResultDto();


        if (selectedBom.isPresent()) {
            Bom bom = selectedBom.get();
            bom.setMain(0L);
            bom.setParent_uid(0L);
            bom.setCode(bomDto.getCode());
            bom.setCompany(company);
            bom.setItem(item);
            bom.setQty(bomDto.getQty());
            bom.setRate(bomDto.getRate());
            bom.setUsed(Math.toIntExact(bomDto.getUsed()));

            bom.setUpdated(LocalDateTime.now());
            updatedBom = bomRepository.save(bom);
            Long uid = updatedBom.getUid();
            List<Map<String, Object>> bomSubList = bomDto.getBom_data();

            if (bomSubList != null) {

                List<Bom> deletedData = bomRepository.findByMain(uid);
                bomRepository.deleteAll(deletedData);


                for (Map<String, Object> bomSubData : bomSubList) {

                    if (bomSubData.containsKey("title")) {

                        String title = (String) bomSubData.get("title");
                        if ("main".equals(title)) {

                            Bom subBom = new Bom();
                            subBom.setMain(uid);
                            subBom.setParent_uid(uid);
                            // item_uid 값이 있다면 item를 가져옴
                            if (bomSubData.containsKey("item_uid")) {
                                Long itemUid = Long.parseLong(bomSubData.get("item_uid").toString());
                                Item selectedItem = itemRepository.findByUid(itemUid);
                                subBom.setItem(selectedItem);
                            }
                            subBom.setCompany(company);
                            subBom.setCode(bomSubData.get("code").toString());
                            subBom.setQty(Double.valueOf(bomSubData.get("qty").toString()));
                            subBom.setRate(Double.valueOf(bomSubData.get("rate").toString()));
                            // product_uid 값이 있다면 product를 가져와서 userProduct에 설정

                            subBom.setCreated(LocalDateTime.now());
                            subBom.setUsed(1);


                            Bom insertSubBom = bomRepository.save(subBom);
                            Long sub_uid = insertSubBom.getUid();


                            if (bomSubData.containsKey("_children")) {
                                @SuppressWarnings("unchecked")
                                List<Map<String, Object>> children = (List<Map<String, Object>>) bomSubData.get("_children");
                                for (Map<String, Object> child : children) {

                                    Bom sub2Bom = new Bom();
                                    sub2Bom.setMain(uid);
                                    sub2Bom.setParent_uid(sub_uid);
                                    // item_uid 값이 있다면 item를 가져옴
                                    if (child.containsKey("item_uid")) {
                                        Long itemUid = Long.parseLong(child.get("item_uid").toString());
                                        Item selectedItem = itemRepository.findByUid(itemUid);
                                        sub2Bom.setItem(selectedItem);
                                    }

                                    sub2Bom.setCompany(company);

                                    sub2Bom.setCode(child.get("code").toString());
                                    sub2Bom.setQty(Double.valueOf(child.get("qty").toString()));
                                    sub2Bom.setRate(Double.valueOf(child.get("rate").toString()));
                                    // product_uid 값이 있다면 product를 가져와서 userProduct에 설정
                                    sub2Bom.setCreated(LocalDateTime.now());
                                    sub2Bom.setUsed(1);
                                    Bom insertSubBom2 = bomRepository.save(sub2Bom);
                                    Long sub2_uid = insertSubBom2.getUid();


                                    if (child.containsKey("_children")) {
                                        @SuppressWarnings("unchecked")
                                        List<Map<String, Object>> children2 = (List<Map<String, Object>>) child.get("_children");
                                        for (Map<String, Object> child2 : children2) {

                                            Bom sub3Bom = new Bom();
                                            sub3Bom.setMain(uid);
                                            sub3Bom.setParent_uid(sub2_uid);
                                            // item_uid 값이 있다면 item를 가져옴
                                            if (child2.containsKey("item_uid")) {
                                                Long itemUid = Long.parseLong(child2.get("item_uid").toString());
                                                Item selectedItem = itemRepository.findByUid(itemUid);
                                                sub3Bom.setItem(selectedItem);
                                            }

                                            sub3Bom.setCompany(company);

                                            sub3Bom.setCode(child2.get("code").toString());
                                            sub3Bom.setQty(Double.valueOf(child2.get("qty").toString()));
                                            sub3Bom.setRate(Double.valueOf(child2.get("rate").toString()));
                                            // product_uid 값이 있다면 product를 가져와서 userProduct에 설정
                                            sub3Bom.setCreated(LocalDateTime.now());
                                            sub3Bom.setUsed(1);

                                            Bom insertSubBom3 = bomRepository.save(sub3Bom);
                                            Long sub3_uid = insertSubBom3.getUid();


                                            if (child2.containsKey("_children")) {
                                                List<Map<String, Object>> children3 = (List<Map<String, Object>>) child2.get("_children");
                                                for (Map<String, Object> child3 : children3) {

                                                    Bom sub4Bom = new Bom();
                                                    sub4Bom.setMain(uid);
                                                    sub4Bom.setParent_uid(sub3_uid);
                                                    // item_uid 값이 있다면 item를 가져옴
                                                    if (child3.containsKey("item_uid")) {
                                                        Long itemUid = Long.parseLong(child3.get("item_uid").toString());
                                                        Item selectedItem = itemRepository.findByUid(itemUid);
                                                        sub4Bom.setItem(selectedItem);
                                                    }

                                                    sub4Bom.setCompany(company);

                                                    sub4Bom.setCode(child2.get("code").toString());
                                                    sub4Bom.setQty(Double.valueOf(child3.get("qty").toString()));
                                                    sub4Bom.setRate(Double.valueOf(child3.get("rate").toString()));
                                                    // product_uid 값이 있다면 product를 가져와서 userProduct에 설정
                                                    sub4Bom.setCreated(LocalDateTime.now());
                                                    sub4Bom.setUsed(1);

                                                    bomRepository.save(sub4Bom);

                                                }



                                            }




                                        }
                                    }
                                }
                            }



                        }

                    }



                }




            }
            setSuccessResult(CommonResultDto);
            return CommonResultDto;


        } else {
            setFailResult(CommonResultDto,"error");
            return CommonResultDto;

        }

    }
//    @Override
//    public String deleteBom(List<Long> uids) throws Exception {
//
//        for (Long uid : uids) {
//            Optional<Bom> selectedBom = bomRepository.findById(uid);
//            if (selectedBom.isPresent()) {
//                Bom bom = selectedBom.get();
//                bom.setUsed(0);
//                bom.setDeleted(LocalDateTime.now());
//                bomRepository.save(bom);
//            } else {
//                throw new Exception("Bom with UID " + uid + " not found.");
//            }
//        }
//        return "Boms deleted successfully";
//    }

    @Override
    public String deleteBom(List<Map<String, Object>> requestList) {
            // requestList에서 각 데이터를 순회하면서 처리
            String test = "SUCCESS";
            for (Map<String, Object> data : requestList) {
                Long uid = Long.valueOf(String.valueOf(data.get("uid")));

                // BOM 객체가 존재하는지 확인
                Bom selectedBom = bomRepository.findById(uid).orElse(null);

                if (selectedBom != null) {
                    // BOM 객체 삭제
                    try {
                        bomRepository.delete(selectedBom);
                        List<Bom> deletedData = bomRepository.findByMain(uid);
                        if (deletedData != null && !deletedData.isEmpty()) {
                            bomRepository.deleteAll(deletedData);
                        }
                    } catch (Exception e) {

                        test = "FAIL";
                        LOGGER.info("액션 : {},", test);
                    }
                } else {

                }
            }
            LOGGER.info("LOGGER : {}",test);
            return test;


    }




    @Override
    public String excelUploadBom(List<Map<String, Object>> requestList) throws Exception {

        LOGGER.info("requestList  : {}",requestList);
        for (Map<String, Object> data : requestList) {

            Object itemCodeObj = data.get("item_code");
            String itemCode = (itemCodeObj != null) ? String.valueOf(itemCodeObj) : "";

            Object mainCodeObj = data.get("main_code");
            String mainCode = (mainCodeObj != null) ? String.valueOf(mainCodeObj) : "";

            Object parentCodeObj = data.get("parent_code");
            String parentCode = (parentCodeObj != null) ? String.valueOf(parentCodeObj) : "";

            Object qtyObj = data.get("qty");
            Double qty = 0.0; // 기본값 설정

            if (qtyObj != null) {
                try {
                    qty = Double.valueOf(String.valueOf(qtyObj));
                } catch (NumberFormatException e) {
                    // 숫자 형식으로 변환할 수 없는 경우 처리
                    // 예외 처리 필요
                    e.printStackTrace();
                }
            }
            Object rateObj = data.get("rate");
            Double rate = 0.0; // 기본값 설정
            if (rateObj != null) {
                try {
                    rate = Double.valueOf(String.valueOf(rateObj));
                } catch (NumberFormatException e) {
                    // 숫자 형식으로 변환할 수 없는 경우 처리
                    // 예외 처리 필요
                    e.printStackTrace();
                }
            }



            Long company_uid = Long.valueOf((String) data.get("company"));

            Company company = companyRepository.findByUid(company_uid);


            if (itemCode != null && !itemCode.isEmpty()) {
                // itemCode 값이 비어 있지 않으므로 추가 작업 수행
                LOGGER.info("itemCODE: {}",itemCode);
                Optional<Item> selectedItem = Optional.ofNullable(itemRepository.findByCodeAndUsedAndCompany(itemCode, 1L, company));

                if (selectedItem.isPresent()) {
                    LOGGER.info("itemCODE존재함: {}",itemCode);
                    if (mainCode.isEmpty()) { // 메인코드가 비었는지 검증(최상위 BOM인지 체크)
                        Optional<Bom> selectedBom = Optional.ofNullable(bomRepository.findByCodeAndMainAndCompanyAndUsed(itemCode, 0L, company, 1L));
                        if (selectedBom.isPresent()) { // 해당 BOM이 존재하면 진행하지 않음
                            LOGGER.info("진행안함: {}",itemCode);

                        } else { // 해당 BOM이 없으면 진행함
                            LOGGER.info("진행함: {}",itemCode);

                            Item item = itemRepository.findByCodeAndUsedAndCompany(itemCode, 1L, company);
                            Bom bom = new Bom();
                            bom.setCompany(company);
                            bom.setItem(item);
                            bom.setParent_uid(0L);
                            bom.setMain(0L);
                            bom.setCode(itemCode);
                            bom.setQty(qty);
                            bom.setRate(rate);
                            bom.setUsed(1);
                            bom.setCreated(LocalDateTime.now());
                            bomRepository.save(bom);
                        }


                    } else {
                        // 메인 BOM이 아니라면 해당 서브 BOM 진행
                        Optional<Bom> selectedBom = Optional.ofNullable(bomRepository.findByCodeAndMainAndCompanyAndUsed(mainCode, 0L, company, 1L));

                        LOGGER.info("진행한 BOM: {}",selectedBom);
                        LOGGER.info("진행한 BOM: {}",selectedBom.isPresent());

                        if (selectedBom.isPresent()) { // 메인 BOM이 존재하면 진행

                            Bom bom = selectedBom.get();
                            // Bom 객체를 이용하여 추가 작업 수행 해당
                            Long uid = bom.getUid();
                            // 이제 첫번째 뎁스부터 체크


                            LOGGER.info("페어런트 BOM: {}",uid);
                            LOGGER.info("페어런트 코드: {}",parentCode);

                            Optional<Bom> selectedParentBom = Optional.ofNullable(bomRepository.findByCodeAndCompanyAndUsed(parentCode,company, 1L));


                                Bom parentBom = selectedParentBom.get();

                                // Bom 객체를 이용하여 추가 작업 수행 해당
                                Item item = itemRepository.findByCodeAndUsedAndCompany(itemCode, 1L, company);


                                Bom subBom = new Bom();
                                subBom.setCompany(company);
                                subBom.setItem(item);
                                subBom.setParent_uid(parentBom.getUid());
                                subBom.setMain(uid);
                                subBom.setCode(itemCode);
                                subBom.setQty(qty);
                                subBom.setRate(rate);
                                subBom.setUsed(1);
                                subBom.setCreated(LocalDateTime.now());
                                bomRepository.save(subBom);




                        }


                    }


                }


            } else {
                System.out.println("itemCode is either null or empty");
                // itemCode가 null이거나 비어 있는 경우에 대한 처리
            }



        }
        return "Boms uploaded successfully";
    }

    private void setSuccessResult(CommonResultDto result){
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }
//    private void setFailResult(CommonResultDto result){
//        result.setSuccess(false);
//        result.setCode(CommonResponse.FAIL.getCode());
//        result.setMsg(CommonResponse.FAIL.getMsg());
//    }
    private void setFailResult(CommonResultDto result, String msg ){
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(msg);
    }

}
