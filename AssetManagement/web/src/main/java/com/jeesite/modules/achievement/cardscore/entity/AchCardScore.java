/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.cardscore.entity;

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
 * 绩效卡评分Entity
 * @author Philip Guan
 * @version 2018-11-24
 */
@Table(name="ach_card_score", alias="a", columns={
		@Column(name="card_score_code", attrName="cardScoreCode", label="评分编号", isPK=true),
		@Column(name="card_code", attrName="cardCode", label="绩效卡编号"),
		@Column(name="user_id", attrName="userId", label="员工编码"),
		@Column(name="examine_score", attrName="examineScore", label="考核得分"),
		@Column(name="examine_name", attrName="examineName", label="绩效指标名称", queryType=QueryType.LIKE),
		@Column(name="evaluation_remark", attrName="evaluationRemark", label="自评汇总"),
		@Column(name="examine_type", attrName="examineType", label="考核类型"),
		@Column(name="remark", attrName="remark", label="自评汇总"),
		@Column(name="standard_score", attrName="standardScore", label="标准分值"),
		@Column(name="last_examine_score", attrName="lastExamineScore", label="上期得分"),
		@Column(name="examined_staff_code", attrName="examinedStaffCode", label="被考核人"),
		@Column(name="evaluation_score", attrName="evaluationScore", label="自评得分"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="audit_by", attrName="auditBy", label="审核人"),
		@Column(name="audit_date", attrName="auditDate", label="审核时间"),
		@Column(name="score_group", attrName="scoreGroup", label="所属评分组"),
		@Column(name="data_status", attrName="dataStatus", label="数据状态"),
}, orderBy="a.update_date DESC"
)
public class AchCardScore extends DataEntity<AchCardScore> {

	private static final long serialVersionUID = 1L;
	private String cardScoreCode;		// 评分编号
	private String cardCode;		// 绩效卡编号
	private String userId;		// 员工编码
	private Double examineScore;		// 考核得分
	private String examineName;		// 绩效指标名称
	private String evaluationRemark;		// 自评汇总
	private String examineType;		// 考核类型
	private String remark;		// 自评汇总
	private Double standardScore;		// 标准分值
	private Double lastExamineScore;		// 上期得分
	private String examinedStaffCode;		// 被考核人
	private Double evaluationScore;		// 自评得分
	private String auditBy;		// 审核人
	private Date auditDate;		// 审核时间
	private String scoreGroup;		// 所属评分组
	private String dataStatus;		// 数据状态

	private String updateBy;
	@Override
	public String getUpdateBy() { return updateBy; }
	@Override
	public void setUpdateBy(String updateBy) { this.updateBy = updateBy; }

	public AchCardScore() {
		this(null);
	}

	public AchCardScore(String id){
		super(id);
	}

	public String getCardScoreCode() {
		return cardScoreCode;
	}

	public void setCardScoreCode(String cardScoreCode) {
		this.cardScoreCode = cardScoreCode;
	}

	@NotBlank(message="绩效卡编号不能为空")
	@Length(min=0, max=64, message="绩效卡编号长度不能超过 64 个字符")
	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	@Length(min=0, max=64, message="员工编码长度不能超过 64 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Double getExamineScore() {
		return examineScore;
	}

	public void setExamineScore(Double examineScore) {
		this.examineScore = examineScore;
	}

	@NotBlank(message="绩效指标名称不能为空")
	@Length(min=0, max=64, message="绩效指标名称长度不能超过 64 个字符")
	public String getExamineName() {
		return examineName;
	}

	public void setExamineName(String examineName) {
		this.examineName = examineName;
	}

	@Length(min=0, max=2000, message="自评汇总长度不能超过 2000 个字符")
	public String getEvaluationRemark() {
		return evaluationRemark;
	}

	public void setEvaluationRemark(String evaluationRemark) {
		this.evaluationRemark = evaluationRemark;
	}

	@Length(min=0, max=64, message="考核类型长度不能超过 64 个字符")
	public String getExamineType() {
		return examineType;
	}

	public void setExamineType(String examineType) {
		this.examineType = examineType;
	}

	@Length(min=0, max=2000, message="自评汇总长度不能超过 2000 个字符")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getStandardScore() {
		return standardScore;
	}

	public void setStandardScore(Double standardScore) {
		this.standardScore = standardScore;
	}

	public Double getLastExamineScore() {
		return lastExamineScore;
	}

	public void setLastExamineScore(Double lastExamineScore) {
		this.lastExamineScore = lastExamineScore;
	}

	@NotBlank(message="被考核人不能为空")
	@Length(min=0, max=64, message="被考核人长度不能超过 64 个字符")
	public String getExaminedStaffCode() {
		return examinedStaffCode;
	}

	public void setExaminedStaffCode(String examinedStaffCode) {
		this.examinedStaffCode = examinedStaffCode;
	}

	public Double getEvaluationScore() {
		return evaluationScore;
	}

	public void setEvaluationScore(Double evaluationScore) {
		this.evaluationScore = evaluationScore;
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

	@Length(min=0, max=64, message="所属评分组长度不能超过 64 个字符")
	public String getScoreGroup() {
		return scoreGroup;
	}

	public void setScoreGroup(String scoreGroup) {
		this.scoreGroup = scoreGroup;
	}

	@Length(min=0, max=3, message="数据状态长度不能超过 3 个字符")
	public String getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}

}