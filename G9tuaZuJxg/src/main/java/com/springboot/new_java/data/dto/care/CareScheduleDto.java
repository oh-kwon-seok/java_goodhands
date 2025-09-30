package com.springboot.new_java.data.dto.care;

import com.springboot.new_java.data.entity.User;
import com.springboot.new_java.data.entity.care.CareHomeType;
import com.springboot.new_java.data.entity.senior.Senior;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@NoArgsConstructor
@ToString
public class CareScheduleDto {

    private Long uid;
    private String care_reserve_date;
    private String care_real_date;
    private User caregiver;
    private User user;
    private Long caregiver_id;
    private Long user_id;
    private Long senior_uid;
    private Senior senior;
    private LocalDateTime created;
    private LocalDateTime updated;
    private LocalDateTime deleted;
    private Boolean used;



    public CareScheduleDto(Long uid, String care_reserve_date, String care_real_date,Long caregiver_id, Long user_id, Long senior_uid,   User caregiver, User user, Senior senior, LocalDateTime created, LocalDateTime updated, LocalDateTime deleted,  String token, Boolean used) {
        this.uid = uid;
        this.care_reserve_date = care_reserve_date;
        this.care_real_date = care_real_date;
        this.caregiver = caregiver;
        this.user = user;
        this.senior = senior;
        this.caregiver_id = caregiver_id;
        this.user_id = user_id;
        this.senior_uid = senior_uid;
        this.created = created;
        this.updated = updated;
        this.deleted = deleted;
        this.used = used;

    }
}
