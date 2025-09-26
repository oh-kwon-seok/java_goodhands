package com.springboot.new_java.data.dto.care;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@Data
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class CareScheduleProtocol {
    @JsonProperty("요일")
    private Map<String, WeeklySchedule> weeklySchedule;

    @JsonProperty("기간")
    private PeriodInfo period;

    @Data
    public static class PeriodInfo {
        @JsonProperty("시작일자")
        private String startDate;

        @JsonProperty("종료일자")
        private String endDate;
    }
}