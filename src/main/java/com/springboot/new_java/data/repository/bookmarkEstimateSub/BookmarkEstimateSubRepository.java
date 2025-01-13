package com.springboot.new_java.data.repository.bookmarkEstimateSub;


import com.springboot.new_java.data.entity.BookmarkEstimateSub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("bookmarkEstimateSubRepositorySupport")
public interface BookmarkEstimateSubRepository extends JpaRepository<BookmarkEstimateSub,String>, BookmarkEstimateSubRepositoryCustom {

    BookmarkEstimateSub getById(String user_id);
    BookmarkEstimateSub findByUid(Long uid);

    List<BookmarkEstimateSub> findByBookmarkEstimateUid(Long uid);

}
