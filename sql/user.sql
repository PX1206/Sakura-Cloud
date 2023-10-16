/*
 Navicat Premium Data Transfer

 Source Server         : 128
 Source Server Type    : MySQL
 Source Server Version : 50743
 Source Host           : 192.168.43.128:3306
 Source Schema         : user

 Target Server Type    : MySQL
 Target Server Version : 50743
 File Encoding         : 65001

 Date: 16/10/2023 14:24:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `t_admin_role`;
CREATE TABLE `t_admin_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `description` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_dt` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_dt` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改日期',
  `status` int(1) NULL DEFAULT 1 COMMENT '状态：1正常 0删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'admin角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_admin_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_admin_role_permission`;
CREATE TABLE `t_admin_role_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色id',
  `permission_id` int(11) NULL DEFAULT NULL COMMENT '权限id',
  `create_dt` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_dt` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改日期',
  `status` int(1) NULL DEFAULT 1 COMMENT '状态：1正常 0删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'admin角色权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_admin_user
-- ----------------------------
DROP TABLE IF EXISTS `t_admin_user`;
CREATE TABLE `t_admin_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户ID',
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `sex` int(1) NULL DEFAULT NULL COMMENT '性别：1男 2女',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `mobile` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `address` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `head_img` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `salt` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '盐',
  `create_dt` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_dt` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改日期',
  `status` int(1) NULL DEFAULT 1 COMMENT '状态：0注销 1正常  2冻结 3临时冻结',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'admin用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_admin_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_admin_user_role`;
CREATE TABLE `t_admin_user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色id',
  `create_dt` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_dt` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改日期',
  `status` int(1) NULL DEFAULT 1 COMMENT '状态：1正常 0删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'admin用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_customer_role
-- ----------------------------
DROP TABLE IF EXISTS `t_customer_role`;
CREATE TABLE `t_customer_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `description` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_dt` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_dt` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改日期',
  `status` int(1) NULL DEFAULT 1 COMMENT '状态：1正常 0删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '客户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_customer_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_customer_role_permission`;
CREATE TABLE `t_customer_role_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色id',
  `permission_id` int(11) NULL DEFAULT NULL COMMENT '权限id',
  `create_dt` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_dt` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改日期',
  `status` int(1) NULL DEFAULT 1 COMMENT '状态：1正常 0删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '客户角色权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_customer_user
-- ----------------------------
DROP TABLE IF EXISTS `t_customer_user`;
CREATE TABLE `t_customer_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户ID',
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `sex` int(1) NULL DEFAULT NULL COMMENT '性别：1男 2女',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `mobile` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `address` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `head_img` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `salt` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '盐',
  `create_dt` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_dt` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改日期',
  `status` int(1) NULL DEFAULT 1 COMMENT '状态：0注销 1正常  2冻结 3临时冻结',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '客户用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_customer_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_customer_user_role`;
CREATE TABLE `t_customer_user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色id',
  `create_dt` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_dt` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改日期',
  `status` int(1) NULL DEFAULT 1 COMMENT '状态：1正常 0删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '客户用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_merchant
-- ----------------------------
DROP TABLE IF EXISTS `t_merchant`;
CREATE TABLE `t_merchant`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `merchant_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商户号',
  `parent_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父商户号，顶层商户为0',
  `merchant_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商户名称',
  `unified_credit_code` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '统一信用代码/营业执照号',
  `effective_start_dt` date NULL DEFAULT NULL COMMENT '营业执照有效期开始日期',
  `effective_end_dt` date NULL DEFAULT NULL COMMENT '营业执照有效期结束日期',
  `legal_person` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '法人',
  `legal_person_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '法人证件号',
  `business_address` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经营地址',
  `registered_capital` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '注册资本',
  `business_scope` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经营范围',
  `check_status` int(1) NULL DEFAULT NULL COMMENT '审核状态：1待提交 2待审核 3审核通过 4审核不通过',
  `create_dt` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_dt` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改日期',
  `status` int(1) NULL DEFAULT 1 COMMENT '状态：0注销 1正常  2冻结',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_merchant_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_merchant_permission`;
CREATE TABLE `t_merchant_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `merchant_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商户号',
  `permission_id` int(11) NULL DEFAULT NULL COMMENT '权限id',
  `create_dt` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_dt` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改日期',
  `status` int(1) NULL DEFAULT 1 COMMENT '状态：1正常 0删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商户权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_merchant_role
-- ----------------------------
DROP TABLE IF EXISTS `t_merchant_role`;
CREATE TABLE `t_merchant_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `merchant_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商户号',
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `description` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_dt` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_dt` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改日期',
  `status` int(1) NULL DEFAULT 1 COMMENT '状态：1正常 0删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_merchant_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_merchant_role_permission`;
CREATE TABLE `t_merchant_role_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色id',
  `permission_id` int(11) NULL DEFAULT NULL COMMENT '权限id',
  `create_dt` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_dt` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改日期',
  `status` int(1) NULL DEFAULT 1 COMMENT '状态：1正常 0删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商户角色权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_merchant_user
-- ----------------------------
DROP TABLE IF EXISTS `t_merchant_user`;
CREATE TABLE `t_merchant_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `merchant_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商户号',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户ID',
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `sex` int(1) NULL DEFAULT NULL COMMENT '性别：1男 2女',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `mobile` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `address` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `head_img` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `salt` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '盐',
  `superuser` int(1) NULL DEFAULT 0 COMMENT '超级管理员：1是 0否',
  `create_dt` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_dt` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改日期',
  `status` int(1) NULL DEFAULT 1 COMMENT '状态：0注销 1正常  2冻结 3临时冻结',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商户用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_merchant_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_merchant_user_role`;
CREATE TABLE `t_merchant_user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色id',
  `create_dt` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_dt` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改日期',
  `status` int(1) NULL DEFAULT 1 COMMENT '状态：1正常 0删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商户用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父ID，顶层为0',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限code',
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  `url` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求url',
  `type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型：菜单menu 按钮button',
  `description` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `classify` int(1) NULL DEFAULT 0 COMMENT '权限归类：0公共 1客户 2商户 3admin',
  `create_dt` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_dt` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改日期',
  `status` int(1) NULL DEFAULT 1 COMMENT '状态：1正常 0删除 ',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
