/**
2018/9/26
 */
ALTER TABLE js_am_order_detail ADD COLUMN shop CHAR (1) COMMENT '店铺';

/**
2018/10/5
 */
ALTER TABLE js_am_order ADD COLUMN is_enjoy VARCHAR (2) COMMENT '是否享受油补';
ALTER TABLE js_am_order ADD COLUMN oil_subsidy DOUBLE(10,2) COMMENT '油费补贴';

ALTER TABLE js_ding_user ADD COLUMN winning_prize CHAR(1) DEFAULT '0' COMMENT '是否中奖(0否1是)';
ALTER TABLE js_ding_user ADD COLUMN prize_type VARCHAR(64) COMMENT '中奖类型';
ALTER TABLE js_ding_user ADD COLUMN achievement VARCHAR(8) COMMENT '竞猜业绩';
ALTER TABLE js_ding_user ADD COLUMN user_status CHAR(1) DEFAULT '0' COMMENT '用户状态(0正常2停用)';


DROP TABLE IF EXISTS `js_am_luck_draw`;
CREATE TABLE `js_am_luck_draw` (
  `document_code` varchar(64) NOT NULL COMMENT '单据编号',
  `bill_time` datetime DEFAULT NULL COMMENT '单据时间',
  `winning_code` varchar(64) DEFAULT NULL COMMENT '中奖码',
  `winners_num` int(6) DEFAULT NULL COMMENT '中奖人数',
  `push_status` char(1) DEFAULT NULL COMMENT '推送状态',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_date` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新人',
  `status` char(1) DEFAULT NULL COMMENT '状态',
  `remarks` varchar(1000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`document_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='抽奖竞猜';


DROP TABLE IF EXISTS `js_am_luck_detail`;
CREATE TABLE `js_am_luck_detail` (
  `detail_code` varchar(64) NOT NULL,
  `document_code` varchar(64) DEFAULT NULL COMMENT '单据编号',
  `emp_name` varchar(64) DEFAULT NULL COMMENT '姓名',
  `work_code` varchar(64) DEFAULT NULL COMMENT '工号',
  `department` varchar(200) DEFAULT '' COMMENT '部门',
  `chinese_name` varchar(64) DEFAULT NULL COMMENT '中文名',
  `user_id` varchar(64) DEFAULT NULL COMMENT '钉钉用户id',
  PRIMARY KEY (`detail_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


/**
2018/10/16  梵工厂异常日志表
 */
DROP TABLE IF EXISTS `js_fgc_log`;
CREATE TABLE `js_fgc_log` (
  `log_id` varchar(64) NOT NULL COMMENT '日志记录编码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `error_info` text DEFAULT NULL COMMENT '异常信息',
  `operation_type` varchar(64) DEFAULT NULL COMMENT '操作类型',
  `fentityId` varchar(64) DEFAULT NULL COMMENT '明细id',
  `fid` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='梵工厂反写日志表';

ALTER TABLE js_fgc_quality_check_details ADD COLUMN reexamination_num int (10) COMMENT '复检可接受数';

ALTER TABLE js_fgc_quality_check_details ADD COLUMN is_receive varchar (2) COMMENT '是否接受收料';




/**
2018/10/22
 */
ALTER TABLE js_fgc_quality_check_details ADD COLUMN packing_length  decimal(15,3) COMMENT '包装长';
ALTER TABLE js_fgc_quality_check_details ADD COLUMN packing_width  decimal(15,3) COMMENT '包装宽';
ALTER TABLE js_fgc_quality_check_details ADD COLUMN packing_high  decimal(15,3) COMMENT '包装高';
ALTER TABLE js_fgc_quality_check_details ADD COLUMN weight  decimal(15,3) COMMENT '重量';
ALTER TABLE js_fgc_quality_check_details ADD COLUMN package_num  int(11) COMMENT '包件数';
ALTER TABLE js_fgc_quality_check_details ADD COLUMN color  varchar(10) COMMENT '颜色';
ALTER TABLE js_fgc_quality_check_details ADD COLUMN structural_disqualifiedQty  int(11) COMMENT '结构不合格数';
ALTER TABLE js_fgc_quality_check_details ADD COLUMN appearance_disqualifiedQty  int(11) COMMENT '外观不合格数';
ALTER TABLE js_fgc_quality_check_details ADD COLUMN package_disqualifiedQty  int(11) COMMENT '包装不合格数';

CREATE TABLE `js_group_purchase` (
  `purchase_code` varchar(64) NOT NULL COMMENT '团购编码',
  `wang_code` varchar(64) DEFAULT '' COMMENT '团长旺旺id',
  `group_phone` varchar(64) DEFAULT NULL COMMENT '团长电话',
  `goods_num` int(11) DEFAULT NULL COMMENT '购买件数',
  `rebate` varchar(5) DEFAULT NULL COMMENT '购买折扣',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `synch_status` varchar(10) DEFAULT NULL COMMENT '同步状态',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `total_num` int(11) DEFAULT NULL COMMENT '购买总件数',
  PRIMARY KEY (`purchase_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='团购信息表';


CREATE TABLE `js_group_detail` (
  `detail_code` varchar(64) NOT NULL COMMENT '团员明细编码',
  `purchase_code` varchar(64) DEFAULT NULL COMMENT '团购编码',
  `member_wang_code` varchar(64) DEFAULT NULL COMMENT '团员旺旺id',
  `goods_num` int(11) DEFAULT NULL COMMENT '购买件数',
  `rebate` varchar(5) DEFAULT NULL COMMENT '折扣',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`detail_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='团员团购信息';

CREATE TABLE `js_bug_registration` (
  `bug_code` varchar(64) NOT NULL COMMENT '编号',
  `bug_date` datetime NOT NULL COMMENT '日期',
  `feedback_person` varchar(50) NOT NULL COMMENT '反馈人',
  `dealer` varchar(50) NOT NULL COMMENT '处理人',
  `liable_type` varchar(15) NOT NULL COMMENT '责任类型',
  `demand_staff` varchar(50) NOT NULL COMMENT '需求人员',
  `developer` varchar(50) NOT NULL COMMENT '开发人员',
  `test_staff` varchar(50) NOT NULL COMMENT '测试人员',
  `fixed_time` datetime DEFAULT NULL COMMENT '修复完成时间',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `project_name` varchar(100) NOT NULL COMMENT '项目名称',
  `severity` varchar(15) NOT NULL COMMENT '严重程度',
  `effected_range` varchar(100) NOT NULL COMMENT '影响范围',
  `introduction` varchar(1000) NOT NULL COMMENT 'bug描述',
  `reason_analyse` varchar(1000) NOT NULL COMMENT '原因分析',
  `fixed_method` varchar(1000) NOT NULL COMMENT '修复方案',
  `task_no` varchar(100) DEFAULT NULL COMMENT '禅道任务号',
  PRIMARY KEY (`bug_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='线上bug登记单';

CREATE TABLE `js_bug_registration_picture` (
  `bugpic_code` varchar(64) NOT NULL COMMENT '图片编号',
  `faultpic_name` varchar(64) DEFAULT NULL COMMENT '图片名称',
  `location` varchar(25) DEFAULT NULL COMMENT '图片位置',
  `bugpic_surfix` varchar(25) DEFAULT NULL COMMENT '文件后缀',
  `save_path` varchar(225) DEFAULT '' COMMENT '存储路径',
  `registration_code` varchar(64) DEFAULT NULL COMMENT '线上bug登记单',
  PRIMARY KEY (`bugpic_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='线上bug登记图片';

CREATE TABLE `fz_message_manage` (
  `pkey` varchar(64) NOT NULL COMMENT '单据号',
  `touser` text NOT NULL COMMENT '接收用户',
  `agend_id` varchar(64) NOT NULL COMMENT '应用id',
  `title` varchar(500) NOT NULL COMMENT '消息提示文案',
  `text` varchar(1000) NOT NULL COMMENT '会话框文案',
  `singleUrl` varchar(400) NOT NULL COMMENT '跳转页面路径',
  `singleTitle` varchar(500) NOT NULL COMMENT '页面跳转显示标题',
  `status` char(1) CHARACTER SET utf8 NOT NULL COMMENT '状态（0正常 1删除 2停用）',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(1000) CHARACTER SET utf8 DEFAULT '' COMMENT '接收失败用户',
  `result` char(1) DEFAULT '' COMMENT '是否推送成功',
  `create_by` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '创建者',
  PRIMARY KEY (`pkey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='梵赞消息推送';


/**
2018/11/19
 */
ALTER TABLE js_ding_user ADD COLUMN is_manager CHAR (1) DEFAULT '0' COMMENT '是否管理层';
ALTER TABLE js_ding_user ADD COLUMN direct_superior VARCHAR (64) COMMENT '直接上级';
ALTER TABLE js_ding_user ADD COLUMN department_header VARCHAR (64) COMMENT '部门长';

ALTER TABLE js_am_order_detail ADD COLUMN expected_delivery VARCHAR (30) COMMENT '预计交期';
ALTER TABLE js_am_order_detail ADD COLUMN purchase_cycle VARCHAR (20) COMMENT '采购周期';
ALTER TABLE js_am_order ADD COLUMN check_by VARCHAR (64) COMMENT '查获人';
ALTER TABLE js_am_order ADD COLUMN check_date datetime COMMENT '查货时间';
ALTER TABLE ach_target ADD COLUMN relevance CHAR (1) COMMENT '相关性';