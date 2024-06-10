package com.springboot.java_eco.data.repository.history;


import com.springboot.java_eco.data.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("historyRepositorySupport")
public interface HistoryRepository extends JpaRepository<History,String>, HistoryRepositoryCustom {

//    History getById(String user_id);
//    History findByUid(Long uid);
//

}
