package com.springboot.new_java.data.dto.item;

import lombok.*;


@Data
@NoArgsConstructor
@ToString

public class ItemDto {
    private Long uid;
    private String code; // 코드
    private String simple_code; // 약호
    private String ingr_kor_name; // 자재/제품 한글명
    private String ingr_eng_name; // 자재/제품 영문명
    private String inout_unit; // 수불단위
    private String inout_type; // 수불구분
    private String currency_unit; // 화폐단위
    private String buy_type; // 구매구분
    private String type_code; // 품목유형  // 원자재 / 부자재 / 반제품 / 완제품 등
    private String classify_code; // 유형코드
    private String component_code; // 성분코드
    private String hs_code; // hs코드
    private String nts_code; // 국세청 코드
    private String description; // 비고
    private String image_url;

    private Long company_uid;
    private Long type_uid;



    private Long used;
    private String token;

    public ItemDto(Long uid,
                   String code, // 코드
                   String simple_code, // 약호
                   String ingr_kor_name, // 자재/제품 한글명
                   String ingr_eng_name, // 자재/제품 영문명
                   String inout_unit, // 수불단위
                   String inout_type, // 수불구분
                   String currency_unit, // 화폐단위
                   String buy_type, // 구매구분
                   String type_code, // 품목유형  // 원자재 / 부자재 / 반제품 / 완제품 등
                   String classify_code, // 유형코드
                   String component_code, // 성분코드
                   String hs_code, // hs코드
                   String nts_code, // 국세청 코드
                   String description, // 비고
                   String image_url,
                   Long company_uid,
                   Long type_uid,
                   Long used, String token){
        this.uid = uid;
        this.code= code;
        this.simple_code= simple_code;
        this.ingr_kor_name= ingr_kor_name;
        this.ingr_eng_name= ingr_eng_name;
        this.inout_unit= inout_unit;
        this.inout_type= inout_type;
        this.currency_unit=  currency_unit;
        this.buy_type= buy_type;
        this.type_code= type_code;
        this.classify_code= classify_code;
        this.component_code= component_code;
        this.hs_code=hs_code;
        this.nts_code=nts_code;
        this.description=description;
        this.image_url=image_url;
        this.company_uid=company_uid;
        this.type_uid=type_uid;
        this.used = used;
        this.token = token;

    }

}
