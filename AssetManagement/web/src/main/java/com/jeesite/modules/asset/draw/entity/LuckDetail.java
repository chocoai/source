/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.draw.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 抽奖竞猜Entity
 * @author len
 * @version 2018-10-05
 */
@Table(name="${_prefix}am_luck_detail", alias="a", columns={
		@Column(name="detail_code", attrName="detailCode", label="明细编码", isPK=true),
		@Column(name="document_code", attrName="documentCode.documentCode", label="单据编号"),
		@Column(name="emp_name", attrName="empName", label="姓名", queryType=QueryType.LIKE),
		@Column(name="work_code", attrName="workCode", label="工号"),
		@Column(name="department", attrName="department", label="部门"),
		@Column(name="chinese_name", attrName="chineseName", label="中文名", queryType=QueryType.LIKE),
		@Column(name="user_id", attrName="userId", label="钉钉用户id", queryType=QueryType.LIKE),
	}, orderBy="a.detail_code ASC"
)
public class LuckDetail extends DataEntity<LuckDetail> {
	
	private static final long serialVersionUID = 1L;
	private String detailCode;		// 明细编码
	private LuckDraw documentCode;		// 单据编号 父类
	private String empName;		// 姓名
	private String workCode;		// 工号
	private String department;		// 部门
	private String chineseName;		// 中文名
	private String userId;			// 钉钉用户id
	
	public LuckDetail() {
		this(null);
	}


	public LuckDetail(LuckDraw documentCode){
		this.documentCode = documentCode;
	}
	
	public String getDetailCode() {
		return detailCode;
	}

	public void setDetailCode(String detailCode) {
		this.detailCode = detailCode;
	}
	
	public LuckDraw getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(LuckDraw documentCode) {
		this.documentCode = documentCode;
	}
	
	@Length(min=0, max=64, message="姓名长度不能超过 64 个字符")
	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}
	
	@Length(min=0, max=64, message="工号长度不能超过 64 个字符")
	public String getWorkCode() {
		return workCode;
	}

	public void setWorkCode(String workCode) {
		this.workCode = workCode;
	}
	
	@Length(min=0, max=64, message="部门长度不能超过 200 个字符")
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	@Length(min=0, max=64, message="中文名长度不能超过 64 个字符")
	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}