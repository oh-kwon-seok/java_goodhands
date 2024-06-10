package com.springboot.java_eco.data.dao.impl;

import ch.qos.logback.classic.Logger;
import com.springboot.java_eco.common.CommonResponse;
import com.springboot.java_eco.data.dao.BookmarkEstimateDAO;
import com.springboot.java_eco.data.dao.BookmarkEstimateDAO;
import com.springboot.java_eco.data.dto.bookmarkEstimate.BookmarkEstimateDto;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.bookmarkEstimate.BookmarkEstimateDto;
import com.springboot.java_eco.data.entity.*;
import com.springboot.java_eco.data.entity.BookmarkEstimateSub;
import com.springboot.java_eco.data.repository.bookmarkEstimate.BookmarkEstimateRepository;
import com.springboot.java_eco.data.repository.bookmarkEstimateSub.BookmarkEstimateSubRepository;
import com.springboot.java_eco.data.repository.company.CompanyRepository;
import com.springboot.java_eco.data.repository.item.ItemRepository;
import com.springboot.java_eco.data.repository.bookmarkEstimate.BookmarkEstimateRepository;
import com.springboot.java_eco.data.repository.bookmarkEstimateSub.BookmarkEstimateSubRepository;
import com.springboot.java_eco.data.repository.user.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class BookmarkEstimateDAOImpl implements BookmarkEstimateDAO {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(BookmarkEstimateDAOImpl.class);

    private final BookmarkEstimateRepository bookmarkEstimateRepository;
    private final BookmarkEstimateSubRepository bookmarkEstimateSubRepository;
    private final UserRepository userRepository;

    private final ItemRepository itemRepository;

    private final CompanyRepository companyRepository;


    @Autowired
    public BookmarkEstimateDAOImpl(BookmarkEstimateRepository bookmarkEstimateRepository,
                                   BookmarkEstimateSubRepository bookmarkEstimateSubRepository,
                                   UserRepository userRepository,
                                   ItemRepository itemRepository,
                                   CompanyRepository companyRepository
                          ) {
        this.bookmarkEstimateRepository = bookmarkEstimateRepository;
        this.bookmarkEstimateSubRepository = bookmarkEstimateSubRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.companyRepository = companyRepository;

     
    }

    @Override
    public CommonResultDto insertBookmarkEstimate(BookmarkEstimateDto bookmarkEstimateDto) throws Exception {

        Company company = companyRepository.findByUid(bookmarkEstimateDto.getCompany_uid());
        User user = userRepository.getById(bookmarkEstimateDto.getUser_id());


        BookmarkEstimate bookmarkEstimate = new BookmarkEstimate();

        bookmarkEstimate.setUser(user);
        bookmarkEstimate.setCompany(company);

        bookmarkEstimate.setName(bookmarkEstimateDto.getName());
        bookmarkEstimate.setProduct_spec(bookmarkEstimateDto.getProduct_spec());
        bookmarkEstimate.setShip_place(bookmarkEstimateDto.getShip_place());

        bookmarkEstimate.setDescription(bookmarkEstimateDto.getDescription());
        bookmarkEstimate.setUsed(Math.toIntExact(bookmarkEstimateDto.getUsed()));
        bookmarkEstimate.setCreated(LocalDateTime.now());

        BookmarkEstimate insertBookmarkEstimate = bookmarkEstimateRepository.save(bookmarkEstimate);

        Long uid = insertBookmarkEstimate.getUid();

        LOGGER.info("[uid] : {}",uid);
        BookmarkEstimate selectedBookmarkEstimate = bookmarkEstimateRepository.findByUid(uid);

        List<Map<String, Object>> bookmarkEstimateSubList = bookmarkEstimateDto.getBookmark_estimate_sub();

        CommonResultDto CommonResultDto = new CommonResultDto();
        if (bookmarkEstimateSubList != null) {

            for (Map<String, Object> bookmarkEstimateSubData : bookmarkEstimateSubList) {
                BookmarkEstimateSub bookmarkEstimateSub = new BookmarkEstimateSub();

                bookmarkEstimateSub.setBookmarkEstimate(selectedBookmarkEstimate);
                if (bookmarkEstimateSubData.containsKey("item_uid")) {
                    Long itemUid = Long.parseLong(bookmarkEstimateSubData.get("item_uid").toString());
                    Item selectedItem = itemRepository.findByUid(itemUid);
                    bookmarkEstimateSub.setItem(selectedItem);
                }
                bookmarkEstimateSub.setUnit(bookmarkEstimateSubData.get("unit").toString());
                bookmarkEstimateSub.setQty(Double.valueOf(bookmarkEstimateSubData.get("qty").toString()));
                bookmarkEstimateSub.setPrice(Integer.valueOf(bookmarkEstimateSubData.get("price").toString()));
                bookmarkEstimateSub.setBuy_price(Integer.valueOf(bookmarkEstimateSubData.get("buy_price").toString()));
                bookmarkEstimateSub.setSupply_price(Integer.valueOf(bookmarkEstimateSubData.get("supply_price").toString()));
                bookmarkEstimateSub.setVat_price(Integer.valueOf(bookmarkEstimateSubData.get("vat_price").toString()));
                bookmarkEstimateSub.setDescription(bookmarkEstimateSubData.get("description").toString());

                bookmarkEstimateSub.setCreated(LocalDateTime.now());


                bookmarkEstimateSubRepository.save(bookmarkEstimateSub);
            }



            setSuccessResult(CommonResultDto);
            return CommonResultDto;

        }else {
            setFailResult(CommonResultDto);
            return CommonResultDto;

        }
    }


    @Override
    public CommonResultDto updateBookmarkEstimate(BookmarkEstimateDto bookmarkEstimateDto) throws Exception {


        Company company = companyRepository.findByUid(bookmarkEstimateDto.getCompany_uid());
        User user = userRepository.getById(bookmarkEstimateDto.getUser_id());

        
        Optional<BookmarkEstimate> selectedBookmarkEstimate = bookmarkEstimateRepository.findById(String.valueOf(bookmarkEstimateDto.getUid()));

        BookmarkEstimate updatedBookmarkEstimate;

        if (selectedBookmarkEstimate.isPresent()) {
            BookmarkEstimate bookmarkEstimate = selectedBookmarkEstimate.get();
            bookmarkEstimate.setCompany(company);
            bookmarkEstimate.setUser(user);

            
            bookmarkEstimate.setName(bookmarkEstimateDto.getName());

            bookmarkEstimate.setProduct_spec(bookmarkEstimateDto.getProduct_spec());
            bookmarkEstimate.setShip_place(bookmarkEstimateDto.getShip_place());

            bookmarkEstimate.setDescription(bookmarkEstimateDto.getDescription());
            bookmarkEstimate.setUsed(Math.toIntExact(bookmarkEstimateDto.getUsed()));
            bookmarkEstimate.setCreated(LocalDateTime.now());
            updatedBookmarkEstimate = bookmarkEstimateRepository.save(bookmarkEstimate);



        } else {
            throw new Exception();
        }


        List<Map<String, Object>> bookmarkEstimateSubList = bookmarkEstimateDto.getBookmark_estimate_sub();

        LOGGER.info("[BookmarkEstimate] : {}",selectedBookmarkEstimate);
        CommonResultDto CommonResultDto = new CommonResultDto();

        if (bookmarkEstimateSubList != null) {

            List<BookmarkEstimateSub> deletedData = bookmarkEstimateSubRepository.findByBookmarkEstimateUid(bookmarkEstimateDto.getUid());
            bookmarkEstimateSubRepository.deleteAll(deletedData);



            for (Map<String, Object> bookmarkEstimateSubData : bookmarkEstimateSubList) {
                BookmarkEstimateSub bookmarkEstimateSub = new BookmarkEstimateSub();
                bookmarkEstimateSub.setBookmarkEstimate(updatedBookmarkEstimate);

                if (bookmarkEstimateSubData.containsKey("item_uid")) {
                    Long itemUid = Long.parseLong(bookmarkEstimateSubData.get("item_uid").toString());
                    Item selectedItem = itemRepository.findByUid(itemUid);
                    bookmarkEstimateSub.setItem(selectedItem);
                }


                if (bookmarkEstimateSubData.get("unit") != null && !bookmarkEstimateSubData.get("unit").toString().isEmpty()) {
                    bookmarkEstimateSub.setUnit(bookmarkEstimateSubData.get("unit").toString());
                } else {
                    bookmarkEstimateSub.setUnit("");
                }
                if (!bookmarkEstimateSubData.get("qty").toString().isEmpty()) {
                    try {
                        bookmarkEstimateSub.setQty(Double.valueOf(bookmarkEstimateSubData.get("qty").toString()));
                    } catch (NumberFormatException e) {
                        bookmarkEstimateSub.setQty((double) 0L);
                    }
                }
                if (!bookmarkEstimateSubData.get("price").toString().isEmpty()) {
                    try {
                        bookmarkEstimateSub.setPrice(Integer.valueOf(bookmarkEstimateSubData.get("price").toString()));
                    } catch (NumberFormatException e) {
                        bookmarkEstimateSub.setPrice(0);
                    }
                }
                if (!bookmarkEstimateSubData.get("buy_price").toString().isEmpty()) {
                    try {
                        bookmarkEstimateSub.setBuy_price(Integer.valueOf(bookmarkEstimateSubData.get("buy_price").toString()));
                    } catch (NumberFormatException e) {
                        bookmarkEstimateSub.setBuy_price(0);
                    }
                }
                if (!bookmarkEstimateSubData.get("supply_price").toString().isEmpty()) {
                    try {
                        bookmarkEstimateSub.setSupply_price(Integer.valueOf(bookmarkEstimateSubData.get("supply_price").toString()));
                    } catch (NumberFormatException e) {
                        bookmarkEstimateSub.setSupply_price(0);
                    }
                }
                if (!bookmarkEstimateSubData.get("vat_price").toString().isEmpty()) {
                    try {
                        bookmarkEstimateSub.setVat_price(Integer.valueOf(bookmarkEstimateSubData.get("vat_price").toString()));
                    } catch (NumberFormatException e) {
                        bookmarkEstimateSub.setVat_price(0);
                    }
                }
                if (bookmarkEstimateSubData.get("description") != null && !bookmarkEstimateSubData.get("description").toString().isEmpty()) {
                    bookmarkEstimateSub.setDescription(bookmarkEstimateSubData.get("description").toString());
                } else {
                    bookmarkEstimateSub.setDescription("");
                }



               // bookmarkEstimateSub.setUnit(bookmarkEstimateSubData.get("unit").toString());
//                bookmarkEstimateSub.setQty(Double.valueOf(bookmarkEstimateSubData.get("qty").toString()));
//                bookmarkEstimateSub.setPrice(Integer.valueOf(bookmarkEstimateSubData.get("price").toString()));
//                bookmarkEstimateSub.setSupply_price(Integer.valueOf(bookmarkEstimateSubData.get("supply_price").toString()));
//                bookmarkEstimateSub.setVat_price(Integer.valueOf(bookmarkEstimateSubData.get("vat_price").toString()));
//                bookmarkEstimateSub.setDescription(bookmarkEstimateSubData.get("description").toString());

                bookmarkEstimateSub.setCreated(LocalDateTime.now());
                bookmarkEstimateSub.setUpdated(LocalDateTime.now());

                bookmarkEstimateSubRepository.save(bookmarkEstimateSub);
            }



            setSuccessResult(CommonResultDto);
            return CommonResultDto;

        }else {
            setFailResult(CommonResultDto);
            return CommonResultDto;

        }
    }


    @Override
    public List<BookmarkEstimate> selectBookmarkEstimate(CommonInfoSearchDto commonInfoSearchDto) {
        return bookmarkEstimateRepository.findInfo(commonInfoSearchDto);

    }

    @Override
    public List<BookmarkEstimate> selectTotalBookmarkEstimate(CommonInfoSearchDto commonInfoSearchDto) {
        return bookmarkEstimateRepository.findAll(commonInfoSearchDto);

    }

    @Override
    public String deleteBookmarkEstimate(List<Long> uids) throws Exception {

        for (Long uid : uids) {
            Optional<BookmarkEstimate> selectedBookmarkEstimate = Optional.ofNullable(bookmarkEstimateRepository.findByUid(uid));
            if (selectedBookmarkEstimate.isPresent()) {
                BookmarkEstimate bookmarkEstimate = selectedBookmarkEstimate.get();
                bookmarkEstimate.setUsed(0);
                bookmarkEstimate.setDeleted(LocalDateTime.now());
                bookmarkEstimateRepository.save(bookmarkEstimate);
            } else {
                throw new Exception("BookmarkEstimate with UID " + uid + " not found.");
            }
        }
        return "BookmarkEstimates deleted successfully";
    }

    @Override
    public String excelUploadBookmarkEstimate(List<Map<String, Object>> requestList) throws Exception {

        for (Map<String, Object> data : requestList) {

            String name = String.valueOf(data.get("name"));
            String product_spec = String.valueOf(data.get("product_spec"));
            String ship_place = String.valueOf(data.get("ship_place"));


            String description = String.valueOf(data.get("description"));


            Long company_uid = Long.valueOf((String) data.get("company"));

            Company company = companyRepository.findByUid(company_uid);

            LOGGER.info("company111:{}",company);

            if (company != null) {

                Optional<BookmarkEstimate> selectedBookmarkEstimate = Optional.ofNullable(bookmarkEstimateRepository.findByNameAndCompanyAndUsed(name, company, 1L));

                if (selectedBookmarkEstimate.isPresent()) {
                    BookmarkEstimate bookmarkEstimate = selectedBookmarkEstimate.get();

                    bookmarkEstimate.setCompany(company);
                    bookmarkEstimate.setName(name);
                    bookmarkEstimate.setProduct_spec(product_spec);
                    bookmarkEstimate.setShip_place(ship_place);
                    bookmarkEstimate.setDescription(description);
                    bookmarkEstimate.setUsed(1);

                    bookmarkEstimate.setUpdated(LocalDateTime.now());
                    bookmarkEstimateRepository.save(bookmarkEstimate);
                    LOGGER.info("bookmarkEstimate111:{}",bookmarkEstimate);
                } else {
                    BookmarkEstimate bookmarkEstimate = new BookmarkEstimate();

                    bookmarkEstimate.setCompany(company);
                    bookmarkEstimate.setName(name);
                    bookmarkEstimate.setProduct_spec(product_spec);
                    bookmarkEstimate.setShip_place(ship_place);
                    bookmarkEstimate.setDescription(description);
                    bookmarkEstimate.setUsed(1);
                    bookmarkEstimate.setCreated(LocalDateTime.now());
                    bookmarkEstimateRepository.save(bookmarkEstimate);

                    LOGGER.info("bookmarkEstimate222:{}",bookmarkEstimate);
                }

            }


        }
        return "BookmarkEstimate uploaded successfully";
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