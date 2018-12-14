/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.usertarget.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 绩效指标管理Entity
 * @author PhilipGuan
 * @version 2018-11-26
 */
@Table(name="ach_user_target", alias="a", columns={
		@Column(name="user_target_code", attrName="userTargetCode", label="用户指标编号", isPK=true),
		@Column(name="card_code", attrName="cardCode", label="单据编号"),
		@Column(name="user_id", attrName="userId", label="用户ID"),
		@Column(name="target_code", attrName="targetCode", label="指标编号"),
		@Column(name="target_name", attrName="targetName", label="指标名称", queryType=QueryType.LIKE),
		@Column(name="target_level", attrName="targetLevel", label="指标级别"),
		@Column(name="target_type", attrName="targetType", label="指标类型"),
		@Column(name="standard_score", attrName="standardScore", label="标准分数"),
		@Column(name="description", attrName="description", label="指标内容描述"),
		@Column(name="formula", attrName="formula", label="统计公式"),
		@Column(name="coefficient_range", attrName="coefficientRange", label="系数范围"),
		@Column(name="bottom", attrName="bottom", label="底线值"),
		@Column(name="basics_down", attrName="basicsDown", label="基础下行"),
		@Column(name="basic_aims", attrName="basicAims", label="基础目标"),
		@Column(name="basics_upstream", attrName="basicsUpstream", label="基础上行"),
		@Column(name="challenge_goal", attrName="challengeGoal", label="挑战目标"),
		@Column(name="target_setting_content", attrName="targetSettingContent", label="目标设定备注"),
		@Column(name="data_sources", attrName="dataSources", label="数据来源/计算过程"),
		@Column(name="score_coefficient", attrName="scoreCoefficient", label="得分系数"),
		@Column(name="relevance", attrName="relevance", label="相关性"),
		@Column(name="actual_score", attrName="actualScore", label="本期实际分数"),
		@Column(name="examined_staff_remark", attrName="examinedStaffRemark", label="被考核人备注"),
		@Column(name="assessor_remark", attrName="assessorRemark", label="考核人备注"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="last_score", attrName="lastScore", label="上一期得分"),
		@Column(name="last_second_score", attrName="lastSecondScore", label="上两期得分"),
		@Column(name="audit_by", attrName="auditBy", label="审核人"),
		@Column(name="last_third_score", attrName="lastThirdScore", label="上三期得分"),
		@Column(name="audit_date", attrName="auditDate", label="审核时间"),
		@Column(name="score_group", attrName="scoreGroup", label="所属评分组"),
		@Column(name="data_status", attrName="dataStatus", label="数据状态"),
		@Column(name="is_achievement", attrName="isAchievement", label="是否业绩指标", comment="是否业绩指标：0：默认否；1：是"),
}, orderBy="a.create_date DESC"
)
public class AchUserTarget extends DataEntity<AchUserTarget> {

	private static final long serialVersionUID = 1L;
	private String userTargetCode;		// 用户指标编号
	private String cardCode;		// 单据编号
	private String userId;		// 用户ID
	private String targetCode;		// 指标编号
	private String targetName;		// 指标名称
	private String targetLevel;		// 指标级别
	private String targetType;		// 指标类型
	private Float standardScore;		// 标准分数
	private String description;		// 指标内容描述
	private String formula;		// 统计公式
	private String coefficientRange;		// 系数范围
	private Float bottom;		// 底线值
	private Float basicsDown;		// 基础下行
	private Float basicAims;		// 基础目标
	private Float basicsUpstream;		// 基础上行
	private Float challengeGoal;		// 挑战目标
	private String targetSettingContent;		// 目标设定备注
	private String dataSources;		// 数据来源/计算过程
	private Float scoreCoefficient;		// 得分系数
	private String relevance;		// 相关性
	private Float actualScore;		// 本期实际分数
	private String examinedStaffRemark;		// 被考核人备注
	private String assessorRemark;		// 考核人备注
	private Float lastScore;		// 上一期得分
	private Float lastSecondScore;		// 上两期得分
	private String auditBy;		// 审核人
	private Float lastThirdScore;		// 上三期得分
	private Date auditDate;		// 审核时间
	private String scoreGroup;		// 所属评分组
	private String dataStatus;		// 数据状态
	private String isAchievement;		// 是否业绩指标：0：默认否；1：是

	private String updateBy;
	@Override
	public String getUpdateBy() { return updateBy; }
	@Override
	public void setUpdateBy(String updateBy) { this.updateBy = updateBy; }

	public AchUserTarget() {
		this(null);
	}

	public AchUserTarget(String id){
		super(id);
	}

	public String getUserTargetCode() {
		return userTargetCode;
	}

	public void setUserTargetCode(String userTargetCode) {
		this.userTargetCode = userTargetCode;
	}

	@Length(min=0, max=64, message="单据编号长度不能超过 64 个字符")
	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	@Length(min=0, max=64, message="用户ID长度不能超过 64 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Length(min=0, max=64, message="指标编号长度不能超过 64 个字符")
	public String getTargetCode() {
		return targetCode;
	}

	public void setTargetCode(String targetCode) {
		this.targetCode = targetCode;
	}

	@Length(min=0, max=64, message="指标名称长度不能超过 64 个字符")
	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	@Length(min=0, max=1, message="指标级别长度不能超过 1 个字符")
	public String getTargetLevel() {
		return targetLevel;
	}

	public void setTargetLevel(String targetLevel) {
		this.targetLevel = targetLevel;
	}

	@Length(min=0, max=1, message="指标类型长度不能超过 1 个字符")
	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public Float getStandardScore() {
		return standardScore;
	}

	public void setStandardScore(Float standardScore) {
		this.standardScore = standardScore;
	}

	@Length(min=0, max=1000, message="指标内容描述长度不能超过 1000 个字符")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Length(min=0, max=500, message="统计公式长度不能超过 500 个字符")
	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getCoefficientRange() {
		return coefficientRange;
	}

	public void setCoefficientRange(String coefficientRange) {
		this.coefficientRange = coefficientRange;
	}

	public Float getBottom() {
		return bottom;
	}

	public void setBottom(Float bottom) {
		this.bottom = bottom;
	}

	public Float getBasicsDown() {
		return basicsDown;
	}

	public void setBasicsDown(Float basicsDown) {
		this.basicsDown = basicsDown;
	}

	public Float getBasicAims() {
		return basicAims;
	}

	public void setBasicAims(Float basicAims) {
		this.basicAims = basicAims;
	}

	public Float getBasicsUpstream() {
		return basicsUpstream;
	}

	public void setBasicsUpstream(Float basicsUpstream) {
		this.basicsUpstream = basicsUpstream;
	}

	public Float getChallengeGoal() {
		return challengeGoal;
	}

	public void setChallengeGoal(Float challengeGoal) {
		this.challengeGoal = challengeGoal;
	}

	@Length(min=0, max=1000, message="目标设定备注长度不能超过 1000 个字符")
	public String getTargetSettingContent() {
		return targetSettingContent;
	}

	public void setTargetSettingContent(String targetSettingContent) {
		this.targetSettingContent = targetSettingContent;
	}

	@Length(min=0, max=500, message="数据来源/计算过程长度不能超过 500 个字符")
	public String getDataSources() {
		return dataSources;
	}

	public void setDataSources(String dataSources) {
		this.dataSources = dataSources;
	}

	public Float getScoreCoefficient() {
		return scoreCoefficient;
	}

	public void setScoreCoefficient(Float scoreCoefficient) {
		this.scoreCoefficient = scoreCoefficient;
	}

	@Length(min=0, max=1, message="相关性长度不能超过 1 个字符")
	public String getRelevance() {
		return relevance;
	}

	public void setRelevance(String relevance) {
		this.relevance = relevance;
	}

	public Float getActualScore() {
		return actualScore;
	}

	public void setActualScore(Float actualScore) {
		this.actualScore = actualScore;
	}

	@Length(min=0, max=2000, message="被考核人备注长度不能超过 2000 个字符")
	public String getExaminedStaffRemark() {
		return examinedStaffRemark;
	}

	public void setExaminedStaffRemark(String examinedStaffRemark) {
		this.examinedStaffRemark = examinedStaffRemark;
	}

	@Length(min=0, max=2000, message="考核人备注长度不能超过 2000 个字符")
	public String getAssessorRemark() {
		return assessorRemark;
	}

	public void setAssessorRemark(String assessorRemark) {
		this.assessorRemark = assessorRemark;
	}

	public Float getLastScore() {
		return lastScore;
	}

	public void setLastScore(Float lastScore) {
		this.lastScore = lastScore;
	}

	public Float getLastSecondScore() {
		return lastSecondScore;
	}

	public void setLastSecondScore(Float lastSecondScore) {
		this.lastSecondScore = lastSecondScore;
	}

	@Length(min=0, max=64, message="审核人长度不能超过 64 个字符")
	public String getAuditBy() {
		return auditBy;
	}

	public void setAuditBy(String auditBy) {
		this.auditBy = auditBy;
	}

	public Float getLastThirdScore() {
		return lastThirdScore;
	}

	public void setLastThirdScore(Float lastThirdScore) {
		this.lastThirdScore = lastThirdScore;
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

	@Length(min=0, max=1, message="是否业绩指标长度不能超过 1 个字符")
	public String getIsAchievement() {
		return isAchievement;
	}

	public void setIsAchievement(String isAchievement) {
		this.isAchievement = isAchievement;
	}

}