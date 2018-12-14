/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.ding.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 钉钉用户表备份Entity
 * @author len
 * @version 2018-10-31
 */
@Table(name="${_prefix}ding_user_backups", alias="a", columns={
		@Column(name="userid", attrName="userid", label="员工唯一标识ID"),
		@Column(name="openid", attrName="openid", label="钉钉唯一标识关注者身份的id", comment="钉钉唯一标识关注者身份的id（不可修改）"),
		@Column(name="name", attrName="name", label="成员名称", queryType=QueryType.LIKE),
		@Column(name="tel", attrName="tel", label="分机号", comment="分机号（仅限企业内部开发调用）"),
		@Column(name="work_place", attrName="workPlace", label="办公地点", comment="办公地点（ISV不可见）"),
		@Column(name="remark", attrName="remark", label="备注", comment="备注（ISV不可见）"),
		@Column(name="mobile", attrName="mobile", label="手机号码", comment="手机号码（ISV不可见）"),
		@Column(name="email", attrName="email", label="员工的电子邮箱", comment="员工的电子邮箱（ISV不可见）"),
		@Column(name="org_email", attrName="orgEmail", label="员工的企业邮箱"),
		@Column(name="active", attrName="active", label="是否已经激活, true已激活, false未激活"),
		@Column(name="orderin_depts", attrName="orderinDepts", label="在对应的部门中的排序, Map结构的json字符串"),
		@Column(name="is_admin", attrName="isAdmin", label="是否为企业管理员, true是, false不是"),
		@Column(name="is_boss", attrName="isBoss", label="是否为企业的老板, true表示是, false表示不是"),
		@Column(name="unionid", attrName="unionid", label="在当前isv全局范围内唯一标识一个用户的身份"),
		@Column(name="is_leader_in_depts", attrName="isLeaderInDepts", label="在对应的部门中是否为主管"),
		@Column(name="is_hide", attrName="isHide", label="是否号码隐藏, true隐藏, false不隐藏"),
		@Column(name="department", attrName="department", label="成员所属部门id列表"),
		@Column(name="position", attrName="position", label="职位信息"),
		@Column(name="avatar", attrName="avatar", label="头像url"),
		@Column(name="hired_date", attrName="hiredDate", label="入职时间"),
		@Column(name="jobnumber", attrName="jobnumber", label="员工工号"),
		@Column(name="extattr", attrName="extattr", label="扩展属性，可以设置多种属性"),
		@Column(name="roleid", attrName="roleid", label="角色id", comment="角色id（ISV不可见）"),
		@Column(name="state_code", attrName="stateCode", label="手机号码区号"),
		@Column(name="is_senior", attrName="isSenior", label="是否是高管"),
		@Column(name="convertible_gold", attrName="convertibleGold", label="可兑换梵钻"),
		@Column(name="in_department_gold", attrName="inDepartmentGold", label="部门内梵钻"),
		@Column(name="out_department_gold", attrName="outDepartmentGold", label="跨部门梵钻"),
		@Column(name="left", attrName="left", label="是否离职"),
		@Column(name="prize_type", attrName="prizeType", label="中奖类型"),
		@Column(name="achievement", attrName="achievement", label="竞猜业绩"),
		@Column(name="winning_prize", attrName="winningPrize", label="是否中奖", comment="是否中奖(0否1是)"),
		@Column(name="user_status", attrName="userStatus", label="用户状态", comment="用户状态(0正常2停用)"),
		@Column(name="pkey", attrName="pkey", label="主键", isPK=true),
		@Column(name="flag", attrName="flag", label="更新标识"),
		@Column(name="date", attrName="date", label="更新时间"),
	}, orderBy="a.date DESC"
)
public class DingUserBackups extends DataEntity<DingUserBackups> {
	
	private static final long serialVersionUID = 1L;
	private String userid;		// 员工唯一标识ID
	private String openid;		// 钉钉唯一标识关注者身份的id（不可修改）
	private String name;		// 成员名称
	private String tel;		// 分机号（仅限企业内部开发调用）
	private String workPlace;		// 办公地点（ISV不可见）
	private String remark;		// 备注（ISV不可见）
	private String mobile;		// 手机号码（ISV不可见）
	private String email;		// 员工的电子邮箱（ISV不可见）
	private String orgEmail;		// 员工的企业邮箱
	private String active;		// 是否已经激活, true已激活, false未激活
	private String orderinDepts;		// 在对应的部门中的排序, Map结构的json字符串
	private String isAdmin;		// 是否为企业管理员, true是, false不是
	private String isBoss;		// 是否为企业的老板, true表示是, false表示不是
	private String unionid;		// 在当前isv全局范围内唯一标识一个用户的身份
	private String isLeaderInDepts;		// 在对应的部门中是否为主管
	private String isHide;		// 是否号码隐藏, true隐藏, false不隐藏
	private String department;		// 成员所属部门id列表
	private String position;		// 职位信息
	private String avatar;		// 头像url
	private Date hiredDate;		// 入职时间
	private String jobnumber;		// 员工工号
	private String extattr;		// 扩展属性，可以设置多种属性
	private String roleid;		// 角色id（ISV不可见）
	private String stateCode;		// 手机号码区号
	private String isSenior;		// 是否是高管
	private Long convertibleGold;		// 可兑换梵钻
	private Long inDepartmentGold;		// 部门内梵钻
	private Long outDepartmentGold;		// 跨部门梵钻
	private String left;		// 是否离职
	private String prizeType;		// 中奖类型
	private String achievement;		// 竞猜业绩
	private String winningPrize;		// 是否中奖(0否1是)
	private String userStatus;		// 用户状态(0正常2停用)
	private String pkey;		// 主键
	private String flag;		// 更新标识
	private Date date;		// 更新时间
	
	public DingUserBackups() {
		this(null);
	}

	public DingUserBackups(String id){
		super(id);
	}
	
	@NotBlank(message="员工唯一标识ID不能为空")
	@Length(min=0, max=225, message="员工唯一标识ID长度不能超过 225 个字符")
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	@Length(min=0, max=225, message="钉钉唯一标识关注者身份的id长度不能超过 225 个字符")
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	@Length(min=0, max=225, message="成员名称长度不能超过 225 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=45, message="分机号长度不能超过 45 个字符")
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	@Length(min=0, max=125, message="办公地点长度不能超过 125 个字符")
	public String getWorkPlace() {
		return workPlace;
	}

	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}
	
	@Length(min=0, max=500, message="备注长度不能超过 500 个字符")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Length(min=0, max=50, message="手机号码长度不能超过 50 个字符")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Length(min=0, max=100, message="员工的电子邮箱长度不能超过 100 个字符")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=500, message="员工的企业邮箱长度不能超过 500 个字符")
	public String getOrgEmail() {
		return orgEmail;
	}

	public void setOrgEmail(String orgEmail) {
		this.orgEmail = orgEmail;
	}
	
	@Length(min=0, max=10, message="是否已经激活, true已激活, false未激活长度不能超过 10 个字符")
	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	
	@Length(min=0, max=500, message="在对应的部门中的排序, Map结构的json字符串长度不能超过 500 个字符")
	public String getOrderinDepts() {
		return orderinDepts;
	}

	public void setOrderinDepts(String orderinDepts) {
		this.orderinDepts = orderinDepts;
	}
	
	@Length(min=0, max=10, message="是否为企业管理员, true是, false不是长度不能超过 10 个字符")
	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	@Length(min=0, max=10, message="是否为企业的老板, true表示是, false表示不是长度不能超过 10 个字符")
	public String getIsBoss() {
		return isBoss;
	}

	public void setIsBoss(String isBoss) {
		this.isBoss = isBoss;
	}
	
	@Length(min=0, max=500, message="在当前isv全局范围内唯一标识一个用户的身份长度不能超过 500 个字符")
	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	
	@Length(min=0, max=500, message="在对应的部门中是否为主管长度不能超过 500 个字符")
	public String getIsLeaderInDepts() {
		return isLeaderInDepts;
	}

	public void setIsLeaderInDepts(String isLeaderInDepts) {
		this.isLeaderInDepts = isLeaderInDepts;
	}
	
	@Length(min=0, max=10, message="是否号码隐藏, true隐藏, false不隐藏长度不能超过 10 个字符")
	public String getIsHide() {
		return isHide;
	}

	public void setIsHide(String isHide) {
		this.isHide = isHide;
	}
	
	@Length(min=0, max=225, message="成员所属部门id列表长度不能超过 225 个字符")
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	@Length(min=0, max=500, message="职位信息长度不能超过 500 个字符")
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	@Length(min=0, max=325, message="头像url长度不能超过 325 个字符")
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getHiredDate() {
		return hiredDate;
	}

	public void setHiredDate(Date hiredDate) {
		this.hiredDate = hiredDate;
	}
	
	@Length(min=0, max=225, message="员工工号长度不能超过 225 个字符")
	public String getJobnumber() {
		return jobnumber;
	}

	public void setJobnumber(String jobnumber) {
		this.jobnumber = jobnumber;
	}
	
	@Length(min=0, max=500, message="扩展属性，可以设置多种属性长度不能超过 500 个字符")
	public String getExtattr() {
		return extattr;
	}

	public void setExtattr(String extattr) {
		this.extattr = extattr;
	}
	
	@Length(min=0, max=225, message="角色id长度不能超过 225 个字符")
	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	
	@Length(min=0, max=100, message="手机号码区号长度不能超过 100 个字符")
	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	
	@Length(min=0, max=10, message="是否是高管长度不能超过 10 个字符")
	public String getIsSenior() {
		return isSenior;
	}

	public void setIsSenior(String isSenior) {
		this.isSenior = isSenior;
	}
	
	@NotNull(message="可兑换梵钻不能为空")
	public Long getConvertibleGold() {
		return convertibleGold;
	}

	public void setConvertibleGold(Long convertibleGold) {
		this.convertibleGold = convertibleGold;
	}
	
	@NotNull(message="部门内梵钻不能为空")
	public Long getInDepartmentGold() {
		return inDepartmentGold;
	}

	public void setInDepartmentGold(Long inDepartmentGold) {
		this.inDepartmentGold = inDepartmentGold;
	}
	
	@NotNull(message="跨部门梵钻不能为空")
	public Long getOutDepartmentGold() {
		return outDepartmentGold;
	}

	public void setOutDepartmentGold(Long outDepartmentGold) {
		this.outDepartmentGold = outDepartmentGold;
	}
	
	@Length(min=0, max=25, message="是否离职长度不能超过 25 个字符")
	public String getLeft() {
		return left;
	}

	public void setLeft(String left) {
		this.left = left;
	}
	
	@Length(min=0, max=64, message="中奖类型长度不能超过 64 个字符")
	public String getPrizeType() {
		return prizeType;
	}

	public void setPrizeType(String prizeType) {
		this.prizeType = prizeType;
	}
	
	@Length(min=0, max=4, message="竞猜业绩长度不能超过 4 个字符")
	public String getAchievement() {
		return achievement;
	}

	public void setAchievement(String achievement) {
		this.achievement = achievement;
	}
	
	@Length(min=0, max=1, message="是否中奖长度不能超过 1 个字符")
	public String getWinningPrize() {
		return winningPrize;
	}

	public void setWinningPrize(String winningPrize) {
		this.winningPrize = winningPrize;
	}
	
	@Length(min=0, max=1, message="用户状态长度不能超过 1 个字符")
	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	
	public String getPkey() {
		return pkey;
	}

	public void setPkey(String pkey) {
		this.pkey = pkey;
	}
	
	@Length(min=0, max=10, message="更新标识长度不能超过 10 个字符")
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}