package com.jeesite.modules.fz.fzlogin.entity;

public class DingTalkData {

    private String jsticket;

    private String url;

    private String signature;

    private String nonceStr;

    private Long timeStamp;

    private String corpId;

    private String agentId;

    public String getJsticket() {
        return jsticket;
    }

    public void setJsticket(String jsticket) {
        this.jsticket = jsticket;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
}
