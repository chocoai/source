/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.k3.report.bom.entity;

import com.jeesite.common.utils.excel.annotation.ExcelField;
import com.jeesite.common.utils.excel.annotation.ExcelFields;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import javax.validation.Valid;

/**
 * 物料清单Entity
 * @author Albert
 * @version 2018-11-21
 */
@Table(name="k3_report_bom", alias="a", columns={
		@Column(name="fid", attrName="fid", label="主键", isPK=true),
		@Column(name="bom_code", attrName="bomCode", label="BOM版本"),
		@Column(name="parent_material_code", attrName="parentMaterialCode", label="父项物料编码"),
		@Column(name="parent_material_name", attrName="parentMaterialName", label="父项物料名称", queryType=QueryType.LIKE),
		@Column(name="bom_group_name", attrName="bomGroupName", label="BOM分组", queryType=QueryType.LIKE),
		@Column(name="sub_material_code", attrName="subMaterialCode", label="子项物料编码"),
		@Column(name="sub_material_name", attrName="subMaterialName", label="子项物料名称", queryType=QueryType.LIKE),
		@Column(name="sub_material_model", attrName="subMaterialModel", label="子项规格型号"),
		@Column(name="sub_unit_name", attrName="subUnitName", label="子项单位", queryType=QueryType.LIKE),
		@Column(name="numerator", attrName="numerator", label="用量", comment="用量:分子"),
		@Column(name="denominator", attrName="denominator", label="用量", comment="用量:分母"),
		@Column(name="sub_bom_code", attrName="subBomCode", label="子项BOM版本"),
	}, orderBy="a.fid DESC"
)
public class K3ReportBom extends DataEntity<K3ReportBom> {
	@Valid
	@ExcelFields({
			@ExcelField(title="BOM版本", attrName="bomCode", align=ExcelField.Align.CENTER, sort=1),
			@ExcelField(title="父项物料编码", attrName="parentMaterialCode", align=ExcelField.Align.CENTER, sort=2),
			@ExcelField(title="父项物料名称", attrName="parentMaterialName", align = ExcelField.Align.CENTER, sort=3),
			@ExcelField(title="BOM分组", attrName="bomGroupName",align = ExcelField.Align.CENTER, sort=4),
			@ExcelField(title="子项物料编码", attrName="subMaterialCode",align = ExcelField.Align.CENTER, sort=5),
			@ExcelField(title="子项物料名称", attrName="subMaterialName", align=ExcelField.Align.CENTER, sort=6),
			@ExcelField(title="子项规格型号", attrName="subMaterialModel", align=ExcelField.Align.CENTER, sort=7),
			@ExcelField(title="子项单位", attrName="subUnitName", align = ExcelField.Align.CENTER, sort=8),
			@ExcelField(title="用量:分子", attrName="numerator",align = ExcelField.Align.CENTER, sort=9),
			@ExcelField(title="用量:分母", attrName="denominator",align = ExcelField.Align.CENTER, sort=10),
			@ExcelField(title="子项BOM版本", attrName="subBomCode",align = ExcelField.Align.CENTER, sort=11),
	})
	private static final long serialVersionUID = 1L;
	private String fid;		// 主键
	private String bomCode;		// BOM版本
	private String parentMaterialCode;		// 父项物料编码
	private String parentMaterialName;		// 父项物料名称
	private String bomGroupName;		// BOM分组
	private String subMaterialCode;		// 子项物料名称
	private String subMaterialName;		// 子项物料名称
	private String subMaterialModel;		// 子项规格型号
	private String subUnitName;		// 子项单位
	private Double numerator;		// 用量:分子
	private Double denominator;		// 用量:分母
	private String subBomCode;		// 子项BOM版本
	private String filterParam; 	//数据过滤参数

	public String getFilterParam() {
		return filterParam;
	}

	public void setFilterParam(String filterParam) {
		this.filterParam = filterParam;
	}

	public K3ReportBom() {
		this(null);
	}

	public K3ReportBom(String id){
		super(id);
	}
	
	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}
	
	@Length(min=0, max=80, message="BOM版本长度不能超过 80 个字符")
	public String getBomCode() {
		return bomCode;
	}

	public void setBomCode(String bomCode) {
		this.bomCode = bomCode;
	}
	
	@Length(min=0, max=80, message="父项物料编码长度不能超过 80 个字符")
	public String getParentMaterialCode() {
		return parentMaterialCode;
	}

	public void setParentMaterialCode(String parentMaterialCode) {
		this.parentMaterialCode = parentMaterialCode;
	}
	
	@Length(min=0, max=255, message="父项物料名称长度不能超过 255 个字符")
	public String getParentMaterialName() {
		return parentMaterialName;
	}

	public void setParentMaterialName(String parentMaterialName) {
		this.parentMaterialName = parentMaterialName;
	}
	
	@Length(min=0, max=255, message="BOM分组长度不能超过 255 个字符")
	public String getBomGroupName() {
		return bomGroupName;
	}

	public void setBomGroupName(String bomGroupName) {
		this.bomGroupName = bomGroupName;
	}
	
	@Length(min=0, max=80, message="子项物料名称长度不能超过 80 个字符")
	public String getSubMaterialCode() {
		return subMaterialCode;
	}

	public void setSubMaterialCode(String subMaterialCode) {
		this.subMaterialCode = subMaterialCode;
	}
	
	@Length(min=0, max=255, message="子项物料名称长度不能超过 255 个字符")
	public String getSubMaterialName() {
		return subMaterialName;
	}

	public void setSubMaterialName(String subMaterialName) {
		this.subMaterialName = subMaterialName;
	}
	
	@Length(min=0, max=510, message="子项规格型号长度不能超过 510 个字符")
	public String getSubMaterialModel() {
		return subMaterialModel;
	}

	public void setSubMaterialModel(String subMaterialModel) {
		this.subMaterialModel = subMaterialModel;
	}
	
	@Length(min=0, max=255, message="子项单位长度不能超过 255 个字符")
	public String getSubUnitName() {
		return subUnitName;
	}

	public void setSubUnitName(String subUnitName) {
		this.subUnitName = subUnitName;
	}
	
	public Double getNumerator() {
		return numerator;
	}

	public void setNumerator(Double numerator) {
		this.numerator = numerator;
	}
	
	public Double getDenominator() {
		return denominator;
	}

	public void setDenominator(Double denominator) {
		this.denominator = denominator;
	}
	
	@Length(min=0, max=80, message="子项BOM版本长度不能超过 80 个字符")
	public String getSubBomCode() {
		return subBomCode;
	}

	public void setSubBomCode(String subBomCode) {
		this.subBomCode = subBomCode;
	}
	
}