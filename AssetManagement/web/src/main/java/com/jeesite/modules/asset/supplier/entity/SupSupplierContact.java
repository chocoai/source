/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.supplier.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 供应商资料Entity
 * @author Scarlett
 * @version 2018-07-04
 */
@Table(name="${_prefix}sup_supplier_contact", alias="a", columns={
		@Column(name="contact_code", attrName="contactCode", label="联系人编号", isPK=true),
		@Column(name="supplier_code", attrName="supplierCode.supplierCode", label="供应商编号"),
		@Column(name="contact_position", attrName="contactPosition", label="职位"),
		@Column(name="contact_name", attrName="contactName", label="姓名", queryType=QueryType.LIKE),
		@Column(name="wechat", attrName="wechat", label="微信"),
		@Column(name="qq", attrName="qq", label="QQ"),
		@Column(name="phonenum", attrName="phonenum", label="手机"),
		@Column(name="email", attrName="email", label="邮箱"),
	}, orderBy="a.contact_code ASC"
)
public class SupSupplierContact extends DataEntity<SupSupplierContact> {
	
	private static final long serialVersionUID = 1L;
	private String contactCode;		// 联系人编号
	private SupSupplier supplierCode;		// 供应商编号 父类
	private String contactPosition;		// 职位
	private String contactName;		// 姓名
	private String wechat;		// 微信
	private String qq;		// QQ
	private String phonenum;		// 手机
	private String email;		// 邮箱
	
	public SupSupplierContact() {
		this(null);
	}


	public SupSupplierContact(SupSupplier supplierCode){
		this.supplierCode = supplierCode;
	}
	
	public String getContactCode() {
		return contactCode;
	}

	public void setContactCode(String contactCode) {
		this.contactCode = contactCode;
	}
	
	public SupSupplier getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(SupSupplier supplierCode) {
		this.supplierCode = supplierCode;
	}
	
	@NotBlank(message="职位不能为空")
	@Length(min=0, max=25, message="职位长度不能超过 25 个字符")
	public String getContactPosition() {
		return contactPosition;
	}

	public void setContactPosition(String contactPosition) {
		this.contactPosition = contactPosition;
	}
	
	@NotBlank(message="姓名不能为空")
	@Length(min=0, max=64, message="姓名长度不能超过 64 个字符")
	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	

	@Length(min=0, max=64, message="微信长度不能超过 64 个字符")
	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	

	@Length(min=0, max=60, message="QQ长度不能超过 60 个字符")
	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}
	
	@NotBlank(message="手机不能为空")
	@Length(min=0, max=30, message="手机长度不能超过 30 个字符")
	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}
	
	@NotBlank(message="邮箱不能为空")
	@Length(min=0, max=100, message="邮箱长度不能超过 100 个字符")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}