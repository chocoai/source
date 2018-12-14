/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.appreciation.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * fz_leaderboardsEntity
 * @author dwh
 * @version 2018-09-26
 */
@Table(name="fz_leaderboards", alias="a", columns={
		@Column(name="leaderboard_code", attrName="leaderboardCode", label="leaderboard_code", isPK=true),
		@Column(name="userid", attrName="userid", label="userid"),
		@Column(name="name", attrName="name", label="name", queryType=QueryType.LIKE),
		@Column(name="jobnumber", attrName="jobnumber", label="jobnumber"),
		@Column(name="praisernumber", attrName="praisernumber", label="praisernumber"),
		@Column(name="avatar", attrName="avatar", label="avatar"),
	}, orderBy="a.leaderboard_code DESC"
)
public class FzLeaderboards extends DataEntity<FzLeaderboards> {
	
	private static final long serialVersionUID = 1L;
	private String leaderboardCode;		// leaderboard_code
	private String userid;		// userid
	private String name;		// name
	private String jobnumber;		// jobnumber
	private Long praisernumber;		// praisernumber
	private String avatar;		// avatar
	
	public FzLeaderboards() {
		this(null);
	}

	public FzLeaderboards(String id){
		super(id);
	}
	
	public String getLeaderboardCode() {
		return leaderboardCode;
	}

	public void setLeaderboardCode(String leaderboardCode) {
		this.leaderboardCode = leaderboardCode;
	}
	
	@Length(min=0, max=255, message="userid长度不能超过 255 个字符")
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	@Length(min=0, max=255, message="name长度不能超过 255 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="jobnumber长度不能超过 255 个字符")
	public String getJobnumber() {
		return jobnumber;
	}

	public void setJobnumber(String jobnumber) {
		this.jobnumber = jobnumber;
	}
	
	public Long getPraisernumber() {
		return praisernumber;
	}

	public void setPraisernumber(Long praisernumber) {
		this.praisernumber = praisernumber;
	}
	
	@Length(min=0, max=255, message="avatar长度不能超过 255 个字符")
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
}