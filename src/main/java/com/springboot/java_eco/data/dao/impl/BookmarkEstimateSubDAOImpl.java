package com.springboot.java_eco.data.dao.impl;

import com.springboot.java_eco.data.dao.BookmarkEstimateSubDAO;
import com.springboot.java_eco.data.dao.BookmarkEstimateSubDAO;
import com.springboot.java_eco.data.dto.bookmarkEstimateSub.BookmarkEstimateSubSearchDto;
import com.springboot.java_eco.data.entity.*;
import com.springboot.java_eco.data.entity.Process;
import com.springboot.java_eco.data.repository.bookmarkEstimate.BookmarkEstimateRepository;
import com.springboot.java_eco.data.repository.company.CompanyRepository;
import com.springboot.java_eco.data.repository.item.ItemRepository;
import com.springboot.java_eco.data.repository.process.ProcessRepository;
import com.springboot.java_eco.data.repository.bookmarkEstimateSub.BookmarkEstimateSubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class BookmarkEstimateSubDAOImpl implements BookmarkEstimateSubDAO {
    
    private final BookmarkEstimateSubRepository bookmarkEstimateSubRepository;

    private final BookmarkEstimateRepository bookmarkEstimateRepository;

    private final ItemRepository itemRepository;

    private final CompanyRepository companyRepository;


    @Autowired
    public BookmarkEstimateSubDAOImpl(BookmarkEstimateSubRepository bookmarkEstimateSubRepository, BookmarkEstimateRepository bookmarkEstimateRepository, CompanyRepository companyRepository, ItemRepository itemRepository){
        this.bookmarkEstimateSubRepository = bookmarkEstimateSubRepository;
        this.bookmarkEstimateRepository = bookmarkEstimateRepository;
        this.companyRepository = companyRepository;
        this.itemRepository = itemRepository;

    }

    @Override
    public List<BookmarkEstimateSub> selectTotalBookmarkEstimateSub(BookmarkEstimateSubSearchDto bookmarkEstimateSubSearchDto) {
        return bookmarkEstimateSubRepository.findAll(bookmarkEstimateSubSearchDto);

    }


    @Override
    public List<BookmarkEstimateSub> selectBookmarkEstimateSub(BookmarkEstimateSubSearchDto bookmarkEstimateSubSearchDto) {
        return bookmarkEstimateSubRepository.findInfo(bookmarkEstimateSubSearchDto);

    }
    @Override
    public List<BookmarkEstimateSub> selectBookmarkEstimateUidSelect(BookmarkEstimateSubSearchDto bookmarkEstimateSubSearchDto) {
        return bookmarkEstimateSubRepository.findByBookmarkEstimateUidSelect(bookmarkEstimateSubSearchDto);

    }

    @Override
    public String excelUploadBookmarkEstimateSub(List<Map<String, Object>> requestList) throws Exception {

        for (Map<String, Object> data : requestList) {

            String bookmark_estimate_name = String.valueOf(data.get("bookmark_estimate_name"));

            String code = String.valueOf(data.get("item_code"));

            Double qty = Double.valueOf(String.valueOf(data.get("qty")));
            Integer price = Integer.valueOf(String.valueOf(data.get("price")));
            String unit = String.valueOf(data.get("unit"));
            Integer buy_price = Integer.valueOf(String.valueOf(data.get("buy_price")));
            Integer supply_price = Integer.valueOf(String.valueOf(data.get("supply_price")));
            Integer vat_price = Integer.valueOf(String.valueOf(data.get("vat_price")));
            String description = String.valueOf(data.get("description"));
            Long company_uid = Long.valueOf(String.valueOf(data.get("company")));

            Company company = companyRepository.findByUid(company_uid);


            if (company != null) {

                Optional<BookmarkEstimate> selectedBookmarkEstimate = Optional.ofNullable(bookmarkEstimateRepository.findByNameAndCompanyAndUsed(bookmark_estimate_name, company, 1L));

                if (selectedBookmarkEstimate.isPresent()) {
                   BookmarkEstimate bookmarkEstimate = selectedBookmarkEstimate.get();

                    BookmarkEstimateSub bookmarkEstimateSub = new BookmarkEstimateSub();

                    bookmarkEstimateSub.setBookmarkEstimate(bookmarkEstimate);


                    Optional<Item> selectedItem = Optional.ofNullable(itemRepository.findByCodeAndUsedAndCompany(code, 1L,company));


                    if(selectedItem.isPresent()){
                        Item item = itemRepository.findByCodeAndUsedAndCompany(code, 1L, company);
                        bookmarkEstimateSub.setItem(item);
                        bookmarkEstimateSub.setQty(qty);
                        bookmarkEstimateSub.setUnit(unit);
                        bookmarkEstimateSub.setPrice(price);
                        bookmarkEstimateSub.setBuy_price(buy_price);
                        bookmarkEstimateSub.setSupply_price(supply_price);
                        bookmarkEstimateSub.setVat_price(vat_price);
                        bookmarkEstimateSub.setDescription(description);



                        bookmarkEstimateSub.setCreated(LocalDateTime.now());
                        bookmarkEstimateSubRepository.save(bookmarkEstimateSub);

                    }


                }

            }


        }
        return "BOOKMARK_ESTIMATE uploaded successfully";
    }

}
