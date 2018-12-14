/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.warehouse.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.entity.TreeEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 仓库Entity
 * @author dwh
 * @version 2018-04-27
 */
@Table(name="${_prefix}am_warehouse", alias="a", columns={
		@Column(name="warehouse_code", attrName="warehouseCode", label="仓库编码", isPK=true),
		@Column(includeEntity=TreeEntity.class),
		@Column(name="warehouse_name", attrName="warehouseName", label="仓库名字", queryType=QueryType.LIKE, isTreeName=true),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.tree_sorts, a.warehouse_code"
)
public class AmWarehouse extends TreeEntity<AmWarehouse> {
	
	private static final long serialVersionUID = 1L;
	private String warehouseCode;		// 仓库编码
	private String warehouseName;		// 仓库名字
	
	public AmWarehouse() {
		this(null);
	}

	public AmWarehouse(String id){
		super(id);
	}
	
	@Override
	public AmWarehouse getParent() {
		return parent;
	}

	@Override
	public void setParent(AmWarehouse parent) {
		this.parent = parent;
	}
	
	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	
	@NotBlank(message="仓库名字不能为空")
	@Length(min=0, max=100, message="仓库名字长度不能超过 100 个字符")
	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	
}