/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.card.entity;

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
 * 绩效卡Entity
 * @author PhilipGuan
 * @version 2018-11-20
 */
@Table(name="ach_card", alias="a", columns={
		@Column(name="card_code", attrName="cardCode", label="单据编号", isPK=true),
		@Column(name="examine_month", attrName="examineMonth", label="考核月份"),
		@Column(name="examined_staff_code", attrName="examinedStaffCode", label="被考核人"),
		@Column(name="depart_code", attrName="departCode", label="所属部门编码"),
		@Column(name="depart_name", attrName="departName", label="所属部门名称"),
		@Column(name="first_depart_code", attrName="firstDepartCode", label="一级部门编码"),
		@Column(name="first_depart_name", attrName="firstDepartName", label="一级部门名称"),
		@Column(name="post_code", attrName="postCode", label="岗位编码"),
		@Column(name="had_follower", attrName="hadFollower", label="是否有下属"),
		@Column(name="assessor_code", attrName="assessorCode", label="考核人"),
		@Column(name="examine_weight", attrName="examineWeight", label="考核权重"),
		@Column(name="examine_score", attrName="examineScore", label="考核得分"),
		@Column(name="second_superior_score", attrName="secondSuperiorScore", label="上上级调整分值"),
		@Column(name="final_score", attrName="finalScore", label="最终得分"),
		@Column(name="strong_point", attrName="strongPoint", label="优点"),
		@Column(name="weak_point", attrName="weakPoint", label="弱点"),
		@Column(name="feedback_type", attrName="feedbackType", label="绩效反馈形式"),
		@Column(name="feedback_remark", attrName="feedbackRemark", label="绩效反馈备注"),
		@Column(name="feedback_minutes", attrName="feedbackMinutes", label="绩效沟通分钟数"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="audit_by", attrName="auditBy", label="审核人"),
		@Column(name="audit_date", attrName="auditDate", label="审核时间"),
		@Column(name="second_superior_remark", attrName="secondSuperiorRemark", label="上上级调整备注"),
		@Column(name="hr_score", attrName="hrScore", label="HR调整分数"),
		@Column(name="hr_score_remark", attrName="hrScoreRemark", label="HR调整分数备注"),
		@Column(name="self_doing_well", attrName="selfDoingWell", label="自评优点"),
		@Column(name="self_can_be_better", attrName="selfCanBeBetter", label="自评缺点"),
		@Column(name="data_status", attrName="dataStatus", label="数据状态"),
		@Column(name="second_superior_code", attrName="secondSuperiorCode", label="上上级编号"),
		@Column(name="second_superior_name", attrName="secondSuperiorName", label="上上级姓名", queryType=QueryType.LIKE),
		@Column(name="interview_summary", attrName="interviewSummary", label="面谈总结"),
		@Column(name="interview_instruction", attrName="interviewInstruction", label="面谈说明"),
}, orderBy="a.update_date DESC"
)
public class AchCard extends DataEntity<AchCard> {

	private static final long serialVersionUID = 1L;
	private String cardCode;		// 单据编号
	private String examineMonth;		// 考核月份
	private String examinedStaffCode;		// 被考核人
	private String departCode;		// 所属部门编码
	private String departName;		// 所属部门名称
	private String firstDepartCode;		// 一级部门编码
	private String firstDepartName;		// 一级部门名称
	private String postCode;		// 岗位编码
	private String hadFollower;		// 是否有下属
	private String assessorCode;		// 考核人
	private Double examineWeight;		// 考核权重
	private Double examineScore;		// 考核得分
	private Double secondSuperiorScore;		// 上上级调整分值
	private Double finalScore;		// 最终得分
	private String strongPoint;		// 优点
	private String weakPoint;		// 弱点
	private String feedbackType;		// 绩效反馈形式
	private String feedbackRemark;		// 绩效反馈备注
	private Integer feedbackMinutes;		// 绩效沟通分钟数
	private String auditBy;		// 审核人
	private Date auditDate;		// 审核时间
	private String secondSuperiorRemark;		// 上上级调整备注
	private Double hrScore;		// HR调整分数
	private String hrScoreRemark;		// HR调整分数备注
	private String selfDoingWell;		// 自评优点
	private String selfCanBeBetter;		// 自评缺点
	private String dataStatus;		// 数据状态
	private String secondSuperiorCode;		// 上上级编号
	private String secondSuperiorName;		// 上上级姓名
	private String interviewSummary;		// 面谈总结
	private String interviewInstruction;		// 面谈说明

	private String updateBy;
	@Override
	public String getUpdateBy() { return updateBy; }
	@Override
	public void setUpdateBy(String updateBy) { this.updateBy = updateBy; }


	public AchCard() {
		this(null);
	}

	public AchCard(String id){
		super(id);
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	@NotBlank(message="考核月份不能为空")
	@Length(min=0, max=8, message="考核月份长度不能超过 8 个字符")
	public String getExamineMonth() {
		return examineMonth;
	}

	public void setExamineMonth(String examineMonth) {
		this.examineMonth = examineMonth;
	}

	@NotBlank(message="被考核人不能为空")
	@Length(min=0, max=64, message="被考核人长度不能超过 64 个字符")
	public String getExaminedStaffCode() {
		return examinedStaffCode;
	}

	public void setExaminedStaffCode(String examinedStaffCode) {
		this.examinedStaffCode = examinedStaffCode;
	}

	@NotBlank(message="所属部门编码不能为空")
	@Length(min=0, max=64, message="所属部门编码长度不能超过 64 个字符")
	public String getDepartCode() {
		return departCode;
	}

	public void setDepartCode(String departCode) {
		this.departCode = departCode;
	}

	@NotBlank(message="岗位编码不能为空")
	@Length(min=0, max=64, message="岗位编码长度不能超过 64 个字符")
	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	@NotBlank(message="是否有下属不能为空")
	@Length(min=0, max=1, message="是否有下属长度不能超过 1 个字符")
	public String getHadFollower() {
		return hadFollower;
	}

	public void setHadFollower(String hadFollower) {
		this.hadFollower = hadFollower;
	}

	@NotBlank(message="考核人不能为空")
	@Length(min=0, max=64, message="考核人长度不能超过 64 个字符")
	public String getAssessorCode() {
		return assessorCode;
	}

	public void setAssessorCode(String assessorCode) {
		this.assessorCode = assessorCode;
	}

	public Double getExamineWeight() {
		return examineWeight;
	}

	public void setExamineWeight(Double examineWeight) {
		this.examineWeight = examineWeight;
	}

	public Double getExamineScore() {
		return examineScore;
	}

	public void setExamineScore(Double examineScore) {
		this.examineScore = examineScore;
	}

	public Double getSecondSuperiorScore() {
		return secondSuperiorScore;
	}

	public void setSecondSuperiorScore(Double secondSuperiorScore) {
		this.secondSuperiorScore = secondSuperiorScore;
	}

	public Double getFinalScore() {
		return finalScore;
	}

	public void setFinalScore(Double finalScore) {
		this.finalScore = finalScore;
	}

	@Length(min=0, max=2000, message="优点长度不能超过 2000 个字符")
	public String getStrongPoint() {
		return strongPoint;
	}

	public void setStrongPoint(String strongPoint) {
		this.strongPoint = strongPoint;
	}

	@Length(min=0, max=64, message="弱点长度不能超过 64 个字符")
	public String getWeakPoint() {
		return weakPoint;
	}

	public void setWeakPoint(String weakPoint) {
		this.weakPoint = weakPoint;
	}

	@Length(min=0, max=1, message="绩效反馈形式长度不能超过 1 个字符")
	public String getFeedbackType() {
		return feedbackType;
	}

	public void setFeedbackType(String feedbackType) {
		this.feedbackType = feedbackType;
	}

	@Length(min=0, max=2000, message="绩效反馈备注长度不能超过 2000 个字符")
	public String getFeedbackRemark() {
		return feedbackRemark;
	}

	public void setFeedbackRemark(String feedbackRemark) {
		this.feedbackRemark = feedbackRemark;
	}

	public Integer getFeedbackMinutes() {
		return feedbackMinutes;
	}

	public void setFeedbackMinutes(Integer feedbackMinutes) {
		this.feedbackMinutes = feedbackMinutes;
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


	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public String getFirstDepartCode() {
		return firstDepartCode;
	}

	public void setFirstDepartCode(String firstDepartCode) {
		this.firstDepartCode = firstDepartCode;
	}

	public String getFirstDepartName() {
		return firstDepartName;
	}

	public void setFirstDepartName(String firstDepartName) {
		this.firstDepartName = firstDepartName;
	}



	@Length(min=0, max=2000, message="上上级调整备注长度不能超过 2000 个字符")
	public String getSecondSuperiorRemark() {
		return secondSuperiorRemark;
	}

	public void setSecondSuperiorRemark(String secondSuperiorRemark) {
		this.secondSuperiorRemark = secondSuperiorRemark;
	}

	public Double getHrScore() {
		return hrScore;
	}

	public void setHrScore(Double hrScore) {
		this.hrScore = hrScore;
	}

	@Length(min=0, max=2000, message="HR调整分数备注长度不能超过 2000 个字符")
	public String getHrScoreRemark() {
		return hrScoreRemark;
	}

	public void setHrScoreRemark(String hrScoreRemark) {
		this.hrScoreRemark = hrScoreRemark;
	}

	@Length(min=0, max=2000, message="自评优点长度不能超过 2000 个字符")
	public String getSelfDoingWell() {
		return selfDoingWell;
	}

	public void setSelfDoingWell(String selfDoingWell) {
		this.selfDoingWell = selfDoingWell;
	}

	@Length(min=0, max=2000, message="自评缺点长度不能超过 2000 个字符")
	public String getSelfCanBeBetter() {
		return selfCanBeBetter;
	}

	public void setSelfCanBeBetter(String selfCanBeBetter) {
		this.selfCanBeBetter = selfCanBeBetter;
	}

	@Length(min=0, max=3, message="数据状态长度不能超过 3 个字符")
	public String getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}

	@Length(min=0, max=64, message="上上级编号长度不能超过 64 个字符")
	public String getSecondSuperiorCode() {
		return secondSuperiorCode;
	}

	public void setSecondSuperiorCode(String secondSuperiorCode) {
		this.secondSuperiorCode = secondSuperiorCode;
	}

	@Length(min=0, max=500, message="上上级姓名长度不能超过 500 个字符")
	public String getSecondSuperiorName() {
		return secondSuperiorName;
	}

	public void setSecondSuperiorName(String secondSuperiorName) {
		this.secondSuperiorName = secondSuperiorName;
	}

	@Length(min=0, max=500, message="面谈总结长度不能超过 500 个字符")
	public String getInterviewSummary() {
		return interviewSummary;
	}

	public void setInterviewSummary(String interviewSummary) {
		this.interviewSummary = interviewSummary;
	}

	@Length(min=0, max=500, message="面谈说明长度不能超过 500 个字符")
	public String getInterviewInstruction() {
		return interviewInstruction;
	}

	public void setInterviewInstruction(String interviewInstruction) {
		this.interviewInstruction = interviewInstruction;
	}
}