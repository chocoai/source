/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.appreciation.entity;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.modules.asset.ding.entity.DingUser;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 赞赏记录表Entity
 * @author dwh
 * @version 2018-09-19
 */
@Table(name="fz_appreciation_follow", alias="a", columns={
		@Column(name="appreciation_follow_code", attrName="appreciationFollowCode", label="跟赞主键", isPK=true),
		@Column(name="appreciation_code", attrName="appreciationCode.appreciationCode", label="赞赏表主键"),
		@Column(name="presenter_id", attrName="presenterId", label="跟赞者"),
		@Column(name="coin_number", attrName="coinNumber", label="赠币数量"),
		@Column(name="content", attrName="content", label="更赞理由"),
		@Column(includeEntity=DataEntity.class),
	}, joinTable = {
		@JoinTable(type = JoinTable.Type.LEFT_JOIN, entity = DingUser.class, alias = "b",
				on = "a.presenter_id = b.userid",
				columns = {@Column(name = "name", attrName = "presenterName", label = "跟赞者名称"),
				}),
},

		orderBy="a.create_date ASC"
)
public class FzAppreciationFollow extends DataEntity<FzAppreciationFollow> {
	
	private static final long serialVersionUID = 1L;
	private String appreciationFollowCode;		// 跟赞主键
	private FzAppreciationRecord appreciationCode;		// 赞赏表主键 父类
	private String presenterId;		// 跟赞者
	private String presenterName;		// 跟赞名字
	private Long coinNumber;		// 赠币数量
	private String content;		// 更赞理由
	private String recordCode;   /// 赞赏表主键，前端传进来使用
	private String depName;
	private DingUser dingUser;
	private String followCode;

	public String getFollowCode() {
		return followCode;
	}

	public void setFollowCode(String followCode) {
		this.followCode = followCode;
	}

	public DingUser getDingUser() {
		return dingUser;
	}

	public void setDingUser(DingUser dingUser) {
		this.dingUser = dingUser;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getPresenterName() {
		return presenterName;
	}

	public void setPresenterName(String presenterName) {
		this.presenterName = presenterName;
	}

	public String getRecordCode() {
		return recordCode;
	}

	public void setRecordCode(String recordCode) {
		this.recordCode = recordCode;
	}

	public FzAppreciationFollow() {
		this(null);
	}


	public FzAppreciationFollow(FzAppreciationRecord appreciationCode){
		this.appreciationCode = appreciationCode;
	}
	
	public String getAppreciationFollowCode() {
		return appreciationFollowCode;
	}

	public void setAppreciationFollowCode(String appreciationFollowCode) {
		this.appreciationFollowCode = appreciationFollowCode;
	}
	
	@Length(min=0, max=255, message="赞赏表主键长度不能超过 255 个字符")
	public FzAppreciationRecord getAppreciationCode() {
		return appreciationCode;
	}

	public void setAppreciationCode(FzAppreciationRecord appreciationCode) {
		this.appreciationCode = appreciationCode;
	}
	
	@NotBlank(message="跟赞者不能为空")
	@Length(min=0, max=255, message="跟赞者长度不能超过 255 个字符")
	public String getPresenterId() {
		return presenterId;
	}

	public void setPresenterId(String presenterId) {
		this.presenterId = presenterId;
	}
	
	@NotNull(message="赠币数量不能为空")
	public Long getCoinNumber() {
		return coinNumber;
	}

	public void setCoinNumber(Long coinNumber) {
		this.coinNumber = coinNumber;
	}
	
	@Length(min=0, max=1000, message="更赞理由长度不能超过 1000 个字符")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}