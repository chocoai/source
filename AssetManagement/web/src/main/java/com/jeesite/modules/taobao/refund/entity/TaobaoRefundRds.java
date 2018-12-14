/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.taobao.refund.entity;

import com.taobao.api.domain.Refund;
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
 * taobao_refund_rdsEntity
 * @author scarlett
 * @version 2018-10-18
 */
@Table(name="taobao_refund_rds", alias="a", columns={
		@Column(name="pkey", attrName="pkey", label="pkey", isPK=true,queryType=QueryType.LIKE),
		@Column(name="refund_id", attrName="refundId", label="退款单号",queryType=QueryType.LIKE),
		@Column(name="seller_nick", attrName="sellerNick", label="卖家昵称", queryType=QueryType.LIKE),
		@Column(name="buyer_nick", attrName="buyerNick", label="买家昵称", queryType=QueryType.LIKE),
		@Column(name="refund_status", attrName="refundStatus", label="退款状态"),
		@Column(name="created", attrName="created", label="created"),
		@Column(name="tid", attrName="tid", label="淘宝交易单号",queryType=QueryType.LIKE),
		@Column(name="oid", attrName="oid", label="子订单号"),
		@Column(name="modified", attrName="modified", label="modified"),
		@Column(name="jdp_response", attrName="jdpResponse", label="响应文本"),
		@Column(name="jdp_hashcode", attrName="jdpHashcode", label="jdp_hashcode"),
		@Column(name="jdp_created", attrName="jdpCreated", label="退款申请时间"),
		@Column(name="jdp_modified", attrName="jdpModified", label="更新时间"),
		@Column(name="syn_time", attrName="synTime", label="同步时间"),
	}, orderBy="a.pkey DESC"
)
public class TaobaoRefundRds extends DataEntity<TaobaoRefundRds> {
	
	private static final long serialVersionUID = 1L;
	private String pkey;		// pkey
	private Long refundId;		// 退款单号
	private String sellerNick;		// 卖家昵称
	private String buyerNick;		// 买家昵称
	private String refundStatus;		// 退款状态
	private Date created;		// created
	private Long tid;		// 淘宝交易单号
	private Long oid;		// 子订单号
	private Date modified;		// modified
	private String jdpResponse;		// 响应文本
	private String jdpHashcode;		// jdp_hashcode
	private Date jdpCreated;		// 退款申请时间
	private Date jdpModified;		// 更新时间
	private Date synTime;		// 同步时间


	private Refund refund;//淘宝refund实体
	
	public TaobaoRefundRds() {
		this(null);
	}

	public TaobaoRefundRds(String id){
		super(id);
	}
	
	public String getPkey() {
		return pkey;
	}

	public void setPkey(String pkey) {
		this.pkey = pkey;
	}
	
	public Long getRefundId() {
		return refundId;
	}

	public void setRefundId(Long refundId) {
		this.refundId = refundId;
	}
	
	@Length(min=0, max=32, message="卖家昵称长度不能超过 32 个字符")
	public String getSellerNick() {
		return sellerNick;
	}

	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}
	
	@Length(min=0, max=255, message="买家昵称长度不能超过 255 个字符")
	public String getBuyerNick() {
		return buyerNick;
	}

	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}
	
	@Length(min=0, max=64, message="退款状态长度不能超过 64 个字符")
	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}
	
	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}
	
	public String getJdpResponse() {
		return jdpResponse;
	}

	public void setJdpResponse(String jdpResponse) {
		this.jdpResponse = jdpResponse;
	}
	
	@Length(min=0, max=128, message="jdp_hashcode长度不能超过 128 个字符")
	public String getJdpHashcode() {
		return jdpHashcode;
	}

	public void setJdpHashcode(String jdpHashcode) {
		this.jdpHashcode = jdpHashcode;
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
	public Refund getRefund() {
		return refund;
	}

	public void setRefund(Refund refund) {
		this.refund = refund;
	}


	public Date getCreated_gte() {
		return sqlMap.getWhere().getValue("created", QueryType.GTE);
	}

	public void setCreated_gte(Date created) {
		sqlMap.getWhere().and("created", QueryType.GTE, created);
	}
	
	public Date getCreated_lte() {
		return sqlMap.getWhere().getValue("created", QueryType.LTE);
	}

	public void setCreated_lte(Date created) {
		sqlMap.getWhere().and("created", QueryType.LTE, created);
	}
	
	public Date getModified_gte() {
		return sqlMap.getWhere().getValue("modified", QueryType.GTE);
	}

	public void setModified_gte(Date modified) {
		sqlMap.getWhere().and("modified", QueryType.GTE, modified);
	}
	
	public Date getModified_lte() {
		return sqlMap.getWhere().getValue("modified", QueryType.LTE);
	}

	public void setModified_lte(Date modified) {
		sqlMap.getWhere().and("modified", QueryType.LTE, modified);
	}
	
	public Date getJdpModified_gte() {
		return sqlMap.getWhere().getValue("jdp_modified", QueryType.GTE);
	}

	public void setJdpModified_gte(Date jdpModified) {
		sqlMap.getWhere().and("jdp_modified", QueryType.GTE, jdpModified);
	}
	
	public Date getJdpModified_lte() {
		return sqlMap.getWhere().getValue("jdp_modified", QueryType.LTE);
	}

	public void setJdpModified_lte(Date jdpModified) {
		sqlMap.getWhere().and("jdp_modified", QueryType.LTE, jdpModified);
	}
	
	public Date getSynTime_gte() {
		return sqlMap.getWhere().getValue("syn_time", QueryType.GTE);
	}

	public void setSynTime_gte(Date synTime) {
		sqlMap.getWhere().and("syn_time", QueryType.GTE, synTime);
	}
	
	public Date getSynTime_lte() {
		return sqlMap.getWhere().getValue("syn_time", QueryType.LTE);
	}

	public void setSynTime_lte(Date synTime) {
		sqlMap.getWhere().and("syn_time", QueryType.LTE, synTime);
	}
	
}