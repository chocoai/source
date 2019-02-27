package com.jeesite.modules.storevideo.ovopark.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class SvOvoparkUserDTO implements Serializable {

    @JSONField(name = "userid")
    private String userId;		// 主键
    @JSONField(name = "orgid")
    private Long orgId;		// 企业id
    @JSONField(name = "departno")
    private Long departNo;		// 门店id
    @JSONField(name = "username")
    private String userName;		// 用户姓名
    @JSONField(name = "membertype")
    private String memberType;		// 用户类型(1:顾客,2:会员,3:店员)
    @JSONField(name = "mobilephone")
    private String mobilePhone;		// 手机号码
    private String gender;		// 性别(男0/女1)
    private String thirdpicurl;		// 图片地址
    private Long checkrepeat;		// 是否验证重复手机号(1/0)

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

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
}
