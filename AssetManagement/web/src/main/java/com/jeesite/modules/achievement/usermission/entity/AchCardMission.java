/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.usermission.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 绩效卡关键任务Entity
 * @author PhilipGuan
 * @version 2018-11-21
 */
@Table(name="ach_card_mission", alias="a", columns={
		@Column(name="mission_code", attrName="missionCode", label="任务编号", isPK=true),
		@Column(name="card_code", attrName="cardCode", label="绩效卡编号"),
		@Column(name="user_id", attrName="userId", label="用户ID"),
		@Column(name="mission_name", attrName="missionName", label="任务名称", queryType=QueryType.LIKE),
		@Column(name="mission_type", attrName="missionType", label="任务类型"),
		@Column(name="standard_score", attrName="standardScore", label="标准得分"),
		@Column(name="goal", attrName="goal", label="任务目标"),
		@Column(name="description", attrName="description", label="任务描述"),
		@Column(name="examine_standard", attrName="examineStandard", label="考核标准"),
		@Column(name="mission_situation", attrName="missionSituation", label="任务情况"),
		@Column(name="reson", attrName="reson", label="原因说明"),
		@Column(name="evaluate_reson", attrName="evaluateReson", label="评级/加扣分理由"),
		@Column(name="self_evaluation", attrName="selfEvaluation", label="自评级别/加扣分"),
		@Column(name="self_evaluation_score", attrName="selfEvaluationScore", label="自评得分"),
		@Column(name="self_evaluation_remark", attrName="selfEvaluationRemark", label="自评备注"),
		@Column(name="examine_evaluation", attrName="examineEvaluation", label="考核级别/加扣分"),
		@Column(name="examine_evaluation_score", attrName="examineEvaluationScore", label="考核得分"),
		@Column(name="examine_evaluation_remark", attrName="examineEvaluationRemark", label="主考备注"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="audit_by", attrName="auditBy", label="审核人"),
		@Column(name="audit_date", attrName="auditDate", label="审核时间"),
		@Column(name="score_group", attrName="scoreGroup", label="所属评分组"),
		@Column(name="data_status", attrName="dataStatus", label="数据状态"),
}, orderBy=" a.create_date DESC"
)
public class AchCardMission extends DataEntity<AchCardMission> {

	private static final long serialVersionUID = 1L;
	private String missionCode;		// 任务编号
	private String cardCode;		// 绩效卡编号
	private String userId;		// 用户ID
	private String missionName;		// 任务名称
	private String missionType;		// 任务类型
	private Double standardScore;		// 标准得分
	private String goal;		// 任务目标
	private String description;		// 任务描述
	private String examineStandard;		// 考核标准
	private String missionSituation;		// 任务情况
	private String reson;		// 原因说明
	private String evaluateReson;		// 评级/加扣分理由
	private String selfEvaluation;		// 自评级别/加扣分
	private Double selfEvaluationScore;		// 自评得分
	private String selfEvaluationRemark;		// 自评备注
	private String examineEvaluation;		// 考核级别/加扣分
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

	public AchCardMission() {
		this(null);
	}

	public AchCardMission(String id){
		super(id);
	}

	public String getMissionCode() {
		return missionCode;
	}

	public void setMissionCode(String missionCode) {
		this.missionCode = missionCode;
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

	@Length(min=0, max=500, message="任务名称长度不能超过 500 个字符")
	public String getMissionName() {
		return missionName;
	}

	public void setMissionName(String missionName) {
		this.missionName = missionName;
	}

	@Length(min=0, max=10, message="任务类型长度不能超过 10 个字符")
	public String getMissionType() {
		return missionType;
	}

	public void setMissionType(String missionType) {
		this.missionType = missionType;
	}

	public Double getStandardScore() {
		return standardScore;
	}

	public void setStandardScore(Double standardScore) {
		this.standardScore = standardScore;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	@Length(min=0, max=2000, message="任务描述长度不能超过 2000 个字符")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Length(min=0, max=2000, message="考核标准长度不能超过 2000 个字符")
	public String getExamineStandard() {
		return examineStandard;
	}

	public void setExamineStandard(String examineStandard) {
		this.examineStandard = examineStandard;
	}

	@Length(min=0, max=64, message="任务情况长度不能超过 64 个字符")
	public String getMissionSituation() {
		return missionSituation;
	}

	public void setMissionSituation(String missionSituation) {
		this.missionSituation = missionSituation;
	}

	@Length(min=0, max=2000, message="原因说明长度不能超过 2000 个字符")
	public String getReson() {
		return reson;
	}

	public void setReson(String reson) {
		this.reson = reson;
	}

	@Length(min=0, max=2000, message="评级/加扣分理由长度不能超过 2000 个字符")
	public String getEvaluateReson() {
		return evaluateReson;
	}

	public void setEvaluateReson(String evaluateReson) {
		this.evaluateReson = evaluateReson;
	}

	@Length(min=0, max=64, message="自评级别/加扣分长度不能超过 64 个字符")
	public String getSelfEvaluation() {
		return selfEvaluation;
	}

	public void setSelfEvaluation(String selfEvaluation) {
		this.selfEvaluation = selfEvaluation;
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

	@Length(min=0, max=64, message="考核级别/加扣分长度不能超过 64 个字符")
	public String getExamineEvaluation() {
		return examineEvaluation;
	}

	public void setExamineEvaluation(String examineEvaluation) {
		this.examineEvaluation = examineEvaluation;
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