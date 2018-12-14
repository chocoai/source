/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.supplier.entity;

/*
import com.jeesite.common.utils.excel.annotation.ExcelField;
import com.jeesite.common.utils.excel.annotation.ExcelFields;
import com.jeesite.common.utils.excel.fieldtype.RoleListType;
*/

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.common.utils.excel.annotation.ExcelField;
import com.jeesite.common.utils.excel.annotation.ExcelField.Align;
import com.jeesite.common.utils.excel.annotation.ExcelFields;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 供应商资料Entity
 * @author Scarlett
 * @version 2018-07-04
 */
@Table(name="${_prefix}sup_supplier", alias="a", columns={
		@Column(name="supplier_code", attrName="supplierCode", label="供应商编号", isPK=true),
		@Column(name="company_name", attrName="companyName", label="公司全称", queryType=QueryType.LIKE),
		@Column(name="abbreviation_name", attrName="abbreviationName", label="K3名称",queryType=QueryType.LIKE),
		@Column(name="written", attrName="written", label="已写入K3"),
		@Column(name="company_onlineadd", attrName="companyOnlineadd", label="公司网址"),
		@Column(name="date_startup", attrName="dateStartup", label="公司成立日期"),
		@Column(name="company_property", attrName="companyProperty", label="公司性质"),
		@Column(name="registered_capital", attrName="registeredCapital", label="注册资本", comment="注册资本(万元)"),
		@Column(name="tax_number", attrName="taxNumber", label="税号/统一社会信用代码号"),
		@Column(name="space", attrName="space", label="占地面积", comment="占地面积(平米)"),
		@Column(name="month_production", attrName="monthProduction", label="月生产能力", comment="月生产能力(万元)"),
		@Column(name="employees", attrName="employees", label="员工数量", comment="员工数量(人)"),
		@Column(name="month_surplus_production", attrName="monthSurplusProduction", label="月富余生产能力", comment="月富余生产能力(万元)"),
		@Column(name="year_turnover", attrName="yearTurnover", label="年营业额", comment="年营业额(万元)"),
		@Column(name="business_type", attrName="businessType", label="经营类型"),
		@Column(name="business_scope", attrName="businessScope", label="企业经营范围"),
		@Column(name="company_introduction", attrName="companyIntroduction", label="公司简介"),
		@Column(name="company_tel", attrName="companyTel", label="公司电话"),
		@Column(name="company_fax", attrName="companyFax", label="公司传真"),
		@Column(name="company_postcode", attrName="companyPostcode", label="邮政编码"),
		@Column(name="country", attrName="country", label="国家",queryType=QueryType.LIKE),
		@Column(name="province", attrName="province", label="省份",queryType=QueryType.LIKE),
		@Column(name="city", attrName="city", label="城市",queryType=QueryType.LIKE),
		@Column(name="full_address", attrName="fullAddress", label="公司详细地址"),
		@Column(name="cooperation_level", attrName="cooperationLevel", label="合作等级"),
		@Column(name="observationlevel", attrName="observationlevel", label="考察等级"),
		@Column(name="score", attrName="score", label="综合评分"),
		@Column(name="save_path", attrName="savePath", label="文件路径"),
		@Column(name="relative_path", attrName="relativePath", label="文件相对路径"),
		//supplier_status
		@Column(name="supplier_status", attrName="supplierStatus", label="单据状态"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class SupSupplier extends DataEntity<SupSupplier> {
	@Valid
	@ExcelFields({
			@ExcelField(title="公司名称", attrName="companyName", align=Align.CENTER, sort=10),
			@ExcelField(title="K3名称", attrName="abbreviationName", align=Align.CENTER, sort=15),
			@ExcelField(title="公司网址", attrName="companyOnlineadd", align = Align.CENTER, sort=20),
			@ExcelField(title="公司成立日期", attrName="dateStartup",align = Align.CENTER, sort=30),
            @ExcelField(title="申请日期", attrName="createDate",align = Align.CENTER, sort=40),
			@ExcelField(title="已写入K3", attrName="written",align = Align.CENTER, sort=45),
			@ExcelField(title="公司性质", attrName="companyProperty", align = Align.CENTER, sort=50),
			@ExcelField(title="注册资本(万元)", attrName="registeredCapital", align = Align.CENTER, sort=60),
			@ExcelField(title="税号/统一社会信用代码号", attrName="taxNumber", align = Align.CENTER, sort=70),
			@ExcelField(title="占地面积(平米)", attrName="space", align = Align.CENTER, sort=80),
			@ExcelField(title="月生产能力(万元)", attrName="monthProduction", align = Align.CENTER, sort=90),
			@ExcelField(title="月富余生产能力(万元)", attrName="monthSurplusProduction", align = Align.CENTER, sort=100),
			@ExcelField(title="年营业额(万元)", attrName="yearTurnover", align = Align.CENTER, sort=110),
			@ExcelField(title="员工数量(人)", attrName="employees", align = Align.CENTER, sort=120),
			@ExcelField(title="经营类型", attrName="businessType", align = Align.CENTER, sort=130),
			@ExcelField(title="企业经营范围", attrName="businessScope", align = Align.CENTER, sort=140),
			@ExcelField(title="公司电话", attrName="companyTel", align = Align.CENTER, sort=150),
			@ExcelField(title="公司传真", attrName="companyFax", align = Align.CENTER, sort=160),
			@ExcelField(title="邮政编码", attrName="companyPostcode", align = Align.CENTER, sort=170),
			@ExcelField(title="国家", attrName="country", align = Align.CENTER, sort=180),
			@ExcelField(title="省份", attrName="province", align = Align.CENTER, sort=190),
			@ExcelField(title="城市", attrName="city", align = Align.CENTER, sort=200),
			@ExcelField(title="公司详细地址", attrName="fullAddress", align = Align.CENTER, sort=210),
			@ExcelField(title="综合评分", attrName="score", align = Align.CENTER, sort=220),
			@ExcelField(title="单据状态", attrName="supplierStatus", align = Align.CENTER, sort=230),
			@ExcelField(title="公司简介", attrName="companyIntroduction", align = Align.CENTER, sort=240),

	})
	
	private static final long serialVersionUID = 1L;
	private String supplierCode;		// 供应商编号
	private String companyName;		// 公司全称
	private String companyOnlineadd;		// 公司网址
	private Date dateStartup;		// 公司成立日期
	private String companyProperty;		// 公司性质
	private Long registeredCapital;		// 注册资本(万元)
	private String taxNumber;		// 税号/统一社会信用代码号
	private Long space;		// 占地面积(平米)
	private Long monthProduction;		// 月生产能力(万元)
	private Long employees;		// 员工数量(人)
	private Long monthSurplusProduction;		// 月富余生产能力(万元)
	private Long yearTurnover;		// 年营业额(万元)
	private String businessType;		// 经营类型
	private String businessScope;		// 企业经营范围
	private String companyIntroduction;		// 公司简介
	private String companyTel;		// 公司电话
	private String companyFax;		// 公司传真
	private String companyPostcode;		// 邮政编码
	private String country;		// 国家
	private String province;		// 省份
	private String city;		// 城市
	private String fullAddress;		// 公司详细地址
	private String cooperationLevel;		// 合作等级
	private String observationlevel;		// 考察等级
	private String abbreviationName;//简称
	private String written;//已写入K3
	private String supplierStatusName;//审核状态名称
	private Double score;//综合评分
	private String businessTypeName;//经营类型名称
	private String companyPropertyName;//公司性质名称
	private String businessScopeName;//企业经营范围名称
	private String savePath;//文件路径
	private String relativePath;//文件相对路径(删除时使用)
	private String supplierStatus;//单据状态

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	public String getSupplierStatus() {
		return supplierStatus;
	}

	public void setSupplierStatus(String supplierStatus) {
		this.supplierStatus = supplierStatus;
	}
	public String getSupplierStatusName() {
		return supplierStatusName;
	}

	public void setSupplierStatusName(String supplierStatusName) {
		this.supplierStatusName = supplierStatusName;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getBusinessTypeName() {
		return businessTypeName;
	}

	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
	}

	public String getCompanyPropertyName() {
		return companyPropertyName;
	}

	public void setCompanyPropertyName(String companyPropertyName) {
		this.companyPropertyName = companyPropertyName;
	}

	public String getBusinessScopeName() {
		return businessScopeName;
	}

	public void setBusinessScopeName(String businessScopeName) {
		this.businessScopeName = businessScopeName;
	}

	public String getWritten() {
		return written;
	}

	public void setWritten(String written) {
		this.written = written;
	}

	@Length(min=0, max=64, message="简称长度不能超过 125 个字符")
	public String getAbbreviationName() {
		return abbreviationName;
	}

	public void setAbbreviationName(String abbreviationName) {
		this.abbreviationName = abbreviationName;
	}



	public String getInertactive() {
		return inertactive;
	}

	public void setInertactive(String inertactive) {
		this.inertactive = inertactive;
	}

	private String inertactive;//前端
	private List<SupSupplierContact> supSupplierContactList = ListUtils.newArrayList();		// 子表列表
	private List<SupSupplierCustomers> supSupplierCustomersList = ListUtils.newArrayList();		// 子表列表
	
	public SupSupplier() {
		this(null);
	}

	public SupSupplier(String id){
		super(id);
	}
	
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	
	@NotBlank(message="公司全称不能为空")
	@Length(min=0, max=64, message="公司全称长度不能超过 64 个字符")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Length(min=0, max=100, message="公司网址长度不能超过 100 个字符")
	public String getCompanyOnlineadd() {
		return companyOnlineadd;
	}

	public void setCompanyOnlineadd(String companyOnlineadd) {
		this.companyOnlineadd = companyOnlineadd;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="公司成立日期不能为空")
	public Date getDateStartup() {
		return dateStartup;
	}

	public void setDateStartup(Date dateStartup) {
		this.dateStartup = dateStartup;
	}
	
	@NotBlank(message="公司性质不能为空")
	@Length(min=0, max=20, message="公司性质长度不能超过 20 个字符")
	public String getCompanyProperty() {
		return companyProperty;
	}

	public void setCompanyProperty(String companyProperty) {
		this.companyProperty = companyProperty;
	}
	
	@NotNull(message="注册资本不能为空")
	public Long getRegisteredCapital() {
		return registeredCapital;
	}

	public void setRegisteredCapital(Long registeredCapital) {
		this.registeredCapital = registeredCapital;
	}
	
	@NotBlank(message="税号/统一社会信用代码号不能为空")
	@Length(min=0, max=225, message="税号/统一社会信用代码号长度不能超过 225 个字符")
	public String getTaxNumber() {
		return taxNumber;
	}

	public void setTaxNumber(String taxNumber) {
		this.taxNumber = taxNumber;
	}
	
	@NotNull(message="占地面积不能为空")
	public Long getSpace() {
		return space;
	}

	public void setSpace(Long space) {
		this.space = space;
	}
	
	@NotNull(message="月生产能力不能为空")
	public Long getMonthProduction() {
		return monthProduction;
	}

	public void setMonthProduction(Long monthProduction) {
		this.monthProduction = monthProduction;
	}
	
	@NotNull(message="员工数量不能为空")
	public Long getEmployees() {
		return employees;
	}

	public void setEmployees(Long employees) {
		this.employees = employees;
	}
	
	@NotNull(message="月富余生产能力不能为空")
	public Long getMonthSurplusProduction() {
		return monthSurplusProduction;
	}

	public void setMonthSurplusProduction(Long monthSurplusProduction) {
		this.monthSurplusProduction = monthSurplusProduction;
	}
	
	@NotNull(message="年营业额不能为空")
	public Long getYearTurnover() {
		return yearTurnover;
	}

	public void setYearTurnover(Long yearTurnover) {
		this.yearTurnover = yearTurnover;
	}
	
	@NotBlank(message="经营类型不能为空")
	@Length(min=0, max=25, message="经营类型长度不能超过 25 个字符")
	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	
	@NotBlank(message="企业经营范围不能为空")
	@Length(min=0, max=25, message="企业经营范围长度不能超过 25 个字符")
	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}
	@Length(min=0, max=1000, message="公司简介长度不能超过 1000 个字符")
	public String getCompanyIntroduction() {
		return companyIntroduction;
	}

	public void setCompanyIntroduction(String companyIntroduction) {
		this.companyIntroduction = companyIntroduction;
	}
	
	@NotBlank(message="公司电话不能为空")
	@Length(min=0, max=25, message="公司电话长度不能超过 25 个字符")
	public String getCompanyTel() {
		return companyTel;
	}

	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
	}
	
	@NotBlank(message="公司传真不能为空")
	@Length(min=0, max=25, message="公司传真长度不能超过 25 个字符")
	public String getCompanyFax() {
		return companyFax;
	}

	public void setCompanyFax(String companyFax) {
		this.companyFax = companyFax;
	}
	
	@NotBlank(message="邮政编码不能为空")
	@Length(min=0, max=25, message="邮政编码长度不能超过 25 个字符")
	public String getCompanyPostcode() {
		return companyPostcode;
	}

	public void setCompanyPostcode(String companyPostcode) {
		this.companyPostcode = companyPostcode;
	}
	
	@NotBlank(message="国家不能为空")
	@Length(min=0, max=50, message="国家长度不能超过 50 个字符")
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	@NotBlank(message="省份不能为空")
	@Length(min=0, max=50, message="省份长度不能超过 50 个字符")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@NotBlank(message="城市不能为空")
	@Length(min=0, max=50, message="城市长度不能超过 50 个字符")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@NotBlank(message="公司详细地址不能为空")
	@Length(min=0, max=200, message="公司详细地址长度不能超过 200 个字符")
	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}
	

	@Length(min=0, max=25, message="合作等级长度不能超过 25 个字符")
	public String getCooperationLevel() {
		return cooperationLevel;
	}

	public void setCooperationLevel(String cooperationLevel) {
		this.cooperationLevel = cooperationLevel;
	}


	@Length(min=0, max=25, message="考察等级长度不能超过 25 个字符")
	public String getObservationlevel() {
		return observationlevel;
	}

	public void setObservationlevel(String observationlevel) {
		this.observationlevel = observationlevel;
	}
	
	public Date getDateStartup_gte() {
		return sqlMap.getWhere().getValue("date_startup", QueryType.GTE);
	}

	public void setDateStartup_gte(Date dateStartup) {
		sqlMap.getWhere().and("date_startup", QueryType.GTE, dateStartup);
	}
	
	public Date getDateStartup_lte() {
		return sqlMap.getWhere().getValue("date_startup", QueryType.LTE);
	}

	public void setDateStartup_lte(Date dateStartup) {
		sqlMap.getWhere().and("date_startup", QueryType.LTE, dateStartup);
	}
	
	public Long getRegisteredCapital_gte() {
		return sqlMap.getWhere().getValue("registered_capital", QueryType.GTE);
	}

	public void setRegisteredCapital_gte(Long registeredCapital) {
		sqlMap.getWhere().and("registered_capital", QueryType.GTE, registeredCapital);
	}
	
	public Long getRegisteredCapital_lte() {
		return sqlMap.getWhere().getValue("registered_capital", QueryType.LTE);
	}

	public void setRegisteredCapital_lte(Long registeredCapital) {
		sqlMap.getWhere().and("registered_capital", QueryType.LTE, registeredCapital);
	}
	
	public Long getSpace_gte() {
		return sqlMap.getWhere().getValue("space", QueryType.GTE);
	}

	public void setSpace_gte(Long space) {
		sqlMap.getWhere().and("space", QueryType.GTE, space);
	}
	
	public Long getSpace_lte() {
		return sqlMap.getWhere().getValue("space", QueryType.LTE);
	}

	public void setSpace_lte(Long space) {
		sqlMap.getWhere().and("space", QueryType.LTE, space);
	}
	
	public Long getMonthProduction_gte() {
		return sqlMap.getWhere().getValue("month_production", QueryType.GTE);
	}

	public void setMonthProduction_gte(Long monthProduction) {
		sqlMap.getWhere().and("month_production", QueryType.GTE, monthProduction);
	}
	
	public Long getMonthProduction_lte() {
		return sqlMap.getWhere().getValue("month_production", QueryType.LTE);
	}

	public void setMonthProduction_lte(Long monthProduction) {
		sqlMap.getWhere().and("month_production", QueryType.LTE, monthProduction);
	}
	
	public Long getEmployees_gte() {
		return sqlMap.getWhere().getValue("employees", QueryType.GTE);
	}

	public void setEmployees_gte(Long employees) {
		sqlMap.getWhere().and("employees", QueryType.GTE, employees);
	}
	
	public Long getEmployees_lte() {
		return sqlMap.getWhere().getValue("employees", QueryType.LTE);
	}

	public void setEmployees_lte(Long employees) {
		sqlMap.getWhere().and("employees", QueryType.LTE, employees);
	}
	
	public Long getMonthSurplusProduction_gte() {
		return sqlMap.getWhere().getValue("month_surplus_production", QueryType.GTE);
	}

	public void setMonthSurplusProduction_gte(Long monthSurplusProduction) {
		sqlMap.getWhere().and("month_surplus_production", QueryType.GTE, monthSurplusProduction);
	}
	
	public Long getMonthSurplusProduction_lte() {
		return sqlMap.getWhere().getValue("month_surplus_production", QueryType.LTE);
	}

	public void setMonthSurplusProduction_lte(Long monthSurplusProduction) {
		sqlMap.getWhere().and("month_surplus_production", QueryType.LTE, monthSurplusProduction);
	}
	
	public Long getYearTurnover_gte() {
		return sqlMap.getWhere().getValue("year_turnover", QueryType.GTE);
	}

	public void setYearTurnover_gte(Long yearTurnover) {
		sqlMap.getWhere().and("year_turnover", QueryType.GTE, yearTurnover);
	}
	
	public Long getYearTurnover_lte() {
		return sqlMap.getWhere().getValue("year_turnover", QueryType.LTE);
	}

	public void setYearTurnover_lte(Long yearTurnover) {
		sqlMap.getWhere().and("year_turnover", QueryType.LTE, yearTurnover);
	}

	public List<SupSupplierContact> getSupSupplierContactList() {
		return supSupplierContactList;
	}

	public void setSupSupplierContactList(List<SupSupplierContact> supSupplierContactList) {
		this.supSupplierContactList = supSupplierContactList;
	}
	
	public List<SupSupplierCustomers> getSupSupplierCustomersList() {
		return supSupplierCustomersList;
	}

	public void setSupSupplierCustomersList(List<SupSupplierCustomers> supSupplierCustomersList) {
		this.supSupplierCustomersList = supSupplierCustomersList;
	}
	public Long getScore_gte() {
		return sqlMap.getWhere().getValue("score", QueryType.GTE);
	}

	public void setScore_gte(Long score) {
		sqlMap.getWhere().and("score", QueryType.GTE, score);
	}

	public Long getScore_lte() {
		return sqlMap.getWhere().getValue("score", QueryType.LTE);
	}

	public void setScore_lte(Long score) {
		sqlMap.getWhere().and("score", QueryType.LTE, score);
	}


}