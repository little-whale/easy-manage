package com.github.littlewhale.easymanage.modules.config.mvc;

import com.github.littlewhale.easymanage.modules.commom.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 参考：BasicErrorController
 * 4xx,5xx 错误处理逻辑，默认的处理逻辑
 * <p>
 * ErrorMvcAutoConfiguration
 * </p>
 *
 * @author cjp
 * @date 2019/1/8
 */
@Slf4j
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class GlobalErrorController extends AbstractErrorController {

    private final ErrorProperties errorProperties;

    /**
     * 构造方法初始化ErrorProperties，参考:ErrorMvcAutoConfiguration的构造方法实现
     *
     * @param errorAttributes
     * @param serverProperties
     */
    public GlobalErrorController(ErrorAttributes errorAttributes, ServerProperties serverProperties) {
        super(errorAttributes);
        Assert.notNull(serverProperties, "ServerProperties must not be null");
        this.errorProperties = serverProperties.getError();
    }


    @Override
    public String getErrorPath() {
        return errorProperties.getPath();
    }

    @RequestMapping
    @ResponseBody
    public Result error(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        Map<String, Object> errorMap = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
        errorLog(errorMap);
        return Result.instance(status.value(), status.getReasonPhrase(), errorMap);
    }

    /**
     * 输出错误请求日志
     *
     * @param errorMap
     */
    private void errorLog(Map<String, Object> errorMap) {
        log.error("error request====>url:{},status:{},errorMsg:{},time:{}",
                errorMap.get("path"), errorMap.get("status"), errorMap.get("message"), errorMap.get("timestamp"));
    }

    /**
     * Determine if the stacktrace attribute should be included.
     *
     * @param request  the source request
     * @param produces the media type produced (or {@code MediaType.ALL})
     * @return if the stacktrace attribute should be included
     */
    protected boolean isIncludeStackTrace(HttpServletRequest request, MediaType produces) {
        ErrorProperties.IncludeStacktrace include = errorProperties.getIncludeStacktrace();
        if (include == ErrorProperties.IncludeStacktrace.ALWAYS) {
            return true;
        }
        if (include == ErrorProperties.IncludeStacktrace.ON_TRACE_PARAM) {
            return getTraceParameter(request);
        }
        return false;
    }

}
