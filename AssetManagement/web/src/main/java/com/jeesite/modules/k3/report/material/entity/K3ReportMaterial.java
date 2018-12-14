/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.k3.report.material.entity;

import com.jeesite.common.utils.excel.annotation.ExcelField;
import com.jeesite.common.utils.excel.annotation.ExcelFields;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import javax.validation.Valid;

/**
 * 物料Entity
 * @author Albert
 * @version 2018-11-28
 */
@Table(name="k3_report_material", alias="a", columns={
		@Column(name="material_code", attrName="materialCode", label="物料编码", isPK=true),
		@Column(name="material_name", attrName="materialName", label="物料名称", queryType=QueryType.LIKE),
		@Column(name="inventory_category", attrName="inventoryCategory", label="存货类别"),
		@Column(name="material_group", attrName="materialGroup", label="物料分组"),
		@Column(name="product_category", attrName="productCategory", label="商品类别"),
		@Column(name="commodity_series", attrName="commoditySeries", label="商品系列"),
		@Column(name="is_wooden", attrName="isWooden", label="出厂木架"),
		@Column(name="is_quality_inspection", attrName="isQualityInspection", label="质检"),
		@Column(name="out_stock_make_wooden", attrName="outStockMakeWooden", label="出库打木架"),
		@Column(name="packing_length", attrName="packingLength", label="包装长"),
		@Column(name="packing_width", attrName="packingWidth", label="包装宽"),
		@Column(name="packing_high", attrName="packingHigh", label="包装高"),
		@Column(name="packing_volume", attrName="packingVolume", label="包装体积"),
		@Column(name="inventory_area", attrName="inventoryArea", label="存货区域"),
		@Column(name="replenishment_strategy", attrName="replenishmentStrategy", label="补货策略"),
		@Column(name="data_status", attrName="dataStatus", label="数据状态"),
		@Column(name="disabled_status", attrName="disabledStatus", label="禁用状态"),
		@Column(name="specification", attrName="specification", label="规格"),
		@Column(name="description", attrName="description", label="描述"),
	}, orderBy="a.material_code DESC"
)
public class K3ReportMaterial extends DataEntity<K3ReportMaterial> {
	@Valid
	@ExcelFields({
			@ExcelField(title="物料编码", attrName="materialCode", align=ExcelField.Align.CENTER, sort=1),
			@ExcelField(title="物料名称", attrName="materialName", align=ExcelField.Align.CENTER, sort=2),
			@ExcelField(title="存货类别", attrName="inventoryCategory", align = ExcelField.Align.CENTER, sort=3),
			@ExcelField(title="物料分组", attrName="materialGroup",align = ExcelField.Align.CENTER, sort=4),
			@ExcelField(title="商品类别", attrName="productCategory",align = ExcelField.Align.CENTER, sort=5),
			@ExcelField(title="商品系列", attrName="commoditySeries", align=ExcelField.Align.CENTER, sort=6),
			@ExcelField(title="出厂木架", attrName="isWooden", align=ExcelField.Align.CENTER, sort=7),
			@ExcelField(title="质检", attrName="isQualityInspection", align = ExcelField.Align.CENTER, sort=8),
			@ExcelField(title="出库打木架", attrName="outStockMakeWooden",align = ExcelField.Align.CENTER, sort=9),
			@ExcelField(title="包装长", attrName="packingLength",align = ExcelField.Align.CENTER, sort=10),
			@ExcelField(title="包装宽", attrName="packingWidth",align = ExcelField.Align.CENTER, sort=11),
			@ExcelField(title="包装高", attrName="packingHigh",align = ExcelField.Align.CENTER, sort=12),
			@ExcelField(title="包装体积", attrName="packingVolume",align = ExcelField.Align.CENTER, sort=13),
			@ExcelField(title="存货区域", attrName="inventoryArea",align = ExcelField.Align.CENTER, sort=14),
			@ExcelField(title="补货策略", attrName="replenishmentStrategy",align = ExcelField.Align.CENTER, sort=15),
			@ExcelField(title="数据状态", attrName="dataStatus",align = ExcelField.Align.CENTER, sort=16),
			@ExcelField(title="禁用状态", attrName="disabledStatus",align = ExcelField.Align.CENTER, sort=17),
			@ExcelField(title="规格", attrName="specification",align = ExcelField.Align.CENTER, sort=18),
			@ExcelField(title="描述", attrName="description",align = ExcelField.Align.CENTER, sort=19),
	})

	private static final long serialVersionUID = 1L;
	private String materialCode;		// 物料编码
	private String materialName;		// 物料名称
	private String inventoryCategory;		// 存货类别
	private String materialGroup;		// 物料分组
	private String productCategory;		// 商品类别
	private String commoditySeries;		// 商品系列
	private String isWooden;		// 出厂木架
	private String isQualityInspection;		// 质检
	private String outStockMakeWooden;		//出库打木架
	private Double packingLength;		// 包装长
	private Double packingWidth;		// 包装宽
	private Double packingHigh;		// 包装高
	private Double packingVolume;		//包装体积
	private String inventoryArea;		// 存货区域
	private String replenishmentStrategy;		// 补货策略
	private String dataStatus;		// 数据状态
	private String disabledStatus;		// 禁用状态
	private String specification;		// 规格
	private String description;		// 描述
	private String filterParam;			//数据过滤参数
	
	public K3ReportMaterial() {
		this(null);
	}

	public K3ReportMaterial(String id){
		super(id);
	}
	
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
	
	@Length(min=0, max=255, message="存货类别长度不能超过 255 个字符")
	public String getInventoryCategory() {
		return inventoryCategory;
	}

	public void setInventoryCategory(String inventoryCategory) {
		this.inventoryCategory = inventoryCategory;
	}
	
	@Length(min=0, max=20, message="物料分组长度不能超过 20 个字符")
	public String getMaterialGroup() {
		return materialGroup;
	}

	public void setMaterialGroup(String materialGroup) {
		this.materialGroup = materialGroup;
	}
	
	@Length(min=0, max=255, message="商品类别长度不能超过 255 个字符")
	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	
	@Length(min=0, max=255, message="商品系列长度不能超过 255 个字符")
	public String getCommoditySeries() {
		return commoditySeries;
	}

	public void setCommoditySeries(String commoditySeries) {
		this.commoditySeries = commoditySeries;
	}
	
	@Length(min=0, max=1, message="出厂木架长度不能超过 1 个字符")
	public String getIsWooden() {
		return isWooden;
	}

	public void setIsWooden(String isWooden) {
		this.isWooden = isWooden;
	}
	
	@Length(min=0, max=1, message="质检长度不能超过 1 个字符")
	public String getIsQualityInspection() {
		return isQualityInspection;
	}

	public void setIsQualityInspection(String isQualityInspection) {
		this.isQualityInspection = isQualityInspection;
	}

	public String getOutStockMakeWooden() { return outStockMakeWooden; }

	public void setOutStockMakeWooden(String outStockMakeWooden) { this.outStockMakeWooden = outStockMakeWooden; }

	public Double getPackingLength() {
		return packingLength;
	}

	public void setPackingLength(Double packingLength) {
		this.packingLength = packingLength;
	}
	
	public Double getPackingWidth() {
		return packingWidth;
	}

	public void setPackingWidth(Double packingWidth) {
		this.packingWidth = packingWidth;
	}
	
	public Double getPackingHigh() {
		return packingHigh;
	}

	public void setPackingHigh(Double packingHigh) {
		this.packingHigh = packingHigh;
	}

	public Double getPackingVolume() { return packingVolume; }

	public void setPackingVolume(Double packingVolume) { this.packingVolume = packingVolume; }

	@Length(min=0, max=255, message="存货区域长度不能超过 255 个字符")
	public String getInventoryArea() {
		return inventoryArea;
	}

	public void setInventoryArea(String inventoryArea) {
		this.inventoryArea = inventoryArea;
	}
	
	@Length(min=0, max=255, message="补货策略长度不能超过 255 个字符")
	public String getReplenishmentStrategy() {
		return replenishmentStrategy;
	}

	public void setReplenishmentStrategy(String replenishmentStrategy) {
		this.replenishmentStrategy = replenishmentStrategy;
	}
	
	@Length(min=0, max=20, message="数据状态长度不能超过 20 个字符")
	public String getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}
	
	@Length(min=0, max=1, message="禁用状态长度不能超过 1 个字符")
	public String getDisabledStatus() {
		return disabledStatus;
	}

	public void setDisabledStatus(String disabledStatus) {
		this.disabledStatus = disabledStatus;
	}
	
	@Length(min=0, max=255, message="规格长度不能超过 255 个字符")
	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}
	
	@Length(min=0, max=510, message="描述长度不能超过 510 个字符")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFilterParam() {
		return filterParam;
	}

	public void setFilterParam(String filterParam) {
		this.filterParam = filterParam;
	}
}