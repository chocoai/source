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

/**
 * 用户登录记录报表Entity
 * @author len
 * @version 2018-10-19
 */
@Table(name="fz_login_report", alias="a", columns={
		@Column(name="userid", attrName="userId", label="用户id", isPK=true),
		@Column(name="name", attrName="name", label="英文名", queryType=QueryType.LIKE),
		@Column(name="position", attrName="position", label="职位", queryType=QueryType.LIKE),
		@Column(name="department", attrName="department", label="部门", queryType=QueryType.LIKE),
		@Column(name="convertible_gold", attrName="convertibleGold", label="可兑换梵钻"),
		@Column(name="in_department_gold", attrName="inDepartmentGold", label="部门内梵钻"),
		@Column(name="out_department_gold", attrName="outDepartmentGold", label="跨部门梵钻"),
		@Column(name="frequency", attrName="frequency", label="登录次数"),
	}, orderBy="a.name ASC"
)
public class FzLoginReport extends DataEntity<FzLoginReport> {
	@Valid
	@ExcelFields({
			@ExcelField(title="英文名", attrName="name", align=ExcelField.Align.CENTER, sort=10),
			@ExcelField(title="职位", attrName="position", align=ExcelField.Align.CENTER, sort=15),
			@ExcelField(title="部门", attrName="department", align = ExcelField.Align.CENTER, sort=20),
			@ExcelField(title="可兑换梵钻", attrName="convertibleGold",align = ExcelField.Align.CENTER, sort=30),
			@ExcelField(title="部门内梵钻", attrName="inDepartmentGold",align = ExcelField.Align.CENTER, sort=40),
			@ExcelField(title="跨部门梵钻 ", attrName="outDepartmentGold",align = ExcelField.Align.CENTER, sort=45),
			@ExcelField(title="登录次数 ", attrName="frequency",align = ExcelField.Align.CENTER, sort=45),
	})
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户id
	private String name;		// 英文名
	private String position;		// 职位
	private String department;		// 部门
	private Long convertibleGold;		// 可兑换梵钻
	private Long inDepartmentGold;		// 部门内梵钻
	private Long outDepartmentGold;		// 跨部门梵钻
	private Long frequency;		// 登录次数
	private Date date_gte;
	private Date date_lte;
	private String departmentId;

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public FzLoginReport() {
		this(null);
	}

	public FzLoginReport(String id){
		super(id);
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=64, message="英文名长度不能超过 64 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=64, message="职位长度不能超过 64 个字符")
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	@Length(min=0, max=64, message="部门长度不能超过 64 个字符")
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	public Long getConvertibleGold() {
		return convertibleGold;
	}

	public void setConvertibleGold(Long convertibleGold) {
		this.convertibleGold = convertibleGold;
	}
	
	public Long getInDepartmentGold() {
		return inDepartmentGold;
	}

	public void setInDepartmentGold(Long inDepartmentGold) {
		this.inDepartmentGold = inDepartmentGold;
	}
	
	public Long getOutDepartmentGold() {
		return outDepartmentGold;
	}

	public void setOutDepartmentGold(Long outDepartmentGold) {
		this.outDepartmentGold = outDepartmentGold;
	}
	
	public Long getFrequency() {
		return frequency;
	}

	public void setFrequency(Long frequency) {
		this.frequency = frequency;
	}

	public Date getDate_gte() {
		return date_gte;
	}

	public void setDate_gte(Date date_gte) {
		this.date_gte = date_gte;
	}

	public Date getDate_lte() {
		return date_lte;
	}

	public void setDate_lte(Date date_lte) {
		this.date_lte = date_lte;
	}
}