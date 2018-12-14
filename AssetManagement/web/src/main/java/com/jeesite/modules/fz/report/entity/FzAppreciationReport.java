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

import java.util.Date;

/**
 * 赠赞数量统计表Entity
 * @author scarlett
 * @version 2018-10-22
 */
@Table(name="fz_appreciation_report", alias="a", columns={
		@Column(name="userid", attrName="userid", label="用户id", isPK=true),
		@Column(name="name", attrName="name", label="姓名", queryType=QueryType.LIKE),
		@Column(name="department", attrName="department", label="部门"),
		@Column(name="position", attrName="position", label="职位"),
		@Column(name="in_department_gold", attrName="inDepartmentGold", label="部门内梵钻"),
		@Column(name="out_department_gold", attrName="outDepartmentGold", label="out_department_gold"),
		@Column(name="coin_num", attrName="coinNum", label="总共赠送出去的赞赏数量（不包括跟赞）"),
	}, orderBy="a.name ASC"
)
public class FzAppreciationReport extends DataEntity<FzAppreciationReport> {
	@ExcelFields({
			@ExcelField(title="姓名", attrName="name", align=ExcelField.Align.CENTER, sort=10),
			@ExcelField(title="职位", attrName="position", align=ExcelField.Align.CENTER, sort=15),
			@ExcelField(title="部门", attrName="department", align = ExcelField.Align.CENTER, sort=20),
			//@ExcelField(title="可兑换梵钻", attrName="convertibleGold",align = ExcelField.Align.CENTER, sort=30),
			@ExcelField(title="部门内梵钻", attrName="inDepartmentGold",align = ExcelField.Align.CENTER, sort=40),
			@ExcelField(title="跨部门梵钻 ", attrName="outDepartmentGold",align = ExcelField.Align.CENTER, sort=45),
			@ExcelField(title="总共赠送出去的赞赏数量（不包括跟赞）", attrName="coinNum",align = ExcelField.Align.CENTER, sort=45),
	})
	
	private static final long serialVersionUID = 1L;
	private String userid;		// 用户id
	private String name;		// 姓名
	private String department;		// 部门
	private String position;		// 职位
	private Long inDepartmentGold;		// 部门内梵钻
	private Long outDepartmentGold;		// out_department_gold
	private Long coinNum;		// 赠赞总数（不包括跟赞）
	private Date date_gte;
	private Date date_lte;
	private String departmentId;

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

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}


	
	public FzAppreciationReport() {
		this(null);
	}

	public FzAppreciationReport(String id){
		super(id);
	}
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	@Length(min=0, max=225, message="姓名长度不能超过 225 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=225, message="部门长度不能超过 225 个字符")
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	@Length(min=0, max=225, message="职位长度不能超过 225 个字符")
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
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
	
	public Long getCoinNum() {
		return coinNum;
	}

	public void setCoinNum(Long coinNum) {
		this.coinNum = coinNum;
	}
	
}