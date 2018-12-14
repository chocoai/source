/*
 Navicat Premium Data Transfer

 Source Server         : MariaDB测试库
 Source Server Type    : MariaDB
 Source Server Version : 100308
 Source Host           : 192.168.1.15:3307
 Source Schema         : jeesite

 Target Server Type    : MariaDB
 Target Server Version : 100308
 File Encoding         : 65001

 Date: 14/07/2018 12:47:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_tianmao_items
-- ----------------------------
DROP TABLE IF EXISTS `tb_tianmao_items`;
CREATE TABLE `tb_tianmao_items`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `body` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT 'json格式数据',
  `time` datetime(0) DEFAULT NULL COMMENT '接收时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
