/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.wish.entity;

import com.jeesite.common.utils.excel.annotation.ExcelField;
import com.jeesite.common.utils.excel.annotation.ExcelFields;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import com.jeesite.common.collect.ListUtils;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import javax.validation.Valid;

/**
 * 初选提名记录表Entity
 * @author len
 * @version 2018-11-20
 */
@Table(name="${_prefix}wish_primary", alias="a", columns={
		@Column(name="user_id", attrName="userId", label="用户id", isPK=true),
		@Column(name="nominee_by", attrName="nomineeBy", label="提名人", queryType=QueryType.LIKE),
		@Column(name="job_number", attrName="jobNumber", label="工号", queryType=QueryType.LIKE),
		@Column(name="position", attrName="position", label="岗位", queryType=QueryType.LIKE),
		@Column(name="department", attrName="department", label="部门"),
		@Column(name="department_id", attrName="departmentId", label="部门id"),
		@Column(name="create_time", attrName="createTime", label="提名时间"),
		@Column(name="avatar", attrName="avatar", label="头像"),
	}, orderBy="a.create_time DESC"
)
public class WishPrimary extends DataEntity<WishPrimary> {
	@Valid
	@ExcelFields({
			@ExcelField(title="提名人", attrName="nomineeBy", align=ExcelField.Align.CENTER,sort=10),
			@ExcelField(title="工号", attrName="jobNumber", align=ExcelField.Align.CENTER, sort=15),
			@ExcelField(title="岗位", attrName="position",align = ExcelField.Align.CENTER, sort=30),
			@ExcelField(title="部门 ", attrName="department",align = ExcelField.Align.CENTER, sort=45),
			@ExcelField(title="提名时间 ", attrName="createTime",align = ExcelField.Align.CENTER, sort=45),
	})
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户id
	private String nomineeBy;		// 提名人
	private String jobNumber;		// 工号
	private String position;		// 岗位
	private String department;		// 部门
	private String departmentId;		// 部门id
	private Date createTime;		// 提名时间
	private List<WishPrimaryDetail> wishPrimaryDetailList = ListUtils.newArrayList();		// 子表列表
	private String avatar;			// 头像

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public WishPrimary() {
		this(null);
	}

	public WishPrimary(String id){
		super(id);
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=64, message="提名人长度不能超过 64 个字符")
	public String getNomineeBy() {
		return nomineeBy;
	}

	public void setNomineeBy(String nomineeBy) {
		this.nomineeBy = nomineeBy;
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
	
	@Length(min=0, max=128, message="部门id长度不能超过 128 个字符")
	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public List<WishPrimaryDetail> getWishPrimaryDetailList() {
		return wishPrimaryDetailList;
	}

	public void setWishPrimaryDetailList(List<WishPrimaryDetail> wishPrimaryDetailList) {
		this.wishPrimaryDetailList = wishPrimaryDetailList;
	}
	
}