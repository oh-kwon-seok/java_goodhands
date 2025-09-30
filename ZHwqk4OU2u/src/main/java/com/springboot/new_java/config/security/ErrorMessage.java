package com.springboot.new_java.config.security;

import jakarta.validation.constraints.NotNull;


@NotNull
public enum ErrorMessage{
    UNKNOWN_ERROR(401,"인증 토큰이 존재하지 않습니다."),
    WRONG_TYPE_TOKEN(401,"잘못된 토큰 정보입니다."),
    EXPIRED_TOKEN(401,"만료된 토큰 정보입니다."),
    UNSUPPORTED_TOKEN(401,"지원하지 않는 토큰 방식입니다."),
    ACCESS_DENIED(401,"알 수 없는 이유로 요청이 거절되었습니다.");


    int httpStatus;
    String msg;

    ErrorMessage(int httpStatus, String msg) {
        this.httpStatus = httpStatus;
        this.msg = msg;

    }
    public int getCode(){
        return httpStatus;
    }
    public String getMsg(){
        return msg;
    }
}

