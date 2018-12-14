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
 * 故障登记单Entity
 * @author Scarlett
 * @version 2018-07-11
 */
@Table(name="${_prefix}fault_registration", alias="a", columns={
		@Column(name="registration_code", attrName="registrationCode", label="故障编号", isPK=true),
		@Column(name="fault_date", attrName="faultDate", label="故障日期"),
		@Column(name="feedback_person", attrName="feedbackPerson", label="反馈人"),
		@Column(name="dealer", attrName="dealer", label="处理人"),
		@Column(name="fault_reason", attrName="faultReason", label="故障来源"),
		@Column(name="project_manager", attrName="projectManager", label="项目经理"),
		@Column(name="effected_produts", attrName="effectedProduts", label="影响产品", queryType=QueryType.LIKE),
		@Column(name="begin_time", attrName="beginTime", label="故障开始时间"),
		@Column(name="end_time", attrName="endTime", label="故障结束时间"),
		@Column(name="fault_description", attrName="faultDescription", label="故障描述"),
		@Column(name="liable_type", attrName="liableType", label="责任类型", queryType=QueryType.LIKE),
		@Column(name="liable_person", attrName="liablePerson", label="责任人", queryType=QueryType.LIKE),
		@Column(name="effected_range", attrName="effectedRange", label="影响范围"),
		@Column(name="fault_dimensions", attrName="faultDimensions", label="故障规模"),
		@Column(name="process_description", attrName="processDescription", label="处理过程描述"),
		@Column(name="fault_analysis", attrName="faultAnalysis", label="故障原因分析"),
		@Column(name="improvement_ways", attrName="improvementWays", label="改善方法"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class FaultRegistration extends DataEntity<FaultRegistration> {
	
	private static final long serialVersionUID = 1L;
	private String registrationCode;		// 故障编号
	private Date faultDate;		// 故障日期
	private String feedbackPerson;		// 反馈人
	private String dealer;		// 处理人
	private String faultReason;		// 故障来源
	private String projectManager;		// 项目经理
	private String effectedProduts;		// 影响产品
	private Date beginTime;		// 故障开始时间
	private Date endTime;		// 故障结束时间
	private String faultDescription;		// 故障描述
	private String liableType;		// 责任类型
	private String liablePerson;		// 责任人
	private String effectedRange;		// 影响范围
	private String faultDimensions;		// 故障规模
	private String processDescription;		// 处理过程描述
	private String faultAnalysis;		// 故障原因分析
	private String improvementWays;		// 改善方法
	
	public FaultRegistration() {
		this(null);
	}

	public FaultRegistration(String id){
		super(id);
	}
	
	public String getRegistrationCode() {
		return registrationCode;
	}

	public void setRegistrationCode(String registrationCode) {
		this.registrationCode = registrationCode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message="故障日期不能为空")
	public Date getFaultDate() {
		return faultDate;
	}

	public void setFaultDate(Date faultDate) {
		this.faultDate = faultDate;
	}
	
	@NotBlank(message="反馈人不能为空")
	@Length(min=0, max=25, message="反馈人长度不能超过 25 个字符")
	public String getFeedbackPerson() {
		return feedbackPerson;
	}

	public void setFeedbackPerson(String feedbackPerson) {
		this.feedbackPerson = feedbackPerson;
	}
	
	@NotBlank(message="处理人不能为空")
	@Length(min=0, max=25, message="处理人长度不能超过 25 个字符")
	public String getDealer() {
		return dealer;
	}

	public void setDealer(String dealer) {
		this.dealer = dealer;
	}
	
	@Length(min=0, max=100, message="故障来源长度不能超过 100 个字符")
	public String getFaultReason() {
		return faultReason;
	}

	public void setFaultReason(String faultReason) {
		this.faultReason = faultReason;
	}
	
	@NotBlank(message="项目经理不能为空")
	@Length(min=0, max=50, message="项目经理长度不能超过 50 个字符")
	public String getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}
	
	@Length(min=0, max=100, message="影响产品长度不能超过 100 个字符")
	public String getEffectedProduts() {
		return effectedProduts;
	}

	public void setEffectedProduts(String effectedProduts) {
		this.effectedProduts = effectedProduts;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="故障开始时间不能为空")
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="故障结束时间不能为空")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@NotBlank(message="故障描述不能为空")
	@Length(min=0, max=1000, message="故障描述长度不能超过 1000 个字符")
	public String getFaultDescription() {
		return faultDescription;
	}

	public void setFaultDescription(String faultDescription) {
		this.faultDescription = faultDescription;
	}
	
	@NotBlank(message="责任类型不能为空")
	@Length(min=0, max=15, message="责任类型长度不能超过 15 个字符")
	public String getLiableType() {
		return liableType;
	}

	public void setLiableType(String liableType) {
		this.liableType = liableType;
	}
	
	@NotBlank(message="责任人不能为空")
	@Length(min=0, max=15, message="责任人长度不能超过 15 个字符")
	public String getLiablePerson() {
		return liablePerson;
	}

	public void setLiablePerson(String liablePerson) {
		this.liablePerson = liablePerson;
	}
	
	@Length(min=0, max=100, message="影响范围长度不能超过 100 个字符")
	public String getEffectedRange() {
		return effectedRange;
	}

	public void setEffectedRange(String effectedRange) {
		this.effectedRange = effectedRange;
	}
	
	@Length(min=0, max=15, message="故障规模长度不能超过 15 个字符")
	public String getFaultDimensions() {
		return faultDimensions;
	}

	public void setFaultDimensions(String faultDimensions) {
		this.faultDimensions = faultDimensions;
	}
	
	@NotBlank(message="处理过程描述不能为空")
	@Length(min=0, max=1000, message="处理过程描述长度不能超过 1000 个字符")
	public String getProcessDescription() {
		return processDescription;
	}

	public void setProcessDescription(String processDescription) {
		this.processDescription = processDescription;
	}
	
	@NotBlank(message="故障原因分析不能为空")
	@Length(min=0, max=1000, message="故障原因分析长度不能超过 1000 个字符")
	public String getFaultAnalysis() {
		return faultAnalysis;
	}

	public void setFaultAnalysis(String faultAnalysis) {
		this.faultAnalysis = faultAnalysis;
	}
	
	@NotBlank(message="改善方法不能为空")
	@Length(min=0, max=1000, message="改善方法长度不能超过 1000 个字符")
	public String getImprovementWays() {
		return improvementWays;
	}

	public void setImprovementWays(String improvementWays) {
		this.improvementWays = improvementWays;
	}
	
	public Date getFaultDate_gte() {
		return sqlMap.getWhere().getValue("fault_date", QueryType.GTE);
	}

	public void setFaultDate_gte(Date faultDate) {
		sqlMap.getWhere().and("fault_date", QueryType.GTE, faultDate);
	}
	
	public Date getFaultDate_lte() {
		return sqlMap.getWhere().getValue("fault_date", QueryType.LTE);
	}

	public void setFaultDate_lte(Date faultDate) {
		sqlMap.getWhere().and("fault_date", QueryType.LTE, faultDate);
	}
	
}