/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.15
Source Server Version : 50505
Source Host           : 192.168.1.15:3307
Source Database       : jeesite

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2018-07-14 12:48:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for js_am_order_discount
-- ----------------------------
DROP TABLE IF EXISTS `js_am_order_discount`;
CREATE TABLE `js_am_order_discount` (
  `detail_code` varchar(64) NOT NULL COMMENT '优惠明细编码',
  `document_code` varchar(64) DEFAULT NULL COMMENT '单据编码',
  `discount` varchar(2000) DEFAULT NULL COMMENT '优惠活动',
  `discount_price` double(13,2) DEFAULT NULL COMMENT '优惠金额',
  PRIMARY KEY (`detail_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
