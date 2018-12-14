package com.jeesite.modules.asset.draw.entity;

/**
 * 推送中奖消息
 */
public class PushMsg {
    /**
     * 要推送的用户
     */
    private String touser;
    /**
     * 发送内容
     */
    private ActionCard markdown;
    /**
     * 代理id
     */
    private String agentid;
    /**
     * 消息类型
     */
    private String msgtype;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public ActionCard getMarkdown() {
        return markdown;
    }

    public void setMarkdown(ActionCard markdown) {
        this.markdown = markdown;
    }

    public String getAgentid() {
        return agentid;
    }

    public void setAgentid(String agentid) {
        this.agentid = agentid;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }
}
