/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.card.entity;

import com.jeesite.modules.achievement.target.entity.AchTarget;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

/**
 * 绩效卡查询数据
 * @author PhilipGuan
 * @version 2018-11-20
 */
public class AchCardData {

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
	private String extattr;		//扩展属性
	private String chineseName;		//中文名称
	private String interviewSummary;		// 面谈总结
	private String interviewInstruction;		// 面谈说明

	private String status;		// 状态,0=已创建，1=已保存，2=已提交，3=上级已审核，4=HR已审核，5=已结束

	private String remark;


	private String name;		// 姓名
	private String assessorName;		// 主考人

	private String avatar;		//头像

	private List<AchTarget> targets;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAssessorName() {
		return assessorName;
	}

	public void setAssessorName(String assessorName) {
		this.assessorName = assessorName;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	public String getExamineMonth() {
		return examineMonth;
	}

	public void setExamineMonth(String examineMonth) {
		this.examineMonth = examineMonth;
	}
	public String getExaminedStaffCode() {
		return examinedStaffCode;
	}

	public void setExaminedStaffCode(String examinedStaffCode) {
		this.examinedStaffCode = examinedStaffCode;
	}
	public String getDepartCode() {
		return departCode;
	}

	public void setDepartCode(String departCode) {
		this.departCode = departCode;
	}
	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getHadFollower() {
		return hadFollower;
	}

	public void setHadFollower(String hadFollower) {
		this.hadFollower = hadFollower;
	}
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

	public String getStrongPoint() {
		return strongPoint;
	}

	public void setStrongPoint(String strongPoint) {
		this.strongPoint = strongPoint;
	}

	public String getWeakPoint() {
		return weakPoint;
	}

	public void setWeakPoint(String weakPoint) {
		this.weakPoint = weakPoint;
	}

	public String getFeedbackType() {
		return feedbackType;
	}

	public void setFeedbackType(String feedbackType) {
		this.feedbackType = feedbackType;
	}

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

	public String getAuditBy() {
		return auditBy;
	}

	public void setAuditBy(String auditBy) {
		this.auditBy = auditBy;
	}

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

	public List<AchTarget> getTargets() {
		return targets;
	}

	public void setTargets(List<AchTarget> targets) {
		this.targets = targets;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
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

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getExtattr() {
		return extattr;
	}

	public void setExtattr(String extattr) {
		this.extattr = extattr;
	}@Length(min=0, max=500, message="面谈总结长度不能超过 500 个字符")
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