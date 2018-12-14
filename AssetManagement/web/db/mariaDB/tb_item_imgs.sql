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

 Date: 14/07/2018 12:46:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_item_imgs
-- ----------------------------
DROP TABLE IF EXISTS `tb_item_imgs`;
CREATE TABLE `tb_item_imgs`  (
  `id` bigint(64) NOT NULL COMMENT '商品图片的id，和商品相对应（主图id默认为0）\r\n',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '图片链接地址\r\n',
  `position` int(64) DEFAULT NULL COMMENT '图片放在第几张（多图时可设置）',
  `created` date DEFAULT NULL COMMENT '图片创建时间 ',
  `item_id` bigint(20) DEFAULT NULL COMMENT '图片所属商品ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
