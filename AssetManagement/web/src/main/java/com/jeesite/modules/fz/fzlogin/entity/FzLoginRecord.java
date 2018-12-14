/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.fzlogin.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 梵赞登录记录Entity
 * @author len
 * @version 2018-10-09
 */
@Table(name="fz_login_record", alias="a", columns={
		@Column(name="record_code", attrName="recordCode", label="record_code", isPK=true),
		@Column(name="user_id", attrName="userId", label="user_id"),
		@Column(name="emp_name", attrName="empName", label="英文名", queryType=QueryType.LIKE),
		@Column(name="chinese_name", attrName="chineseName", label="中文名", queryType=QueryType.LIKE),
		@Column(name="job_number", attrName="jobNumber", label="工号", queryType=QueryType.LIKE),
		@Column(name="work_place", attrName="workPlace", label="办公地点", queryType=QueryType.LIKE),
		@Column(name="login_date", attrName="loginDate", label="登录时间"),
	}, orderBy="a.record_code DESC"
)
public class FzLoginRecord extends DataEntity<FzLoginRecord> {
	
	private static final long serialVersionUID = 1L;
	private String recordCode;		// record_code
	private String userId;		// user_id
	private String empName;		// 英文名
	private String chineseName;		// 中文名
	private String jobNumber;		// 工号
	private String workPlace;		// 办公地点
	private Date loginDate;		// 登录时间
	
	public FzLoginRecord() {
		this(null);
	}

	public FzLoginRecord(String id){
		super(id);
	}
	
	public String getRecordCode() {
		return recordCode;
	}

	public void setRecordCode(String recordCode) {
		this.recordCode = recordCode;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=64, message="英文名长度不能超过 64 个字符")
	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}
	
	@Length(min=0, max=64, message="中文名长度不能超过 64 个字符")
	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	
	@Length(min=0, max=5, message="工号长度不能超过 5 个字符")
	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}
	
	@Length(min=0, max=125, message="办公地点长度不能超过 125 个字符")
	public String getWorkPlace() {
		return workPlace;
	}

	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	
}