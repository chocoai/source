/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.card.entity;

/**
 * 绩效卡查询数据
 * @author PhilipGuan
 * @version 2018-11-20
 */
public class AchCardGroupData {

	private String userId;		// 用户id
	private String userName;		// 用户姓名
	private Double addSubScore;		// 加减分
	private Double examineScore;		// 考核分
	private String cardCode;	//绩效卡号
	private String dataStatus;	//绩效卡状态
	private Double target;		// 指标总分
	private Double mission;		// 任务总分
	private Double senseWorth;	//价值观
	private Double synthetical;		// 基础素养总分
    private String examineMonth;    //月份
    private String avatar;          //头像
    private String departmentName;  //部门名称
    private String position;        //岗位
    private String jobnumber;       //工号




	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Double getAddSubScore() {
		return addSubScore;
	}

	public void setAddSubScore(Double addSubScore) {
		this.addSubScore = addSubScore;
	}

	public Double getExamineScore() {
		return examineScore;
	}

	public void setExamineScore(Double examineScore) {
		this.examineScore = examineScore;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}

	public Double getTarget() {
		return target;
	}

	public void setTarget(Double target) {
		this.target = target;
	}

	public Double getMission() {
		return mission;
	}

	public void setMission(Double mission) {
		this.mission = mission;
	}

	public Double getSenseWorth() {
		return senseWorth;
	}

	public void setSenseWorth(Double senseWorth) {
		this.senseWorth = senseWorth;
	}

	public Double getSynthetical() {
		return synthetical;
	}

	public void setSynthetical(Double synthetical) {
		this.synthetical = synthetical;
	}

    public String getExamineMonth() {
        return examineMonth;
    }

    public void setExamineMonth(String examineMonth) {
        this.examineMonth = examineMonth;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getJobnumber() {
        return jobnumber;
    }

    public void setJobnumber(String jobnumber) {
        this.jobnumber = jobnumber;
    }
}