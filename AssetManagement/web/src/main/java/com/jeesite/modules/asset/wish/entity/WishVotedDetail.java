/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.wish.entity;

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
 * 入围名单Entity
 * @author len
 * @version 2018-11-20
 */
@Table(name="${_prefix}wish_voted_detail", alias="a", columns={
		@Column(name="detail_code", attrName="detailCode", label="明细编码", isPK=true),
		@Column(name="user_id", attrName="userId.userId", label="用户id"),
		@Column(name="voters_user_id", attrName="votersUserId", label="投票人Id"),
		@Column(name="user_name", attrName="userName", label="被提名人", queryType=QueryType.LIKE),
		@Column(name="job_number", attrName="jobNumber", label="工号"),
		@Column(name="position", attrName="position", label="岗位"),
		@Column(name="department_id", attrName="departmentId", label="部门id"),
		@Column(name="department", attrName="department", label="部门"),
		@Column(name="voted_time", attrName="votedTime", label="投票时间"),
		@Column(name="avatar", attrName="avatar", label="头像"),
	}, orderBy="a.detail_code ASC"
)
public class WishVotedDetail extends DataEntity<WishVotedDetail> {
	
	private static final long serialVersionUID = 1L;
	private String detailCode;		// 明细编码
	private WishShortlist userId;		// 用户id 父类
	private String userName;		// 被提名人
	private String jobNumber;		// 工号
	private String position;		// 岗位
	private String departmentId;		// 部门id
	private String department;		// 部门
	private Date votedTime;		// 投票时间
	private String votersUserId;	// 投票人Id
	private String avatar;		// 头像

	public String getVotersUserId() {
		return votersUserId;
	}

	public void setVotersUserId(String votersUserId) {
		this.votersUserId = votersUserId;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public WishVotedDetail() {
		this(null);
	}


	public WishVotedDetail(WishShortlist userId){
		this.userId = userId;
	}
	
	public String getDetailCode() {
		return detailCode;
	}

	public void setDetailCode(String detailCode) {
		this.detailCode = detailCode;
	}
	
	public WishShortlist getUserId() {
		return userId;
	}

	public void setUserId(WishShortlist userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=64, message="被提名人长度不能超过 64 个字符")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Length(min=0, max=10, message="工号长度不能超过 10 个字符")
	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}
	
	@Length(min=0, max=64, message="岗位长度不能超过 64 个字符")
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	
	@Length(min=0, max=64, message="部门长度不能超过 64 个字符")
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getVotedTime() {
		return votedTime;
	}

	public void setVotedTime(Date votedTime) {
		this.votedTime = votedTime;
	}
	
}