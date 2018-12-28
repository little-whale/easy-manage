package com.github.littlewhale.easymanage.common.response;

/**
 * 统一响应编码枚举
 *
 * @author cjp
 * @date 2018/12/27
 */
public enum ResultCode {

    SUCCESS(200),//成功
    FAIL(400),//失败
    UNAUTHORIZED(401),//未认证（签名错误）
    NOT_FOUND(404),//接口不存在
    INTERNAL_SERVER_ERROR(500);//服务器内部错误

    private final int code;

    ResultCode(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }
}
