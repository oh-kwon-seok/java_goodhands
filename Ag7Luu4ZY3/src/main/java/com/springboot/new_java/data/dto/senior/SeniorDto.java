package com.springboot.new_java.data.dto.senior;

import com.springboot.new_java.data.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Data
@NoArgsConstructor
@ToString
public class SeniorDto {

    private Long uid;
    private String user_id;
    private String name;
    private String birth_date;

    private String caregiver_id;
    private String care_schedule_protocol;

    private Long care_home_uid;
    private User user;
    private User caregiver;

    private List<Map<String, Object>> senior_disease;

    private LocalDateTime created;
    private Boolean use_care_schedule;
    private Boolean used;

    private String token;


    public SeniorDto(Long uid, String user_id,String name, String birth_date,String caregiver_id,User user,User caregiver,String care_schedule_protocol,Long care_home_uid,Boolean use_care_schedule, List<Map<String, Object>> senior_disease, LocalDateTime created,   Boolean used, String token) {
        this.uid = uid;
        this.user_id = user_id;
        this.name = name;
        this.birth_date = birth_date;
        this.caregiver_id = caregiver_id;
        this.care_schedule_protocol = care_schedule_protocol;
        this.care_home_uid = care_home_uid;
        this.user = user;
        this.caregiver = caregiver;
        this.use_care_schedule = use_care_schedule;
        this.senior_disease = senior_disease;
        this.created = created;
        this.used = used;
        this.token = token;
    }

}
