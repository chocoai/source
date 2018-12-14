/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.k3.report.inventory.entity;

import com.jeesite.common.utils.excel.annotation.ExcelField;
import com.jeesite.common.utils.excel.annotation.ExcelFields;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import javax.validation.Valid;

/**
 * 即时库存Entity
 * @author Albert
 * @version 2018-11-21
 */
@Table(name="k3_report_inventory", alias="a", columns={
		@Column(name="fid", attrName="fid", label="主键", isPK=true),
		@Column(name="material_code", attrName="materialCode", label="物料编码"),
		@Column(name="material_name", attrName="materialName", label="物料名称", queryType=QueryType.LIKE),
		@Column(name="bom_version", attrName="bomVersion", label="Bom版本"),
		@Column(name="stock_name", attrName="stockName", label="仓库名称", queryType=QueryType.LIKE),
		@Column(name="stock_loc_name", attrName="stockLocName", label="仓位名称", queryType=QueryType.LIKE),
		@Column(name="lot", attrName="lot", label="批号"),
		@Column(name="stock_unit", attrName="stockUnit", label="库存主单位"),
		@Column(name="stock_qty", attrName="stockQty", label="库存量", comment="库存量(主单位)"),
		@Column(name="avb_qty", attrName="avbQty", label="可用量", comment="可用量(主单位)"),
		@Column(name="stock_status", attrName="stockStatus", label="库存状态"),
	}, orderBy="a.fid DESC"
)
public class K3ReportInventory extends DataEntity<K3ReportInventory> {
	@Valid
	@ExcelFields({
			@ExcelField(title="物料编码", attrName="materialCode", align=ExcelField.Align.CENTER, sort=1),
			@ExcelField(title="物料名称", attrName="materialName", align=ExcelField.Align.CENTER, sort=2),
			@ExcelField(title="Bom版本", attrName="bomVersion", align = ExcelField.Align.CENTER, sort=3),
			@ExcelField(title="仓库名称", attrName="stockName",align = ExcelField.Align.CENTER, sort=4),
			@ExcelField(title="仓位名称", attrName="stockLocName",align = ExcelField.Align.CENTER, sort=5),
			@ExcelField(title="批号", attrName="lot", align=ExcelField.Align.CENTER, sort=6),
			@ExcelField(title="库存主单位", attrName="stockUnit", align=ExcelField.Align.CENTER, sort=7),
			@ExcelField(title="库存量(主单位)", attrName="stockQty", align = ExcelField.Align.CENTER, sort=8),
			@ExcelField(title="可用量(主单位)", attrName="avbQty",align = ExcelField.Align.CENTER, sort=9),
			@ExcelField(title="库存状态", attrName="stockStatus",align = ExcelField.Align.CENTER, sort=10),
	})
	
	private static final long serialVersionUID = 1L;
	private String fid;		// 主键
	private String materialCode;		// 物料编码
	private String materialName;		// 物料名称
	private String bomVersion;		// Bom版本
	private String stockName;		// 仓库名称
	private String stockLocName;		// 仓位名称
	private String lot;		// 批号
	private String stockUnit;		// 库存主单位
	private Double stockQty;		// 库存量(主单位)
	private Double avbQty;		// 可用量(主单位)
	private String stockStatus;		// 库存状态
	private String filterParam;			//数据过滤参数

	public String getFilterParam() {
		return filterParam;
	}

	public void setFilterParam(String filterParam) {
		this.filterParam = filterParam;
	}

	public K3ReportInventory() {
		this(null);
	}

	public K3ReportInventory(String id){
		super(id);
	}
	
	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}
	
	@Length(min=0, max=80, message="物料编码长度不能超过 80 个字符")
	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	
	@Length(min=0, max=255, message="物料名称长度不能超过 255 个字符")
	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	
	@Length(min=0, max=80, message="Bom版本长度不能超过 80 个字符")
	public String getBomVersion() {
		return bomVersion;
	}

	public void setBomVersion(String bomVersion) {
		this.bomVersion = bomVersion;
	}
	
	@Length(min=0, max=255, message="仓库名称长度不能超过 255 个字符")
	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	
	@Length(min=0, max=255, message="仓位名称长度不能超过 255 个字符")
	public String getStockLocName() {
		return stockLocName;
	}

	public void setStockLocName(String stockLocName) {
		this.stockLocName = stockLocName;
	}
	
	@Length(min=0, max=255, message="批号长度不能超过 255 个字符")
	public String getLot() {
		return lot;
	}

	public void setLot(String lot) {
		this.lot = lot;
	}
	
	@Length(min=0, max=255, message="库存主单位长度不能超过 255 个字符")
	public String getStockUnit() {
		return stockUnit;
	}

	public void setStockUnit(String stockUnit) {
		this.stockUnit = stockUnit;
	}
	
	public Double getStockQty() {
		return stockQty;
	}

	public void setStockQty(Double stockQty) {
		this.stockQty = stockQty;
	}
	
	public Double getAvbQty() {
		return avbQty;
	}

	public void setAvbQty(Double avbQty) {
		this.avbQty = avbQty;
	}
	
	@Length(min=0, max=255, message="库存状态长度不能超过 255 个字符")
	public String getStockStatus() {
		return stockStatus;
	}

	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
	}
	
}