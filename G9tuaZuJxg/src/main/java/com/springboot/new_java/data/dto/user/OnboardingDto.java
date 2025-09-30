package com.springboot.new_java.data.dto.user;

import lombok.Data;

@Data
public class OnboardingDto {
    private String name;
    private String birthDate;  // YYYY-MM-DD 형식
    private String phone;
}