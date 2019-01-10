
CREATE DATABASE easy_manage DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
Use easy_manage;

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `pkid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `account` varchar(255) DEFAULT NULL COMMENT '账号',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `salt` varchar(20) DEFAULT NULL COMMENT '盐值',
  `status` int(1) DEFAULT '1' COMMENT '状态（0：禁用，1：启用）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_host` varchar(255) DEFAULT NULL COMMENT '创建host',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_host` varchar(255) DEFAULT NULL COMMENT '更新host',
  PRIMARY KEY (`pkid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- Records of t_user
-- ----------------------------
