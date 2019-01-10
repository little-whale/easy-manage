package com.github.littlewhale.easymanage.modules.commom.constant;

/**
 * 基础的常量类
 *
 * @author cjp
 * @date 2018/12/27
 */
public class BaseConstant {

    /**
     * 状态：有效
     */
    public static final int STATUS_VALID = 1;

    /**
     * 状态：无效
     */
    public static final int STATUS_INVALID = 2;

    /**
     * redis-OK
     */
    public final static String OK = "OK";

    /**
     * redis过期时间，以秒为单位，一分钟
     */
    public final static int EXRP_MINUTE = 60;

    /**
     * redis过期时间，以秒为单位，一小时
     */
    public final static int EXRP_HOUR = 60 * 60;

    /**
     * redis过期时间，以秒为单位，一天
     */
    public final static int EXRP_DAY = 60 * 60 * 24;

    /**
     * redis-key-前缀-shiro:cache:
     */
    public final static String PREFIX_SHIRO_CACHE = "shiro:cache:";

    /**
     * redis-key-前缀-shiro:access_token:
     */
    public final static String PREFIX_SHIRO_ACCESS_TOKEN = "shiro:access_token:";

    /**
     * redis-key-前缀-shiro:refresh_token:
     */
    public final static String PREFIX_SHIRO_REFRESH_TOKEN = "shiro:refresh_token:";

    /**
     * JWT-secret:
     */
    public final static String JWT_SECRET = "easyJwt";

    /**
     * JWT-account:
     */
    public final static String CLAIMS_ACCOUNT = "account";

    /**
     * JWT-currentTimeMillis:
     */
    public final static String CURRENT_TIME_MILLIS = "currentTimeMillis";

    /**
     * PASSWORD_MAX_LEN
     */
    public static final Integer PASSWORD_MAX_LEN = 8;


}
