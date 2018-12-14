/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.useragent.entity;

import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.sys.entity.DictData;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 绩效卡用户代理Entity
 * @author Philip Guan
 * @version 2018-11-23
 */
@Table(name="ach_user_agent", alias="a", columns={
		@Column(name="user_agent_code", attrName="userAgentCode", label="主键", isPK=true),
		@Column(name="user_id", attrName="userId", label="被代理人编码"),
		@Column(name="agent_user_id", attrName="agentUserId", label="代理人编码"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="audit_by", attrName="auditBy", label="审核人"),
		@Column(name="audit_date", attrName="auditDate", label="审核时间"),},
		joinTable = {
				@JoinTable(type = JoinTable.Type.LEFT_JOIN, entity = DingUser.class, alias = "b",
						on = "a.agent_user_id = b.userid", attrName = "dingUser",
						columns = {
								@Column(name = "userid", label = "id", isPK = true),
								@Column(name = "name", label = "姓名", isQuery = false),
								@Column(name = "jobnumber", label = "工号", isQuery = false),
								@Column(name = "mobile", label = "手机", isQuery = false),
								@Column(name = "position", label = "职位", isQuery = false)
						}),
	}, orderBy=" a.update_date DESC"
)
public class AchUserAgent extends DataEntity<AchUserAgent> {
	
	private static final long serialVersionUID = 1L;
	private String userAgentCode;		// 主键
	private String userId;		// 被代理人编码
	private String agentUserId;		// 代理人编码
	private String auditBy;		// 审核人
	private Date auditDate;		// 审核时间

	private DingUser dingUser;
	
	public AchUserAgent() {
		this(null);
	}

	public AchUserAgent(String id){
		super(id);
	}
	
	public String getUserAgentCode() {
		return userAgentCode;
	}

	public void setUserAgentCode(String userAgentCode) {
		this.userAgentCode = userAgentCode;
	}
	
	@Length(min=0, max=64, message="被代理人编码长度不能超过 64 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@NotBlank(message="代理人编码不能为空")
	@Length(min=0, max=64, message="代理人编码长度不能超过 64 个字符")
	public String getAgentUserId() {
		return agentUserId;
	}

	public void setAgentUserId(String agentUserId) {
		this.agentUserId = agentUserId;
	}
	
	@Length(min=0, max=64, message="审核人长度不能超过 64 个字符")
	public String getAuditBy() {
		return auditBy;
	}

	public void setAuditBy(String auditBy) {
		this.auditBy = auditBy;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}


	public DingUser getDingUser() {
		return dingUser;
	}

	public void setDingUser(DingUser dingUser) {
		this.dingUser = dingUser;
	}
}