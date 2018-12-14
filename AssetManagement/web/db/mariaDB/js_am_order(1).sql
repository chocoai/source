/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.15
Source Server Version : 50505
Source Host           : 192.168.1.15:3307
Source Database       : jeesite

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2018-07-14 12:48:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for js_am_order
-- ----------------------------
DROP TABLE IF EXISTS `js_am_order`;
CREATE TABLE `js_am_order` (
  `document_code` varchar(64) NOT NULL COMMENT '单据编号',
  `document_type` varchar(20) DEFAULT NULL COMMENT '订单类型',
  `document_status` varchar(10) DEFAULT NULL COMMENT '订单状态',
  `customer_name` varchar(100) NOT NULL COMMENT '客户姓名',
  `customer_nick` varchar(100) DEFAULT NULL COMMENT '客户昵称',
  `mobile_phone` varchar(20) NOT NULL COMMENT '移动电话',
  `fixed_phone` varchar(20) DEFAULT NULL COMMENT '固定电话',
  `province` varchar(15) NOT NULL COMMENT '省',
  `city` varchar(30) NOT NULL COMMENT '市',
  `region` varchar(30) DEFAULT NULL COMMENT '区',
  `detailed_address` varchar(1000) NOT NULL COMMENT '详细地址',
  `distribution` varchar(50) DEFAULT NULL COMMENT '配送方式',
  `pay_type` varchar(50) DEFAULT NULL COMMENT '支付方式',
  `create_time` varchar(100) DEFAULT '' COMMENT '创建时间',
  `total_price` double(13,2) DEFAULT NULL COMMENT '总价',
  `logistics_fee` double(13,2) DEFAULT NULL COMMENT '物流费',
  `three_charges` double(13,2) DEFAULT NULL COMMENT '三包费',
  `preferential` double(13,2) DEFAULT NULL COMMENT '优惠金额',
  `total_fee` double(13,2) DEFAULT NULL COMMENT '合计应收',
  `remarks` varchar(1000) DEFAULT NULL COMMENT '备注',
  `stage_status` varchar(50) DEFAULT NULL COMMENT '分阶段状态',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `submit_by` varchar(64) DEFAULT NULL COMMENT '提交人',
  `submit_date` varchar(100) DEFAULT '' COMMENT '提交时间',
  `confirm_by` varchar(64) DEFAULT NULL COMMENT '确认人',
  `confirm_date` varchar(100) DEFAULT '' COMMENT '确认时间',
  `printing_by` varchar(64) DEFAULT NULL COMMENT '打印人',
  `printing_date` varchar(100) DEFAULT '' COMMENT '打印时间',
  `customer_source` varchar(50) DEFAULT NULL COMMENT '客户来源',
  `order_source` varchar(20) DEFAULT NULL COMMENT '订单来源',
  `privilege1` varchar(100) DEFAULT NULL COMMENT '特权定金单号1',
  `privilege2` varchar(100) DEFAULT NULL COMMENT '特权定金单号2',
  `privilege3` varchar(100) DEFAULT NULL COMMENT '特权定金单号3',
  `discount_amount` double(13,2) DEFAULT NULL COMMENT '优惠金额',
  PRIMARY KEY (`document_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单管理';
