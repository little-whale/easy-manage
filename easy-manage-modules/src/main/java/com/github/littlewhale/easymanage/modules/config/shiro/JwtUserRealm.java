package com.github.littlewhale.easymanage.modules.config.shiro;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.littlewhale.easymanage.modules.commom.constant.BaseConstant;
import com.github.littlewhale.easymanage.modules.commom.exception.BizException;
import com.github.littlewhale.easymanage.modules.commom.util.JwtUtil;
import com.github.littlewhale.easymanage.modules.system.entity.User;
import com.github.littlewhale.easymanage.modules.system.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author cjp
 * @date 2019/1/4
 */
@Component
public class JwtUserRealm extends AuthorizingRealm {

    @Autowired
    IUserService userService;

    /**
     * 检验用户是否有权限时调用此方法
     * 1、subject.hasRole(“admin”) 或 subject.isPermitted(“admin”)
     * 2、@RequiresRoles("admin")
     * 3、[@shiro.hasPermission name = "admin"][/@shiro.hasPermission]：在页面上加shiro标签的时调用
     *
     * @param principal
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User)principal.getPrimaryPrincipal();
        if(user != null ){
            // 添加角色，添加权限
            authorizationInfo.addRole("测试");
            authorizationInfo.addStringPermission("test");
        }
        return authorizationInfo;
    }

    /**
     * 校验用户名和密码是否正确
     * <p>
     * 当执行SecurityUtils.getSubject().login(token)时调用
     *
     * @param authToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        String token = (String)authToken.getCredentials();
        String account = JwtUtil.getAccount(token);
        if(StringUtils.isBlank(account)) {
            throw new AuthenticationException("token账号信息为空！");
        }
        User user = userService.getOne(new QueryWrapper(new User().setAccount(account)));
        if(user == null) {
            throw new AuthenticationException("用户信息不存在!");
        }
        if(!JwtUtil.verify(token, user.getPassword())) {
            // 统一抛出异常信息(密钥无效，token失效等)
            throw new AuthenticationException("无效的token信息！");
        }
        return new SimpleAuthenticationInfo(user, token, getName());

    }

    /**
     * 必须重写此方法，不然Shiro会报错！
     * 源码：AuthenticatingRealm:getAuthenticationTokenClass为UsernamePasswordToken,为false报错
     * return token != null && getAuthenticationTokenClass().isAssignableFrom(token.getClass());
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }
}
