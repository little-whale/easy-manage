
CREATE DATABASE easy_manage DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
Use easy_manage;

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `pkid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `parent_id` int(11) DEFAULT NULL COMMENT '上级ID',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `path` varchar(200) DEFAULT NULL COMMENT '菜单路径',
  `perms` varchar(200) DEFAULT NULL COMMENT '权限授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(1) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT '0' COMMENT '排序序号',
  `status` int(1) DEFAULT '1' COMMENT '状态（0：禁用，1：启用）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_host` varchar(50) DEFAULT NULL COMMENT '创建host',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_host` varchar(50) DEFAULT NULL COMMENT '更新host',
  PRIMARY KEY (`pkid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_menu
-- ----------------------------

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `pkid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `status` int(1) DEFAULT '1' COMMENT '状态（0：禁用，1：启用）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_host` varchar(50) DEFAULT NULL COMMENT '创建host',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_host` varchar(50) DEFAULT NULL COMMENT '更新host',
  PRIMARY KEY (`pkid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_role
-- ----------------------------

-- ----------------------------
-- Table structure for t_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_role_menu`;
CREATE TABLE `t_role_menu` (
  `pkid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `fk_role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  `fk_menu_id` int(11) DEFAULT NULL COMMENT '菜单ID',
  `status` int(1) DEFAULT '1' COMMENT '状态（0：禁用，1：启用）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_host` varchar(50) DEFAULT NULL COMMENT '创建host',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_host` varchar(50) DEFAULT NULL COMMENT '更新host',
  PRIMARY KEY (`pkid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `pkid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `account` varchar(100) DEFAULT NULL COMMENT '账号',
  `name` varchar(100) DEFAULT NULL COMMENT '姓名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `salt` varchar(20) DEFAULT NULL COMMENT '盐值',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `photo` varchar(200) DEFAULT NULL COMMENT '头像图片路径',
  `status` int(1) DEFAULT '1' COMMENT '状态（0：禁用，1：启用）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_host` varchar(50) DEFAULT NULL COMMENT '创建host',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_host` varchar(50) DEFAULT NULL COMMENT '更新host',
  PRIMARY KEY (`pkid`),
  UNIQUE KEY `un_account` (`account`) COMMENT '账号唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- Records of t_user
-- ----------------------------

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `pkid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `fk_user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `fk_role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  `status` int(1) DEFAULT '1' COMMENT '状态（0：禁用，1：启用）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_host` varchar(50) DEFAULT NULL COMMENT '创建host',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_host` varchar(50) DEFAULT NULL COMMENT '更新host',
  PRIMARY KEY (`pkid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------