/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.log.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 绩效卡操作日志Entity
 * @author Philip Guan
 * @version 2018-11-28
 */
@Table(name="ach_log", alias="a", columns={
		@Column(name="code", attrName="code", label="主键", isPK=true),
		@Column(name="user_id", attrName="userId", label="用户ID"),
		@Column(name="user_name", attrName="userName", label="用户姓名", queryType=QueryType.LIKE),
		@Column(name="description", attrName="description", label="描述", queryType=QueryType.LIKE),
		@Column(name="action", attrName="action", label="操作"),
		@Column(name="request", attrName="request", label="请求参数", queryType=QueryType.LIKE),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class AchLog extends DataEntity<AchLog> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 主键
	private String userId;		// 用户ID
	private String userName;		// 用户姓名
	private String description;		// 描述
	private String action;		// 操作
	private String request;		// 请求参数
	
	public AchLog() {
		this(null);
	}

	public AchLog(String id){
		super(id);
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=64, message="用户ID长度不能超过 64 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=127, message="用户姓名长度不能超过 127 个字符")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Length(min=0, max=2000, message="描述长度不能超过 2000 个字符")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Length(min=0, max=500, message="操作长度不能超过 500 个字符")
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	@Length(min=0, max=4000, message="请求参数长度不能超过 4000 个字符")
	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}
	
}