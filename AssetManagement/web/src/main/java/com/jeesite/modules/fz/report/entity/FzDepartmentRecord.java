/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.report.entity;

import com.jeesite.common.utils.excel.annotation.ExcelField;
import com.jeesite.common.utils.excel.annotation.ExcelFields;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * 梵赞部门的赞赏数据Entity
 * @author easter
 * @version 2018-11-14
 */
@Table(name="fz_department_record", alias="a", columns={
		@Column(name="department", attrName="department", label="部门", isPK=true),
		@Column(name="max_department", attrName="maxDepartment", label="该部门赞赏最多的"),
		@Column(name="avg_max", attrName="avgMax", label="最多人均赞赏次数"),
		@Column(name="min_department", attrName="minDepartment", label="该部门赞赏最少的"),
		@Column(name="avg_min", attrName="avgMin", label="最少人均赞赏次数"),
		@Column(name="avg_in", attrName="avgIn", label="人均部门内赞赏/获赞次数"),
		@Column(name="avg_out_presenter", attrName="avgOutPresenter", label="人均跨部门赞赏次数"),
		@Column(name="avg_out_praiser", attrName="avgOutPraiser", label="人均跨部门获赞次数"),
		@Column(name="sum_presenter", attrName="sumPresenter", label="人均总赞赏次数"),
		@Column(name="sum_praiser", attrName="sumPraiser", label="人均总获赞次数"),
		@Column(name="max_type", attrName="maxType", label="最爱用的赞赏类型"),
		@Column(name="min_type", attrName="minType", label="最不喜欢用的赞赏类型"),
	}, orderBy="a.department DESC"
)
public class FzDepartmentRecord extends DataEntity<FzDepartmentRecord> {
	@Valid
	@ExcelFields({
			@ExcelField(title="部门", attrName="department", align=ExcelField.Align.CENTER, sort=10),
			@ExcelField(title="该部门赞赏最多的", attrName="maxDepartment", align=ExcelField.Align.CENTER, sort=15),
			@ExcelField(title="最多人均赞赏次数", attrName="avgMax", align = ExcelField.Align.CENTER, sort=20),
			@ExcelField(title="该部门赞赏最少的", attrName="minDepartment",align = ExcelField.Align.CENTER, sort=30),
			@ExcelField(title="最少人均赞赏次数", attrName="avgMin",align = ExcelField.Align.CENTER, sort=40),
			@ExcelField(title="人均部门内赞赏/获赞次数", attrName="avgIn",align = ExcelField.Align.CENTER, sort=40),
			@ExcelField(title="人均跨部门赞赏次数", attrName="avgOutPresenter",align = ExcelField.Align.CENTER, sort=40),
			@ExcelField(title="人均跨部门获赞次数", attrName="avgOutPraiser",align = ExcelField.Align.CENTER, sort=40),
			@ExcelField(title="人均总赞赏次数", attrName="sumPresenter",align = ExcelField.Align.CENTER, sort=40),
			@ExcelField(title="人均总获赞次数", attrName="sumPraiser",align = ExcelField.Align.CENTER, sort=40),
			@ExcelField(title="最爱用的赞赏类型", attrName="maxType",align = ExcelField.Align.CENTER, sort=40),
	})
	
	private static final long serialVersionUID = 1L;
	private String department;		// 部门
	private String maxDepartment;		// 该部门赞赏最多的
	private Double avgMax;		// 最多人均赞赏次数
	private String minDepartment;		// 该部门赞赏最少的
	private Double avgMin;		// 最少人均赞赏次数
	private Double avgIn;		// 人均部门内赞赏/获赞次数
	private Double avgOutPresenter;		// 人均跨部门赞赏次数
	private Double avgOutPraiser;		// 人均跨部门获赞次数
	private Double sumPresenter;		// 人均总赞赏次数
	private Double sumPraiser;		// 人均总获赞次数
	private String maxType;		// 最爱用的赞赏类型
	private String month; 		//选中的月份来统计

	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}

	public FzDepartmentRecord() {
		this(null);
	}

	public FzDepartmentRecord(String id){
		super(id);
	}
	
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	@Length(min=0, max=64, message="该部门赞赏最多的长度不能超过 64 个字符")
	public String getMaxDepartment() {
		return maxDepartment;
	}

	public void setMaxDepartment(String maxDepartment) {
		this.maxDepartment = maxDepartment;
	}
	
	public Double getAvgMax() {
		return avgMax;
	}

	public void setAvgMax(Double avgMax) {
		this.avgMax = avgMax;
	}
	
	@Length(min=0, max=64, message="该部门赞赏最少的长度不能超过 64 个字符")
	public String getMinDepartment() {
		return minDepartment;
	}

	public void setMinDepartment(String minDepartment) {
		this.minDepartment = minDepartment;
	}
	
	public Double getAvgMin() {
		return avgMin;
	}

	public void setAvgMin(Double avgMin) {
		this.avgMin = avgMin;
	}
	
	public Double getAvgIn() {
		return avgIn;
	}

	public void setAvgIn(Double avgIn) {
		this.avgIn = avgIn;
	}
	
	public Double getAvgOutPresenter() {
		return avgOutPresenter;
	}

	public void setAvgOutPresenter(Double avgOutPresenter) {
		this.avgOutPresenter = avgOutPresenter;
	}
	
	public Double getAvgOutPraiser() {
		return avgOutPraiser;
	}

	public void setAvgOutPraiser(Double avgOutPraiser) {
		this.avgOutPraiser = avgOutPraiser;
	}

	public Double getSumPresenter() {
		return sumPresenter;
	}

	public void setSumPresenter(Double sumPresenter) {
		this.sumPresenter = sumPresenter;
	}
	
	public Double getSumPraiser() {
		return sumPraiser;
	}

	public void setSumPraiser(Double sumPraiser) {
		this.sumPraiser = sumPraiser;
	}
	
	@Length(min=0, max=64, message="最爱用的赞赏类型长度不能超过 64 个字符")
	public String getMaxType() {
		return maxType;
	}

	public void setMaxType(String maxType) {
		this.maxType = maxType;
	}
	


}