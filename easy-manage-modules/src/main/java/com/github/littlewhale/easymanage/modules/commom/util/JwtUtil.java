package com.github.littlewhale.easymanage.modules.commom.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.littlewhale.easymanage.modules.commom.constant.BaseConstant;
import com.google.common.collect.Maps;
import com.vip.vjtools.vjkit.id.IdUtil;
import com.vip.vjtools.vjkit.text.Charsets;
import com.vip.vjtools.vjkit.text.EncodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * java-jwt工具类
 * <p>
 * secret必须存在服务器端，且生成应该加密，用来进行jwt的签发和jwt的验证！
 * 生成源码参考：JWTCreator
 * </p>
 *
 * @author cjp
 * @date 2019/1/4
 */
@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    /**
     * 过期时间 5分钟
     */
    private static final long EXPIRE_TIME = 5 * 60 * 1000;

    /**
     * 生成密钥-增加uuid
     *
     * @param signId
     * @param secretPwd
     * @return
     */
    public static String secret(String signId, String secretPwd) {
        return String.join(".", signId, secretPwd);
    }

    /**
     * 生成token令牌(头部：headerClaims,载荷：payloadClaims)
     *
     * @param account 用户账号
     * @param secret  密钥
     * @return String
     */
    public static String sign(String account, String secret) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        secret = BaseConstant.JWT_SECRET + secret;
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withClaim(BaseConstant.CLAIMS_ACCOUNT, account)
                // 失效时间
                .withExpiresAt(date)
                .sign(algorithm);
    }

    /**
     * 校验token令牌是否正确
     * 直接抛出异常,可以更好捕获异常!
     *
     * @param token  令牌
     * @param secret 密钥
     * @return boolean
     */
    public static boolean verify(String token, String secret) {
        try {
            secret = BaseConstant.JWT_SECRET + secret;
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (Exception ex) {
            if(logger.isWarnEnabled()){
                logger.warn(ex.getMessage());
            }
            return false;
        }
    }


    /**
     * 获取token中的用户账号
     *
     * @param token
     * @return
     */
    public static Map<String, String> getTokenMap(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            Map<String, Claim> claimsMap = jwt.getClaims();
            Map<String, String> tokenMap = Maps.newHashMap();
            for (Map.Entry<String, Claim> entry : claimsMap.entrySet()) {
                tokenMap.put(entry.getKey(), entry.getValue().asString());
            }
            return tokenMap;
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获取token中的用户账号
     *
     * @param token
     * @return
     */
    public static String getAccount(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(BaseConstant.CLAIMS_ACCOUNT).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }


    public static void main(String[] args) {
        String account = "cjp";
        String secretPwd = "123";
        String signId = IdUtil.fastUUID().toString().replace("-", "");
        String secret = secret(signId, secretPwd);
        System.out.println("secret:" + secret);
        String token = sign(account, secret);
        String[] parts = token.split("\\.");
        String header = new String(EncodeUtil.decodeBase64(parts[0]), Charsets.UTF_8);
        String playload = new String(EncodeUtil.decodeBase64(parts[1]), Charsets.UTF_8);
        System.out.println("token:" + token);
        System.out.println("header:" + header);
        System.out.println("playload:" + playload);
        boolean result = verify(token, secret);
        System.out.println("result:" + result);
    }
}
