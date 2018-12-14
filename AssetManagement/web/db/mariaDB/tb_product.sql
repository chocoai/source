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

 Date: 14/07/2018 12:46:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_product
-- ----------------------------
DROP TABLE IF EXISTS `tb_product`;
CREATE TABLE `tb_product`  (
  `num_iid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品数字id',
  `title` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品标题',
  `pic_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品主图片地址',
  `cid` int(32) DEFAULT NULL COMMENT '子类目id',
  `approve_status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品上传后的状态。onsale出售中，instock库中',
  `price` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '价格',
  `detail_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品url',
  `list_time` datetime(0) DEFAULT NULL COMMENT '上架时间',
  `delist_tim` datetime(0) DEFAULT NULL COMMENT '下架时间',
  `modified` datetime(0) DEFAULT NULL COMMENT '商品修改时间',
  PRIMARY KEY (`num_iid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '同步淘宝商品列表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
