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
 * 获赞者获赞数统计表Entity
 * @author len
 * @version 2018-10-22
 */
@Table(name="fz_praiser_report", alias="b", columns={
		@Column(name="user_id", attrName="userId", label="用户编码", isPK=true),
		@Column(name="name", attrName="name", label="名称", queryType=QueryType.LIKE),
		@Column(name="position", attrName="position", label="职位", queryType=QueryType.LIKE),
		@Column(name="convertible_gold", attrName="convertibleGold", label="可兑换梵钻"),
		@Column(name="praise_total", attrName="praiseTotal", label="获赞总数"),
	}, orderBy="b.name ASC"
)
public class FzPraiserReport extends DataEntity<FzPraiserReport> {
	@Valid
	@ExcelFields({
			@ExcelField(title="英文名", attrName="name", align=ExcelField.Align.CENTER, sort=10),
			@ExcelField(title="职位", attrName="position", align=ExcelField.Align.CENTER, sort=15),
			@ExcelField(title="部门", attrName="department", align = ExcelField.Align.CENTER, sort=20),
			@ExcelField(title="可兑换梵钻", attrName="convertibleGold",align = ExcelField.Align.CENTER, sort=30),
			@ExcelField(title="获赞总数", attrName="praiseTotal",align = ExcelField.Align.CENTER, sort=40),
	})
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户编码
	private String name;		// 名称
	private String position;		// 职位
	private Long convertibleGold;		// 可兑换梵钻
	private Long praiseTotal;		// 获赞总数
	private Date date_gte;
	private Date date_lte;
	private String department;		// 部门
	private String departmentId;

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public FzPraiserReport() {
		this(null);
	}

	public FzPraiserReport(String id){
		super(id);
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=64, message="名称长度不能超过 64 个字符")
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
	
	public Long getConvertibleGold() {
		return convertibleGold;
	}

	public void setConvertibleGold(Long convertibleGold) {
		this.convertibleGold = convertibleGold;
	}
	
	public Long getPraiseTotal() {
		return praiseTotal;
	}

	public void setPraiseTotal(Long praiseTotal) {
		this.praiseTotal = praiseTotal;
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