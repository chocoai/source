package com.jeesite.modules.dingding.entity;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.collect.ListUtils;
import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;


public class DingApiUser {

    private String orderInDepts;
    private String position;
    private String remark;
    private List<Integer> department;
    private String unionid;
    private String tel;
    private String userid;
    private Boolean isSenior;
    private String dingId;
    private String workPlace;
    private Boolean isBoss;
    private String name;
    private String errmsg;
    private String extattr;
    private String stateCode;
    private String avatar;
    private Integer errcode;
    private String jobnumber;
    private String isLeaderInDepts;
    private String email;
    private Boolean active;
    private Boolean isAdmin;
    private String openId;
    private Long hiredDate;
    private String mobile;
    private Boolean isHide;
    private List<DingApiRole> roles;


    public Long getHiredDate() {
        return hiredDate;
    }

    public void setHiredDate(Long hiredDate) {
        this.hiredDate = hiredDate;
    }
    public Boolean getSenior() {
        return isSenior;
    }

    public void setSenior(Boolean senior) {
        isSenior = senior;
    }

    public Boolean getBoss() {
        return isBoss;
    }

    public void setBoss(Boolean boss) {
        isBoss = boss;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Boolean getHide() {
        return isHide;
    }

    public void setHide(Boolean hide) {
        isHide = hide;
    }

    public List<DingApiRole> getRoles() {
        return roles;
    }

    public void setRoles(List<DingApiRole> roles) {
        this.roles = roles;
    }


    public void setOrderInDepts(String orderInDepts) {
        this.orderInDepts = orderInDepts;
    }
    public String getOrderInDepts() {
        return orderInDepts;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    public String getPosition() {
        return position;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getRemark() {
        return remark;
    }

    public void setDepartment(List<Integer> department) {
        this.department = department;
    }
    public List<Integer> getDepartment() {
        return department;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
    public String getUnionid() {
        return unionid;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
    public String getTel() {
        return tel;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getUserid() {
        return userid;
    }

    public void setIsSenior(Boolean isSenior) {
        this.isSenior = isSenior;
    }
    public Boolean getIsSenior() {
        return isSenior;
    }

    public void setDingId(String dingId) {
        this.dingId = dingId;
    }
    public String getDingId() {
        return dingId;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }
    public String getWorkPlace() {
        return workPlace;
    }

    public void setIsBoss(Boolean isBoss) {
        this.isBoss = isBoss;
    }
    public Boolean getIsBoss() {
        return isBoss;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
    public String getErrmsg() {
        return errmsg;
    }

    public void setExtattr(String extattr) {
        this.extattr = extattr;
    }
    public String getExtattr() {
        return extattr;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }
    public String getStateCode() {
        return stateCode;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getAvatar() {
        return avatar;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }
    public Integer getErrcode() {
        return errcode;
    }

    public void setJobnumber(String jobnumber) {
        this.jobnumber = jobnumber;
    }
    public String getJobnumber() {
        return jobnumber;
    }

    public void setIsLeaderInDepts(String isLeaderInDepts) {
        this.isLeaderInDepts = isLeaderInDepts;
    }
    public String getIsLeaderInDepts() {
        return isLeaderInDepts;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    public Boolean getActive() {
        return active;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
    public String getOpenId() {
        return openId;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getMobile() {
        return mobile;
    }

    public void setIsHide(Boolean isHide) {
        this.isHide = isHide;
    }
    public Boolean getIsHide() {
        return isHide;
    }

    public void CopyTo(DingUser dingUser){
        dingUser.setleft("0");
        setByJObject(this.getUserid(), dingUser::setUserid);
        setByJObject(this.getName(), dingUser::setName);
        setByJObject(this.getJobnumber(), dingUser::setJobnumber);
        setByJObject(this.getActive(), dingUser::setActive);
        setByJObject(this.getIsAdmin(), dingUser::setIsAdmin);
        setByJObject(this.getIsBoss(), dingUser::setIsBoss);
        setByJObject(this.getIsHide(), dingUser::setIsHide);
        setByJObject(this.getIsSenior(), dingUser::setIsSenior);
        setByJObject(this.getAvatar(), dingUser::setAvatar);
        setByJObject(this.getExtattr(), dingUser::setExtattr);
        if(!StringUtils.isEmpty(this.getExtattr())){
            JSONObject extAttrJObject = JSONObject.parseObject(this.getExtattr());
            if(!extAttrJObject.isEmpty()){
                if(extAttrJObject.containsKey("中文名")) dingUser.setChineseName(extAttrJObject.getString("中文名"));
                if(extAttrJObject.containsKey("性别")) dingUser.setSex(extAttrJObject.getString("性别"));
            } else {
                dingUser.setExtattr(null);
            }
        }
        setByJObjectDate(this.getHiredDate(), dingUser::setHiredDate);
        setByJObject(this.getEmail(), dingUser::setEmail);
        setByJObject(this.getIsLeaderInDepts(), dingUser::setIsLeaderInDepts);


        //setByJObject(this(), dingUser::setJobLevel);
        setByJObject(this.getOrderInDepts(), dingUser::setOrderinDepts);
        setByJObject(this.getMobile(), dingUser::setMobile);
        setByJObject(this.getOpenId(), dingUser::setOpenid);
        setByJObject(this.getRemark(), dingUser::setRemark);
        if(ListUtils.isNotEmpty(this.getRoles()))
            setByJObject(this.getRoles().stream().map(a->a.getName()).collect(Collectors.joining(",")), dingUser::setRoleNames);
        setByJObject(this.getWorkPlace(), dingUser::setWorkPlace);
        setByJObject(this.getUnionid(), dingUser::setUnionid);
        //setByJObject(this.getse(), dingUser::setSex);
        setByJObject(this.getStateCode(), dingUser::setStateCode);
        setByJObject(this.getPosition(), dingUser::setPosition);
        setByJObject(this.getTel(), dingUser::setTel);
    }


    public void setByJObject(String value, Consumer<String> setter){
        if(!StringUtils.isEmpty(value)){
            setter.accept(value);
        }
    }
    public void setByJObject(Boolean value, Consumer<String> setter){
        if(value != null)
            setter.accept(value ? "1" : "0");
    }
    public void setByJObject(Integer value, Consumer<String> setter){
        if(value != null)
            setter.accept(value.toString());
    }
    public void setByJObjectDate(Long value, Consumer<Date> setter){
        if(value != null){
            Date date = new Date(value);
            setter.accept(date);
        }
    }

}