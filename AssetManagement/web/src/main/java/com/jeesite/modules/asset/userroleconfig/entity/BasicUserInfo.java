///**
// * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
// */
//package com.jeesite.modules.asset.userroleconfig.entity;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.jeesite.common.entity.DataEntity;
//import com.jeesite.common.entity.Extend;
//import com.jeesite.common.mybatis.annotation.Column;
//import com.jeesite.common.mybatis.annotation.Table;
//import com.jeesite.common.mybatis.mapper.query.QueryType;
//import org.hibernate.validator.constraints.Length;
//import org.hibernate.validator.constraints.NotBlank;
//
//import java.util.Date;
//
///**
// * 用户表Entity
// * @author dwh
// * @version 2018-07-18
// */
//@Table(name="${_prefix}sys_user", alias="a", columns={
//		@Column(name="user_code", attrName="userCode", label="用户编码", isPK=true),
//		@Column(name="login_code", attrName="loginCode", label="登录账号",queryType=QueryType.LIKE),
//		@Column(name="user_name", attrName="userName", label="用户昵称", queryType=QueryType.LIKE),
//		@Column(name="password", attrName="password", label="登录密码"),
//		@Column(name="email", attrName="email", label="电子邮箱",queryType=QueryType.LIKE),
//		@Column(name="mobile", attrName="mobile", label="手机号码",queryType=QueryType.LIKE),
//		@Column(name="phone", attrName="phone", label="办公电话",queryType=QueryType.LIKE),
//		@Column(name="sex", attrName="sex", label="用户性别"),
//		@Column(name="avatar", attrName="avatar", label="头像路径"),
//		@Column(name="sign", attrName="sign", label="个性签名"),
//		@Column(name="wx_openid", attrName="wxOpenid", label="绑定的微信号"),
//		@Column(name="mobile_imei", attrName="mobileImei", label="绑定的手机串号"),
//		@Column(name="user_type", attrName="userType", label="用户类型"),
//		@Column(name="ref_code", attrName="refCode", label="用户类型引用编号"),
//		@Column(name="ref_name", attrName="refName", label="用户类型引用姓名"),
//		@Column(name="mgr_type", attrName="mgrType", label="管理员类型", comment="管理员类型（0非管理员 1系统管理员  2二级管理员）"),
//		@Column(name="pwd_security_level", attrName="pwdSecurityLevel", label="密码安全级别", comment="密码安全级别（0初始 1很弱 2弱 3安全 4很安全）"),
//		@Column(name="pwd_update_date", attrName="pwdUpdateDate", label="密码最后更新时间"),
//		@Column(name="pwd_update_record", attrName="pwdUpdateRecord", label="密码修改记录"),
//		@Column(name="pwd_question", attrName="pwdQuestion", label="密保问题"),
//		@Column(name="pwd_question_answer", attrName="pwdQuestionAnswer", label="密保问题答案"),
//		@Column(name="pwd_question_2", attrName="pwdQuestion2", label="密保问题2"),
//		@Column(name="pwd_question_answer_2", attrName="pwdQuestionAnswer2", label="密保问题答案2"),
//		@Column(name="pwd_question_3", attrName="pwdQuestion3", label="密保问题3"),
//		@Column(name="pwd_question_answer_3", attrName="pwdQuestionAnswer3", label="密保问题答案3"),
//		@Column(name="pwd_quest_update_date", attrName="pwdQuestUpdateDate", label="密码问题修改时间"),
//		@Column(name="last_login_ip", attrName="lastLoginIp", label="最后登陆IP"),
//		@Column(name="last_login_date", attrName="lastLoginDate", label="最后登陆时间"),
//		@Column(name="freeze_date", attrName="freezeDate", label="冻结时间"),
//		@Column(name="freeze_cause", attrName="freezeCause", label="冻结原因"),
//		@Column(name="user_weight", attrName="userWeight", label="用户权重", comment="用户权重（降序）"),
//		@Column(includeEntity=DataEntity.class),
////		@Column(includeEntity=BaseEntity.class),
//		@Column(includeEntity=Extend.class, attrName="extend"),
//	}, orderBy="a.update_date DESC"
//)
//public class BasicUserInfo extends DataEntity<BasicUserInfo> {
//
//	private static final long serialVersionUID = 1L;
//	private String userCode;		// 用户编码
//	private String loginCode;		// 登录账号
//	private String userName;		// 用户昵称
//	private String password;		// 登录密码
//	private String email;		// 电子邮箱
//	private String mobile;		// 手机号码
//	private String phone;		// 办公电话
//	private String sex;		// 用户性别
//	private String avatar;		// 头像路径
//	private String sign;		// 个性签名
//	private String wxOpenid;		// 绑定的微信号
//	private String mobileImei;		// 绑定的手机串号
//	private String userType;		// 用户类型
//	private String refCode;		// 用户类型引用编号
//	private String refName;		// 用户类型引用姓名
//	private String mgrType;		// 管理员类型（0非管理员 1系统管理员  2二级管理员）
//	private Integer pwdSecurityLevel;		// 密码安全级别（0初始 1很弱 2弱 3安全 4很安全）
//	private Date pwdUpdateDate;		// 密码最后更新时间
//	private String pwdUpdateRecord;		// 密码修改记录
//	private String pwdQuestion;		// 密保问题
//	private String pwdQuestionAnswer;		// 密保问题答案
//	private String pwdQuestion2;		// 密保问题2
//	private String pwdQuestionAnswer2;		// 密保问题答案2
//	private String pwdQuestion3;		// 密保问题3
//	private String pwdQuestionAnswer3;		// 密保问题答案3
//	private Date pwdQuestUpdateDate;		// 密码问题修改时间
//	private String lastLoginIp;		// 最后登陆IP
//	private Date lastLoginDate;		// 最后登陆时间
//	private Date freezeDate;		// 冻结时间
//	private String freezeCause;		// 冻结原因
//	private Integer userWeight;		// 用户权重（降序）
//	private Extend extend;		// 扩展字段
//
//	public BasicUserInfo() {
//		this(null);
//	}
//
//	public BasicUserInfo(String id){
//		super(id);
//	}
//
//	public String getUserCode() {
//		return userCode;
//	}
//
//	public void setUserCode(String userCode) {
//		this.userCode = userCode;
//	}
//
//	@NotBlank(message="登录账号不能为空")
//	@Length(min=0, max=100, message="登录账号长度不能超过 100 个字符")
//	public String getLoginCode() {
//		return loginCode;
//	}
//
//	public void setLoginCode(String loginCode) {
//		this.loginCode = loginCode;
//	}
//
//	@NotBlank(message="用户昵称不能为空")
//	@Length(min=0, max=100, message="用户昵称长度不能超过 100 个字符")
//	public String getUserName() {
//		return userName;
//	}
//
//	public void setUserName(String userName) {
//		this.userName = userName;
//	}
//
//	@NotBlank(message="登录密码不能为空")
//	@Length(min=0, max=100, message="登录密码长度不能超过 100 个字符")
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	@Length(min=0, max=300, message="电子邮箱长度不能超过 300 个字符")
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	@Length(min=0, max=100, message="手机号码长度不能超过 100 个字符")
//	public String getMobile() {
//		return mobile;
//	}
//
//	public void setMobile(String mobile) {
//		this.mobile = mobile;
//	}
//
//	@Length(min=0, max=100, message="办公电话长度不能超过 100 个字符")
//	public String getPhone() {
//		return phone;
//	}
//
//	public void setPhone(String phone) {
//		this.phone = phone;
//	}
//
//	@Length(min=0, max=1, message="用户性别长度不能超过 1 个字符")
//	public String getSex() {
//		return sex;
//	}
//
//	public void setSex(String sex) {
//		this.sex = sex;
//	}
//
//	@Length(min=0, max=1000, message="头像路径长度不能超过 1000 个字符")
//	public String getAvatar() {
//		return avatar;
//	}
//
//	public void setAvatar(String avatar) {
//		this.avatar = avatar;
//	}
//
//	@Length(min=0, max=200, message="个性签名长度不能超过 200 个字符")
//	public String getSign() {
//		return sign;
//	}
//
//	public void setSign(String sign) {
//		this.sign = sign;
//	}
//
//	@Length(min=0, max=100, message="绑定的微信号长度不能超过 100 个字符")
//	public String getWxOpenid() {
//		return wxOpenid;
//	}
//
//	public void setWxOpenid(String wxOpenid) {
//		this.wxOpenid = wxOpenid;
//	}
//
//	@Length(min=0, max=100, message="绑定的手机串号长度不能超过 100 个字符")
//	public String getMobileImei() {
//		return mobileImei;
//	}
//
//	public void setMobileImei(String mobileImei) {
//		this.mobileImei = mobileImei;
//	}
//
//	@NotBlank(message="用户类型不能为空")
//	@Length(min=0, max=16, message="用户类型长度不能超过 16 个字符")
//	public String getUserType() {
//		return userType;
//	}
//
//	public void setUserType(String userType) {
//		this.userType = userType;
//	}
//
//	@Length(min=0, max=64, message="用户类型引用编号长度不能超过 64 个字符")
//	public String getRefCode() {
//		return refCode;
//	}
//
//	public void setRefCode(String refCode) {
//		this.refCode = refCode;
//	}
//
//	@Length(min=0, max=100, message="用户类型引用姓名长度不能超过 100 个字符")
//	public String getRefName() {
//		return refName;
//	}
//
//	public void setRefName(String refName) {
//		this.refName = refName;
//	}
//
//	@NotBlank(message="管理员类型不能为空")
//	@Length(min=0, max=1, message="管理员类型长度不能超过 1 个字符")
//	public String getMgrType() {
//		return mgrType;
//	}
//
//	public void setMgrType(String mgrType) {
//		this.mgrType = mgrType;
//	}
//
//	public Integer getPwdSecurityLevel() {
//		return pwdSecurityLevel;
//	}
//
//	public void setPwdSecurityLevel(Integer pwdSecurityLevel) {
//		this.pwdSecurityLevel = pwdSecurityLevel;
//	}
//
//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//	public Date getPwdUpdateDate() {
//		return pwdUpdateDate;
//	}
//
//	public void setPwdUpdateDate(Date pwdUpdateDate) {
//		this.pwdUpdateDate = pwdUpdateDate;
//	}
//
//	@Length(min=0, max=1000, message="密码修改记录长度不能超过 1000 个字符")
//	public String getPwdUpdateRecord() {
//		return pwdUpdateRecord;
//	}
//
//	public void setPwdUpdateRecord(String pwdUpdateRecord) {
//		this.pwdUpdateRecord = pwdUpdateRecord;
//	}
//
//	@Length(min=0, max=200, message="密保问题长度不能超过 200 个字符")
//	public String getPwdQuestion() {
//		return pwdQuestion;
//	}
//
//	public void setPwdQuestion(String pwdQuestion) {
//		this.pwdQuestion = pwdQuestion;
//	}
//
//	@Length(min=0, max=200, message="密保问题答案长度不能超过 200 个字符")
//	public String getPwdQuestionAnswer() {
//		return pwdQuestionAnswer;
//	}
//
//	public void setPwdQuestionAnswer(String pwdQuestionAnswer) {
//		this.pwdQuestionAnswer = pwdQuestionAnswer;
//	}
//
//	@Length(min=0, max=200, message="密保问题2长度不能超过 200 个字符")
//	public String getPwdQuestion2() {
//		return pwdQuestion2;
//	}
//
//	public void setPwdQuestion2(String pwdQuestion2) {
//		this.pwdQuestion2 = pwdQuestion2;
//	}
//
//	@Length(min=0, max=200, message="密保问题答案2长度不能超过 200 个字符")
//	public String getPwdQuestionAnswer2() {
//		return pwdQuestionAnswer2;
//	}
//
//	public void setPwdQuestionAnswer2(String pwdQuestionAnswer2) {
//		this.pwdQuestionAnswer2 = pwdQuestionAnswer2;
//	}
//
//	@Length(min=0, max=200, message="密保问题3长度不能超过 200 个字符")
//	public String getPwdQuestion3() {
//		return pwdQuestion3;
//	}
//
//	public void setPwdQuestion3(String pwdQuestion3) {
//		this.pwdQuestion3 = pwdQuestion3;
//	}
//
//	@Length(min=0, max=200, message="密保问题答案3长度不能超过 200 个字符")
//	public String getPwdQuestionAnswer3() {
//		return pwdQuestionAnswer3;
//	}
//
//	public void setPwdQuestionAnswer3(String pwdQuestionAnswer3) {
//		this.pwdQuestionAnswer3 = pwdQuestionAnswer3;
//	}
//
//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//	public Date getPwdQuestUpdateDate() {
//		return pwdQuestUpdateDate;
//	}
//
//	public void setPwdQuestUpdateDate(Date pwdQuestUpdateDate) {
//		this.pwdQuestUpdateDate = pwdQuestUpdateDate;
//	}
//
//	@Length(min=0, max=100, message="最后登陆IP长度不能超过 100 个字符")
//	public String getLastLoginIp() {
//		return lastLoginIp;
//	}
//
//	public void setLastLoginIp(String lastLoginIp) {
//		this.lastLoginIp = lastLoginIp;
//	}
//
//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//	public Date getLastLoginDate() {
//		return lastLoginDate;
//	}
//
//	public void setLastLoginDate(Date lastLoginDate) {
//		this.lastLoginDate = lastLoginDate;
//	}
//
//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//	public Date getFreezeDate() {
//		return freezeDate;
//	}
//
//	public void setFreezeDate(Date freezeDate) {
//		this.freezeDate = freezeDate;
//	}
//
//	@Length(min=0, max=200, message="冻结原因长度不能超过 200 个字符")
//	public String getFreezeCause() {
//		return freezeCause;
//	}
//
//	public void setFreezeCause(String freezeCause) {
//		this.freezeCause = freezeCause;
//	}
//
//	public Integer getUserWeight() {
//		return userWeight;
//	}
//
//	public void setUserWeight(Integer userWeight) {
//		this.userWeight = userWeight;
//	}
//
//	public Extend getExtend() {
//		return extend;
//	}
//
//	public void setExtend(Extend extend) {
//		this.extend = extend;
//	}
//
//}