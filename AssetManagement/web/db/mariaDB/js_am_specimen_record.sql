/*
Navicat MySQL Data Transfer

Source Server         : 3307
Source Server Version : 50505
Source Host           : 192.168.1.15:3307
Source Database       : jeesite

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2018-07-14 12:45:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for js_am_specimen_record
-- ----------------------------
DROP TABLE IF EXISTS `js_am_specimen_record`;
CREATE TABLE `js_am_specimen_record` (
  `record_code` varchar(64) NOT NULL COMMENT '编号',
  `operator` varchar(100) DEFAULT '' COMMENT '操作人',
  `operator_time` datetime DEFAULT NULL COMMENT '时间',
  `remark` varchar(1000) DEFAULT '' COMMENT '操作人',
  `specimen_code` varchar(100) NOT NULL COMMENT '单据编号',
  PRIMARY KEY (`record_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='构建样品进度_操作记录';
