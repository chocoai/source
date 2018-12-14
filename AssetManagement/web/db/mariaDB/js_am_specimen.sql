/*
Navicat MySQL Data Transfer

Source Server         : 3307
Source Server Version : 50505
Source Host           : 192.168.1.15:3307
Source Database       : jeesite

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2018-07-14 12:45:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for js_am_specimen
-- ----------------------------
DROP TABLE IF EXISTS `js_am_specimen`;
CREATE TABLE `js_am_specimen` (
  `specimen_code` varchar(32) NOT NULL,
  `date` datetime DEFAULT NULL COMMENT '单据时间',
  `data_status` char(1) DEFAULT NULL COMMENT '单据类型',
  `factory` varchar(32) DEFAULT NULL COMMENT '制造工厂',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `bills_status` char(1) DEFAULT NULL COMMENT '单据状态',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(128) DEFAULT NULL,
  `status` char(1) DEFAULT NULL,
  PRIMARY KEY (`specimen_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='构建样品进度';
