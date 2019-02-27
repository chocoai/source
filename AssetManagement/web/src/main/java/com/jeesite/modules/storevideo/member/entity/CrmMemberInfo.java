/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.member.entity;

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
 * 淘宝会员信息Entity
 * @author Albert Feng
 * @version 2019-02-16
 */
@Table(name="crm_member_info", alias="a", columns={
		@Column(name="member_code", attrName="memberCode", label="编号", isPK=true),
		@Column(name="mobile", attrName="mobile", label="手机号码"),
		@Column(name="taobao_nick", attrName="taobaoNick", label="淘宝昵称"),
		@Column(name="source_name", attrName="sourceName", label="来源名称", queryType=QueryType.LIKE),
		@Column(name="member_name", attrName="memberName", label="姓名", queryType=QueryType.LIKE),
		@Column(name="sex", attrName="sex", label="性别"),
		@Column(name="birthday", attrName="birthday", label="出生月日"),
		@Column(name="birth_date", attrName="birthDate", label="出生年月日"),
		@Column(name="baby_birthday", attrName="babyBirthday", label="宝宝生日"),
		@Column(name="province", attrName="province", label="省区码"),
		@Column(name="city", attrName="city", label="市区码"),
		@Column(name="email", attrName="email", label="电子邮箱"),
		@Column(name="employee_no", attrName="employeeNo", label="导购工号"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.member_code DESC"
)
public class CrmMemberInfo extends DataEntity<CrmMemberInfo> {
	
	private static final long serialVersionUID = 1L;
	private String memberCode;		// 编号
	private String mobile;		// 手机号码
	private String taobaoNick;		// 淘宝昵称
	private String sourceName;		// 来源名称
	private String memberName;		// 姓名
	private String sex;		// 性别
	private String birthday;		// 出生月日
	private String birthDate;		// 出生年月日
	private String babyBirthday;		// 宝宝生日
	private String province;		// 省区码
	private String city;		// 市区码
	private String email;		// 电子邮箱
	private String employeeNo;		// 导购工号
	
	public CrmMemberInfo() {
		this(null);
	}

	public CrmMemberInfo(String id){
		super(id);
	}
	
	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	
	@Length(min=0, max=50, message="手机号码长度不能超过 50 个字符")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Length(min=0, max=255, message="淘宝昵称长度不能超过 255 个字符")
	public String getTaobaoNick() {
		return taobaoNick;
	}

	public void setTaobaoNick(String taobaoNick) {
		this.taobaoNick = taobaoNick;
	}
	
	@Length(min=0, max=255, message="来源名称长度不能超过 255 个字符")
	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	
	@Length(min=0, max=50, message="姓名长度不能超过 50 个字符")
	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	
	@Length(min=0, max=1, message="性别长度不能超过 1 个字符")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=10, message="出生月日长度不能超过 10 个字符")
	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getBabyBirthday() {
		return babyBirthday;
	}

	public void setBabyBirthday(String babyBirthday) {
		this.babyBirthday = babyBirthday;
	}
	
	@Length(min=0, max=100, message="省区码长度不能超过 100 个字符")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@Length(min=0, max=100, message="市区码长度不能超过 100 个字符")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Length(min=0, max=255, message="电子邮箱长度不能超过 255 个字符")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=255, message="导购工号长度不能超过 255 个字符")
	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}
	
}