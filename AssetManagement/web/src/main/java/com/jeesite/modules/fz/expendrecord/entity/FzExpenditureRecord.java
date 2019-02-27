/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.expendrecord.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;

/**
 * 梵钻支出表Entity
 * @author len
 * @version 2018-12-18
 */
@Table(name="fz_expenditure_record", alias="a", columns={
		@Column(name="record_code", attrName="recordCode", label="支出记录", isPK=true),
		@Column(name="user_id", attrName="userId", label="用户号"),
		@Column(name="user_name", attrName="userName", label="用户名"),
		@Column(name="expend_num", attrName="expendNum", label="梵赞支出数量"),
		@Column(name="expend_time", attrName="expendTime", label="支出时间"),
		@Column(name="expend_mode", attrName="expendMode", label="消费方式"),
	}, orderBy="a.record_code DESC"
)
public class FzExpenditureRecord extends DataEntity<FzExpenditureRecord> {
	
	private static final long serialVersionUID = 1L;
	private String recordCode;		// 支出记录
	private String userId;		// 用户号
	private Double expendNum;		// 梵赞支出数量
	private Date expendTime;		// 支出时间
	private String expendMode;		// 消费方式
	private String userName;		// 用户名

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public FzExpenditureRecord() {
		this(null);
	}

	public FzExpenditureRecord(String id){
		super(id);
	}
	
	public String getRecordCode() {
		return recordCode;
	}

	public void setRecordCode(String recordCode) {
		this.recordCode = recordCode;
	}
	
	@Length(min=0, max=64, message="用户号长度不能超过 64 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public Double getExpendNum() {
		return expendNum;
	}

	public void setExpendNum(Double expendNum) {
		this.expendNum = expendNum;
	}
	
//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getExpendTime() {
		return expendTime;
	}

	public void setExpendTime(Date expendTime) {
		this.expendTime = expendTime;
	}
	
	@Length(min=0, max=128, message="消费方式长度不能超过 128 个字符")
	public String getExpendMode() {
		return expendMode;
	}

	public void setExpendMode(String expendMode) {
		this.expendMode = expendMode;
	}
	
}