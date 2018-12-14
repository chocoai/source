/**
2018-11-06
 */
ALTER TABLE js_ding_user ADD COLUMN user_is_vip CHAR(1) DEFAULT '0' NULL COMMENT '是不是特权用户 1:是,0:不是';
ALTER TABLE fz_gold_distribution ADD COLUMN `fz_type` CHAR(1) NULL COMMENT '梵赞类型 0:部门内梵赞,1:跨部门梵赞';

/**
2018-11-14
 */
CREATE TABLE `fz_statistical_item` (
  `id` int(11) NOT NULL,
  `item` varchar(64) DEFAULT NULL COMMENT '统计类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='一级部门赞赏统计项目';
INSERT INTO fz_statistical_item (id, item) VALUES ('1', '该部门赞赏最多的');
INSERT INTO fz_statistical_item (id, item) VALUES ('2', '最多人均赞赏次数');
INSERT INTO fz_statistical_item (id, item) VALUES ('3', '该部门赞赏最少的');
INSERT INTO fz_statistical_item (id, item) VALUES ('4', '最少人均赞赏次数');
INSERT INTO fz_statistical_item (id, item) VALUES ('5', '人均部门内赞赏/获赞次数');
INSERT INTO fz_statistical_item (id, item) VALUES ('6', '人均跨部门赞赏次数');
INSERT INTO fz_statistical_item (id, item) VALUES ('7', '人均跨部门获赞次数');
INSERT INTO fz_statistical_item (id, item) VALUES ('8', '人均总赞赏次数');
INSERT INTO fz_statistical_item (id, item) VALUES ('9', '人均总获赞次数');
INSERT INTO fz_statistical_item (id, item) VALUES ('10', '最爱用的赞赏类型');

ALTER TABLE js_ding_user ADD COLUMN freeze_point DOUBLE DEFAULT 0 NULL COMMENT '当前锁定梵砖';
ALTER TABLE js_ding_user ADD COLUMN used_point DOUBLE DEFAULT 0 NULL COMMENT '已消费的总梵砖数量';

CREATE TABLE `fz_order` (
  `order_id` varchar(64) NOT NULL COMMENT '订单id',
  `pay_status` char(1) DEFAULT NULL COMMENT '支付状态 1:未支付 2:已支付 3:已退款',
  `order_status` char(1) DEFAULT NULL COMMENT '订单状态 1:未完成 2:已取消 3:已完成',
  `status` char(1) DEFAULT NULL COMMENT '0：正常；1：删除；2：停用；3：冻结；4：审核、待审核；5：审核驳回；9：草稿',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `bn` varchar(30) DEFAULT NULL COMMENT '商品sku_id',
  `nums` int(11) DEFAULT NULL COMMENT '兑换数量',
  `receiver_mobile` varchar(32) DEFAULT NULL COMMENT '收货人手机号码',
  `detail_address` varchar(64) DEFAULT NULL COMMENT '收货详细地址',
  `province` varchar(64) DEFAULT NULL COMMENT '收货省名',
  `city` varchar(64) DEFAULT NULL COMMENT '收货市名',
  `county` varchar(64) DEFAULT NULL COMMENT '收货县名',
  `town` varchar(64) DEFAULT NULL COMMENT '收货城镇名',
  `remarks` varchar(1000) DEFAULT NULL COMMENT '备注信息',
  `money` double DEFAULT 0 COMMENT '兑换消耗的钱',
  `fz_num` double DEFAULT 0 COMMENT '兑换消耗的梵砖',
  `userid` varchar(255) DEFAULT NULL COMMENT '创建订单的用户id',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='梵赞兑换订单表'

/**
2018-11-24
 */
 CREATE TABLE `fz_neigou_fzgold_log` (
  `log_id` varchar(64) NOT NULL COMMENT '日志id',
  `action` varchar(64) DEFAULT NULL COMMENT '执行类型,1:查询积分,2:锁定积分,3:锁定积分取消,4;锁定积分确定,5:退款',
  `create_time` datetime DEFAULT NULL COMMENT '操作时间',
  `user_name` varchar(64) DEFAULT NULL COMMENT '用户名字',
  `user_id` varchar(64) DEFAULT NULL COMMENT '用户id',
  `action_result` text DEFAULT NULL COMMENT '执行结果',
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单号',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='梵砖积分操作表'


CREATE TABLE `fz_neigou_login_log` (
  `log_id` varchar(64) NOT NULL COMMENT '日志登陆id',
  `user_id` varchar(64) DEFAULT NULL COMMENT '用户id',
  `login_time` datetime DEFAULT NULL COMMENT '登陆时间',
  `user_name` varchar(64) DEFAULT NULL COMMENT '用户英文名',
  `login_success` char(1) DEFAULT NULL COMMENT '登陆是否成功,0:失败,1:成功,2:获取login_token失败',
  `result` text DEFAULT NULL COMMENT '如果登陆失败的话记录原因',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='梵赞内购登陆日志'

CREATE TABLE `fz_neigou_order` (
  `order_id` varchar(64) NOT NULL COMMENT '订单id',
  `pay_status` char(1) DEFAULT NULL COMMENT '支付状态 1:未支付 2:已支付 3:已退款',
  `order_status` char(1) DEFAULT NULL COMMENT '订单状态 1:未完成 2:已取消 3:已完成',
  `status` char(1) DEFAULT NULL COMMENT '0：正常；1：删除；2：停用；3：冻结；4：审核、待审核；5：审核驳回；9：草稿',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `bn` varchar(30) DEFAULT NULL COMMENT '商品sku_id',
  `nums` int(11) DEFAULT NULL COMMENT '兑换数量',
  `receiver_mobile` varchar(32) DEFAULT NULL COMMENT '收货人手机号码',
  `detail_address` varchar(64) DEFAULT NULL COMMENT '收货详细地址',
  `province` varchar(64) DEFAULT NULL COMMENT '收货省名',
  `city` varchar(64) DEFAULT NULL COMMENT '收货市名',
  `county` varchar(64) DEFAULT NULL COMMENT '收货县名',
  `town` varchar(64) DEFAULT NULL COMMENT '收货城镇名',
  `remarks` varchar(1000) DEFAULT NULL COMMENT '备注信息',
  `money` double DEFAULT 0 COMMENT '兑换消耗的钱',
  `fz_num` double DEFAULT 0 COMMENT '兑换消耗的梵砖',
  `userid` varchar(255) DEFAULT NULL COMMENT '创建订单的用户id',
  `refund_num` double DEFAULT 0 COMMENT '此订单退款积分',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='梵赞兑换订单表'

/**
2018-12-5
 */

