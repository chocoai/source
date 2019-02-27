/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.ovopark.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import java.io.Serializable;

/**
 * 万店掌用户Entity
 * @author Philip Guan
 * @version 2019-02-19
 */
@Table(name="sv_ovopark_user", alias="a", columns={
		@Column(name="user_id", attrName="userId", label="主键", isPK=true),
		@Column(name="org_id", attrName="orgId", label="企业id"),
		@Column(name="depart_no", attrName="departNo", label="门店id"),
		@Column(name="user_name", attrName="userName", label="用户姓名", queryType=QueryType.LIKE),
		@Column(name="member_type", attrName="memberType", label="用户类型", comment="用户类型(1:顾客,2:会员,3:店员)"),
		@Column(name="mobile_phone", attrName="mobilePhone", label="手机号码"),
		@Column(name="gender", attrName="gender", label="性别", comment="性别(男0/女1)"),
		@Column(name="thirdpicurl", attrName="thirdpicurl", label="图片地址"),
		@Column(name="checkrepeat", attrName="checkrepeat", label="是否验证重复手机号", comment="是否验证重复手机号(1/0)"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class SvOvoparkUser extends DataEntity<SvOvoparkUser> {

	private static final long serialVersionUID = 1L;
	private String userId;		// 主键
	private Long orgId;		// 企业id
	private Long departNo;		// 门店id
	private String userName;		// 用户姓名
	private String memberType;		// 用户类型(1:顾客,2:会员,3:店员)
	private String mobilePhone;		// 手机号码
	private String gender;		// 性别(男0/女1)
	private String thirdpicurl;		// 图片地址
	private Long checkrepeat;		// 是否验证重复手机号(1/0)
	
	public SvOvoparkUser() {
		this(null);
	}

	public SvOvoparkUser(String id){
		super(id);
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	
	public Long getDepartNo() {
		return departNo;
	}

	public void setDepartNo(Long departNo) {
		this.departNo = departNo;
	}
	
	@NotBlank(message="用户姓名不能为空")
	@Length(min=0, max=128, message="用户姓名长度不能超过 128 个字符")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Length(min=0, max=2, message="用户类型长度不能超过 2 个字符")
	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}
	
	@Length(min=0, max=11, message="手机号码长度不能超过 11 个字符")
	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	@Length(min=0, max=2, message="性别长度不能超过 2 个字符")
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	@Length(min=0, max=500, message="图片地址长度不能超过 500 个字符")
	public String getThirdpicurl() {
		return thirdpicurl;
	}

	public void setThirdpicurl(String thirdpicurl) {
		this.thirdpicurl = thirdpicurl;
	}
	
	public Long getCheckrepeat() {
		return checkrepeat;
	}

	public void setCheckrepeat(Long checkrepeat) {
		this.checkrepeat = checkrepeat;
	}

	public void copyTo(SvOvoparkUserDTO dto){
        dto.setCheckrepeat(getCheckrepeat());
        dto.setDepartNo(getDepartNo());
        dto.setGender(getGender());
        dto.setMemberType(getMemberType());
        dto.setMobilePhone(getMobilePhone());
        dto.setOrgId(getOrgId());
        dto.setThirdpicurl(getThirdpicurl());
        dto.setUserId(getUserId());
        dto.setUserName(getUserName());
    }
}