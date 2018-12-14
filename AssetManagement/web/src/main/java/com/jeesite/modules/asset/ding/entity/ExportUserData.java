package com.jeesite.modules.asset.ding.entity;

public class ExportUserData {
    /**
     * 用户名
     */
    private String name;
    /**
     * 工号
     */
    private String jobnumber;
    /**
     * 部门名
     */
    private String departmentNames;
    /**
     * 中文名
     */
    private String chineseName;
    /**
     * 竞猜业绩
     */
    private String achievement;
    /**
     * 中奖码
     */
    private String prizeType;
    /**
     * 是否中奖
     */
    private String winningPrize;

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

    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }

    public String getPrizeType() {
        return prizeType;
    }

    public void setPrizeType(String prizeType) {
        this.prizeType = prizeType;
    }

    public String getWinningPrize() {
        return winningPrize;
    }

    public void setWinningPrize(String winningPrize) {
        this.winningPrize = winningPrize;
    }
}
