/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.record.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 日志管理Entity
 * @author scarlett
 * @version 2018-09-17
 */
@Table(name="${_prefix}record_log", alias="a", columns={
		@Column(name="log_code", attrName="logCode", label="记录id", isPK=true),
		@Column(name="title", attrName="title", label="标题", queryType=QueryType.LIKE),
		@Column(name="type", attrName="type", label="来源"),
		@Column(name="create_time", attrName="createTime", label="生成时间"),
		@Column(name="write_time", attrName="writeTime", label="写入时间"),
		@Column(name="content", attrName="content", label="内容"),
		@Column(name="level", attrName="level", label="级别"),
		@Column(name="path", attrName="path", label="路径"),
	}, orderBy="a.log_code DESC"
)
public class RecordLog extends DataEntity<RecordLog> {
	
	private static final long serialVersionUID = 1L;
	private String logCode;		// 记录id
	private String title;		// 标题
	private String type;		// 来源
	private Date createTime;		// 生成时间
	private Date writeTime;		// 写入时间
	private String content;		// 内容
	private String level;		// 级别
	private String path;		// 路径
	
	public RecordLog() {
		this(null);
	}

	public RecordLog(String id){
		super(id);
	}
	
	public String getLogCode() {
		return logCode;
	}

	public void setLogCode(String logCode) {
		this.logCode = logCode;
	}
	
	@Length(min=0, max=225, message="标题长度不能超过 225 个字符")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=100, message="来源长度不能超过 100 个字符")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getWriteTime() {
		return writeTime;
	}

	public void setWriteTime(Date writeTime) {
		this.writeTime = writeTime;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=15, message="级别长度不能超过 15 个字符")
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	@Length(min=0, max=225, message="路径长度不能超过 225 个字符")
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	public Date getCreateTime_gte() {
		return sqlMap.getWhere().getValue("create_time", QueryType.GTE);
	}
	public void setCreateTime_gte(Date createTime) {
		sqlMap.getWhere().and("create_time", QueryType.GTE, createTime);
	}
	public Date getCreateTime_lte() {
		return sqlMap.getWhere().getValue("create_time", QueryType.LTE);
	}
	public void setCreateTime_lte(Date createTime) {
		sqlMap.getWhere().and("create_time", QueryType.LTE, createTime);
	}

	public Date getWriteTime_gte() {
		return sqlMap.getWhere().getValue("write_time", QueryType.GTE);
	}
	public void setWriteTime_gte(Date writeTime) {
		sqlMap.getWhere().and("write_time", QueryType.GTE, writeTime);
	}

	public Date getWriteTime_lte() {
		return sqlMap.getWhere().getValue("write_time", QueryType.LTE);
	}
	public void setWriteTime_lte(Date writeTime) {
		sqlMap.getWhere().and("write_time", QueryType.LTE, writeTime);
	}
	
}