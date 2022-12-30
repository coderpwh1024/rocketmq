package com.coderpwh.rocketmq.util;

import com.coderpwh.rocketmq.enums.ResultEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author coderpwh
 * @date 2022/12/28 16:00
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int SUCCESS_CODE = 0;

    public static final int FAIL_CODE = 1;

    public static final String SUCCESS_MSG = "success";

    public static final String FAIL_MSG = "error";

    /**
     * 返回标记：成功标记=0，失败标记=1
     */
    private int code;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 数据
     */
    private T results;

    public static <T> Result<T> ok() {
        return restResult(null, SUCCESS_CODE, SUCCESS_MSG);
    }

    public static <T> Result<T> ok(T data) {
        return restResult(data, SUCCESS_CODE, SUCCESS_MSG);
    }

    public static <T> Result<T> ok(T data, String msg) {
        return restResult(data, SUCCESS_CODE, msg);
    }

    public static <T> Result<T> failed() {
        return restResult(null, FAIL_CODE, FAIL_MSG);
    }

    public static <T> Result<T> failed(String msg) {
        return restResult(null, FAIL_CODE, msg);
    }

    public static <T> Result<T> failed(int code, String msg) {
        return restResult(null, code, msg);
    }

    public static <T> Result<T> failed(ResultEnum resultEnum) {
        return restResult(null, resultEnum.getCode(), resultEnum.getMsg());
    }

    public static <T> Result<T> build(int code, String msg) {
        return restResult(null, code, msg);
    }

    private static <T> Result<T> restResult(T data, int code, String msg) {
        Result<T> apiResult = new Result<>();
        apiResult.setCode(code);
        apiResult.setResults(data);
        apiResult.setMessage(msg);
        return apiResult;
    }

}
