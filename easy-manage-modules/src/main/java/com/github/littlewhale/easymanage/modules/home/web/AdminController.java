package com.github.littlewhale.easymanage.modules.home.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.littlewhale.easymanage.modules.commom.response.Result;
import com.github.littlewhale.easymanage.modules.commom.util.JwtUtil;
import com.github.littlewhale.easymanage.modules.commom.util.ShiroUtil;
import com.github.littlewhale.easymanage.modules.config.shiro.JwtToken;
import com.github.littlewhale.easymanage.modules.config.shiro.JwtUserRealm;
import com.github.littlewhale.easymanage.modules.home.vo.LoginReq;
import com.github.littlewhale.easymanage.modules.home.vo.UserInfoVo;
import com.github.littlewhale.easymanage.modules.system.entity.User;
import com.github.littlewhale.easymanage.modules.system.service.IUserService;
import com.google.common.collect.Maps;
import com.vip.vjtools.vjkit.id.IdUtil;
import com.vip.vjtools.vjkit.mapper.BeanMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.Map;

/**
 * @author cjp
 * @date 2019/1/3
 */
@Api(value = "登录信息", tags = "登录信息")
@RestController
@RequestMapping("/admin")
@Validated
public class AdminController {

    @Autowired
    IUserService userService;

    @PostMapping("/login")
    @ApiOperation(value = "登录接口", notes = "登录接口,返回token")
    public Result login(@RequestBody @Valid LoginReq loginReq, HttpServletResponse response) {
        String account = loginReq.getAccount();
        User user = userService.getOne(new QueryWrapper<>(new User().setAccount(account)));
        String message = "用户不存在或密码错误！";
        if(user == null) {
            return Result.fail(message);
        }
        String encryPwd = ShiroUtil.sha1(loginReq.getPassword(), user.getSalt());
        if(!encryPwd.equals(user.getPassword())){
            return Result.fail(message);
        }
        String token = JwtUtil.sign(account, encryPwd);
        response.setHeader(HttpHeaders.AUTHORIZATION, token);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.AUTHORIZATION);
        return Result.ok(token);
    }

    @GetMapping("/getUserByToken")
    public Result getUserByToken(@RequestParam @NotEmpty(message = "token信息不能为空") String token){
        JwtToken jwtToken = new JwtToken(token);
        Subject subject = SecurityUtils.getSubject();
        subject.login(jwtToken);
        User user = (User)subject.getPrincipal();
        UserInfoVo userInfoVo = BeanMapper.map(user,UserInfoVo.class);
        userInfoVo.setAccess("admin");
        return Result.ok(userInfoVo);
    }

    @PostMapping("/logout")
    public Result logout(){
        return Result.ok();
    }

    @GetMapping(value = "/test")
    public Result test(@RequestParam String token) {
        System.out.println("userInfo:" + token);
        Subject subject = SecurityUtils.getSubject();
        // 当前已登录的
        if(subject.isAuthenticated()){
           return Result.ok(subject.getPrincipal());
        }
        return Result.ok();
    }

    /**
     * 必须有权限，header需要有效的token
     * @param token
     * @return
     */
    @GetMapping(value = "/hello")
    @RequiresAuthentication
    public Result hello(@RequestParam String token) {
        System.out.println("userInfo:" + token);

        return Result.ok();
    }

    /**
     * 必须有权限，header需要有效的token
     * @param role
     * @return
     */
    @GetMapping(value = "/role")
    @RequiresPermissions("test")
    public Result role(String role) {
        System.out.println("role:" + role);
        return Result.ok();
    }

    /**
     * 必须有权限，header需要有效的token
     * @param role
     * @return
     */
    @GetMapping(value = "/role2")
    @RequiresPermissions("test22")
    public Result role2(String role) {
        System.out.println("role:" + role);
        return Result.ok();
    }


}
