/*
 Navicat Premium Data Transfer

 Source Server         : 128
 Source Server Type    : MySQL
 Source Server Version : 50743
 Source Host           : 192.168.43.128:3306
 Source Schema         : stock

 Target Server Type    : MySQL
 Target Server Version : 50743
 File Encoding         : 65001

 Date: 25/10/2023 16:04:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_stock
-- ----------------------------
DROP TABLE IF EXISTS `t_stock`;
CREATE TABLE `t_stock`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `product_no` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品编号',
  `product_num` int(11) NULL DEFAULT NULL COMMENT '商品数量',
  `create_dt` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_dt` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改日期',
  `status` int(11) NULL DEFAULT 1 COMMENT '状态：1正常 0删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '库存表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `branch_id` bigint(20) NOT NULL COMMENT '分支事务ID',
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '全局事务ID',
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '上下文',
  `rollback_info` longblob NOT NULL COMMENT '回滚信息',
  `log_status` int(11) NOT NULL COMMENT '状态，0正常，1全局已完成',
  `log_created` datetime(6) NOT NULL COMMENT '创建时间',
  `log_modified` datetime(6) NOT NULL COMMENT '修改时间',
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'AT transaction mode undo table' ROW_FORMAT = COMPACT;

SET FOREIGN_KEY_CHECKS = 1;
