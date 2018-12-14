/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.job.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import java.util.Date;


/**
 * 定时任务调度表Entity
 * @author len
 * @version 2018-11-08
 */
@Table(name="js_job", alias="a", columns={
		@Column(name="job_id", attrName="jobId", label="任务ID", isPK=true),
		@Column(name="job_name", attrName="jobName", label="任务名称", queryType=QueryType.LIKE),
		@Column(name="job_group", attrName="jobGroup", label="任务组名"),
		@Column(name="method_name", attrName="methodName", label="任务方法", queryType=QueryType.LIKE),
		@Column(name="method_params", attrName="methodParams", label="方法参数"),
		@Column(name="cron_expression", attrName="cronExpression", label="cron执行表达式"),
		@Column(name="misfire_policy", attrName="misfirePolicy", label="计划执行错误策略", comment="计划执行错误策略（0默认 1继续 2等待 3放弃）"),
		@Column(name="job_status", attrName="jobStatus", label="任务状态"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class SysJob extends DataEntity<SysJob> {
	
	private static final long serialVersionUID = 1L;
	private String jobId;		// 任务ID
	private String jobName;		// 任务名称
	private String jobGroup;		// 任务组名
	private String methodName;		// 任务方法
	private String methodParams;		// 方法参数
	private String cronExpression;		// cron执行表达式
	private String misfirePolicy;		// 计划执行错误策略（0默认 1继续 2等待 3放弃）
	private String jobStatus;
	private Date createDate;

	@Override
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDate() {
		return createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	
	public SysJob() {
		this(null);
	}

	public SysJob(String id){
		super(id);
	}
	
	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	
	@Length(min=0, max=64, message="任务名称长度不能超过 64 个字符")
	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
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
	
	@Length(min=0, max=255, message="cron执行表达式长度不能超过 255 个字符")
	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	
	@Length(min=0, max=20, message="计划执行错误策略长度不能超过 20 个字符")
	public String getMisfirePolicy() {
		return misfirePolicy;
	}

	public void setMisfirePolicy(String misfirePolicy) {
		this.misfirePolicy = misfirePolicy;
	}

	
}