/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.neigou.entity;

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
 * 梵钻内购退货流水表Entity
 * @author easter
 * @version 2018-12-03
 */
@Table(name="fz_neigou_refund", alias="a", columns={
		@Column(name="refund_id", attrName="refundId", label="退款流水号", isPK=true),
		@Column(name="order_id", attrName="orderId", label="订单编号"),
		@Column(name="refund_date", attrName="refundDate", label="退款时间"),
		@Column(name="refund_point", attrName="refundPoint", label="退款梵钻积分数"),
		@Column(name="user_id", attrName="userId", label="用户钉钉id"),
	}, orderBy="a.refund_id DESC"
)
public class FzNeigouRefund extends DataEntity<FzNeigouRefund> {
	
	private static final long serialVersionUID = 1L;
	private String refundId;		// 退款流水号
	private String orderId;		// 订单编号
	private Date refundDate;		// 退款时间
	private Double refundPoint;		// 退款梵钻积分数
	private String userId;		// 用户钉钉id
	
	public FzNeigouRefund() {
		this(null);
	}

	public FzNeigouRefund(String id){
		super(id);
	}
	
	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}
	
	@Length(min=0, max=64, message="订单编号长度不能超过 64 个字符")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}
	
	public Double getRefundPoint() {
		return refundPoint;
	}

	public void setRefundPoint(Double refundPoint) {
		this.refundPoint = refundPoint;
	}
	
	@Length(min=0, max=64, message="用户钉钉id长度不能超过 64 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}