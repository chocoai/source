package com.jeesite.modules.asset.ding.entity;

import java.io.Serializable;

/**
 * 用户锁定积分实体类
 * @author easter
 * @data 2018/11/22 9:18
 */
public class UserLockIntegral implements Serializable {
    private String member_bn;          //对接平台用户标识,userid
    private Double money;             //锁定积分数（折算成RMB)
    private Double point;              //积分数量
    private String trade_no;            //交易流水号
    private String refund_id;           //退款流水号

    public UserLockIntegral() {
    }

    public String getRefund_id() {
        return refund_id;
    }

    public void setRefund_id(String refund_id) {
        this.refund_id = refund_id;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getMember_bn() {
        return member_bn;
    }

    public void setMember_bn(String member_bn) {
        this.member_bn = member_bn;
    }


    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getPoint() {
        return point;
    }

    public void setPoint(Double point) {
        this.point = point;
    }
}
