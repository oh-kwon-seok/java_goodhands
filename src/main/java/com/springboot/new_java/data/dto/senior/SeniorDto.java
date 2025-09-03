package com.springboot.new_java.data.dto.senior;

import com.springboot.new_java.data.entity.senior.Senior;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Data
@NoArgsConstructor
@ToString
@Builder
public class SeniorDto {

    private Long uid;
    private String user_id;
    private String name;
    private String birth_date;

    private String caregiver_id;
    private String care_schedule_protocol;

    private Long care_home_uid;

    private Boolean use_care_schedule;
    private Boolean used;

    private String token;


    public SeniorDto(Long uid, String user_id,String name, String birth_date,String caregiver_id,String care_schedule_protocol,Long care_home_uid,Boolean use_care_schedule,   Boolean used, String token) {
        this.uid = uid;
        this.user_id = user_id;
        this.name = name;
        this.birth_date = birth_date;
        this.caregiver_id = caregiver_id;
        this.care_schedule_protocol = care_schedule_protocol;
        this.care_home_uid = care_home_uid;
        this.use_care_schedule = use_care_schedule;
        this.used = used;
        this.token = token;
    }

}
