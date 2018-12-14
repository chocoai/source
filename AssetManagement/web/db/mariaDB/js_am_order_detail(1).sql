/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.15
Source Server Version : 50505
Source Host           : 192.168.1.15:3307
Source Database       : jeesite

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2018-07-14 12:48:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for js_am_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `js_am_order_detail`;
CREATE TABLE `js_am_order_detail` (
  `detail_code` varchar(64) NOT NULL COMMENT '明细编号',
  `document_code` varchar(64) NOT NULL COMMENT '单据编号',
  `goods_name` varchar(1000) DEFAULT '' COMMENT '商品名称',
  `sku_id` varchar(64) DEFAULT NULL COMMENT 'sku',
  `spec` varchar(500) DEFAULT '' COMMENT '规格',
  `quantity` bigint(11) DEFAULT NULL COMMENT '数量',
  `stand_price` double(13,2) DEFAULT NULL COMMENT '标准售价',
  `price` double(13,2) DEFAULT NULL COMMENT '单价',
  `amount` double(13,2) DEFAULT NULL COMMENT '金额',
  `sku` varchar(1000) DEFAULT '' COMMENT 'sku',
  PRIMARY KEY (`detail_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
