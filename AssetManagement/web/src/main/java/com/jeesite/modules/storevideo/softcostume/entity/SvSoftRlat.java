/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.softcostume.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 店铺视频关系Entity
 * @author len
 * @version 2019-02-26
 */
@Table(name="sv_soft_rlat", alias="a", columns={
		@Column(name="soft_rlat_code", attrName="softRlatCode", label="关系编号", isPK=true),
		@Column(name="soft_costume_code", attrName="softCostumeCode", label="编号"),
		@Column(name="dimension_id", attrName="dimensionId", label="类型ID"),
		@Column(name="dimension_value", attrName="dimensionValue", label="类型值"),
		@Column(name="dimension_label", attrName="dimensionLabel", label="类型显示"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class SvSoftRlat extends DataEntity<SvSoftRlat> {
	
	private static final long serialVersionUID = 1L;
	private String softRlatCode;		// 关系编号
	private String softCostumeCode;		// 编号
	private String dimensionId;		// 类型ID
	private String dimensionValue;		// 类型值
	private String dimensionLabel;		// 类型显示
	
	public SvSoftRlat() {
		this(null);
	}

	public SvSoftRlat(String id){
		super(id);
	}
	
	public String getSoftRlatCode() {
		return softRlatCode;
	}

	public void setSoftRlatCode(String softRlatCode) {
		this.softRlatCode = softRlatCode;
	}
	
	@NotBlank(message="编号不能为空")
	@Length(min=0, max=64, message="编号长度不能超过 64 个字符")
	public String getSoftCostumeCode() {
		return softCostumeCode;
	}

	public void setSoftCostumeCode(String softCostumeCode) {
		this.softCostumeCode = softCostumeCode;
	}
	
	@NotBlank(message="类型ID不能为空")
	@Length(min=0, max=255, message="类型ID长度不能超过 255 个字符")
	public String getDimensionId() {
		return dimensionId;
	}

	public void setDimensionId(String dimensionId) {
		this.dimensionId = dimensionId;
	}
	
	@NotBlank(message="类型值不能为空")
	@Length(min=0, max=255, message="类型值长度不能超过 255 个字符")
	public String getDimensionValue() {
		return dimensionValue;
	}

	public void setDimensionValue(String dimensionValue) {
		this.dimensionValue = dimensionValue;
	}
	
	@NotBlank(message="类型显示不能为空")
	@Length(min=0, max=512, message="类型显示长度不能超过 512 个字符")
	public String getDimensionLabel() {
		return dimensionLabel;
	}

	public void setDimensionLabel(String dimensionLabel) {
		this.dimensionLabel = dimensionLabel;
	}
	
}