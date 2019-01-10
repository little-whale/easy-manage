package com.github.littlewhale.easymanage.modules.commom.response;

/**
 * 统一响应编码枚举
 *
 * @author cjp
 * @date 2018/12/27
 */
public enum ResultCode {

    /**
     * 成功
     */
    SUCCESS(200),
    /**
     * 失败
     */
    FAIL(400),
    /**
     * 未认证（签名错误）
     */
    UNAUTHORIZED(401),
    /**
     * 接口不存在
     */
    NOT_FOUND(404),
    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR(500);

    private final int code;

    ResultCode(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }
}
