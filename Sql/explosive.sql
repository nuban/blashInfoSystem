/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : explosive

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 04/04/2022 09:34:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ammunition
-- ----------------------------
DROP TABLE IF EXISTS `ammunition`;
CREATE TABLE `ammunition`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '弹药库',
  `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '弹药描述',
  `nickname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '弹药别名',
  `ingredient` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '主要成分',
  `picture` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '弹药图片',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for caseman
-- ----------------------------
DROP TABLE IF EXISTS `caseman`;
CREATE TABLE `caseman`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '涉爆人员id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `phonenumber` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '涉爆人的电话',
  `firm` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '涉爆人员的公司',
  `reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '查询的理由',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for danger
-- ----------------------------
DROP TABLE IF EXISTS `danger`;
CREATE TABLE `danger`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '危险人物',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '人名',
  `identity` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '身份证号',
  `address` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '个人住址',
  `phonenumber` varchar(12) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '电话号码',
  `danger_value` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '危险评估值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dangerplace
-- ----------------------------
DROP TABLE IF EXISTS `dangerplace`;
CREATE TABLE `dangerplace`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '危险地点',
  `enterprice` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '涉事企业',
  `place` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '危险地点',
  `explosive` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '丢失炸药',
  `danger_value` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '危险评估值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for marterial
-- ----------------------------
DROP TABLE IF EXISTS `marterial`;
CREATE TABLE `marterial`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '材料id',
  `date` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '上传时间',
  `place` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '案件地点',
  `reporter` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '报警单位',
  `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '案件描述',
  `main_explosive` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '主要的爆炸物',
  `sub_explosive` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '次要爆炸物',
  `user_id` bigint(0) NOT NULL COMMENT '民警id',
  `examined` tinyint(1) NULL DEFAULT NULL COMMENT '是否被审核了',
  `picture` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '案件相关图片',
  `caseman` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '涉爆人',
  `caseenterprice` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '涉爆企业',
  `caseenterprce_id` bigint(0) NULL DEFAULT NULL COMMENT '涉爆企业id',
  `caseman_id` bigint(0) NULL DEFAULT NULL COMMENT '涉爆人id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `caseman_id`(`caseman_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `caseman_id` FOREIGN KEY (`caseman_id`) REFERENCES `caseman` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` bigint(0) NOT NULL COMMENT '权限id',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '权限标识',
  `permission` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '菜单图标',
  `createby` bigint(0) NULL DEFAULT NULL,
  `createtime` datetime(0) NULL DEFAULT NULL,
  `delflag` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '是否删除',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '0', 'explosive:administer', NULL, NULL, '0', NULL);
INSERT INTO `menu` VALUES (2, '0', 'explosive:user', NULL, NULL, '0', NULL);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint(0) NOT NULL COMMENT '角色id',
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `rolekey` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '角色权限字符串',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '是否停用',
  `delflag` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '是否删除',
  `createby` bigint(0) NULL DEFAULT NULL,
  `updatetime` datetime(0) NULL DEFAULT NULL,
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '管理员', 'administer', '0', '0', NULL, '2022-03-28 17:14:13', '管理员角色');
INSERT INTO `role` VALUES (2, '普通用户', 'user', '0', '0', NULL, '2022-03-28 17:33:59', '普通用户权限');

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `roleid` bigint(0) NOT NULL COMMENT '角色id',
  `menuid` bigint(0) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`roleid`, `menuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES (1, 1);
INSERT INTO `role_menu` VALUES (1, 2);
INSERT INTO `role_menu` VALUES (2, 2);

-- ----------------------------
-- Table structure for role_user
-- ----------------------------
DROP TABLE IF EXISTS `role_user`;
CREATE TABLE `role_user`  (
  `userid` bigint(0) NOT NULL,
  `roleid` bigint(0) NOT NULL,
  PRIMARY KEY (`userid`, `roleid`) USING BTREE,
  INDEX `roleid`(`roleid`) USING BTREE,
  CONSTRAINT `roleid` FOREIGN KEY (`roleid`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `userid` FOREIGN KEY (`userid`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_user
-- ----------------------------
INSERT INTO `role_user` VALUES (1, 1);
INSERT INTO `role_user` VALUES (2, 2);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `nickname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT 'https://tse4-mm.cn.bing.net/th/id/OIP-C.308fke0-oJZMApzlmE5DUAHaFj?w=236&h=180&c=7&r=0&o=5&dpr=1.88&pid=1.7',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '默认为0是异常的，等待管理员认证',
  `delflag` char(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '是否删除',
  `createtime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  `gender` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '男' COMMENT '性别',
  `signcode` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '注册的token',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'zs', '$2a$10$HVdYknkYJ5Lo39j5yDWyuOzPne48M4kXyyzmKVixZA/qeT.jBRG6G', '张三', 'https://tse4-mm.cn.bing.net/th/id/OIP-C.308fke0-oJZMApzlmE5DUAHaFj?w=236&h=180&c=7&r=0&o=5&dpr=1.88&pid=1.7', '1', '0', NULL, NULL, '男', 'bili');
INSERT INTO `user` VALUES (2, 'lisi', '123456', '李四', 'https://tse4-mm.cn.bing.net/th/id/OIP-C.308fke0-oJZMApzlmE5DUAHaFj?w=236&h=180&c=7&r=0&o=5&dpr=1.88&pid=1.7', '1', '0', NULL, NULL, '男', NULL);
INSERT INTO `user` VALUES (4, 'zs1', '$2a$10$9nVb63qNh2qqQPccAdzayuj5GNB0MVmpN1.SsWGBDMR9SVK4xeRgO', NULL, 'https://tse4-mm.cn.bing.net/th/id/OIP-C.308fke0-oJZMApzlmE5DUAHaFj?w=236&h=180&c=7&r=0&o=5&dpr=1.88&pid=1.7', '1', '0', NULL, NULL, '男', NULL);

SET FOREIGN_KEY_CHECKS = 1;
