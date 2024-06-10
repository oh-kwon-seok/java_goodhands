package com.springboot.java_eco.data.repository.bookmarkEstimate;


import com.springboot.java_eco.data.entity.Company;
import com.springboot.java_eco.data.entity.BookmarkEstimate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("bookmarkEstimateRepositorySupport")
public interface BookmarkEstimateRepository extends JpaRepository<BookmarkEstimate,String>, BookmarkEstimateRepositoryCustom {

    BookmarkEstimate getById(String user_id);
    BookmarkEstimate findByUid(Long uid);
    BookmarkEstimate findByNameAndCompanyAndUsed(String name, Company company, Long used);

}
