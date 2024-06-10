package com.springboot.java_eco.common;

public enum CommonResponse {

    SUCCESS(0,"Success"), // 등록 성공
    FAIL(-1,"FAIL"); // 등록 실패


    int code;
    String msg;

    CommonResponse(int code,String msg){
        this.code = code;
        this.msg = msg;

    }
    public int getCode(){
        return code;
    }
    public String getMsg(){
        return msg;
    }
}
