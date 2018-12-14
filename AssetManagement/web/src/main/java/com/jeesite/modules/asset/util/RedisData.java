package com.jeesite.modules.asset.util;

import java.io.Serializable;
import java.util.Date;

public class RedisData implements Serializable {
    private String userCode;
    private String dateTime;
    private int flag=0;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
