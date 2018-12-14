/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.job.entity;

import org.hibernate.validator.constraints.NotBlank;
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
 * 定时任务调度日志表Entity
 * @author len
 * @version 2018-11-08
 */
@Table(name="js_job_log", alias="a", columns={
		@Column(name="job_log_id", attrName="jobLogId", label="任务日志ID", isPK=true),
		@Column(name="job_name", attrName="jobName", label="任务名称", queryType=QueryType.LIKE),
		@Column(name="job_group", attrName="jobGroup", label="任务组名"),
		@Column(name="method_name", attrName="methodName", label="任务方法", queryType=QueryType.LIKE),
		@Column(name="method_params", attrName="methodParams", label="方法参数"),
		@Column(name="job_message", attrName="jobMessage", label="日志信息"),
		@Column(name="status", attrName="status", label="状态"),
		@Column(name="exception_info", attrName="exceptionInfo", label="异常信息"),
		@Column(name="create_time", attrName="createTime", label="创建时间"),
	}, orderBy="a.job_log_id DESC"
)
public class SysJobLog extends DataEntity<SysJobLog> {
	
	private static final long serialVersionUID = 1L;
	private String jobLogId;		// 任务日志ID
	private String jobName;		// 任务名称
	private String jobGroup;		// 任务组名
	private String methodName;		// 任务方法
	private String methodParams;		// 方法参数
	private String jobMessage;		// 日志信息
	private String exceptionInfo;		// 异常信息
	private Date createTime;		// 创建时间
	private String status;

	@Override
	public String getStatus() {
		return status;
	}

	@Override
	public void setStatus(String status) {
		this.status = status;
	}

	public SysJobLog() {
		this(null);
	}

	public SysJobLog(String id){
		super(id);
	}
	
	public String getJobLogId() {
		return jobLogId;
	}

	public void setJobLogId(String jobLogId) {
		this.jobLogId = jobLogId;
	}
	
	@NotBlank(message="任务名称不能为空")
	@Length(min=0, max=64, message="任务名称长度不能超过 64 个字符")
	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
	@NotBlank(message="任务组名不能为空")
	@Length(min=0, max=64, message="任务组名长度不能超过 64 个字符")
	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	
	@Length(min=0, max=500, message="任务方法长度不能超过 500 个字符")
	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	@Length(min=0, max=200, message="方法参数长度不能超过 200 个字符")
	public String getMethodParams() {
		return methodParams;
	}

	public void setMethodParams(String methodParams) {
		this.methodParams = methodParams;
	}
	
	@Length(min=0, max=500, message="日志信息长度不能超过 500 个字符")
	public String getJobMessage() {
		return jobMessage;
	}

	public void setJobMessage(String jobMessage) {
		this.jobMessage = jobMessage;
	}
	
	public String getExceptionInfo() {
		return exceptionInfo;
	}

	public void setExceptionInfo(String exceptionInfo) {
		this.exceptionInfo = exceptionInfo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}