/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.fault.entity;

import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 线上bug登记单Entity
 * @author Scarlett
 * @version 2018-10-25
 */
@Table(name="${_prefix}bug_registration", alias="a", columns={
		@Column(name="bug_code", attrName="bugCode", label="编号", isPK=true),
		@Column(name="bug_date", attrName="bugDate", label="日期"),
		@Column(name="feedback_person", attrName="feedbackPerson", label="反馈人"),
		@Column(name="dealer", attrName="dealer", label="处理人"),
		@Column(name="liable_type", attrName="liableType", label="责任类型"),
		@Column(name="demand_staff", attrName="demandStaff", label="需求人员"),
		@Column(name="developer", attrName="developer", label="开发人员"),
		@Column(name="test_staff", attrName="testStaff", label="测试人员", queryType=QueryType.LIKE),
		@Column(name="fixed_time", attrName="fixedTime", label="修复完成时间"),
		@Column(name="start_time", attrName="startTime", label="开始时间"),
		@Column(name="project_name", attrName="projectName", label="项目名称", queryType=QueryType.LIKE),
		@Column(name="severity", attrName="severity", label="严重程度"),
		@Column(name="effected_range", attrName="effectedRange", label="影响范围"),
		@Column(name="introduction", attrName="introduction", label="bug描述"),
		@Column(name="reason_analyse", attrName="reasonAnalyse", label="原因分析"),
		@Column(name="fixed_method", attrName="fixedMethod", label="修复方案"),
		@Column(name="task_no", attrName="taskNo", label="禅道任务号"),
	}, orderBy="a.bug_code DESC"
)
public class BugRegistration extends DataEntity<BugRegistration> {
	
	private static final long serialVersionUID = 1L;
	private String bugCode;		// 编号
	private Date bugDate;		// 日期
	private String feedbackPerson;		// 反馈人
	private String dealer;		// 处理人
	private String liableType;		// 责任类型
	private String demandStaff;		// 需求人员
	private String developer;		// 开发人员
	private String testStaff;		// 测试人员
	private Date fixedTime;		// 修复完成时间
	private Date startTime;		// 开始时间
	private String projectName;		// 项目名称
	private String severity;		// 严重程度
	private String effectedRange;		// 影响范围
	private String introduction;		// bug描述
	private String reasonAnalyse;		// 原因分析
	private String fixedMethod;		// 修复方案
	private String taskNo;		// 禅道任务号
	
	public BugRegistration() {
		this(null);
	}

	public BugRegistration(String id){
		super(id);
	}
	
	public String getBugCode() {
		return bugCode;
	}

	public void setBugCode(String bugCode) {
		this.bugCode = bugCode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message="日期不能为空")
	public Date getBugDate() {
		return bugDate;
	}

	public void setBugDate(Date bugDate) {
		this.bugDate = bugDate;
	}
	
	@NotBlank(message="反馈人不能为空")
	@Length(min=0, max=50, message="反馈人长度不能超过 50 个字符")
	public String getFeedbackPerson() {
		return feedbackPerson;
	}

	public void setFeedbackPerson(String feedbackPerson) {
		this.feedbackPerson = feedbackPerson;
	}
	
	@NotBlank(message="处理人不能为空")
	@Length(min=0, max=50, message="处理人长度不能超过 50 个字符")
	public String getDealer() {
		return dealer;
	}

	public void setDealer(String dealer) {
		this.dealer = dealer;
	}
	
	@NotBlank(message="责任类型不能为空")
	@Length(min=0, max=15, message="责任类型长度不能超过 15 个字符")
	public String getLiableType() {
		return liableType;
	}

	public void setLiableType(String liableType) {
		this.liableType = liableType;
	}
	
	@NotBlank(message="需求人员不能为空")
	@Length(min=0, max=50, message="需求人员长度不能超过 50 个字符")
	public String getDemandStaff() {
		return demandStaff;
	}

	public void setDemandStaff(String demandStaff) {
		this.demandStaff = demandStaff;
	}
	
	@NotBlank(message="开发人员不能为空")
	@Length(min=0, max=50, message="开发人员长度不能超过 50 个字符")
	public String getDeveloper() {
		return developer;
	}

	public void setDeveloper(String developer) {
		this.developer = developer;
	}
	
	@NotBlank(message="测试人员不能为空")
	@Length(min=0, max=50, message="测试人员长度不能超过 50 个字符")
	public String getTestStaff() {
		return testStaff;
	}

	public void setTestStaff(String testStaff) {
		this.testStaff = testStaff;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getFixedTime() {
		return fixedTime;
	}

	public void setFixedTime(Date fixedTime) {
		this.fixedTime = fixedTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@NotBlank(message="项目名称不能为空")
	@Length(min=0, max=100, message="项目名称长度不能超过 100 个字符")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@NotBlank(message="严重程度不能为空")
	@Length(min=0, max=15, message="严重程度长度不能超过 15 个字符")
	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}
	
	@NotBlank(message="影响范围不能为空")
	@Length(min=0, max=100, message="影响范围长度不能超过 100 个字符")
	public String getEffectedRange() {
		return effectedRange;
	}

	public void setEffectedRange(String effectedRange) {
		this.effectedRange = effectedRange;
	}
	
	@NotBlank(message="bug描述不能为空")
	@Length(min=0, max=1000, message="bug描述长度不能超过 1000 个字符")
	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	
	@NotBlank(message="原因分析不能为空")
	@Length(min=0, max=1000, message="原因分析长度不能超过 1000 个字符")
	public String getReasonAnalyse() {
		return reasonAnalyse;
	}

	public void setReasonAnalyse(String reasonAnalyse) {
		this.reasonAnalyse = reasonAnalyse;
	}
	
	@NotBlank(message="修复方案不能为空")
	@Length(min=0, max=1000, message="修复方案长度不能超过 1000 个字符")
	public String getFixedMethod() {
		return fixedMethod;
	}

	public void setFixedMethod(String fixedMethod) {
		this.fixedMethod = fixedMethod;
	}
	
	@Length(min=0, max=100, message="禅道任务号长度不能超过 100 个字符")
	public String getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	
	public Date getBugDate_gte() {
		return sqlMap.getWhere().getValue("bug_date", QueryType.GTE);
	}

	public void setBugDate_gte(Date bugDate) {
		sqlMap.getWhere().and("bug_date", QueryType.GTE, bugDate);
	}
	
	public Date getBugDate_lte() {
		return sqlMap.getWhere().getValue("bug_date", QueryType.LTE);
	}

	public void setBugDate_lte(Date bugDate) {
		sqlMap.getWhere().and("bug_date", QueryType.LTE, bugDate);
	}
	
	public Date getFixedTime_gte() {
		return sqlMap.getWhere().getValue("fixed_time", QueryType.GTE);
	}

	public void setFixedTime_gte(Date fixedTime) {
		sqlMap.getWhere().and("fixed_time", QueryType.GTE, fixedTime);
	}
	
	public Date getFixedTime_lte() {
		return sqlMap.getWhere().getValue("fixed_time", QueryType.LTE);
	}

	public void setFixedTime_lte(Date fixedTime) {
		sqlMap.getWhere().and("fixed_time", QueryType.LTE, fixedTime);
	}
	
	public Date getStartTime_gte() {
		return sqlMap.getWhere().getValue("start_time", QueryType.GTE);
	}

	public void setStartTime_gte(Date startTime) {
		sqlMap.getWhere().and("start_time", QueryType.GTE, startTime);
	}
	
	public Date getStartTime_lte() {
		return sqlMap.getWhere().getValue("start_time", QueryType.LTE);
	}

	public void setStartTime_lte(Date startTime) {
		sqlMap.getWhere().and("start_time", QueryType.LTE, startTime);
	}
	
}