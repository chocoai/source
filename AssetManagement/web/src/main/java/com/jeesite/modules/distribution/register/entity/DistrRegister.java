/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.distribution.register.entity;

import com.jeesite.modules.sys.entity.Office;
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
 * 分销注册申请Entity
 * @author len
 * @version 2019-01-03
 */
@Table(name="distr_register", alias="a", columns= {
		@Column(name = "register_code", attrName = "registerCode", label = "申请单号", isPK = true),
		@Column(name = "phone", attrName = "phone", label = "手机号", queryType = QueryType.LIKE),
		@Column(name = "full_name", attrName = "fullName", label = "姓名", queryType = QueryType.LIKE),
		@Column(name = "province", attrName = "province", label = "省"),
		@Column(name = "city", attrName = "city", label = "市"),
		@Column(name = "region", attrName = "region", label = "区"),
		@Column(name = "qualifications", attrName = "qualifications", label = "资质说明"),
		@Column(name = "voucher_img", attrName = "voucherImg", label = "凭证"),
		@Column(name = "register_status", attrName = "registerStatus", label = "状态"),
		@Column(name = "reject_reason", attrName = "rejectReason", label = "驳回理由"),
		@Column(name = "create_time", attrName = "createTime", label = "创建时间"),
		@Column(name = "audit_by", attrName = "auditBy", label = "审核人"),
		@Column(name = "audit_time", attrName = "auditTime", label = "审核时间"),
		@Column(name = "audit_code", attrName = "auditCode", label = "审核人账号"),
		@Column(name = "reject_by", attrName = "rejectBy", label = "驳回人"),
		@Column(name = "reject_code", attrName = "rejectCode", label = "驳回人账号"),
		@Column(name = "reject_time", attrName = "rejectTime", label = "驳回时间"),
		@Column(name = "office_code", attrName = "officeCode", label = "所属部门"),
//,joinTable = {
//		@JoinTable(type=Type.LEFT_JOIN, entity=Office.class, alias="b",
//				on="b.office_code = a.office_code",
//				columns={
//						@Column(name="office_code", label="部门编码"),
//						@Column(name="office_name", label="部门名称"),}),
	}, orderBy="a.create_time DESC"
)
public class DistrRegister extends DataEntity<DistrRegister> {
	
	private static final long serialVersionUID = 1L;
	private String registerCode;		// 申请单号
	private String phone;		// 手机号
	private String fullName;		// 姓名
	private String province;		// 省
	private String city;		// 市
	private String region;		// 区
	private String qualifications;		// 资质说明
	private String voucherImg;		// 凭证
	private String registerStatus;		// 状态
	private String rejectReason;		// 驳回理由
	private Date createTime;		// 创建时间
	private String auditBy;		// 审核人
	private Date auditTime;		// 审核时间
	private String auditCode;		// 审核人账号
	private String rejectBy;		// 驳回人
	private String rejectCode;		// 驳回人账号
	private Date rejectTime;		// 驳回时间
	private String statusLabel;		// 状态页面显示用
//	private String officeCode;
//	private Office office;
	private boolean created;		// 是否是创建状态

	public boolean isCreated() {
		return created;
	}

	public void setCreated(boolean created) {
		this.created = created;
	}


	public void setStatusLabel(String statusLabel) {
		this.statusLabel = statusLabel;
	}

	public DistrRegister() {
		this(null);
	}

	public DistrRegister(String id){
		super(id);
	}
	
	public String getRegisterCode() {
		return registerCode;
	}

	public void setRegisterCode(String registerCode) {
		this.registerCode = registerCode;
	}
	
	@NotBlank(message="手机号不能为空")
	@Length(min=0, max=20, message="手机号长度不能超过 20 个字符")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@NotBlank(message="姓名不能为空")
	@Length(min=0, max=64, message="姓名长度不能超过 64 个字符")
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	@Length(min=0, max=50, message="省长度不能超过 50 个字符")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@Length(min=0, max=50, message="市长度不能超过 50 个字符")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Length(min=0, max=50, message="区长度不能超过 50 个字符")
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	
	@Length(min=0, max=2000, message="资质说明长度不能超过 2000 个字符")
	public String getQualifications() {
		return qualifications;
	}

	public void setQualifications(String qualifications) {
		this.qualifications = qualifications;
	}
	
	@Length(min=0, max=500, message="凭证长度不能超过 500 个字符")
	public String getVoucherImg() {
		return voucherImg;
	}

	public void setVoucherImg(String voucherImg) {
		this.voucherImg = voucherImg;
	}
	
	@Length(min=0, max=1, message="状态长度不能超过 1 个字符")
	public String getRegisterStatus() {
		return registerStatus;
	}

	public void setRegisterStatus(String registerStatus) {
		this.registerStatus = registerStatus;
	}
	
	@Length(min=0, max=2000, message="驳回理由长度不能超过 2000 个字符")
	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=0, max=64, message="审核人长度不能超过 64 个字符")
	public String getAuditBy() {
		return auditBy;
	}

	public void setAuditBy(String auditBy) {
		this.auditBy = auditBy;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	
	@Length(min=0, max=64, message="审核人账号长度不能超过 64 个字符")
	public String getAuditCode() {
		return auditCode;
	}

	public void setAuditCode(String auditCode) {
		this.auditCode = auditCode;
	}
	
	@Length(min=0, max=64, message="驳回人长度不能超过 64 个字符")
	public String getRejectBy() {
		return rejectBy;
	}

	public void setRejectBy(String rejectBy) {
		this.rejectBy = rejectBy;
	}
	
	@Length(min=0, max=64, message="驳回人账号长度不能超过 64 个字符")
	public String getRejectCode() {
		return rejectCode;
	}

	public void setRejectCode(String rejectCode) {
		this.rejectCode = rejectCode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRejectTime() {
		return rejectTime;
	}

	public void setRejectTime(Date rejectTime) {
		this.rejectTime = rejectTime;
	}
	
}