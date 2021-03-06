/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.score.entity;

import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.utils.excel.annotation.ExcelField;
import com.jeesite.common.utils.excel.annotation.ExcelFields;
import com.jeesite.modules.asset.ding.entity.DingDepartment;
import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.sys.entity.Office;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import javax.validation.Valid;
import javax.xml.crypto.Data;

/**
 * 加减分管理Entity
 * @author len
 * @version 2018-11-16
 */
@Table(name="ach_score", alias="a", columns={
		@Column(name="bill_code", attrName="billCode", label="单据编号", isPK=true),
		@Column(name="source_depart_code", attrName="sourceDepartCode", label="来源部门编码"),
		@Column(name="source_depart_name", attrName="sourceDepartName", label="来源部门", queryType=QueryType.LIKE),
		@Column(name="score_type", attrName="scoreType", label="类型"),
		@Column(name="staff_code", attrName="staffCode", label="员工编码"),
		@Column(name="staff_name", attrName="staffName", label="员工", queryType=QueryType.LIKE),
		@Column(name="depart_code", attrName="departCode", label="部门编码"),
		@Column(name="depart_name", attrName="departName", label="部门", queryType=QueryType.LIKE),
		@Column(name="post_code", attrName="postCode", label="岗位编码"),
		@Column(name="post_name", attrName="postName", label="岗位", queryType=QueryType.LIKE),
		@Column(name="ach_month", attrName="achMonth", label="绩效月份"),
		@Column(name="add_sub_score", attrName="addSubScore", label="加扣分值"),
		@Column(name="add_sub_reason", attrName="addSubReason", label="加扣分理由"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="data_type", attrName="dataType", label="数据状态"),
		@Column(name="audit_by", attrName="auditBy", label="审核人"),
		@Column(name="audit_date", attrName="auditDate", label="审核时间"),},
		joinTable = {
				@JoinTable(type = JoinTable.Type.LEFT_JOIN, entity = DingDepartment.class, alias = "b",
						on = "a.source_depart_code = b.department_id", attrName = "department",
						columns = {@Column(name = "department_id", label = "部门编码", isPK = true),
								@Column(name = "name", label = "部门名称", isQuery = false),
						}),
				@JoinTable(type = JoinTable.Type.LEFT_JOIN, entity = DingUser.class, alias = "c",
						on = "a.staff_code = c.userid", attrName = "dingUser",
						columns = {@Column(name = "userid", label = "用户编码", isPK = true),
								@Column(name = "name", label = "用户名", isQuery = false),
						}),
	}, orderBy="a.update_date DESC"
)
public class AchScore extends DataEntity<AchScore> {

	@Valid
	@ExcelFields({
			@ExcelField(title="来源部门编码", attrName="sourceDepartCode", align=ExcelField.Align.CENTER, sort=10),
			@ExcelField(title="类型", attrName="scoreType", align=ExcelField.Align.CENTER, sort=30),
			@ExcelField(title="员工编码", attrName="staffCode", align = ExcelField.Align.CENTER, sort=20),
			@ExcelField(title="部门", attrName="departName", align = ExcelField.Align.CENTER, sort=20),
			@ExcelField(title="岗位", attrName="postName", align = ExcelField.Align.CENTER, sort=20),
			@ExcelField(title="绩效月份", attrName="achMonth", align=ExcelField.Align.CENTER, sort=40),
			@ExcelField(title="加扣分值", attrName="addSubScore", align=ExcelField.Align.LEFT, sort=50),
			@ExcelField(title="加扣分理由", attrName="addSubReason", align=ExcelField.Align.CENTER, sort=60),
	})
	private static final long serialVersionUID = 1L;
	private String billCode;		// 单据编号
	private String sourceDepartCode;		// 来源部门编码
	private String sourceDepartName;		// 来源部门
	private String scoreType;		// 类型
	private String staffCode;		// 员工编码
	private String staffName;		// 员工
	private String departCode;		// 部门编码
	private String departName;		// 部门
	private String postCode;		// 岗位编码
	private String postName;		// 岗位
	private String achMonth;		// 绩效月份
	private Double addSubScore;		// 加扣分值
	private String addSubReason;		// 加扣分理由
	private String dataType;		// 数据状态
	private String auditBy;		// 审核人
	private Date auditDate;		// 审核时间
	private DingDepartment department;
	private DingUser dingUser;
	private String scoreVal;
	private String dataVal;
	private boolean isAudit;	// 是否是审核状态

	public boolean isAudit() {
		return isAudit;
	}

	public void setAudit(boolean audit) {
		isAudit = audit;
	}

	public String getDataVal() {
		return dataVal;
	}

	public void setDataVal(String dataVal) {
		this.dataVal = dataVal;
	}

	public String getScoreVal() {
		return scoreVal;
	}

	public void setScoreVal(String scoreVal) {
		this.scoreVal = scoreVal;
	}

	public DingDepartment getDepartment() {
		return department;
	}

	public void setDepartment(DingDepartment department) {
		this.department = department;
	}

	public DingUser getDingUser() {
		return dingUser;
	}

	public void setDingUser(DingUser dingUser) {
		this.dingUser = dingUser;
	}

	public AchScore() {
		this(null);
	}

	public AchScore(String id){
		super(id);
	}
	
	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}
	
	@Length(min=0, max=64, message="来源部门编码长度不能超过 64 个字符")
	public String getSourceDepartCode() {
		return sourceDepartCode;
	}

	public void setSourceDepartCode(String sourceDepartCode) {
		this.sourceDepartCode = sourceDepartCode;
	}
	
	@Length(min=0, max=64, message="来源部门长度不能超过 64 个字符")
	public String getSourceDepartName() {
		return sourceDepartName;
	}

	public void setSourceDepartName(String sourceDepartName) {
		this.sourceDepartName = sourceDepartName;
	}
	
	@Length(min=0, max=1, message="类型长度不能超过 1 个字符")
	public String getScoreType() {
		return scoreType;
	}

	public void setScoreType(String scoreType) {
		this.scoreType = scoreType;
	}
	
	@Length(min=0, max=64, message="员工编码长度不能超过 64 个字符")
	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}
	
	@Length(min=0, max=64, message="员工长度不能超过 64 个字符")
	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	
	@Length(min=0, max=64, message="部门编码长度不能超过 64 个字符")
	public String getDepartCode() {
		return departCode;
	}

	public void setDepartCode(String departCode) {
		this.departCode = departCode;
	}
	
	@Length(min=0, max=64, message="部门长度不能超过 64 个字符")
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
	
	@Length(min=0, max=64, message="岗位长度不能超过 64 个字符")
	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}
	@JsonFormat(pattern = "yyyy-MM")
	public String getAchMonth() {
		return achMonth;
	}

	public void setAchMonth(String achMonth) {
		this.achMonth = achMonth;
	}
	
	public Double getAddSubScore() {
		return addSubScore;
	}

	public void setAddSubScore(Double addSubScore) {
		this.addSubScore = addSubScore;
	}
	
	@Length(min=0, max=1000, message="加扣分理由长度不能超过 1000 个字符")
	public String getAddSubReason() {
		return addSubReason;
	}

	public void setAddSubReason(String addSubReason) {
		this.addSubReason = addSubReason;
	}
	
	@Length(min=0, max=1, message="数据状态长度不能超过 1 个字符")
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
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
	
}