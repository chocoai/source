/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.fzgolddistribution.entity;

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
 * 分配梵砖Entity
 * @author dwh
 * @version 2018-09-21
 */
@Table(name="fz_gold_distribution", alias="a", columns={
		@Column(name="document_code", attrName="documentCode", label="分配单号", isPK=true),
		@Column(name="number", attrName="number", label="数量"),
		@Column(name="distribution_type", attrName="distributionType", label="分配类型"),
		@Column(name="document_status", attrName="documentStatus", label="单据状态"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="review_user", attrName="reviewUser", label="审核人"),
		@Column(name="review_date", attrName="reviewDate", label="审核时间"),
		@Column(name="user_ids", attrName="userIds", label="用户集合"),
		@Column(name="department_ids", attrName="departmentId", label="部门集合"),
		@Column(name="fz_type", attrName="fzType", label="梵赞类型"),

	}, orderBy="a.update_date DESC"
)
public class FzGoldDistribution extends DataEntity<FzGoldDistribution> {
	
	private static final long serialVersionUID = 1L;
	private String documentCode;		// 分配单号
	private Long number;		// 数量
	private String distributionType;		// 分配类型
	private String documentStatus;		// 单据状态
	private String reviewUser;		// 审核人
	private Date reviewDate;		// 审核时间
	private String userIds;         //用户Id集合
	private  String departmentId;  //部门id
	private String departmentName;     //前端显示部门名字，逗号分割的字符串
	private String userName;     //前端显示用户名字，逗号分割的字符串
	private String fzType;		// 梵赞类型 0:部门内梵赞,1:跨部门梵赞

	public String getFzType() {
		return fzType;
	}

	public void setFzType(String fzType) {
		this.fzType = fzType;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public FzGoldDistribution() {
		this(null);
	}

	public FzGoldDistribution(String id){
		super(id);
	}
	
	public String getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}
	
	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}
	
	@NotBlank(message="分配类型不能为空")
	@Length(min=0, max=1, message="分配类型长度不能超过 1 个字符")
	public String getDistributionType() {
		return distributionType;
	}

	public void setDistributionType(String distributionType) {
		this.distributionType = distributionType;
	}
	
	@Length(min=0, max=255, message="单据状态长度不能超过 255 个字符")
	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}
	
	@Length(min=0, max=255, message="审核人长度不能超过 255 个字符")
	public String getReviewUser() {
		return reviewUser;
	}

	public void setReviewUser(String reviewUser) {
		this.reviewUser = reviewUser;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}
	
}