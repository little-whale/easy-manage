package com.github.littlewhale.easymanage.modules.system.service.impl;

import com.github.littlewhale.easymanage.modules.system.entity.User;
import com.github.littlewhale.easymanage.modules.system.mapper.UserMapper;
import com.github.littlewhale.easymanage.modules.system.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author cjp
 * @since 2019-01-10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
