/*
Navicat MySQL Data Transfer

Source Server         : 3307
Source Server Version : 50505
Source Host           : 192.168.1.15:3307
Source Database       : jeesite

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2018-07-14 12:45:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for js_am_speci_qualifications
-- ----------------------------
DROP TABLE IF EXISTS `js_am_speci_qualifications`;
CREATE TABLE `js_am_speci_qualifications` (
  `speci_qualifications_code` varchar(100) NOT NULL COMMENT '文件编号',
  `code` varchar(100) NOT NULL COMMENT '样品进度code',
  `qualification_name` varchar(100) DEFAULT NULL COMMENT '文件名',
  `type_name` varchar(100) DEFAULT NULL COMMENT '文件类型',
  `profile_surfix` varchar(100) DEFAULT NULL,
  `save_path` varchar(100) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `file_url` varchar(200) DEFAULT NULL COMMENT '全路径',
  PRIMARY KEY (`speci_qualifications_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='样品进度单文件表';
