package com.springboot.java_eco.data.dto.stockRequest;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StockRequestSearchDto {

    private LocalDateTime start_date;
    private String search_text;
    private String filter_title;
    private LocalDateTime end_date;
    private Long work_task_uid;

}
