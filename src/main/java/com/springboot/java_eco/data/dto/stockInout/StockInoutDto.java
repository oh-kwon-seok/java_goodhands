package com.springboot.java_eco.data.dto.stockInout;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter

@Data
@NoArgsConstructor
@ToString

public class StockInoutDto {
    private Long uid;

    private String doc_type;
    private String status;

    private Long company_uid;
    private String user_id;
    private String token;
    private List<Map<String, Object>> stock_inout_sub;

    public StockInoutDto(
             Long uid,

            String doc_type,
            String status,
            Long company_uid,
            String user_id,
            String token
    ){
        this.uid = uid;
        this.doc_type = doc_type;
        this.status = status;
        this.company_uid = company_uid;
        this.user_id = user_id;
        this.token = token;

    }

}
