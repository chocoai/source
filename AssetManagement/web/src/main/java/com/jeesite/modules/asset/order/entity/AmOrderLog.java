/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.order.entity;

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
 * 订单管理日志异常日志表Entity
 * @author len
 * @version 2018-11-12
 */
@Table(name="${_prefix}am_order_log", alias="a", columns={
		@Column(name="log_id", attrName="logId", label="log_id", isPK=true),
		@Column(name="api_uri", attrName="apiUri", label="请求地址"),
		@Column(name="log_info", attrName="logInfo", label="日志信息"),
		@Column(name="create_time", attrName="createTime", label="创建时间",queryType=QueryType.LIKE),
	}, orderBy="a.create_time DESC"
)
public class AmOrderLog extends DataEntity<AmOrderLog> {
	
	private static final long serialVersionUID = 1L;
	private String logId;		// log_id
	private String apiUri;		// 请求地址
	private String logInfo;		// 日志信息
	private Date createTime;		// 创建时间
	
	public AmOrderLog() {
		this(null);
	}

	public AmOrderLog(String id){
		super(id);
	}
	
	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}
	
	@Length(min=0, max=64, message="请求地址长度不能超过 64 个字符")
	public String getApiUri() {
		return apiUri;
	}

	public void setApiUri(String apiUri) {
		this.apiUri = apiUri;
	}
	
	public String getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}