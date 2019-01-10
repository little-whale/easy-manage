package com.github.littlewhale.easymanage.modules.commom.exception;

/**
 * 业务Exception
 *
 * @author cjp
 * @date 2019/1/7
 */
public class BizException extends RuntimeException {
    // 错误码
    private int code;

    public BizException() {

    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, int code) {
        super(message);
        this.code = code;
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
