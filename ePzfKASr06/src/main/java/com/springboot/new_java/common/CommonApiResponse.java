package com.springboot.new_java.common;

public class CommonApiResponse<T> {
    private boolean success;
    private T data;
    private String message;
    private Integer code;
    private String errorCode; // 에러 코드 추가 (INVALID_PHONE, USER_NOT_FOUND 등)

    public CommonApiResponse(boolean success, T data, String message, Integer code) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.code = code;
    }

    public CommonApiResponse(boolean success, T data, String message, Integer code, String errorCode) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.code = code;
        this.errorCode = errorCode;
    }

    // ===== 성공 응답 메서드 =====

    /**
     * 성공 응답 (데이터만)
     */
    public static <T> CommonApiResponse<T> success(T data) {
        return new CommonApiResponse<>(true, data, "성공", 200);
    }

    /**
     * 성공 응답 (데이터 + 메시지)
     */
    public static <T> CommonApiResponse<T> success(T data, String message) {
        return new CommonApiResponse<>(true, data, message, 200);
    }

    /**
     * 성공 응답 (메시지만, 데이터 없음)
     */
    public static <T> CommonApiResponse<T> successWithoutData(String message) {
        return new CommonApiResponse<>(true, null, message, 200);
    }

    // ===== 에러 응답 메서드 =====

    /**
     * 에러 응답 (메시지만, 400 Bad Request)
     */
    public static <T> CommonApiResponse<T> error(String message) {
        return new CommonApiResponse<>(false, null, message, 400);
    }

    /**
     * 에러 응답 (메시지 + HTTP 상태 코드)
     */
    public static <T> CommonApiResponse<T> error(String message, int code) {
        return new CommonApiResponse<>(false, null, message, code);
    }

    /**
     * 에러 응답 (에러 코드 + 메시지, 400 Bad Request)
     */
    public static <T> CommonApiResponse<T> error(String errorCode, String message) {
        return new CommonApiResponse<>(false, null, message, 400, errorCode);
    }

    /**
     * 에러 응답 (에러 코드 + 메시지 + HTTP 상태 코드)
     */
    public static <T> CommonApiResponse<T> error(String errorCode, String message, int code) {
        return new CommonApiResponse<>(false, null, message, code, errorCode);
    }

    // ===== 특정 HTTP 상태 코드별 편의 메서드 =====

    /**
     * 400 Bad Request 에러
     */
    public static <T> CommonApiResponse<T> badRequest(String message) {
        return new CommonApiResponse<>(false, null, message, 400);
    }

    /**
     * 400 Bad Request 에러 (에러 코드 포함)
     */
    public static <T> CommonApiResponse<T> badRequest(String errorCode, String message) {
        return new CommonApiResponse<>(false, null, message, 400, errorCode);
    }

    /**
     * 401 Unauthorized 에러
     */
    public static <T> CommonApiResponse<T> unauthorized(String message) {
        return new CommonApiResponse<>(false, null, message, 401);
    }

    /**
     * 401 Unauthorized 에러 (에러 코드 포함)
     */
    public static <T> CommonApiResponse<T> unauthorized(String errorCode, String message) {
        return new CommonApiResponse<>(false, null, message, 401, errorCode);
    }

    /**
     * 404 Not Found 에러
     */
    public static <T> CommonApiResponse<T> notFound(String message) {
        return new CommonApiResponse<>(false, null, message, 404);
    }

    /**
     * 404 Not Found 에러 (에러 코드 포함)
     */
    public static <T> CommonApiResponse<T> notFound(String errorCode, String message) {
        return new CommonApiResponse<>(false, null, message, 404, errorCode);
    }

    /**
     * 500 Internal Server Error
     */
    public static <T> CommonApiResponse<T> serverError(String message) {
        return new CommonApiResponse<>(false, null, message, 500);
    }

    /**
     * 500 Internal Server Error (에러 코드 포함)
     */
    public static <T> CommonApiResponse<T> serverError(String errorCode, String message) {
        return new CommonApiResponse<>(false, null, message, 500, errorCode);
    }

    // ===== Getter & Setter =====

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

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}