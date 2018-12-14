package com.jeesite.modules.asset.wechat.entity;

import java.util.Date;

/**
 * 梵工厂日志实体类
 *
 * @author Jace Xiong
 */
public class WechatLog {
    private String wxuser;
    private String sysuser;
    private String k3user;
    private String url;
    private String action;
    private String result;
    private Date actionTime;

    public String getWxuser() {
        return wxuser;
    }

    public void setWxuser(String wxuser) {
        this.wxuser = wxuser;
    }

    public String getSysuser() {
        return sysuser;
    }

    public void setSysuser(String sysuser) {
        this.sysuser = sysuser;
    }

    public String getK3user() {
        return k3user;
    }

    public void setK3user(String k3user) {
        this.k3user = k3user;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Date getActionTime() {
        return actionTime;
    }

    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
    }
}
