package com.github.littlewhale.easymanage.modules.commom.handle;

import com.github.littlewhale.easymanage.modules.commom.exception.BizException;
import com.github.littlewhale.easymanage.modules.commom.response.Result;
import com.github.littlewhale.easymanage.modules.commom.response.ResultCode;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;

/**
 * 全局自定义异常处理,返回Result对象
 *
 * @author cjp
 * @date 2019/1/7
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 业务错误
     *
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(BizException.class)
    public Result baseExceptionHandler(HttpServletResponse response, BizException e) {
        logger.error(e.getMessage(), e);
        response.setStatus(500);
        return Result.instance(e.getCode(), e.getMessage());
    }

    /**
     * 其他内部错误
     *
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result otherExceptionHandler(HttpServletResponse response, Exception e) {
        int code = ResultCode.INTERNAL_SERVER_ERROR.code();
        String message = e.getMessage();
        if (e instanceof NullPointerException) {
            message = "空指针异常！";
        } else if (e instanceof ServletRequestBindingException) {
            message = "请求参数异常！";
        } else if (e instanceof NoHandlerFoundException) {
            code = ResultCode.NOT_FOUND.code();
        } else if(e instanceof AuthorizationException) {
            code = ResultCode.UNAUTHORIZED.code();
            message = "无权限访问！";
        }
        response.setStatus(code);
        if(logger.isDebugEnabled()){
            logger.debug(message, e);
        }
        return Result.instance(code, message);
    }

}
