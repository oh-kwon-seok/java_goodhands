package com.springboot.new_java.data.repository.history;


import com.springboot.new_java.data.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("historyRepositorySupport")
public interface HistoryRepository extends JpaRepository<History,String>, HistoryRepositoryCustom {

//    History getById(Long user_id);
//    History findByUid(Long uid);
//

}
