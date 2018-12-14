/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.k3.report.supplier.entity;

import com.jeesite.common.utils.excel.annotation.ExcelField;
import com.jeesite.common.utils.excel.annotation.ExcelFields;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import javax.validation.Valid;

/**
 * 供应商Entity
 * @author Albert
 * @version 2018-11-28
 */
@Table(name="k3_report_supplier", alias="a", columns={
		@Column(name="supplier_code", attrName="supplierCode", label="供应商编码", isPK=true),
		@Column(name="supplier_name", attrName="supplierName", label="供应商名称", queryType=QueryType.LIKE),
		@Column(name="short_name", attrName="shortName", label="简称", queryType=QueryType.LIKE),
		@Column(name="province", attrName="province", label="省份"),
		@Column(name="city", attrName="city", label="城市"),
		@Column(name="district", attrName="district", label="区县"),
		@Column(name="mailing_address", attrName="mailingAddress", label="通讯地址"),
		@Column(name="phone", attrName="phone", label="电话"),
		@Column(name="is_quality_inspection", attrName="isQualityInspection", label="质检"),
		@Column(name="quality_inspector", attrName="qualityInspector", label="质检员"),
		@Column(name="project_team", attrName="projectTeam", label="项目组"),
		@Column(name="supplier_classification", attrName="supplierClassification", label="供应商分类"),
		@Column(name="supply_category", attrName="supplyCategory", label="供应类别"),
		@Column(name="supplier_group", attrName="supplierGroup", label="供应商分组"),
		@Column(name="company_category", attrName="companyCategory", label="公司类别"),
		@Column(name="company_nature", attrName="companyNature", label="公司性质"),
		@Column(name="company_scale", attrName="companyScale", label="公司规模"),
		@Column(name="supplier_status", attrName="supplierStatus", label="供应商状态"),
		@Column(name="data_status", attrName="dataStatus", label="数据状态"),
		@Column(name="disabled_status", attrName="disabledStatus", label="禁用状态"),
		@Column(name="auditor", attrName="auditor", label="审核人"),
		@Column(name="audit_date", attrName="auditDate", label="审核日期"),
	}, orderBy="a.supplier_code DESC"
)
public class K3ReportSupplier extends DataEntity<K3ReportSupplier> {
	@Valid
	@ExcelFields({
			@ExcelField(title="供应商编码", attrName="supplierCode", align=ExcelField.Align.CENTER, sort=1),
			@ExcelField(title="供应商名称", attrName="supplierName", align=ExcelField.Align.CENTER, sort=2),
			@ExcelField(title="简称", attrName="shortName", align = ExcelField.Align.CENTER, sort=3),
			@ExcelField(title="省份", attrName="province",align = ExcelField.Align.CENTER, sort=4),
			@ExcelField(title="城市", attrName="city",align = ExcelField.Align.CENTER, sort=5),
			@ExcelField(title="区县", attrName="district", align=ExcelField.Align.CENTER, sort=6),
			@ExcelField(title="通讯地址", attrName="mailingAddress", align=ExcelField.Align.CENTER, sort=7),
			@ExcelField(title="电话", attrName="phone", align = ExcelField.Align.CENTER, sort=8),
			@ExcelField(title="质检", attrName="isQualityInspection",align = ExcelField.Align.CENTER, sort=9),
			@ExcelField(title="质检员", attrName="qualityInspector",align = ExcelField.Align.CENTER, sort=10),
			@ExcelField(title="项目组", attrName="projectTeam",align = ExcelField.Align.CENTER, sort=11),
			@ExcelField(title="供应商分类", attrName="supplierClassification",align = ExcelField.Align.CENTER, sort=12),
			@ExcelField(title="供应类别", attrName="supplyCategory",dictType = "k3_supply_category",align = ExcelField.Align.CENTER, sort=13),
			@ExcelField(title="供应商分组", attrName="supplierGroup",align = ExcelField.Align.CENTER, sort=14),
			@ExcelField(title="公司类别", attrName="companyCategory",align = ExcelField.Align.CENTER, sort=15),
			@ExcelField(title="公司性质", attrName="companyNature",align = ExcelField.Align.CENTER, sort=16),
			@ExcelField(title="公司规模", attrName="companyScale",align = ExcelField.Align.CENTER, sort=17),
			@ExcelField(title="供应商状态", attrName="supplierStatus",dictType = "k3_supplier_status",align = ExcelField.Align.CENTER, sort=18),
			@ExcelField(title="数据状态", attrName="dataStatus",dictType = "k3_data_status",align = ExcelField.Align.CENTER, sort=19),
			@ExcelField(title="禁用状态", attrName="disabledStatus",dictType = "k3_disabled_status",align = ExcelField.Align.CENTER, sort=20),
			@ExcelField(title="审核人", attrName="auditor",align = ExcelField.Align.CENTER, sort=21),
			@ExcelField(title="审核日期", attrName="auditDate",align = ExcelField.Align.CENTER, sort=22),
	})
	
	private static final long serialVersionUID = 1L;
	private String supplierCode;		// 供应商编码
	private String supplierName;		// 供应商名称
	private String shortName;		// 简称
	private String province;		// 省份
	private String city;		// 城市
	private String district;		// 区县
	private String mailingAddress;		// 通讯地址
	private String phone;		// 电话
	private String isQualityInspection;		// 质检
	private String qualityInspector;		// 质检员
	private String projectTeam;		// 项目组
	private String supplierClassification;		// 供应商分类
	private String supplyCategory;		// 供应类别
	private String supplierGroup;		// 供应商分组
	private String companyCategory;		// 公司类别
	private String companyNature;		// 公司性质
	private String companyScale;		// 公司规模
	private String supplierStatus;		// 供应商状态
	private String dataStatus;		// 数据状态
	private String disabledStatus;		// 禁用状态
	private String auditor;		// 审核人
	private Date auditDate;		// 审核日期
	private String filterParam;			//数据过滤参数
	
	public K3ReportSupplier() {
		this(null);
	}

	public K3ReportSupplier(String id){
		super(id);
	}
	
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	
	@Length(min=0, max=255, message="供应商名称长度不能超过 255 个字符")
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
	@Length(min=0, max=50, message="简称长度不能超过 50 个字符")
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	@Length(min=0, max=50, message="省份长度不能超过 50 个字符")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@Length(min=0, max=50, message="城市长度不能超过 50 个字符")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Length(min=0, max=50, message="区县长度不能超过 50 个字符")
	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
	
	@Length(min=0, max=255, message="通讯地址长度不能超过 255 个字符")
	public String getMailingAddress() {
		return mailingAddress;
	}

	public void setMailingAddress(String mailingAddress) {
		this.mailingAddress = mailingAddress;
	}
	
	@Length(min=0, max=50, message="电话长度不能超过 50 个字符")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=1, message="质检长度不能超过 1 个字符")
	public String getIsQualityInspection() {
		return isQualityInspection;
	}

	public void setIsQualityInspection(String isQualityInspection) {
		this.isQualityInspection = isQualityInspection;
	}
	
	@Length(min=0, max=255, message="质检员长度不能超过 255 个字符")
	public String getQualityInspector() {
		return qualityInspector;
	}

	public void setQualityInspector(String qualityInspector) {
		this.qualityInspector = qualityInspector;
	}
	
	@Length(min=0, max=100, message="项目组长度不能超过 100 个字符")
	public String getProjectTeam() {
		return projectTeam;
	}

	public void setProjectTeam(String projectTeam) {
		this.projectTeam = projectTeam;
	}
	
	@Length(min=0, max=255, message="供应商分类长度不能超过 255 个字符")
	public String getSupplierClassification() {
		return supplierClassification;
	}

	public void setSupplierClassification(String supplierClassification) {
		this.supplierClassification = supplierClassification;
	}
	
	@Length(min=0, max=36, message="供应类别长度不能超过 36 个字符")
	public String getSupplyCategory() {
		return supplyCategory;
	}

	public void setSupplyCategory(String supplyCategory) {
		this.supplyCategory = supplyCategory;
	}
	
	@Length(min=0, max=100, message="供应商分组长度不能超过 100 个字符")
	public String getSupplierGroup() {
		return supplierGroup;
	}

	public void setSupplierGroup(String supplierGroup) {
		this.supplierGroup = supplierGroup;
	}
	
	@Length(min=0, max=255, message="公司类别长度不能超过 255 个字符")
	public String getCompanyCategory() {
		return companyCategory;
	}

	public void setCompanyCategory(String companyCategory) {
		this.companyCategory = companyCategory;
	}
	
	@Length(min=0, max=255, message="公司性质长度不能超过 255 个字符")
	public String getCompanyNature() {
		return companyNature;
	}

	public void setCompanyNature(String companyNature) {
		this.companyNature = companyNature;
	}
	
	@Length(min=0, max=255, message="公司规模长度不能超过 255 个字符")
	public String getCompanyScale() {
		return companyScale;
	}

	public void setCompanyScale(String companyScale) {
		this.companyScale = companyScale;
	}
	
	@Length(min=0, max=1, message="供应商状态长度不能超过 1 个字符")
	public String getSupplierStatus() {
		return supplierStatus;
	}

	public void setSupplierStatus(String supplierStatus) {
		this.supplierStatus = supplierStatus;
	}
	
	@Length(min=0, max=1, message="数据状态长度不能超过 1 个字符")
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
	
	@Length(min=0, max=50, message="审核人长度不能超过 50 个字符")
	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public String getFilterParam() {
		return filterParam;
	}

	public void setFilterParam(String filterParam) {
		this.filterParam = filterParam;
	}
}