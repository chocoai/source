/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fgc.entity;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import org.hibernate.validator.constraints.Length;

/**
 * 梵工厂用户表Entity
 * @author dwh
 * @version 2018-08-14
 */
@Table(name="${_prefix}fgc_user", alias="a", columns={
		@Column(name="document_code", attrName="documentCode", label="主键", isPK=true),
		@Column(name="open_id", attrName="openId", label="OpenID"),
		@Column(name="user_name", attrName="userName", label="用户名", queryType=QueryType.LIKE),
		@Column(name="verification_code", attrName="verificationCode", label="绑定的验证码"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="nickname", attrName="nickname", label="微信用户昵称"),
		@Column(name="sex", attrName="sex", label="用户的性别，值为1时是男性，值为2时是女性，值为0时是未知"),
		@Column(name="province", attrName="province", label="用户个人资料填写的省份"),
		@Column(name="city", attrName="city", label="普通用户个人资料填写的城市"),
		@Column(name="country", attrName="country", label="国家，如中国为CN"),
		@Column(name="headimgurl", attrName="headimgurl", label="用户头像，最后一个数值代表正方形头像大小"),
		@Column(name="privilege", attrName="privilege", label="用户特权信息，json 数组，如微信沃卡用户为", comment="用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）"),
		@Column(name="unionid", attrName="unionid", label="只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。"),
		@Column(name="sys_login_code", attrName="sysLoginCode", label="加密后的系统登录账号"),
		@Column(name="sys_login_pas", attrName="sysLoginPas", label="加密后的系统登录密码"),
	}, orderBy="a.update_date DESC"
)
public class FgcUser extends DataEntity<FgcUser> {
	
	private static final long serialVersionUID = 1L;
	private String documentCode;		// 主键
	private String openId;		// OpenID
	private String userName;		// 用户名
	private String verificationCode;		// 绑定的验证码
	private String nickname;		// 微信用户昵称
	private String sex;		// 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	private String province;		// 用户个人资料填写的省份
	private String city;		// 普通用户个人资料填写的城市
	private String country;		// 国家，如中国为CN
	private String headimgurl;		// 用户头像，最后一个数值代表正方形头像大小
	private String privilege;		// 用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
	private Long unionid;		// 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
	private String sysLoginCode;		// 加密后的系统登录账号
	private String sysLoginPas;		// 加密后的系统登录密码
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public FgcUser() {
		this(null);
	}

	public FgcUser(String id){
		super(id);
	}
	
	public String getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}
	
	@Length(min=0, max=200, message="OpenID长度不能超过 200 个字符")
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	@Length(min=0, max=200, message="用户名长度不能超过 200 个字符")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Length(min=0, max=200, message="绑定的验证码长度不能超过 200 个字符")
	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	
	@Length(min=0, max=200, message="微信用户昵称长度不能超过 200 个字符")
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@Length(min=0, max=20, message="用户的性别，值为1时是男性，值为2时是女性，值为0时是未知长度不能超过 20 个字符")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=255, message="用户个人资料填写的省份长度不能超过 255 个字符")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@Length(min=0, max=255, message="普通用户个人资料填写的城市长度不能超过 255 个字符")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Length(min=0, max=255, message="国家，如中国为CN长度不能超过 255 个字符")
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	@Length(min=0, max=1000, message="用户头像，最后一个数值代表正方形头像大小长度不能超过 1000 个字符")
	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	
	@Length(min=0, max=255, message="用户特权信息，json 数组，如微信沃卡用户为长度不能超过 255 个字符")
	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}
	
	public Long getUnionid() {
		return unionid;
	}

	public void setUnionid(Long unionid) {
		this.unionid = unionid;
	}
	
	@Length(min=0, max=1000, message="加密后的系统登录账号长度不能超过 1000 个字符")
	public String getSysLoginCode() {
		return sysLoginCode;
	}

	public void setSysLoginCode(String sysLoginCode) {
		this.sysLoginCode = sysLoginCode;
	}
	
	@Length(min=0, max=1000, message="加密后的系统登录密码长度不能超过 1000 个字符")
	public String getSysLoginPas() {
		return sysLoginPas;
	}

	public void setSysLoginPas(String sysLoginPas) {
		this.sysLoginPas = sysLoginPas;
	}
	
}