package com.jeesite.modules.asset.ding.entity;

import com.jeesite.common.collect.ListUtils;

import java.util.Date;
import java.util.List;

public class UserData {
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
    //private String roleid;		// 角色id（ISV不可见）
    private String stateCode;		// 手机号码区号
    private String isSenior;		// 是否是高管
    private List<DingDepartment> dingDepartmentList = ListUtils.newArrayList();		// 子表列表
    //private List<DingRole> dingRoleList = ListUtils.newArrayList();		// 子表列表
    private Long convertibleGold;          //可兑换梵钻
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
    private String isManager;
    private String directSuperior;
    private String departmentHeader;

    private UserData agentDingUser; //我代理的人


    public String getUvan_token() {
        return uvan_token;
    }

    public void setUvan_token(String uvan_token) {
        this.uvan_token = uvan_token;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getHiredDate() {
        return hiredDate;
    }

    public void setHiredDate(Date hiredDate) {
        this.hiredDate = hiredDate;
    }

    public String getJobnumber() {
        return jobnumber;
    }

    public void setJobnumber(String jobnumber) {
        this.jobnumber = jobnumber;
    }

    public String getExtattr() {
        return extattr;
    }

    public void setExtattr(String extattr) {
        this.extattr = extattr;
    }

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

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
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


    public UserData getAgentDingUser() {
        return agentDingUser;
    }

    public void setAgentDingUser(UserData agentDingUser) {
        this.agentDingUser = agentDingUser;
    }
}
