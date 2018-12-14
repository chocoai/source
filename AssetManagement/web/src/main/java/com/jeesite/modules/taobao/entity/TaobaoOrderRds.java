/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.taobao.entity;

import com.taobao.api.domain.Trade;
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
 * taobao_order_rdsEntity
 * @author scarlett
 * @version 2018-10-17
 */
@Table(name="taobao_order_rds", alias="a", columns={
		@Column(name="tid", attrName="tid", label="tid"),
		@Column(name="pkey", attrName="pkey", label="pkey", isPK=true,queryType=QueryType.LIKE),
		@Column(name="rds_status", attrName="rdsStatus", label="rds_status"),
		@Column(name="type", attrName="type", label="type"),
		@Column(name="seller_nick", attrName="sellerNick", label="seller_nick",queryType=QueryType.LIKE),
		@Column(name="buyer_nick", attrName="buyerNick", label="buyer_nick",queryType=QueryType.LIKE),
		@Column(name="created", attrName="created", label="created"),
		@Column(name="modified", attrName="modified", label="modified"),
		@Column(name="jdp_hashcode", attrName="jdpHashcode", label="jdp_hashcode"),
		@Column(name="jdp_response", attrName="jdpResponse", label="jdp_response"),
		@Column(name="jdp_created", attrName="jdpCreated", label="jdp_created"),
		@Column(name="jdp_modified", attrName="jdpModified", label="jdp_modified"),
		@Column(name="syn_time", attrName="synTime", label="syn_time"),
	}, orderBy="a.pkey DESC"
)
public class TaobaoOrderRds extends DataEntity<TaobaoOrderRds> {
	
	private static final long serialVersionUID = 1L;
	private Long tid;		// tid
	private String pkey;		// pkey
	private String rdsStatus;		// rds_status
	private String type;		// type
	private String sellerNick;		// seller_nick
	private String buyerNick;		// buyer_nick
	private Date created;		// created
	private Date modified;		// modified
	private String jdpHashcode;		// jdp_hashcode
	private String jdpResponse;		// jdp_response
	private Date jdpCreated;		// jdp_created
	private Date jdpModified;		// jdp_modified
	private Date synTime;		// syn_time

/*
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private String status;*/

	public Trade getTrade() {
		return trade;
	}

	public void setTrade(Trade trade) {
		this.trade = trade;
	}

	private Trade trade;
	
	public TaobaoOrderRds() {
		this(null);
	}

	public TaobaoOrderRds(String id){
		super(id);
	}

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public String getPkey() {
		return pkey;
	}

	public void setPkey(String pkey) {
		this.pkey = pkey;
	}
	
	@Length(min=0, max=64, message="rds_status长度不能超过 64 个字符")
	public String getRdsStatus() {
		return rdsStatus;
	}

	public void setRdsStatus(String rdsStatus) {
		this.rdsStatus = rdsStatus;
	}
	
	@Length(min=0, max=64, message="type长度不能超过 64 个字符")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=32, message="seller_nick长度不能超过 32 个字符")
	public String getSellerNick() {
		return sellerNick;
	}

	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}
	
	@Length(min=0, max=255, message="buyer_nick长度不能超过 255 个字符")
	public String getBuyerNick() {
		return buyerNick;
	}

	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}
	
	@Length(min=0, max=128, message="jdp_hashcode长度不能超过 128 个字符")
	public String getJdpHashcode() {
		return jdpHashcode;
	}

	public void setJdpHashcode(String jdpHashcode) {
		this.jdpHashcode = jdpHashcode;
	}
	
	public String getJdpResponse() {
		return jdpResponse;
	}

	public void setJdpResponse(String jdpResponse) {
		this.jdpResponse = jdpResponse;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getJdpCreated() {
		return jdpCreated;
	}

	public void setJdpCreated(Date jdpCreated) {
		this.jdpCreated = jdpCreated;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getJdpModified() {
		return jdpModified;
	}

	public void setJdpModified(Date jdpModified) {
		this.jdpModified = jdpModified;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSynTime() {
		return synTime;
	}

	public void setSynTime(Date synTime) {
		this.synTime = synTime;
	}
	
}