/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.wechat.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * js_wechat_k3_userEntity
 * @author jace
 * @version 2018-08-01
 */
@Table(name="${_prefix}wechat_k3_user", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="k3_username", attrName="k3Username", label="k3用户名"),
		@Column(name="k3_password", attrName="k3Password", label="k3用户密码"),
		@Column(name="open_id", attrName="openId", label="微信openId", isPK=true),
		@Column(name="sex", attrName="sex", label="用户的性别，值为1时是男性，值为2时是女性，值为0时是未知"),
		@Column(name="nickname", attrName="nickname", label="微信用户昵称"),
		@Column(name="province", attrName="province", label="用户个人资料填写的省份"),
		@Column(name="city", attrName="city", label="普通用户个人资料填写的城市"),
		@Column(name="country", attrName="country", label="国家，如中国为CN"),
		@Column(name="headimgurl", attrName="headimgurl", label="用户头像，最后一个数值代表正方形头像大小"),
		@Column(name="privilege", attrName="privilege", label="用户特权信息，json 数组，如微信沃卡用户为", comment="用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）"),
		@Column(name="unionid", attrName="unionid", label="只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。"),
		@Column(name="sys_login_code", attrName="sysLoginCode", label="加密后的系统登录账号"),
		@Column(name="sys_login_pas", attrName="sysLoginPas", label="加密后的系统登录密码"),
		@Column(name="k3_db_id", attrName="k3DbId"),
		@Column(name="k3_user_id", attrName="k3UserId"),
		@Column(name="k3_user_rs_name", attrName="k3rsUserName"),
		@Column(name="k3_custo_name", attrName="k3CustoName"),
		@Column(name="k3_data_center_name", attrName="k3DataCenterName"),
	}, orderBy="a.id DESC, a.open_id DESC"
)
public class WechatK3User extends DataEntity<WechatK3User> {
	
	private static final long serialVersionUID = 1L;
	private String k3Username;		// k3用户名
	private String k3Password;		// k3用户密码
	private String openId;		// 微信openId
	private String sex;		// 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	private String nickname;		// 微信用户昵称
	private String province;		// 用户个人资料填写的省份
	private String city;		// 普通用户个人资料填写的城市
	private String country;		// 国家，如中国为CN
	private String headimgurl;		// 用户头像，最后一个数值代表正方形头像大小
	private String privilege;		// 用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
	private Long unionid;		// 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
	private String sysLoginCode;		// 加密后的系统登录账号
	private String sysLoginPas;		// 加密后的系统登录密码
	private String k3DbId;
	private String k3UserId;
	private String k3rsUserName;
	private String k3CustoName;
	private String k3DataCenterName;

	public String getK3DbId() {
		return k3DbId;
	}

	public void setK3DbId(String k3DbId) {
		this.k3DbId = k3DbId;
	}

	public String getK3UserId() {
		return k3UserId;
	}

	public void setK3UserId(String k3UserId) {
		this.k3UserId = k3UserId;
	}

	public String getK3rsUserName() {
		return k3rsUserName;
	}

	public void setK3rsUserName(String k3rsUserName) {
		this.k3rsUserName = k3rsUserName;
	}

	public String getK3CustoName() {
		return k3CustoName;
	}

	public void setK3CustoName(String k3CustoName) {
		this.k3CustoName = k3CustoName;
	}

	public String getK3DataCenterName() {
		return k3DataCenterName;
	}

	public void setK3DataCenterName(String k3DataCenterName) {
		this.k3DataCenterName = k3DataCenterName;
	}

	public WechatK3User() {
		this(null,null);
	}

	public WechatK3User(String id, String openId){
		this.id = id;
		this.openId = openId;
	}
	
	@Length(min=0, max=255, message="k3用户名长度不能超过 255 个字符")
	public String getK3Username() {
		return k3Username;
	}

	public void setK3Username(String k3Username) {
		this.k3Username = k3Username;
	}
	
	@Length(min=0, max=255, message="k3用户密码长度不能超过 255 个字符")
	public String getK3Password() {
		return k3Password;
	}

	public void setK3Password(String k3Password) {
		this.k3Password = k3Password;
	}
	
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=255, message="微信用户昵称长度不能超过 255 个字符")
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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
	
	@Length(min=0, max=255, message="用户头像，最后一个数值代表正方形头像大小长度不能超过 255 个字符")
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
	
	@Length(min=0, max=200, message="加密后的系统登录账号长度不能超过 200 个字符")
	public String getSysLoginCode() {
		return sysLoginCode;
	}

	public void setSysLoginCode(String sysLoginCode) {
		this.sysLoginCode = sysLoginCode;
	}
	
	@Length(min=0, max=200, message="加密后的系统登录密码长度不能超过 200 个字符")
	public String getSysLoginPas() {
		return sysLoginPas;
	}

	public void setSysLoginPas(String sysLoginPas) {
		this.sysLoginPas = sysLoginPas;
	}
	
}