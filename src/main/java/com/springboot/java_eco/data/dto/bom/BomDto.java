package com.springboot.java_eco.data.dto.bom;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Data
@NoArgsConstructor
@ToString

public class BomDto {
    private Long uid;
    private Long company_uid;
    private Long item_uid;
    private Long parent_uid;
    private Long test_uid;
    private String code;
    private Double qty;
    private Double rate;

    private List<Map<String, Object>> bom_data;

    private Long used;
    private String token;

    public BomDto(Long uid, Long company_uid, Long item_uid, Long parent_uid, Long test_uid,  String code, Double qty, Double rate,  Long used, List<Map<String, Object>> bom_data,String token){
        this.uid = uid;
        this.company_uid = company_uid;
        this.item_uid = item_uid;
        this.parent_uid = parent_uid;
        this.test_uid = test_uid;

        this.code = code;
        this.qty = qty;
        this.rate = rate;
        this.bom_data = bom_data;
        this.used = used;
        this.token = token;

    }

}
