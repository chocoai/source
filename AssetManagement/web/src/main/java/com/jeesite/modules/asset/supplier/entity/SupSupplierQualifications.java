/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.supplier.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 供应商资料Entity
 * @author Scarlett
 * @version 2018-07-04
 */
@Table(name="${_prefix}sup_supplier_qualifications", alias="a", columns={
		@Column(name="supplier_code", attrName="supplierCode", label="供应商编号"),
		@Column(name="qualification_code", attrName="qualificationCode", label="文件编号", isPK=true),
		@Column(name="qualification_name", attrName="qualificationName", label="阿里云图片相对路径", queryType=QueryType.LIKE),
		@Column(name="type_name", attrName="typeName", label="资质文件类型"),
		@Column(name="profile_surfix", attrName="profileSurfix", label="文件后缀"),
		@Column(name="save_path", attrName="savePath", label="存储路径"),
		@Column(name="effective_date", attrName="effectiveDate", label="生效日期"),
		@Column(name="expire_date", attrName="expireDate", label="失效日期"),
		@Column(name="is_never_expired", attrName="isNeverExpired", label="是否长期有效"),
	}, orderBy="a.qualification_code ASC"
)
public class SupSupplierQualifications extends DataEntity<SupSupplierQualifications> {
	
	private static final long serialVersionUID = 1L;
	private String supplierCode;		// 供应商编号
	private String qualificationCode;		// 文件编号
	private String qualificationName;		// 阿里云图片相对路径
	private String typeName;		// 资质文件类型
	private String profileSurfix;		// 文件后缀
	private String savePath;		// 存储路径
	private Date effectiveDate;		// 生效日期
	private Date expireDate;		// 失效日期
	private String isNeverExpired;		// 是否长期有效
	/*private String qualificationStatus;//状态

	public String getQualificationStatus() {
		return qualificationStatus;
	}

	public void setQualificationStatus(String qualificationStatus) {
		this.qualificationStatus = qualificationStatus;
	}*/


	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	
	public String getQualificationCode() {
		return qualificationCode;
	}

	public void setQualificationCode(String qualificationCode) {
		this.qualificationCode = qualificationCode;
	}
	

	@Length(min=0, max=64, message="阿里云图片相对路径长度不能超过225 个字符")
	public String getQualificationName() {
		return qualificationName;
	}

	public void setQualificationName(String qualificationName) {
		this.qualificationName = qualificationName;
	}
	

	@Length(min=0, max=25, message="资质文件类型长度不能超过 25 个字符")
	public String getTypeName(){
		return typeName;
	}
	public void setTypeName(String typeName){this.typeName=typeName;}
	

	@Length(min=0, max=25, message="文件后缀长度不能超过 25 个字符")
	public String getProfileSurfix() {
		return profileSurfix;
	}

	public void setProfileSurfix(String profileSurfix) {
		this.profileSurfix = profileSurfix;
	}

	@Length(min=0, max=64, message="存储路径长度不能超过 64 个字符")
	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	
	@Length(min=0, max=1, message="是否长期有效长度不能超过 1 个字符")
	public String getIsNeverExpired() {
		return isNeverExpired;
	}

	public void setIsNeverExpired(String isNeverExpired) {
		this.isNeverExpired = isNeverExpired;
	}
	
}