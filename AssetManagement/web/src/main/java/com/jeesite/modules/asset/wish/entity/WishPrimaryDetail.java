/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.wish.entity;

import com.jeesite.common.utils.excel.annotation.ExcelField;
import com.jeesite.common.utils.excel.annotation.ExcelFields;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import javax.validation.Valid;
import java.util.Date;

/**
 * 初选提名记录表Entity
 * @author len
 * @version 2018-11-20
 */
@Table(name="${_prefix}wish_primary_detail", alias="a", columns={
		@Column(name="detail_code", attrName="detailCode", label="明细编码", isPK=true),
		@Column(name="user_id", attrName="userId.userId", label="用户名"),
		@Column(name="voters_user_id", attrName="votersUserId", label="被提名用户id"),
		@Column(name="user_name", attrName="userName", label="被提名人"),
		@Column(name="job_number", attrName="jobNumber", label="工号"),
		@Column(name="position", attrName="position", label="岗位"),
		@Column(name="department", attrName="department", label="部门"),
		@Column(name="department_id", attrName="departmentId", label="部门id"),
		@Column(name="reason", attrName="reason", label="提名理由"),
		@Column(name="create_time", attrName="createTime", label="创建时间"),
		@Column(name="avatar", attrName="avatar", label="头像"),
	}, orderBy="a.detail_code ASC"
)
public class WishPrimaryDetail extends DataEntity<WishPrimaryDetail> {
	@Valid
	@ExcelFields({
			@ExcelField(title="被提名用户id", attrName="votersUserId", align=ExcelField.Align.CENTER,sort=10),
			@ExcelField(title="被提名人", attrName="userName", align=ExcelField.Align.CENTER, sort=15),
			@ExcelField(title="工号", attrName="jobNumber",align = ExcelField.Align.CENTER, sort=30),
			@ExcelField(title="岗位", attrName="position",align = ExcelField.Align.CENTER, sort=30),
			@ExcelField(title="部门id", attrName="departmentId",align = ExcelField.Align.CENTER, sort=30),
			@ExcelField(title="部门 ", attrName="department",align = ExcelField.Align.CENTER, sort=45),
			@ExcelField(title="提名次数 ", attrName="nominateNum",align = ExcelField.Align.CENTER, sort=45),
	})

	private static final long serialVersionUID = 1L;
	private String detailCode;		// 明细编码
	private WishPrimary userId;		// 用户名 父类
	private String votersUserId;		// 被提名人id
	private String userName;		// 被提名人
	private String jobNumber;		// 工号
	private String position;		// 岗位
	private String department;		// 部门
	private String departmentId;		// 部门id
	private String reason;		// 提名理由
	private String userid;		// 用于sql插入参数
	private Date createTime;	// 创建时间
	private String avatar;		// 头像
	private Integer nominateNum;		// 提名次数

	public Integer getNominateNum() {
		return nominateNum;
	}

	public void setNominateNum(Integer nominateNum) {
		this.nominateNum = nominateNum;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getVotersUserId() {
		return votersUserId;
	}

	public void setVotersUserId(String votersUserId) {
		this.votersUserId = votersUserId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public WishPrimaryDetail() {
		this(null);
	}


	public WishPrimaryDetail(WishPrimary userId){
		this.userId = userId;
	}
	
	public String getDetailCode() {
		return detailCode;
	}

	public void setDetailCode(String detailCode) {
		this.detailCode = detailCode;
	}
	
	public WishPrimary getUserId() {
		return userId;
	}

	public void setUserId(WishPrimary userId) {
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
	
	@Length(min=0, max=64, message="部门长度不能超过 64 个字符")
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	
	@Length(min=0, max=1000, message="提名理由长度不能超过 1000 个字符")
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
}