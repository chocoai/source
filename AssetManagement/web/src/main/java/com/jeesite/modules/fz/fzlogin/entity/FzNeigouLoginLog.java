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
 * 梵赞内购登陆日志Entity
 * @author easter
 * @version 2018-11-27
 */
@Table(name="fz_neigou_login_log", alias="a", columns={
		@Column(name="log_id", attrName="logId", label="日志登陆id", isPK=true),
		@Column(name="user_id", attrName="userId", label="用户id"),
		@Column(name="login_time", attrName="loginTime", label="登陆时间"),
		@Column(name="user_name", attrName="userName", label="用户英文名", queryType=QueryType.LIKE),
		@Column(name="login_success", attrName="loginSuccess", label="登陆是否成功,0", comment="登陆是否成功,0:失败,1:成功"),
		@Column(name="result", attrName="result", label="如果登陆失败的话记录原因"),
	}, orderBy="a.log_id DESC"
)
public class FzNeigouLoginLog extends DataEntity<FzNeigouLoginLog> {
	
	private static final long serialVersionUID = 1L;
	private String logId;		// 日志登陆id
	private String userId;		// 用户id
	private Date loginTime;		// 登陆时间
	private String userName;		// 用户英文名
	private String loginSuccess;		// 登陆是否成功,0:失败,1:成功
	private String result;		// 如果登陆失败的话记录原因
	
	public FzNeigouLoginLog() {
		this(null);
	}

	public FzNeigouLoginLog(String id){
		super(id);
	}
	
	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}
	
	@Length(min=0, max=64, message="用户id长度不能超过 64 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	
	@Length(min=0, max=64, message="用户英文名长度不能超过 64 个字符")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Length(min=0, max=1, message="登陆是否成功,0长度不能超过 1 个字符")
	public String getLoginSuccess() {
		return loginSuccess;
	}

	public void setLoginSuccess(String loginSuccess) {
		this.loginSuccess = loginSuccess;
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
}