/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.tvclient.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 电视客户端日志Entity
 * @author Philip Guan
 * @version 2019-02-13
 */
@Table(name="sv_tv_client_log", alias="a", columns={
		@Column(name="log_code", attrName="logCode", label="编号", isPK=true),
		@Column(name="ip", attrName="ip", label="IP"),
		@Column(name="mac", attrName="mac", label="mac"),
		@Column(name="online", attrName="online", label="在线状态"),
		@Column(name="log_date", attrName="logDate", label="日志时间"),
		@Column(name="log_type", attrName="logType", label="日志类型"),
		@Column(name="log_message", attrName="logMessage", label="内容"),
	}, orderBy="a.log_code DESC"
)
public class SvTvClientLog extends DataEntity<SvTvClientLog> {
	
	private static final long serialVersionUID = 1L;
	private String logCode;		// 编号
	private String ip;		// IP
	private String mac;		// mac
	private String online;		// 在线状态
	private Date logDate;		// 日志时间
	private String logType;		// 日志类型
	private String logMessage;		// 内容
	
	public SvTvClientLog() {
		this(null);
	}

	public SvTvClientLog(String id){
		super(id);
	}
	
	public String getLogCode() {
		return logCode;
	}

	public void setLogCode(String logCode) {
		this.logCode = logCode;
	}
	
	@Length(min=0, max=255, message="IP长度不能超过 255 个字符")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@Length(min=0, max=255, message="mac长度不能超过 255 个字符")
	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}
	
	@Length(min=0, max=1, message="在线状态长度不能超过 1 个字符")
	public String getOnline() {
		return online;
	}

	public void setOnline(String online) {
		this.online = online;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="日志时间不能为空")
	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}
	
	@NotBlank(message="日志类型不能为空")
	@Length(min=0, max=255, message="日志类型长度不能超过 255 个字符")
	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}
	
	@NotBlank(message="内容不能为空")
	@Length(min=0, max=2000, message="内容长度不能超过 2000 个字符")
	public String getLogMessage() {
		return logMessage;
	}

	public void setLogMessage(String logMessage) {
		this.logMessage = logMessage;
	}
	
}