package com.jeesite.modules.fz.appreciation.entity;

import java.io.Serializable;

/**
 * 梵赞滑动数据点击数据提供
 * @author easter
 * @data 2018/10/29 13:24
 */
public class FzAppreciationDate implements Serializable {
    private String userid;         //员工id
    private Long coinCounts;      // 获梵赞总数
    private Long coinNumber;		// 赠梵赞数量
    private Long leaderboards;    //收到梵赞总数排名
    private Long praiserNum;      //赞该员工的有多少人
    private Long presenterNum;    //该员工赞多少人

    public FzAppreciationDate() {
    }

    public FzAppreciationDate(String userid, Long coinCounts, Long coinNumber, Long leaderboards, Long praiserNum, Long presenterNum) {
        this.userid = userid;
        this.coinCounts = coinCounts;
        this.coinNumber = coinNumber;
        this.leaderboards = leaderboards;
        this.praiserNum = praiserNum;
        this.presenterNum = presenterNum;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Long getCoinCounts() {
        return coinCounts;
    }

    public void setCoinCounts(Long coinCounts) {
        this.coinCounts = coinCounts;
    }

    public Long getCoinNumber() {
        return coinNumber;
    }

    public void setCoinNumber(Long coinNumber) {
        this.coinNumber = coinNumber;
    }

    public Long getLeaderboards() {
        return leaderboards;
    }

    public void setLeaderboards(Long leaderboards) {
        this.leaderboards = leaderboards;
    }

    public Long getPraiserNum() {
        return praiserNum;
    }

    public void setPraiserNum(Long praiserNum) {
        this.praiserNum = praiserNum;
    }

    public Long getPresenterNum() {
        return presenterNum;
    }

    public void setPresenterNum(Long presenterNum) {
        this.presenterNum = presenterNum;
    }
}
