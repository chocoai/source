/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.fgcqualitycheck.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 梵工厂反写日志表Entity
 * @author len
 * @version 2018-10-16
 */
@Table(name="${_prefix}fgc_log", alias="a", columns={
		@Column(name="log_id", attrName="logId", label="日志记录编码", isPK=true),
		@Column(name="error_info", attrName="errorInfo", label="异常信息", queryType=QueryType.LIKE),
		@Column(name="operation_type", attrName="operationType", label="操作类型", queryType=QueryType.LIKE),
		@Column(name="create_time", attrName="createTime", label="创建时间"),
		@Column(name="fentityId", attrName="fentityId"),
		@Column(name="fid", attrName="fid"),
	}, orderBy="a.log_id DESC"
)
public class FgcLog extends DataEntity<FgcLog> {
	
	private static final long serialVersionUID = 1L;
	private String logId;		// 日志记录编码
	private String errorInfo;		// 异常信息
	private String operationType;		// 操作类型
	private Date createTime;		// 创建时间
	private String fentityId;
	private String fid;
	
	public FgcLog() {
		this(null);
	}

	public FgcLog(String id){
		super(id);
	}
	
	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}
	
	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	
	@Length(min=0, max=64, message="操作类型长度不能超过 64 个字符")
	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getFentityId() {
		return fentityId;
	}

	public void setFentityId(String fentityId) {
		this.fentityId = fentityId;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public Date getDate_gte() {
		return sqlMap.getWhere().getValue("create_time", QueryType.GTE);
	}

	public void setDate_gte(Date billDate) {
		sqlMap.getWhere().and("create_time", QueryType.GTE, billDate);
	}

	public Date getDate_lte() {
		return sqlMap.getWhere().getValue("create_time", QueryType.LTE);
	}

	public void setDate_lte(Date billDate) {
		sqlMap.getWhere().and("create_time", QueryType.LTE, billDate);
	}
}