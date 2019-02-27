/** 
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.ding.entity;

import com.jeesite.common.utils.excel.annotation.ExcelField;
import com.jeesite.common.utils.excel.annotation.ExcelFields;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import com.jeesite.common.collect.ListUtils;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import javax.validation.Valid;

/**
 * 人员管理Entity
 * @author scarlett
 * @version 2018-09-19
 */
@Table(name="${_prefix}ding_user", alias="a", columns={
        @Column(name="userid", attrName="userid", label="员工唯一标识ID", isPK=true),
        @Column(name="openid", attrName="openid", label="钉钉唯一标识关注者身份的id", comment="钉钉唯一标识关注者身份的id（不可修改）"),
        @Column(name="name", attrName="name", label="成员名称", queryType=QueryType.LIKE),
        @Column(name="tel", attrName="tel", label="分机号", comment="分机号（仅限企业内部开发调用）"),
        @Column(name="work_place", attrName="workPlace", label="办公地点", comment="办公地点（ISV不可见）",queryType=QueryType.LIKE),
        @Column(name="remark", attrName="remark", label="备注", comment="备注（ISV不可见）"),
        @Column(name="mobile", attrName="mobile", label="手机号码", comment="手机号码（ISV不可见）",queryType=QueryType.LIKE),
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
        @Column(name="left", attrName="left", label="是否离职",queryType=QueryType.EQ),
        @Column(name="winning_prize", attrName="winningPrize", label="是否中奖"),
        @Column(name="prize_type", attrName="prizeType", label="中奖类型",queryType=QueryType.EQ),
        @Column(name="achievement", attrName="achievement", label="竞猜业绩",queryType=QueryType.EQ),
        @Column(name="user_status", attrName="userStatus", label="用户状态",queryType=QueryType.EQ),
        @Column(name="user_is_vip", attrName="userIsVip", label="用户是否特权用户",queryType=QueryType.EQ),
        @Column(name="is_manager", attrName="isManager", label="是否管理层",queryType=QueryType.EQ),
        @Column(name="direct_superior", attrName="directSuperior", label="直接上级",queryType=QueryType.EQ),
        @Column(name="department_header", attrName="departmentHeader", label="部门长",queryType=QueryType.EQ),
        @Column(name="used_point", attrName="usedPoint", label="已消费的总梵砖数量",queryType=QueryType.EQ),
        @Column(name="freeze_point", attrName="freezePoint", label="当前锁定梵砖",queryType=QueryType.EQ),
        @Column(name="job_level", attrName="jobLevel", label="职级",queryType=QueryType.EQ),
        @Column(name="customized", attrName="customized", label="定制",queryType=QueryType.EQ),
},/* joinTable = {
		@JoinTable(type=JoinTable.Type.LEFT_JOIN, entity=DingUserDepartment.class, alias="b",
		on="b.user_id = a.userid", attrName="deptUser",
		columns={@Column(name="id", label="id",isPK=true),
				@Column(name="user_id", label="用户id",isQuery=false),
				@Column(name="department_id", label="部门代码",isQuery=false),
		}),
		@JoinTable(type=JoinTable.Type.LEFT_JOIN, entity=DingDepartment.class, alias="c",
				on="c.department_id = b.department_id", attrName="dept",
				columns={@Column(name="department_id", label="部门代码",isPK=true),
						@Column(name="name", label="部门名称",isQuery=false),
				}),
},*/
        orderBy="a.user_is_vip DESC,a.userid DESC"
)
public class DingUser extends DataEntity<DingUser> {
    @Valid
    @ExcelFields({
            @ExcelField(title="用户", attrName="name", align=ExcelField.Align.CENTER, sort=10),
            @ExcelField(title="工号", attrName="jobnumber", align=ExcelField.Align.CENTER, sort=15),
            @ExcelField(title="部门", attrName="departmentNames", align = ExcelField.Align.CENTER, sort=20),
            @ExcelField(title="中文名", attrName="chineseName",align = ExcelField.Align.CENTER, sort=30),
            @ExcelField(title="竞猜业绩", attrName="achievement",align = ExcelField.Align.CENTER, sort=40),
            @ExcelField(title="中奖码 ", attrName="prizeType",align = ExcelField.Align.CENTER, sort=45),
            @ExcelField(title="是否中奖 ", attrName="winningPrize",align = ExcelField.Align.CENTER, sort=45),
            @ExcelField(title="职级 ", attrName="jobLevel",align = ExcelField.Align.CENTER, sort=45),
    })

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
    private List<DingDepartment> dingDepartmentList = ListUtils.newArrayList();		// 子表列表
    //private List<DingRole> dingRoleList = ListUtils.newArrayList();		// 子表列表
    private Long convertibleGold;          	//可兑换梵钻
    private Long inDepartmentGold;			//部门内梵钻
    private Long outDepartmentGold;			//跨部门梵钻
    private String departmentNames;//部门名称
    private String chineseName;//中文名
    private String sex;//性别
    private String roleNames;//角色名
    private String departmentId;
    private String left;//是否离职
    private String uvan_token;
    private String praiserName;
    private String presenterName;

    private String winningPrize;
    private String prizeType;
    private String achievement;
    private String userStatus;
    private String removeId;//剔除当前用户
    private String userIsVip;//是否特权用户 1:是,0:不是
    private String isManager;	//

    private Double usedPoint; //已消费的总梵砖数量
    private Double	freezePoint;	//当前锁定积分数
    private String directSuperior;      //直属上级
    private String departmentHeader;    //部门长（上上级）
    private DingUser dingUser1;
    private DingUser dingUser2;
    private String jobLevel;
    private boolean customized;
    private List<List<DepartmentData>> parentDeptlist = ListUtils.newArrayList();//用户部门层级明细


    public boolean isCustomized() {
        return customized;
    }

    public void setCustomized(boolean customized) {
        this.customized = customized;
    }

    public String getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel;
    }

    public Double getUsedPoint() {
        return usedPoint;
    }

    public void setUsedPoint(Double usedPoint) {
        this.usedPoint = usedPoint;
    }

    public Double getFreezePoint() {
        return freezePoint;
    }

    public void setFreezePoint(Double freezePoint) {
        this.freezePoint = freezePoint;
    }


    public DingUser getDingUser1() {
        return dingUser1;
    }

    public void setDingUser1(DingUser dingUser1) {
        this.dingUser1 = dingUser1;
    }

    public DingUser getDingUser2() {
        return dingUser2;
    }

    public void setDingUser2(DingUser dingUser2) {
        this.dingUser2 = dingUser2;
    }

    public String getIsManager() {
        return isManager;
    }

    public void setIsManager(String isManager) {
        this.isManager = isManager;
    }

    public String getDirectSuperior() {
        return directSuperior;
    }

    public void setDirectSuperior(String directSuperior) {
        this.directSuperior = directSuperior;
    }

    public String getDepartmentHeader() {
        return departmentHeader;
    }

    public void setDepartmentHeader(String departmentHeader) {
        this.departmentHeader = departmentHeader;
    }

    public String getRemoveId() {
        return removeId;
    }

    public void setRemoveId(String removeId) {
        this.removeId = removeId;
    }


    public String getUserIsVip() {
        return userIsVip;
    }

    public void setUserIsVip(String userIsVip) {
        this.userIsVip = userIsVip;
    }

    public String getWinningPrize() {
        return winningPrize;
    }

    public void setWinningPrize(String winningPrize) {
        this.winningPrize = winningPrize;
    }

    public String getPrizeType() {
        return prizeType;
    }

    public void setPrizeType(String prizeType) {
        this.prizeType = prizeType;
    }

    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getUvan_token() {
        return uvan_token;
    }

    public void setUvan_token(String uvan_token) {
        this.uvan_token = uvan_token;
    }

    public String getPraiserName() {
        return praiserName;
    }

    public void setPraiserName(String praiserName) {
        this.praiserName = praiserName;
    }

    public String getPresenterName() {
        return presenterName;
    }

    public void setPresenterName(String presenterName) {
        this.presenterName = presenterName;
    }

    public String getSpecialUser() {
        return specialUser;
    }

    public void setSpecialUser(String special) {
        this.specialUser = special;
    }

    private String specialUser;

    public String getleft() {
        return left;
    }

    public void setleft(String left) {
        this.left = left;
    }



    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }


    public String getDepartmentNames() {
        return departmentNames;
    }

    public void setDepartmentNames(String departmentNames) {
        this.departmentNames = departmentNames;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }



    public DingUser() {
        this(null);
    }

    public DingUser(String id){
        super(id);
    }

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


    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

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

    public String getIsLeaderInDepts() {
        return isLeaderInDepts;
    }

    public void setIsLeaderInDepts(String isLeaderInDepts) {
        this.isLeaderInDepts = isLeaderInDepts;
    }

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

    public String getIsSenior() {
        return isSenior;
    }

    public void setIsSenior(String isSenior) {
        this.isSenior = isSenior;
    }

    public List<DingDepartment> getDingDepartmentList() {
        return dingDepartmentList;
    }

    public void setDingDepartmentList(List<DingDepartment> dingDepartmentList) {
        this.dingDepartmentList = dingDepartmentList;
    }

    public Long getConvertibleGold() {
        return convertibleGold;
    }

    public void setConvertibleGold(Long convertibleGold) {
        this.convertibleGold = convertibleGold;
    }

    public Long getInDepartmentGold() {
        return inDepartmentGold;
    }

    public void setInDepartmentGold(Long inDepartmentGold) {
        this.inDepartmentGold = inDepartmentGold;
    }

    public Long getOutDepartmentGold() {
        return outDepartmentGold;
    }

    public void setOutDepartmentGold(Long outDepartmentGold) {
        this.outDepartmentGold = outDepartmentGold;
    }

	/*public List<DingRole> getDingRoleList() {
		return dingRoleList;
	}

	public void setDingRoleList(List<DingRole> dingRoleList) {
		this.dingRoleList = dingRoleList;
	}*/

    public List<List<DepartmentData>> getParentDeptlist() {
        return parentDeptlist;
    }

    public void setParentDeptlist(List<List<DepartmentData>> parentDeptlist) {
        this.parentDeptlist = parentDeptlist;
    }
}