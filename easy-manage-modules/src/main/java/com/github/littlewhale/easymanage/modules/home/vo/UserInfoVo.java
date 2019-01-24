package com.github.littlewhale.easymanage.modules.home.vo;


import lombok.Data;

/**
 * @author cjp
 * @date 2019/1/4
 */
@Data
public class UserInfoVo {

    private Long pkid;

    /**
     * 姓名
     */
    private String name;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 头像图片路径
     */
    private String photo;

    /**
     * 账号
     */
    private String account;

    /**
     * router的权限数组
     */
    private String access;

}
