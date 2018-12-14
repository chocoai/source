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

 Date: 14/07/2018 12:46:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_sku
-- ----------------------------
DROP TABLE IF EXISTS `tb_sku`;
CREATE TABLE `tb_sku`  (
  `sku_id` bigint(64) NOT NULL,
  `num_iid` bigint(64) DEFAULT NULL COMMENT 'sku所属商品id',
  `properties_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规格名称',
  `price` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '价格',
  `quantity` int(32) DEFAULT NULL COMMENT '数量',
  `properties` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'sku的销售属性组合字符串',
  `created` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'sku创建日期 ',
  `modified` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '	sku最后修改日期',
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'sku状态',
  `barcode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品级别的条形码\r\n',
  `outer_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商家设置的外部id',
  PRIMARY KEY (`sku_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品-SKU管理' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
