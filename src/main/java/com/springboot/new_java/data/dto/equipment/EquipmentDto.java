package com.springboot.new_java.data.dto.equipment;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@ToString

public class EquipmentDto {
    private Long uid;
    private Long company_uid;

    private String name;
    private String purpose;
    private String description;
    private Long used;
    private String token;

    public EquipmentDto(Long uid, Long company_uid, String name,String purpose,String description, Long used, String token){
        this.uid = uid;
        this.company_uid = company_uid;
        this.name = name;
        this.purpose = purpose;
        this.description = description;

        this.used = used;
        this.token = token;

    }

}
