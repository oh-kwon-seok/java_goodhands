package com.springboot.new_java.data.dto.care;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@ToString
public class CareScheduleDto {

    private Long uid;
    private String user_id;
    private String care_reserve_date;
    private String care_real_date;
    private Long caregiver_id;
    private Long senior_uid;

    private Boolean used;
    private String token;


    public CareScheduleDto(Long uid, String user_id, String name, Boolean used, String token) {
        this.user_id = user_id;
        this.uid = uid;

        this.used = used;
        this.token = token;
    }
}
