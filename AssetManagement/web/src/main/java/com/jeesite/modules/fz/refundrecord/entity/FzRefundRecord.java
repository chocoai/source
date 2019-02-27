/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.refundrecord.entity;

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
 * 商城退款记录Entity
 * @author len
 * @version 2019-01-04
 */
@Table(name="fz_refund_record", alias="a", columns={
		@Column(name="refund_code", attrName="refundCode", label="商城退款记录", isPK=true),
		@Column(name="user_id", attrName="userId", label="用户id"),
		@Column(name="refund_num", attrName="refundNum", label="梵钻数量"),
		@Column(name="refund_time", attrName="refundTime", label="退款时间"),
		@Column(name="refund_mode", attrName="refundMode", label="退款方式"),
		@Column(name="user_name", attrName="userName", label="用户名", queryType=QueryType.LIKE),
	}, orderBy="a.refund_code DESC"
)
public class FzRefundRecord extends DataEntity<FzRefundRecord> {
	
	private static final long serialVersionUID = 1L;
	private String refundCode;		// 商城退款记录
	private String userId;		// 用户id
	private Double refundNum;		// 梵钻数量
	private Date refundTime;		// 退款时间
	private String refundMode;		// 退款方式
	private String userName;		// 用户名
	
	public FzRefundRecord() {
		this(null);
	}

	public FzRefundRecord(String id){
		super(id);
	}
	
	public String getRefundCode() {
		return refundCode;
	}

	public void setRefundCode(String refundCode) {
		this.refundCode = refundCode;
	}
	
	@Length(min=0, max=64, message="用户id长度不能超过 64 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public Double getRefundNum() {
		return refundNum;
	}

	public void setRefundNum(Double refundNum) {
		this.refundNum = refundNum;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}
	
	@Length(min=0, max=64, message="退款方式长度不能超过 64 个字符")
	public String getRefundMode() {
		return refundMode;
	}

	public void setRefundMode(String refundMode) {
		this.refundMode = refundMode;
	}
	
	@Length(min=0, max=64, message="用户名长度不能超过 64 个字符")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}