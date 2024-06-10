package com.springboot.java_eco.data.repository.bookmarkEstimateSub;


import com.springboot.java_eco.data.entity.BookmarkEstimateSub;
import com.springboot.java_eco.data.entity.Company;
import com.springboot.java_eco.data.entity.FactorySub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("bookmarkEstimateSubRepositorySupport")
public interface BookmarkEstimateSubRepository extends JpaRepository<BookmarkEstimateSub,String>, BookmarkEstimateSubRepositoryCustom {

    BookmarkEstimateSub getById(String user_id);
    BookmarkEstimateSub findByUid(Long uid);

    List<BookmarkEstimateSub> findByBookmarkEstimateUid(Long uid);

}
