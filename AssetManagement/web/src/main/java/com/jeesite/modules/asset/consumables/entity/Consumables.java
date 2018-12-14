/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.consumables.entity;

import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.modules.asset.category.entity.Category;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 耗材档案Entity
 * @author dwh
 * @version 2018-04-23
 */
@Table(name="${_prefix}am_consumables", alias="a", columns={
		@Column(name="consumables_code", attrName="consumablesCode", label="耗材编号", isPK=true),
		@Column(name="consumables_name", attrName="consumablesName", label="耗材名称", queryType=QueryType.LIKE),
		@Column(name="bar_code", attrName="barCode", label="商品条码"),
		@Column(name="category_code", attrName="categoryCode", label="分类编号"),
		@Column(name="brand_trademark", attrName="brandTrademark", label="品牌商标"),
		@Column(name="specifications", attrName="specifications", label="规格型号"),
		@Column(name="measure_unit", attrName="measureUnit", label="计量单位"),
		@Column(name="max_stock", attrName="maxStock", label="最大库存"),
		@Column(name="min_stock", attrName="minStock", label="库存下限"),
		@Column(includeEntity=DataEntity.class),
	}, joinTable={
		@JoinTable(type=JoinTable.Type.LEFT_JOIN, entity=Category.class, alias="b",
				on="b.category_code=a.category_code ",
				 columns={@Column(includeEntity=Category.class)})
		}, orderBy="a.update_date DESC"
)
public class Consumables extends DataEntity<Consumables> {
	
	private static final long serialVersionUID = 1L;
	private String consumablesCode;		// 耗材编号
	private String consumablesName;		// 耗材名称
	private String barCode;		// 商品条码
	private String categoryCode;		// 分类编号
	private String brandTrademark;		// 品牌商标
	private String specifications;		// 规格型号
	private String measureUnit;		// 计量单位
	private Long maxStock;		// 最大库存
	private Long minStock;		// 库存下限
	private Category category;
	private String measureValue;

	private String imgPath;   //照片绝对路径

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}


	public String getMeasureValue() {
		return measureValue;
	}

	public void setMeasureValue(String measureValue) {
		this.measureValue = measureValue;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Consumables() {
		this(null);
	}

	public Consumables(String id){
		super(id);
	}
	
	public String getConsumablesCode() {
		return consumablesCode;
	}

	public void setConsumablesCode(String consumablesCode) {
		this.consumablesCode = consumablesCode;
	}
	
	@NotBlank(message="耗材名称不能为空")
	@Length(min=0, max=100, message="耗材名称长度不能超过 100 个字符")
	public String getConsumablesName() {
		return consumablesName;
	}

	public void setConsumablesName(String consumablesName) {
		this.consumablesName = consumablesName;
	}

//	@NotBlank(message="商品条码不能为空")
//	@Length(min=0, max=100, message="商品条码长度不能超过 100 个字符")
	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	
	@NotBlank(message="分类编号不能为空")
	@Length(min=0, max=64, message="分类编号长度不能超过 64 个字符")
	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	
	@Length(min=0, max=100, message="品牌商标长度不能超过 100 个字符")
	public String getBrandTrademark() {
		return brandTrademark;
	}

	public void setBrandTrademark(String brandTrademark) {
		this.brandTrademark = brandTrademark;
	}
	
	@Length(min=0, max=100, message="规格型号长度不能超过 100 个字符")
	public String getSpecifications() {
		return specifications;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}
	
	@NotBlank(message="计量单位不能为空")
	@Length(min=0, max=1, message="计量单位长度不能超过 1 个字符")
	public String getMeasureUnit() {
		return measureUnit;
	}

	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}
	
//	@NotNull(message="最大库存不能为空")
	public Long getMaxStock() {
		return maxStock;
	}

	public void setMaxStock(Long maxStock) {
		this.maxStock = maxStock;
	}
	
//	@NotNull(message="库存下限不能为空")
	public Long getMinStock() {
		return minStock;
	}

	public void setMinStock(Long minStock) {
		this.minStock = minStock;
	}


}