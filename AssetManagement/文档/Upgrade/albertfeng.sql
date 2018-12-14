-- 2018-09-21 20:41
alter table `js_fgc_quality_check_details` add COLUMN `package_volume` decimal(23,4) DEFAULT 0 COMMENT '包装体积';


-- 2018-11-20 16:39
ALTER TABLE js_sys_employee ADD k3_account varchar(50) NULL COMMENT 'K3账号';
ALTER TABLE js_sys_employee ADD k3_secret varchar(50) NULL COMMENT 'K3密钥';

-- 2018-11-21 10:45
CREATE TABLE `k3_report_inventory` (
  `fid` varchar(50) NOT NULL COMMENT '主键',
  `material_code` varchar(80) DEFAULT NULL COMMENT '物料编码',
  `material_name` varchar(255) DEFAULT NULL COMMENT '物料名称',
  `bom_version` varchar(80) DEFAULT NULL COMMENT 'Bom版本',
  `stock_name` varchar(255) DEFAULT NULL COMMENT '仓库名称',
  `stock_loc_name` varchar(255) DEFAULT NULL COMMENT '仓位名称',
  `lot` varchar(255) DEFAULT NULL COMMENT '批号',
  `stock_unit` varchar(255) DEFAULT NULL COMMENT '库存主单位',
  `stock_qty` decimal(23,2) DEFAULT NULL COMMENT '库存量(主单位)',
  `avb_qty` decimal(23,2) DEFAULT NULL COMMENT '可用量(主单位)',
  `stock_status` varchar(255) DEFAULT NULL COMMENT '库存状态',
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='即时库存';


CREATE TABLE `k3_report_bom` (
  `fid` varchar(50) NOT NULL COMMENT '主键',
  `bom_code` varchar(80) DEFAULT NULL COMMENT 'BOM版本',
  `parent_material_code` varchar(80) DEFAULT NULL COMMENT '父项物料编码',
  `parent_material_name` varchar(255) DEFAULT NULL COMMENT '父项物料名称',
  `bom_group_name` varchar(255) DEFAULT NULL COMMENT 'BOM分组',
  `sub_material_code` varchar(80) DEFAULT 'NULL' COMMENT '子项物料编码',
  `sub_material_name` varchar(255) DEFAULT NULL COMMENT '子项物料名称',
  `sub_material_model` varchar(510) DEFAULT NULL COMMENT '子项规格型号',
  `sub_unit_name` varchar(255) DEFAULT NULL COMMENT '子项单位',
  `numerator` decimal(23,2) DEFAULT NULL COMMENT '用量:分子',
  `denominator` decimal(23,2) DEFAULT NULL COMMENT '用量:分母',
  `sub_bom_code` varchar(80) DEFAULT NULL COMMENT '子项BOM版本',
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物料清单';

-- 2018-11-28 19:24
CREATE TABLE `k3_report_material` (
  `material_code` varchar(80) DEFAULT NULL COMMENT '物料编码',
  `material_name` varchar(255) DEFAULT NULL COMMENT '物料名称',
  `inventory_category` varchar(255) DEFAULT NULL COMMENT '存货类别',
  `material_group` varchar(20) DEFAULT NULL COMMENT '物料分组',
  `product_category` varchar(255) DEFAULT NULL COMMENT '商品类别',
  `commodity_series` varchar(255) DEFAULT NULL COMMENT '商品系列',
  `is_wooden` char(1) DEFAULT NULL COMMENT '出厂木架',
  `is_quality_inspection` char(1) DEFAULT NULL COMMENT '质检',
  `out_stock_make_wooden` char(1) DEFAULT NULL COMMENT '出库打木架',
  `packing_length` decimal(23,3) DEFAULT NULL COMMENT '包装长',
  `packing_width` decimal(23,3) DEFAULT NULL COMMENT '包装宽',
  `packing_high` decimal(23,3) DEFAULT NULL COMMENT '包装高',
  `packing_volume` decimal(23,3) DEFAULT NULL COMMENT '包装体积',
  `inventory_area` varchar(255) DEFAULT NULL COMMENT '存货区域',
  `replenishment_strategy` varchar(255) DEFAULT NULL COMMENT '补货策略',
  `data_status` varchar(20) DEFAULT NULL COMMENT '数据状态',
  `disabled_status` char(1) DEFAULT NULL COMMENT '禁用状态',
  `specification` varchar(255) DEFAULT NULL COMMENT '规格',
  `description` varchar(510) DEFAULT NULL COMMENT '描述'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物料';

CREATE TABLE `k3_report_supplier` (
  `supplier_code` varchar(80) DEFAULT 'NULL' COMMENT '供应商编码',
  `supplier_name` varchar(255) DEFAULT 'NULL' COMMENT '供应商名称',
  `short_name` varchar(50) DEFAULT 'NULL' COMMENT '简称',
  `province` varchar(50) DEFAULT NULL COMMENT '省份',
  `city` varchar(50) DEFAULT NULL COMMENT '城市',
  `district` varchar(50) DEFAULT NULL COMMENT '区县',
  `mailing_address` varchar(255) DEFAULT 'NULL' COMMENT '通讯地址',
  `phone` varchar(50) DEFAULT NULL COMMENT '电话',
  `is_quality_inspection` char(1) DEFAULT NULL COMMENT '质检',
  `quality_inspector` varchar(255) DEFAULT NULL COMMENT '质检员',
  `project_team` varchar(100) DEFAULT NULL COMMENT '项目组',
  `supplier_classification` varchar(255) DEFAULT NULL COMMENT '供应商分类',
  `supply_category` varchar(36) DEFAULT NULL COMMENT '供应类别',
  `supplier_group` varchar(100) DEFAULT NULL COMMENT '供应商分组',
  `company_category` varchar(255) DEFAULT NULL COMMENT '公司类别',
  `company_nature` varchar(255) DEFAULT NULL COMMENT '公司性质',
  `company_scale` varchar(255) DEFAULT NULL COMMENT '公司规模',
  `supplier_status` char(1) DEFAULT NULL COMMENT '供应商状态',
  `data_status` char(1) DEFAULT NULL COMMENT '数据状态',
  `disabled_status` char(1) DEFAULT NULL COMMENT '禁用状态',
  `auditor` varchar(50) DEFAULT NULL COMMENT '审核人',
  `audit_date` datetime DEFAULT NULL COMMENT '审核日期'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='供应商';