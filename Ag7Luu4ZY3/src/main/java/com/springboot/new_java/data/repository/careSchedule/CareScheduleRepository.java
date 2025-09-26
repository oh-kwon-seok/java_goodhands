package com.springboot.new_java.data.repository.careSchedule;


import com.springboot.new_java.data.entity.care.CareSchedule;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository("CareScheduleRepositorySupport")
public interface CareScheduleRepository extends JpaRepository<CareSchedule,Long>, CareScheduleRepositoryCustom {

    CareSchedule findByUid(Long uid);




    boolean existsBySeniorUidAndCareReserveDateAndUsed(Long seniorUid, String careReserveDate, boolean used);
    long countByCareReserveDateBetween(String startDate, String endDate);
}
