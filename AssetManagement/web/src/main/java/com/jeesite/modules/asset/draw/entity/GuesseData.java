package com.jeesite.modules.asset.draw.entity;

import java.util.Date;

public class GuesseData {
    /**
     * 用户Id
     */
    private String userId;
    /**
     * 是否过期
     */
    private boolean flag;
    /**
     * 截止时间
     */
    private String date;
    /**
     * 竞猜业绩
     */
    private String achievement;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }
}
