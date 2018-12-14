/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.amlocation.entity;

import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.modules.asset.warehouse.entity.AmWarehouse;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 库位管理Entity
 * @author Mclaran
 * @version 2018-05-03
 */
@Table(name="${_prefix}am_location", alias="a", columns={
		@Column(name="location_code", attrName="locationCode", label="库位编码", isPK=true),
		@Column(name="location_name", attrName="locationName", label="库位名称", queryType=QueryType.LIKE),
		@Column(name="warehouse_code", attrName="warehouseCode", label="仓库编码"),
		@Column(includeEntity=DataEntity.class),
	},
		joinTable={
				@JoinTable(type=JoinTable.Type.JOIN, entity=AmWarehouse.class, alias="b",
						on="b.warehouse_code=a.warehouse_code ",
						columns={@Column(includeEntity=AmWarehouse.class)})
		} ,
		orderBy="a.update_date DESC"
)
public class AmLocation extends DataEntity<AmLocation> {
	
	private static final long serialVersionUID = 1L;
	private String locationCode;		// 库位编码
	private String locationName;		// 库位名称
	private String warehouseCode;		// 仓库编码
	private AmWarehouse amWarehouse;
	
	public AmLocation() {
		this(null);
	}

	public AmLocation(String id){
		super(id);
	}
	
	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	
	@NotBlank(message="库位名称不能为空")
	@Length(min=0, max=64, message="库位名称长度不能超过 64 个字符")
	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
	@Length(min=0, max=64, message="仓库编码长度不能超过 64 个字符")
	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public AmWarehouse getAmWarehouse() {
		return amWarehouse;
	}

	public void setAmWarehouse(AmWarehouse amWarehouse) {
		this.amWarehouse = amWarehouse;
	}
}