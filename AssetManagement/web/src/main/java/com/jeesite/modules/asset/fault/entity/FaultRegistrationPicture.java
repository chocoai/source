/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.fault.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 故障登记图片Entity
 * @author Scarlett
 * @version 2018-07-14
 */
@Table(name="${_prefix}fault_registration_picture", alias="a", columns={
		@Column(name="faultpic_code", attrName="faultpicCode", label="图片编号", isPK=true),
		@Column(name="faultpic_name", attrName="faultpicName", label="图片名称", queryType=QueryType.LIKE),
		@Column(name="location", attrName="location", label="图片位置"),
		@Column(name="faultpic_surfix", attrName="faultpicSurfix", label="文件后缀"),
		@Column(name="save_path", attrName="savePath", label="存储路径"),
		@Column(name="registration_code", attrName="registrationCode", label="故障登记单号"),
	}, orderBy="a.faultpic_code DESC"
)
public class FaultRegistrationPicture extends DataEntity<FaultRegistrationPicture> {
	
	private static final long serialVersionUID = 1L;
	private String faultpicCode;		// 图片编号
	private String faultpicName;		// 图片名称
	private String location;		// 图片位置
	private String faultpicSurfix;		// 文件后缀
	private String savePath;		// 存储路径
	private String registrationCode;		// 故障登记单号
	
	public FaultRegistrationPicture() {
		this(null);
	}

	public FaultRegistrationPicture(String id){
		super(id);
	}
	
	public String getFaultpicCode() {
		return faultpicCode;
	}

	public void setFaultpicCode(String faultpicCode) {
		this.faultpicCode = faultpicCode;
	}
	
	@Length(min=0, max=64, message="图片名称长度不能超过 64 个字符")
	public String getFaultpicName() {
		return faultpicName;
	}

	public void setFaultpicName(String faultpicName) {
		this.faultpicName = faultpicName;
	}
	
	@Length(min=0, max=25, message="图片位置长度不能超过 25 个字符")
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	@Length(min=0, max=25, message="文件后缀长度不能超过 25 个字符")
	public String getFaultpicSurfix() {
		return faultpicSurfix;
	}

	public void setFaultpicSurfix(String faultpicSurfix) {
		this.faultpicSurfix = faultpicSurfix;
	}
	
	@Length(min=0, max=64, message="存储路径长度不能超过 64 个字符")
	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	
	@Length(min=0, max=64, message="故障登记单号长度不能超过 64 个字符")
	public String getRegistrationCode() {
		return registrationCode;
	}

	public void setRegistrationCode(String registrationCode) {
		this.registrationCode = registrationCode;
	}
	
}