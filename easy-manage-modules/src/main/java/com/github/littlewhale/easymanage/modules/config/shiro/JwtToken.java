package com.github.littlewhale.easymanage.modules.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * JwtToken
 *
 * @author cjp
 * @date 2019/1/4
 */
public class JwtToken implements AuthenticationToken {

    /**
     * 密钥：Token
     */
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
