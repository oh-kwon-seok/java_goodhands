package com.springboot.new_java.data.dto.stock;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
