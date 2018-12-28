package com.github.littlewhale.easymanage.common.response;

import com.alibaba.fastjson.JSON;

/**
 * 统一响应封装
 *
 * @author cjp
 * @date 2018/12/27
 */
public class Result<T> {
    /**
     * 成功消息
     */
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    private int code;
    private String message;
    private T data;

    /**
     * 成功
     *
     * @return
     */
    public static Result ok() {
        return new Result().setCode(ResultCode.SUCCESS).setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    /**
     * 成功-数据
     *
     * @return
     */
    public static <T> Result<T> ok(T data) {
        return new Result().setCode(ResultCode.SUCCESS).setMessage(DEFAULT_SUCCESS_MESSAGE).setData(data);
    }

    /**
     * 失败-消息
     *
     * @return
     */
    public static Result fail(String message) {
        return new Result().setCode(ResultCode.FAIL).setMessage(message);
    }


    public int getCode() {
        return code;
    }

    public Result setCode(ResultCode resultCode) {
        this.code = resultCode.code();
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
