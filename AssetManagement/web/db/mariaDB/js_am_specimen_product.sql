/*
Navicat MySQL Data Transfer

Source Server         : 3307
Source Server Version : 50505
Source Host           : 192.168.1.15:3307
Source Database       : jeesite

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2018-07-14 12:45:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for js_am_specimen_product
-- ----------------------------
DROP TABLE IF EXISTS `js_am_specimen_product`;
CREATE TABLE `js_am_specimen_product` (
  `code` varchar(32) NOT NULL,
  `product_code` varchar(32) DEFAULT NULL COMMENT '产品编号',
  `product_name` varchar(32) DEFAULT NULL COMMENT '产品名称',
  `count` int(32) DEFAULT NULL COMMENT '数量',
  `material` varchar(128) DEFAULT NULL COMMENT '材质',
  `es` varchar(128) DEFAULT NULL COMMENT '材料',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(1000) DEFAULT NULL,
  `specimen_code` varchar(32) DEFAULT NULL COMMENT '样品进度code',
  `photo` varchar(100) DEFAULT NULL COMMENT '图片',
  `status` char(1) DEFAULT NULL,
  PRIMARY KEY (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='构建样品进度_明细信息';
