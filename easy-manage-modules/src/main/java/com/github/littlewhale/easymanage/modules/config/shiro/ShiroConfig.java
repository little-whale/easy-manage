package com.github.littlewhale.easymanage.modules.config.shiro;

import com.github.littlewhale.easymanage.modules.system.service.IUserService;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author cjp
 * @date 2019/1/4
 */
@Configuration
public class ShiroConfig {

    /**
     * 通过构造器注入，可以自定义凭证匹配器和缓存
     * @param userService
     * @return
     */
    @Bean
    public JwtUserRealm userRealm(IUserService userService){
        JwtUserRealm userRealm = new JwtUserRealm(userService);
        return userRealm;
    }

    /**
     * 会话管理器
     *
     * @param jwtUserRealm
     * @return
     */
    @Bean("securityManager")
    public DefaultWebSecurityManager securityManager(JwtUserRealm jwtUserRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        // 使用自定义Realm
        manager.setRealm(jwtUserRealm);
        // 关闭Shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        manager.setSubjectDAO(subjectDAO);
        return manager;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean filterFactory(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean filterFactory = new ShiroFilterFactoryBean();
        filterFactory.setSecurityManager(securityManager);
        // 1. 设置filter
        Map<String, Filter> filterMap = new HashMap<>(2);
        filterMap.put("jwt", new JwtFilter());
        filterFactory.setFilters(filterMap);


        // 定义shiro过滤链url信息
        Map<String, String> filterChainMap = new LinkedHashMap<>();
        // 不经过拦截器,swagger文档信息
        filterChainMap.put("/","anon");
        filterChainMap.put("/index","anon");
        filterChainMap.put("/*.ico", "anon");
        filterChainMap.put("/webjars/**","anon");
        filterChainMap.put("/swagger-resources","anon");
        filterChainMap.put("/v2/api-docs","anon");
        filterChainMap.put("/doc.html","anon");
        filterChainMap.put("/static/**","anon");
        filterChainMap.put("/admin/login", "anon");
        // 所有请求通过我们自己的JWTFilter
        filterChainMap.put("/**", "jwt");
        filterFactory.setFilterChainDefinitionMap(filterChainMap);
        return filterFactory;
    }

    /**
     * 添加注解支持
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        // https://zhuanlan.zhihu.com/p/29161098
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * 开启shiro aop注解支持. 使用代理方式; 所以需要开启代码支持;
     *
     * @param securityManager 安全管理器
     * @return 授权Advisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

}
