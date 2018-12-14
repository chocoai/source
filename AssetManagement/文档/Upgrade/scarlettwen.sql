/*
Date: 2018-08-24 13:19:58
*/
alter table `js_am_file_upload` add COLUMN `pic_status` varchar(10) DEFAULT '' COMMENT '图片审核状态';
alter table `js_am_file_upload` add COLUMN  `pic_remarks` varchar(225) DEFAULT '' COMMENT '图片审核备注';
alter table `js_sup_supplier_qualifications` modify column `qualification_name` varchar(225);
alter table `js_sup_supplier_qualifications` modify column `save_path` varchar(225);
alter table `js_fault_registration_picture`  modify column `save_path` varchar(225)
INSERT INTO `jeesite`.`js_sys_dict_data` (`dict_code`, `parent_code`, `parent_codes`, `tree_sort`, `tree_sorts`, `tree_leaf`, `tree_level`, `tree_names`, `dict_label`, `dict_value`, `dict_type`, `is_sys`, `description`, `css_style`, `css_class`, `status`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `corp_code`, `corp_name`, `extend_s1`, `extend_s2`, `extend_s3`, `extend_s4`, `extend_s5`, `extend_s6`, `extend_s7`, `extend_s8`, `extend_i1`, `extend_i2`, `extend_i3`, `extend_i4`, `extend_f1`, `extend_f2`, `extend_f3`, `extend_f4`, `extend_d1`, `extend_d2`, `extend_d3`, `extend_d4`) VALUES ('1027571190664888320', '0', '0,', '30', '0000000030,', '1', '0', '否', '否', '0', 'written', '1', '', '', '', '0', 'system', '2018-08-09 23:03:57', 'system', '2018-08-09 23:03:57', '', '0', 'JeeSite', '', '', '', '', '', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `jeesite`.`js_sys_dict_data` (`dict_code`, `parent_code`, `parent_codes`, `tree_sort`, `tree_sorts`, `tree_leaf`, `tree_level`, `tree_names`, `dict_label`, `dict_value`, `dict_type`, `is_sys`, `description`, `css_style`, `css_class`, `status`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `corp_code`, `corp_name`, `extend_s1`, `extend_s2`, `extend_s3`, `extend_s4`, `extend_s5`, `extend_s6`, `extend_s7`, `extend_s8`, `extend_i1`, `extend_i2`, `extend_i3`, `extend_i4`, `extend_f1`, `extend_f2`, `extend_f3`, `extend_f4`, `extend_d1`, `extend_d2`, `extend_d3`, `extend_d4`) VALUES ('1027571235426500608', '0', '0,', '60', '0000000060,', '1', '0', '是', '是', '1', 'written', '1', '', '', '', '0', 'system', '2018-08-09 23:04:08', 'system', '2018-08-09 23:04:08', '', '0', 'JeeSite', '', '', '', '', '', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `jeesite`.`js_sys_dict_type` (`id`, `dict_name`, `dict_type`, `is_sys`, `status`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`) VALUES ('1027571087069773824', '已写入K3', 'written', '1', '0', 'system', '2018-08-09 23:03:33', 'system', '2018-08-09 23:03:33', '');
/*
Date: 2018-09-14 11:11:58
*/
alter table js_sup_supplier add COLUMN `supplier_status` varchar(20) NOT NULL DEFAULT '0' COMMENT '单据状态（0创建  1重新审核 2已审核)';
INSERT INTO `jeesite`.`js_sys_dict_data` (`dict_code`, `parent_code`, `parent_codes`, `tree_sort`, `tree_sorts`, `tree_leaf`, `tree_level`, `tree_names`, `dict_label`, `dict_value`, `dict_type`, `is_sys`, `description`, `css_style`, `css_class`, `status`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `corp_code`, `corp_name`, `extend_s1`, `extend_s2`, `extend_s3`, `extend_s4`, `extend_s5`, `extend_s6`, `extend_s7`, `extend_s8`, `extend_i1`, `extend_i2`, `extend_i3`, `extend_i4`, `extend_f1`, `extend_f2`, `extend_f3`, `extend_f4`, `extend_d1`, `extend_d2`, `extend_d3`, `extend_d4`) VALUES ('1040438574930640896', '0', '0,', '30', '0000000030,', '1', '0', '创建', '创建', '0', 'supplier_status', '1', '', '', '', '0', 'system', '2018-09-14 11:14:21', 'system', '2018-09-14 11:14:21', '', '0', 'JeeSite', '', '', '', '', '', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `jeesite`.`js_sys_dict_data` (`dict_code`, `parent_code`, `parent_codes`, `tree_sort`, `tree_sorts`, `tree_leaf`, `tree_level`, `tree_names`, `dict_label`, `dict_value`, `dict_type`, `is_sys`, `description`, `css_style`, `css_class`, `status`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `corp_code`, `corp_name`, `extend_s1`, `extend_s2`, `extend_s3`, `extend_s4`, `extend_s5`, `extend_s6`, `extend_s7`, `extend_s8`, `extend_i1`, `extend_i2`, `extend_i3`, `extend_i4`, `extend_f1`, `extend_f2`, `extend_f3`, `extend_f4`, `extend_d1`, `extend_d2`, `extend_d3`, `extend_d4`) VALUES ('1040438625862074368', '0', '0,', '60', '0000000060,', '1', '0', '重新审核', '重新审核', '1', 'supplier_status', '1', '', '', '', '0', 'system', '2018-09-14 11:14:33', 'system', '2018-09-14 11:14:33', '', '0', 'JeeSite', '', '', '', '', '', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `jeesite`.`js_sys_dict_data` (`dict_code`, `parent_code`, `parent_codes`, `tree_sort`, `tree_sorts`, `tree_leaf`, `tree_level`, `tree_names`, `dict_label`, `dict_value`, `dict_type`, `is_sys`, `description`, `css_style`, `css_class`, `status`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `corp_code`, `corp_name`, `extend_s1`, `extend_s2`, `extend_s3`, `extend_s4`, `extend_s5`, `extend_s6`, `extend_s7`, `extend_s8`, `extend_i1`, `extend_i2`, `extend_i3`, `extend_i4`, `extend_f1`, `extend_f2`, `extend_f3`, `extend_f4`, `extend_d1`, `extend_d2`, `extend_d3`, `extend_d4`) VALUES ('1040438668908216320', '0', '0,', '90', '0000000090,', '1', '0', '已审核', '已审核', '2', 'supplier_status', '1', '', '', '', '0', 'system', '2018-09-14 11:14:43', 'system', '2018-09-14 11:14:43', '', '0', 'JeeSite', '', '', '', '', '', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `jeesite`.`js_sys_dict_type` (`id`, `dict_name`, `dict_type`, `is_sys`, `status`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`) VALUES ('1040438181848858624', '单据状态', 'supplier_status', '1', '0', 'system', '2018-09-14 11:12:47', 'system', '2018-09-14 11:12:47', '供应商单据状态');
alter table js_sup_supplier add COLUMN `score` bigint(4) COMMENT '综合评分';
alter table js_sup_supplier add COLUMN `save_path` VARCHAR(350) COMMENT '文件路径';
alter table js_sup_supplier add COLUMN `relative_path` VARCHAR(350) COMMENT '文件相对路径';

alter table `js_sup_supplier_contact` modify column `email` varchar(100);
UPDATE js_sup_supplier set written='否' where written='0';
UPDATE js_sup_supplier set written='是' where written='1';
update js_sup_supplier set supplier_status='0';

/*
Date: 2018-09-18 11:25:58
*/
CREATE TABLE `js_record_log` (
  `log_code` varchar(64) NOT NULL COMMENT '记录id',
  `title` varchar(225) DEFAULT NULL COMMENT '标题',
  `type` varchar(100) DEFAULT NULL COMMENT '来源',
  `create_time` datetime DEFAULT NULL COMMENT '生成时间',
  `write_time` datetime DEFAULT NULL COMMENT '写入时间',
  `content` text DEFAULT NULL COMMENT '内容',
  `level` varchar(15) DEFAULT NULL COMMENT '级别',
  `path` varchar(225) DEFAULT NULL COMMENT '路径',
  PRIMARY KEY (`log_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日志管理';
/*
Date: 2018-09-28 13:50:58，供应商省份修改，设置供应商是否写入K3字段默认值为“否”
*/
UPDATE js_sup_supplier set province='广东省' where province='广东';
UPDATE js_sup_supplier set province='浙江省' where province='浙江';
UPDATE js_sup_supplier set city='佛山市' where city='佛山';
UPDATE js_sup_supplier set city='东莞市' where city='东莞';
UPDATE js_sup_supplier set city='湖州市' where city='湖州';
UPDATE js_sup_supplier set city='宁波市' where city='宁波';

UPDATE js_sup_supplier set written='否' where written='0';
UPDATE js_sup_supplier set written='是' where written='1';

alter table js_sup_supplier modify COLUMN `written` varchar(20) DEFAULT '否' COMMENT '已写入K3';

/*
Date: 2018-10-19 13:50:58,供应商综合评分改为double类型，创建淘宝订单、退款单数据表
*/
alter table js_sup_supplier modify COLUMN `score` double(4,1) DEFAULT NULL COMMENT '综合评分';

SET FOREIGN_KEY_CHECKS=0;

CREATE TABLE `taobao_order_rds` (
  `pkey` varchar(64) NOT NULL,
  `rds_status` varchar(64) DEFAULT NULL,
  `type` varchar(64) DEFAULT NULL,
  `seller_nick` varchar(32) DEFAULT NULL,
  `buyer_nick` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `jdp_hashcode` varchar(128) DEFAULT NULL,
  `jdp_response` text DEFAULT NULL,
  `jdp_created` datetime DEFAULT NULL,
  `jdp_modified` datetime DEFAULT NULL,
  `syn_time` datetime DEFAULT NULL,
  `tid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`pkey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS=0;


CREATE TABLE `taobao_refund_rds` (
  `pkey` varchar(64) NOT NULL,
  `refund_id` bigint(18) DEFAULT NULL COMMENT '退款单号',
  `seller_nick` varchar(32) DEFAULT NULL COMMENT '卖家昵称',
  `buyer_nick` varchar(255) DEFAULT NULL COMMENT '买家昵称',
  `refund_status` varchar(64) DEFAULT NULL COMMENT '退款状态',
  `created` datetime DEFAULT NULL,
  `tid` bigint(8) DEFAULT NULL COMMENT '淘宝交易单号',
  `oid` bigint(8) DEFAULT NULL COMMENT '子订单号',
  `modified` datetime DEFAULT NULL,
  `jdp_response` text DEFAULT NULL COMMENT '响应文本',
  `jdp_hashcode` varchar(128) DEFAULT NULL,
  `jdp_created` datetime DEFAULT NULL COMMENT '退款申请时间',
  `jdp_modified` datetime DEFAULT NULL COMMENT '更新时间',
  `syn_time` datetime DEFAULT NULL COMMENT '同步时间',
  PRIMARY KEY (`pkey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;





