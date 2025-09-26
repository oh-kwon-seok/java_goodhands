package com.springboot.new_java.data.dto.care;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeeklySchedule {
    @JsonProperty("방문유무")
    private boolean visitEnabled;

    @JsonProperty("횟수")
    private int visitCount;
}
