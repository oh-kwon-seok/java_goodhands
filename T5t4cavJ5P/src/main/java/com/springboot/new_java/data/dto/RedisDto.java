package com.springboot.new_java.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Duration;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RedisDto {
    private String key;
    private String value;
    private Duration duration;



}
