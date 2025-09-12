package com.springboot.new_java.common;

public class CommonApiResponse<T> {
    private boolean success;
    private T data;
    private String message;
    private Integer code;

    public CommonApiResponse(boolean success, T data, String message, Integer code) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.code = code;
    }

    public static <T> CommonApiResponse<T> success(T data) {
        return new CommonApiResponse<>(true, data, "성공2", 200);
    }

    public static <T> CommonApiResponse<T> success(T data, String message) {
        return new CommonApiResponse<>(true, data, message, 200);
    }

    public static <T> CommonApiResponse<T> error(String message, int code) {
        return new CommonApiResponse<>(false, null, message, code);
    }

    public static <T> CommonApiResponse<T> error(String message) {
        return new CommonApiResponse<>(false, null, message, 400);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
