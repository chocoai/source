package com.jeesite.modules.fz.appreciation.returnData;

import java.util.List;

public class LeaderboardData {
    private String appreciationCode;
    private String userid;
    private String name;
    private String jobnumber;
    private List<String> department;
    private List<String> departmentName;
    private long praiserNumber;            //获赞总数
    private String avatar;
    private int sotNo;                //本人排序，如果为0则不在排行榜
    private String tag;
    private Long coinCounts;
    private String position;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Long getCoinCounts() {
        return coinCounts;
    }

    public void setCoinCounts(Long coinCounts) {
        this.coinCounts = coinCounts;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getSotNo() {
        return sotNo;
    }

    public void setSotNo(int sotNo) {
        this.sotNo = sotNo;
    }

    public String getAppreciationCode() {
        return appreciationCode;
    }

    public void setAppreciationCode(String appreciationCode) {
        this.appreciationCode = appreciationCode;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getPraiserNumber() {
        return praiserNumber;
    }

    public void setPraiserNumber(long praiserNumber) {
        this.praiserNumber = praiserNumber;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobnumber() {
        return jobnumber;
    }

    public void setJobnumber(String jobnumber) {
        this.jobnumber = jobnumber;
    }

    public List<String> getDepartment() {
        return department;
    }

    public void setDepartment(List<String> department) {
        this.department = department;
    }

    public List<String> getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(List<String> departmentName) {
        this.departmentName = departmentName;
    }
}
