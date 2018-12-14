/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.cardSnapshot.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 绩效卡快照Entity
 * @author Philip Guan
 * @version 2018-12-04
 */
@Table(name="ach_card_snapshot", alias="a", columns={
		@Column(name="card_snapshot_code", attrName="cardSnapshotCode", label="单据编号", isPK=true),
		@Column(name="card_code", attrName="cardCode", label="绩效卡编号"),
		@Column(name="user_id", attrName="userId", label="员工编码"),
		@Column(name="user_name", attrName="userName", label="员工名称", queryType=QueryType.LIKE),
		@Column(name="depart_code", attrName="departCode", label="部门编码"),
		@Column(name="depart_name", attrName="departName", label="部门名称", queryType=QueryType.LIKE),
		@Column(name="post_code", attrName="postCode", label="岗位编码"),
		@Column(name="post_name", attrName="postName", label="岗位名称", queryType=QueryType.LIKE),
		@Column(name="examine_month", attrName="examineMonth", label="考核月份"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="data_status", attrName="dataStatus", label="数据状态"),
		@Column(name="data_type", attrName="dataType", label="数据状态"),
		@Column(name="data", attrName="data", label="快照内容"),
	}, orderBy="a.update_date DESC"
)
public class AchCardSnapshot extends DataEntity<AchCardSnapshot> {
	
	private static final long serialVersionUID = 1L;
	private String cardSnapshotCode;		// 单据编号
	private String cardCode;		// 绩效卡编号
	private String userId;		// 员工编码
	private String userName;		// 员工名称
	private String departCode;		// 部门编码
	private String departName;		// 部门名称
	private String postCode;		// 岗位编码
	private String postName;		// 岗位名称
	private String examineMonth;		// 考核月份
	private String dataStatus;		// 数据状态
	private String dataType;		// 数据状态
	private String data;		// 快照内容
	
	public AchCardSnapshot() {
		this(null);
	}

	public AchCardSnapshot(String id){
		super(id);
	}
	
	public String getCardSnapshotCode() {
		return cardSnapshotCode;
	}

	public void setCardSnapshotCode(String cardSnapshotCode) {
		this.cardSnapshotCode = cardSnapshotCode;
	}
	
	@NotBlank(message="绩效卡编号不能为空")
	@Length(min=0, max=64, message="绩效卡编号长度不能超过 64 个字符")
	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	
	@Length(min=0, max=64, message="员工编码长度不能超过 64 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=64, message="员工名称长度不能超过 64 个字符")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Length(min=0, max=64, message="部门编码长度不能超过 64 个字符")
	public String getDepartCode() {
		return departCode;
	}

	public void setDepartCode(String departCode) {
		this.departCode = departCode;
	}
	
	@Length(min=0, max=64, message="部门名称长度不能超过 64 个字符")
	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}
	
	@Length(min=0, max=64, message="岗位编码长度不能超过 64 个字符")
	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	
	@Length(min=0, max=64, message="岗位名称长度不能超过 64 个字符")
	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}
	
	@Length(min=0, max=10, message="考核月份长度不能超过 10 个字符")
	public String getExamineMonth() {
		return examineMonth;
	}

	public void setExamineMonth(String examineMonth) {
		this.examineMonth = examineMonth;
	}
	
	@Length(min=0, max=4, message="数据状态长度不能超过 4 个字符")
	public String getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}
	
	@Length(min=0, max=127, message="数据状态长度不能超过 4 个字符")
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
}