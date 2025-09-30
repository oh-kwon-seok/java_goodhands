package com.springboot.new_java.data.dto.care;

import lombok.*;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Data
@NoArgsConstructor
@ToString
public class CareLogDto {

    private Long uid;
    private Long user_id;
    private Long care_schedule_uid;
    private String caregiver_checklist;
    private String start_time;
    private String end_time;

    private String token;


    public CareLogDto(Long uid, Long user_id,Long care_schedule_uid,String caregiver_checklist, String start_time,String end_time, Boolean used, String token) {
        this.user_id = user_id;
        this.uid = uid;
        this.care_schedule_uid = care_schedule_uid;
        this.caregiver_checklist = caregiver_checklist;
        this.start_time = start_time;
        this.end_time = end_time;
        this.token = token;
    }
}
