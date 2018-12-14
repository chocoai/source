/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.synthetical.entity;

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
 * 绩效综合管理Entity
 * @author len
 * @version 2018-11-16
 */
@Table(name="ach_synthetical", alias="a", columns={
		@Column(name="synthetical_code", attrName="syntheticalCode", label="综合管理编码", isPK=true),
		@Column(name="examine_type", attrName="examineType", label="考核类型"),
		@Column(name="ach_quota", attrName="achQuota", label="绩效指标", queryType=QueryType.LIKE),
		@Column(name="examine_quota", attrName="examineQuota", label="考核指标", queryType=QueryType.LIKE),
		@Column(name="manage_score", attrName="manageScore", label="管理层标准分"),
		@Column(name="no_manage_score", attrName="noManageScore", label="非管理层标准分"),
		@Column(name="manage_high_score", attrName="manageHighScore", label="管理层最高加分"),
		@Column(name="no_manage_high_score", attrName="noManageHighScore", label="非管理层最高加分"),
		@Column(name="data_type", attrName="dataType", label="数据状态"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="audit_by", attrName="auditBy", label="审核人"),
		@Column(name="audit_date", attrName="auditDate", label="审核时间"),
		@Column(name="disable_by", attrName="disableBy", label="禁用人"),
		@Column(name="disable_date", attrName="disableDate", label="禁用时间"),
		@Column(name="disable_status", attrName="disableStatus", label="禁用时间"),
		@Column(name="score_group", attrName="scoreGroup", label="所属评分组"),
	}, orderBy="a.synthetical_code DESC"
)
public class AchSynthetical extends DataEntity<AchSynthetical> {
	
	private static final long serialVersionUID = 1L;
	private String syntheticalCode;		// 综合管理编码
	private String examineType;		// 考核类型
	private String achQuota;		// 绩效指标
	private String examineQuota;		// 考核指标
	private Integer manageScore;		// 管理层标准分
	private Integer noManageScore;		// 非管理层标准分
	private Integer manageHighScore;		// 管理层最高加分
	private Integer noManageHighScore;		// 非管理层最高加分
	private String dataType;		// 数据状态
	private String auditBy;		// 审核人
	private Date auditDate;		// 审核时间
	private String disableBy;		// 禁用人
	private Date disableDate;		// 禁用时间
	private String disableStatus;	// 禁用状态
	private String dataVal;
	private String disableVal;
	private boolean isAudit;		// 是否已审核
	private String examineVal;		// 考核类型
	private String scoreGroup;		// 所属评分组

	public String getExamineVal() {
		return examineVal;
	}

	public void setExamineVal(String examineVal) {
		this.examineVal = examineVal;
	}

	public boolean isAudit() {
		return isAudit;
	}

	public void setAudit(boolean audit) {
		isAudit = audit;
	}

	public String getDisableVal() {
		return disableVal;
	}

	public void setDisableVal(String disableVal) {
		this.disableVal = disableVal;
	}

	public String getDataVal() {
		return dataVal;
	}

	public void setDataVal(String dataVal) {
		this.dataVal = dataVal;
	}

	public String getDisableStatus() {
		return disableStatus;
	}

	public void setDisableStatus(String disableStatus) {
		this.disableStatus = disableStatus;
	}

	public AchSynthetical() {
		this(null);
	}

	public AchSynthetical(String id){
		super(id);
	}
	
	public String getSyntheticalCode() {
		return syntheticalCode;
	}

	public void setSyntheticalCode(String syntheticalCode) {
		this.syntheticalCode = syntheticalCode;
	}
	
	@Length(min=0, max=1, message="考核类型长度不能超过 1 个字符")
	public String getExamineType() {
		return examineType;
	}

	public void setExamineType(String examineType) {
		this.examineType = examineType;
	}
	
	@Length(min=0, max=300, message="绩效指标长度不能超过 300 个字符")
	public String getAchQuota() {
		return achQuota;
	}

	public void setAchQuota(String achQuota) {
		this.achQuota = achQuota;
	}
	
	@Length(min=0, max=1000, message="考核指标长度不能超过 1000 个字符")
	public String getExamineQuota() {
		return examineQuota;
	}

	public void setExamineQuota(String examineQuota) {
		this.examineQuota = examineQuota;
	}
	
	public Integer getManageScore() {
		return manageScore;
	}

	public void setManageScore(Integer manageScore) {
		this.manageScore = manageScore;
	}
	
	public Integer getNoManageScore() {
		return noManageScore;
	}

	public void setNoManageScore(Integer noManageScore) {
		this.noManageScore = noManageScore;
	}
	
	public Integer getManageHighScore() {
		return manageHighScore;
	}

	public void setManageHighScore(Integer manageHighScore) {
		this.manageHighScore = manageHighScore;
	}
	
	public Integer getNoManageHighScore() {
		return noManageHighScore;
	}

	public void setNoManageHighScore(Integer noManageHighScore) {
		this.noManageHighScore = noManageHighScore;
	}
	
	@Length(min=0, max=10, message="数据状态长度不能超过 10 个字符")
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	@Length(min=0, max=64, message="审核人长度不能超过 64 个字符")
	public String getAuditBy() {
		return auditBy;
	}

	public void setAuditBy(String auditBy) {
		this.auditBy = auditBy;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	
	@Length(min=0, max=64, message="禁用人长度不能超过 64 个字符")
	public String getDisableBy() {
		return disableBy;
	}

	public void setDisableBy(String disableBy) {
		this.disableBy = disableBy;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDisableDate() {
		return disableDate;
	}

	public void setDisableDate(Date disableDate) {
		this.disableDate = disableDate;
	}

	@Length(min=0, max=64, message="所属评分组长度不能超过 64 个字符")
	public String getScoreGroup() {
		return scoreGroup;
	}

	public void setScoreGroup(String scoreGroup) {
		this.scoreGroup = scoreGroup;
	}
	
}