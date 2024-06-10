package com.springboot.java_eco.data.dto.stock;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LotSearchDto {


    private Long company_uid;
    private String lot;
    private Long factory_uid;
    private Long factory_sub_uid;


}
