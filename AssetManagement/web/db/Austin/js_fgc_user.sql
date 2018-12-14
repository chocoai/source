/*
Navicat MySQL Data Transfer

Source Server         : 3307
Source Server Version : 50505
Source Host           : 192.168.2.15:3307
Source Database       : jeesite

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2018-08-27 17:26:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for js_fgc_user
-- ----------------------------
DROP TABLE IF EXISTS `js_fgc_user`;
CREATE TABLE `js_fgc_user` (
  `document_code` varchar(64) NOT NULL COMMENT '主键',
  `open_id` varchar(200) DEFAULT '' COMMENT 'OpenID',
  `user_name` varchar(200) DEFAULT '' COMMENT '用户名',
  `verification_code` varchar(200) DEFAULT '' COMMENT '绑定的验证码',
  `create_by` varchar(200) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(200) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(1000) DEFAULT '' COMMENT '备注',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '0:正常，1:禁用',
  `nickname` varchar(200) DEFAULT '' COMMENT '微信用户昵称',
  `sex` char(20) DEFAULT '' COMMENT '用户的性别，值为1时是男性，值为2时是女性，值为0时是未知',
  `province` varchar(255) DEFAULT '' COMMENT '用户个人资料填写的省份',
  `city` varchar(255) DEFAULT '' COMMENT '普通用户个人资料填写的城市',
  `country` varchar(255) DEFAULT '' COMMENT '国家，如中国为CN',
  `headimgurl` varchar(1000) DEFAULT '' COMMENT '用户头像，最后一个数值代表正方形头像大小',
  `privilege` varchar(255) DEFAULT '' COMMENT '用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）',
  `unionid` int(11) DEFAULT NULL COMMENT '只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。',
  `sys_login_code` varchar(1000) DEFAULT '' COMMENT '加密后的系统登录账号',
  `sys_login_pas` varchar(1000) DEFAULT '' COMMENT '加密后的系统登录密码',
  PRIMARY KEY (`document_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='梵工厂用户表';



INSERT INTO `jeesite`.`js_sys_dict_data` (`dict_code`, `parent_code`, `parent_codes`, `tree_sort`, `tree_sorts`, `tree_leaf`, `tree_level`, `tree_names`, `dict_label`, `dict_value`, `dict_type`, `is_sys`, `description`, `css_style`, `css_class`, `status`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `corp_code`, `corp_name`, `extend_s1`, `extend_s2`, `extend_s3`, `extend_s4`, `extend_s5`, `extend_s6`, `extend_s7`, `extend_s8`, `extend_i1`, `extend_i2`, `extend_i3`, `extend_i4`, `extend_f1`, `extend_f2`, `extend_f3`, `extend_f4`, `extend_d1`, `extend_d2`, `extend_d3`, `extend_d4`) VALUES ('1022007428650127360', '0', '0,', '1', '0000000001,', '1', '0', '暂存', '暂存', 'Z', 'fgc_document_status', '0', '', '', '', '0', 'system', '2018-07-25 14:35:33', 'system', '2018-07-25 14:42:48', '', '0', 'JeeSite', '', '', '', '', '', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `jeesite`.`js_sys_dict_data` (`dict_code`, `parent_code`, `parent_codes`, `tree_sort`, `tree_sorts`, `tree_leaf`, `tree_level`, `tree_names`, `dict_label`, `dict_value`, `dict_type`, `is_sys`, `description`, `css_style`, `css_class`, `status`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `corp_code`, `corp_name`, `extend_s1`, `extend_s2`, `extend_s3`, `extend_s4`, `extend_s5`, `extend_s6`, `extend_s7`, `extend_s8`, `extend_i1`, `extend_i2`, `extend_i3`, `extend_i4`, `extend_f1`, `extend_f2`, `extend_f3`, `extend_f4`, `extend_d1`, `extend_d2`, `extend_d3`, `extend_d4`) VALUES ('1022007507037474816', '0', '0,', '2', '0000000002,', '1', '0', '创建', '创建', 'A', 'fgc_document_status', '0', '', '', '', '0', 'system', '2018-07-25 14:35:52', 'system', '2018-07-25 14:42:57', '', '0', 'JeeSite', '', '', '', '', '', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `jeesite`.`js_sys_dict_data` (`dict_code`, `parent_code`, `parent_codes`, `tree_sort`, `tree_sorts`, `tree_leaf`, `tree_level`, `tree_names`, `dict_label`, `dict_value`, `dict_type`, `is_sys`, `description`, `css_style`, `css_class`, `status`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `corp_code`, `corp_name`, `extend_s1`, `extend_s2`, `extend_s3`, `extend_s4`, `extend_s5`, `extend_s6`, `extend_s7`, `extend_s8`, `extend_i1`, `extend_i2`, `extend_i3`, `extend_i4`, `extend_f1`, `extend_f2`, `extend_f3`, `extend_f4`, `extend_d1`, `extend_d2`, `extend_d3`, `extend_d4`) VALUES ('1022007625719500800', '0', '0,', '3', '0000000003,', '1', '0', '审核中', '审核中', 'B', 'fgc_document_status', '0', '', '', '', '0', 'system', '2018-07-25 14:36:20', 'system', '2018-07-25 14:43:01', '', '0', 'JeeSite', '', '', '', '', '', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `jeesite`.`js_sys_dict_data` (`dict_code`, `parent_code`, `parent_codes`, `tree_sort`, `tree_sorts`, `tree_leaf`, `tree_level`, `tree_names`, `dict_label`, `dict_value`, `dict_type`, `is_sys`, `description`, `css_style`, `css_class`, `status`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `corp_code`, `corp_name`, `extend_s1`, `extend_s2`, `extend_s3`, `extend_s4`, `extend_s5`, `extend_s6`, `extend_s7`, `extend_s8`, `extend_i1`, `extend_i2`, `extend_i3`, `extend_i4`, `extend_f1`, `extend_f2`, `extend_f3`, `extend_f4`, `extend_d1`, `extend_d2`, `extend_d3`, `extend_d4`) VALUES ('1022007716081586176', '0', '0,', '4', '0000000004,', '1', '0', '已审核', '已审核', 'C', 'fgc_document_status', '0', '', '', '', '0', 'system', '2018-07-25 14:36:42', 'system', '2018-07-25 14:43:12', '', '0', 'JeeSite', '', '', '', '', '', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `jeesite`.`js_sys_dict_data` (`dict_code`, `parent_code`, `parent_codes`, `tree_sort`, `tree_sorts`, `tree_leaf`, `tree_level`, `tree_names`, `dict_label`, `dict_value`, `dict_type`, `is_sys`, `description`, `css_style`, `css_class`, `status`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `corp_code`, `corp_name`, `extend_s1`, `extend_s2`, `extend_s3`, `extend_s4`, `extend_s5`, `extend_s6`, `extend_s7`, `extend_s8`, `extend_i1`, `extend_i2`, `extend_i3`, `extend_i4`, `extend_f1`, `extend_f2`, `extend_f3`, `extend_f4`, `extend_d1`, `extend_d2`, `extend_d3`, `extend_d4`) VALUES ('1022007792929624064', '0', '0,', '5', '0000000005,', '1', '0', '重新审核', '重新审核', 'D', 'fgc_document_status', '0', '', '', '', '0', 'system', '2018-07-25 14:37:00', 'system', '2018-07-25 14:43:16', '', '0', 'JeeSite', '', '', '', '', '', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `jeesite`.`js_sys_dict_data` (`dict_code`, `parent_code`, `parent_codes`, `tree_sort`, `tree_sorts`, `tree_leaf`, `tree_level`, `tree_names`, `dict_label`, `dict_value`, `dict_type`, `is_sys`, `description`, `css_style`, `css_class`, `status`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `corp_code`, `corp_name`, `extend_s1`, `extend_s2`, `extend_s3`, `extend_s4`, `extend_s5`, `extend_s6`, `extend_s7`, `extend_s8`, `extend_i1`, `extend_i2`, `extend_i3`, `extend_i4`, `extend_f1`, `extend_f2`, `extend_f3`, `extend_f4`, `extend_d1`, `extend_d2`, `extend_d3`, `extend_d4`) VALUES ('1028892770868936704', '0', '0,', '30', '0000000030,', '1', '0', '正常', '正常', '0', 'js_fgc_user_status', '0', '', '', '', '0', 'system', '2018-08-13 14:35:27', 'system', '2018-08-13 14:35:27', '', '0', 'JeeSite', '', '', '', '', '', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `jeesite`.`js_sys_dict_data` (`dict_code`, `parent_code`, `parent_codes`, `tree_sort`, `tree_sorts`, `tree_leaf`, `tree_level`, `tree_names`, `dict_label`, `dict_value`, `dict_type`, `is_sys`, `description`, `css_style`, `css_class`, `status`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `corp_code`, `corp_name`, `extend_s1`, `extend_s2`, `extend_s3`, `extend_s4`, `extend_s5`, `extend_s6`, `extend_s7`, `extend_s8`, `extend_i1`, `extend_i2`, `extend_i3`, `extend_i4`, `extend_f1`, `extend_f2`, `extend_f3`, `extend_f4`, `extend_d1`, `extend_d2`, `extend_d3`, `extend_d4`) VALUES ('1028892818868551680', '0', '0,', '60', '0000000060,', '1', '0', '禁用', '禁用', '1', 'js_fgc_user_status', '0', '', '', '', '0', 'system', '2018-08-13 14:35:38', 'system', '2018-08-13 14:35:38', '', '0', 'JeeSite', '', '', '', '', '', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

INSERT INTO `jeesite`.`js_sys_dict_type` (`id`, `dict_name`, `dict_type`, `is_sys`, `status`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`) VALUES ('1022006899756781568', '单据状态(梵工厂)', 'fgc_document_status', '0', '0', 'system', '2018-07-25 14:33:27', 'system', '2018-07-25 14:34:32', '');
INSERT INTO `jeesite`.`js_sys_dict_type` (`id`, `dict_name`, `dict_type`, `is_sys`, `status`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`) VALUES ('1028892684113952768', '梵工厂用户状态', 'js_fgc_user_status', '0', '0', 'system', '2018-08-13 14:35:06', 'system', '2018-08-13 14:35:06', '');
