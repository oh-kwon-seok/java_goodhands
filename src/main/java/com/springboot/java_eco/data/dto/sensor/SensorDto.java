package com.springboot.java_eco.data.dto.sensor;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@ToString

public class SensorDto {
    private Long uid;

    private String code;  // 장비명

    private Double data;   //
    private String type;  //  용도   TEMP   / HUMI   / WEIGHT   // PH

    private String token;

    public SensorDto(Long uid, String code ,String name, Double data, String type,String token){
        this.uid = uid;
        this.code = code;
        this.data = data;
        this.type = type;
        this.token = token;

    }

}
