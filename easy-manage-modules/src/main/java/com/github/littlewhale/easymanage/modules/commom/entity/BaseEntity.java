package com.github.littlewhale.easymanage.modules.commom.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author cjp
 * @date 2018/12/27
 */
@Data
@Accessors(chain = true)
public class BaseEntity {

    private Long pkid;

}
