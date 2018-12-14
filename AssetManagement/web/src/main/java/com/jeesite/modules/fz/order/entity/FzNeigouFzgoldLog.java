/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.order.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 梵砖积分操作表Entity
 * @author easter
 * @version 2018-11-27
 */
@Table(name="fz_neigou_fzgold_log", alias="a", columns={
		@Column(name="log_id", attrName="logId", label="日志id", isPK=true),
		@Column(name="action", attrName="action", label="执行类型"),
		@Column(name="create_time", attrName="createTime", label="操作时间"),
		@Column(name="user_name", attrName="userName", label="用户名字", queryType=QueryType.LIKE),
		@Column(name="user_id", attrName="userId", label="用户id"),
		@Column(name="action_result", attrName="actionResult", label="执行结果"),
		@Column(name="order_id", attrName="orderId", label="订单号"),
	}, orderBy="a.log_id DESC"
)
public class FzNeigouFzgoldLog extends DataEntity<FzNeigouFzgoldLog> {
	
	private static final long serialVersionUID = 1L;
	private String logId;		// 日志id
	private String action;		// 执行类型,1:查询积分,2:锁定积分,3:锁定积分取消,4;锁定积分确定,5:退款
	private Date createTime;		// 操作时间
	private String userName;		// 用户名字
	private String userId;		// 用户id
	private String actionResult;		// 执行结果
	private String orderId;		// 订单号
	
	public FzNeigouFzgoldLog() {
		this(null);
	}

	public FzNeigouFzgoldLog(String id){
		super(id);
	}
	
	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}
	
	@Length(min=0, max=1, message="执行类型长度不能超过 64个字符")
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=0, max=64, message="用户名字长度不能超过 64 个字符")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Length(min=0, max=64, message="用户id长度不能超过 64 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getActionResult() {
		return actionResult;
	}

	public void setActionResult(String actionResult) {
		this.actionResult = actionResult;
	}
	
	@Length(min=0, max=64, message="订单号长度不能超过 64 个字符")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
}