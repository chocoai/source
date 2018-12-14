/*
Navicat MySQL Data Transfer

Source Server         : 3307
Source Server Version : 50505
Source Host           : 192.168.1.15:3307
Source Database       : jeesite

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2018-07-14 12:45:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for js_am_specimen_schedule
-- ----------------------------
DROP TABLE IF EXISTS `js_am_specimen_schedule`;
CREATE TABLE `js_am_specimen_schedule` (
  `code` varchar(32) NOT NULL,
  `dispose` varchar(32) DEFAULT NULL COMMENT '处理人',
  `date` varchar(100) DEFAULT '' COMMENT '完成时间',
  `node` varchar(64) DEFAULT NULL COMMENT '处理节点',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(128) DEFAULT NULL,
  `specimen_code` varchar(32) DEFAULT NULL COMMENT '样品进度code',
  `status` char(1) DEFAULT NULL,
  `product_code` varchar(64) DEFAULT NULL,
  `estimated_days` int(11) DEFAULT NULL COMMENT '预计天数',
  `start_date` varchar(100) DEFAULT '' COMMENT '开始时间',
  PRIMARY KEY (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='构建样品进度_处理进度';
