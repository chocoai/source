/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.cardsynthetical.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;

/**
 * 绩效卡综合管理Entity
 * @author Philip Guan
 * @version 2018-11-22
 */
@Table(name="ach_card_synthetical", alias="a", columns={
		@Column(name="card_synthetical_code", attrName="cardSyntheticalCode", label="绩效卡综合管理编码", isPK=true),
		@Column(name="card_code", attrName="cardCode", label="绩效卡编号"),
		@Column(name="user_id", attrName="userId", label="用户ID"),
		@Column(name="synthetical_code", attrName="syntheticalCode", label="综合管理编码"),
		@Column(name="examine_type", attrName="examineType", label="考核类型"),
		@Column(name="ach_quota", attrName="achQuota", label="绩效指标"),
		@Column(name="examine_quota", attrName="examineQuota", label="考核标准"),
		@Column(name="standard_score", attrName="standardScore", label="标准分"),
		@Column(name="high_score", attrName="highScore", label="最高加分"),
		@Column(name="self_evaluation_score", attrName="selfEvaluationScore", label="自评得分"),
		@Column(name="self_evaluation_remark", attrName="selfEvaluationRemark", label="自评备注"),
		@Column(name="examine_evaluation_score", attrName="examineEvaluationScore", label="考核得分"),
		@Column(name="examine_evaluation_remark", attrName="examineEvaluationRemark", label="主考备注"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="audit_by", attrName="auditBy", label="审核人"),
		@Column(name="audit_date", attrName="auditDate", label="审核时间"),
		@Column(name="score_group", attrName="scoreGroup", label="所属评分组"),
		@Column(name="data_status", attrName="dataStatus", label="数据状态"),
}, orderBy="a.update_date DESC"
)
public class AchCardSynthetical extends DataEntity<AchCardSynthetical> {

	private static final long serialVersionUID = 1L;
	private String cardSyntheticalCode;		// 绩效卡综合管理编码
	private String cardCode;		// 绩效卡编号
	private String userId;		// 用户ID
	private String syntheticalCode;		// 综合管理编码
	private String examineType;		// 考核类型
	private String achQuota;		// 绩效指标
	private String examineQuota;		// 考核标准
	private Double standardScore;		// 标准分
	private Integer highScore;		// 最高加分
	private Double selfEvaluationScore;		// 自评得分
	private String selfEvaluationRemark;		// 自评备注
	private Double examineEvaluationScore;		// 考核得分
	private String examineEvaluationRemark;		// 主考备注
	private String auditBy;		// 审核人
	private Date auditDate;		// 审核时间
	private String scoreGroup;		// 所属评分组
	private String dataStatus;		// 数据状态

	private String updateBy;
	@Override
	public String getUpdateBy() { return updateBy; }
	@Override
	public void setUpdateBy(String updateBy) { this.updateBy = updateBy; }

	public AchCardSynthetical() {
		this(null);
	}

	public AchCardSynthetical(String id){
		super(id);
	}

	public String getCardSyntheticalCode() {
		return cardSyntheticalCode;
	}

	public void setCardSyntheticalCode(String cardSyntheticalCode) {
		this.cardSyntheticalCode = cardSyntheticalCode;
	}

	@NotBlank(message="绩效卡编号不能为空")
	@Length(min=0, max=64, message="绩效卡编号长度不能超过 64 个字符")
	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	//@NotBlank(message="用户ID不能为空")
	@Length(min=0, max=64, message="用户ID长度不能超过 64 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@NotBlank(message="综合管理编码不能为空")
	@Length(min=0, max=64, message="综合管理编码长度不能超过 64 个字符")
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

	@Length(min=0, max=1000, message="考核标准长度不能超过 1000 个字符")
	public String getExamineQuota() {
		return examineQuota;
	}

	public void setExamineQuota(String examineQuota) {
		this.examineQuota = examineQuota;
	}

	public Double getStandardScore() {
		return standardScore;
	}

	public void setStandardScore(Double standardScore) {
		this.standardScore = standardScore;
	}

	public Integer getHighScore() {
		return highScore;
	}

	public void setHighScore(Integer highScore) {
		this.highScore = highScore;
	}

	public Double getSelfEvaluationScore() {
		return selfEvaluationScore;
	}

	public void setSelfEvaluationScore(Double selfEvaluationScore) {
		this.selfEvaluationScore = selfEvaluationScore;
	}

	@Length(min=0, max=2000, message="自评备注长度不能超过 2000 个字符")
	public String getSelfEvaluationRemark() {
		return selfEvaluationRemark;
	}

	public void setSelfEvaluationRemark(String selfEvaluationRemark) {
		this.selfEvaluationRemark = selfEvaluationRemark;
	}

	public Double getExamineEvaluationScore() {
		return examineEvaluationScore;
	}

	public void setExamineEvaluationScore(Double examineEvaluationScore) {
		this.examineEvaluationScore = examineEvaluationScore;
	}

	@Length(min=0, max=2000, message="主考备注长度不能超过 2000 个字符")
	public String getExamineEvaluationRemark() {
		return examineEvaluationRemark;
	}

	public void setExamineEvaluationRemark(String examineEvaluationRemark) {
		this.examineEvaluationRemark = examineEvaluationRemark;
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