package com.github.littlewhale.easymanage.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.github.littlewhale.easymanage.modules.commom.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author cjp
 * @since 2019-01-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_role_menu")
public class RoleMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    private Integer fkRoleId;

    /**
     * 菜单ID
     */
    private Integer fkMenuId;

    /**
     * 状态（0：禁用，1：启用）
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建host
     */
    private String createHost;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 更新host
     */
    private String updateHost;


}
