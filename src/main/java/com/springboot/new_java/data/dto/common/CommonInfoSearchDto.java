package com.springboot.new_java.data.dto.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommonInfoSearchDto {

//    private LocalDateTime start_date;
//    private LocalDateTime end_date;

    private String search_text;
    private String filter_title;

}
