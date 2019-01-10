package com.github.littlewhale.easymanage.modules.commom.response;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 统一响应封装
 *
 * @author cjp
 * @date 2018/12/27
 */
@ApiModel(description = "返回数据类")
public class Result<T> {
    /**
     * 成功消息
     */
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    @ApiModelProperty(value="返回编码",required = true)
    private int code;
    @ApiModelProperty(value="返回说明")
    private String message;
    @ApiModelProperty(value = "返回对象")
    private T data;

    private Result() {
    }
    
    private Result(int code,String message){
        this.code = code;
        this.message = message;
    }


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

    /**
     * 构造方法
     *
     * @param code
     * @param message
     * @return
     */
    public static Result instance(int code,String message){
        return new Result(code,message);
    }
    /**
     * 构造方法
     *
     * @param code
     * @param message
     * @param data
     * @return
     */
    public static <T> Result<T> instance(int code,String message,T data){
        return new Result(code,message).setData(data);
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
