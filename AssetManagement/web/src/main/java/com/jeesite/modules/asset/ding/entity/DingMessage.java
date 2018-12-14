package com.jeesite.modules.asset.ding.entity;

public class DingMessage {
    private String touser;
    private String agentid;
    private String msgtype;
    private ActionCard action_card;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
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

    public ActionCard getAction_card() {
        return action_card;
    }

    public void setAction_card(ActionCard action_card) {
        this.action_card = action_card;
    }
}
