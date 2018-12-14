/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.group.entity;

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
 * 团购信息表Entity
 * @author len
 * @version 2018-10-23
 */
@Table(name="${_prefix}group_detail", alias="a", columns={
		@Column(name="detail_code", attrName="detailCode", label="团员明细编码", isPK=true),
		@Column(name="purchase_code", attrName="purchaseCode.purchaseCode", label="团购编码"),
		@Column(name="member_wang_code", attrName="memberWangCode", label="团员"),
		@Column(name="goods_num", attrName="goodsNum", label="购买件数"),
		@Column(name="rebate", attrName="rebate", label="折扣"),
		@Column(name="create_time", attrName="createTime", label="创建时间"),
	}, orderBy="a.detail_code ASC"
)
public class GroupDetail extends DataEntity<GroupDetail> {
	
	private static final long serialVersionUID = 1L;
	private String detailCode;		// 团员明细编码
	private GroupPurchase purchaseCode;		// 团购编码 父类
	private String memberWangCode;		// 团员
	private Long goodsNum;		// 购买件数
	private String rebate;		// 折扣
	private Date createTime;		// 创建时间
	
	public GroupDetail() {
		this(null);
	}


	public GroupDetail(GroupPurchase purchaseCode){
		this.purchaseCode = purchaseCode;
	}
	
	public String getDetailCode() {
		return detailCode;
	}

	public void setDetailCode(String detailCode) {
		this.detailCode = detailCode;
	}
	
	@Length(min=0, max=64, message="团购编码长度不能超过 64 个字符")
	public GroupPurchase getPurchaseCode() {
		return purchaseCode;
	}

	public void setPurchaseCode(GroupPurchase purchaseCode) {
		this.purchaseCode = purchaseCode;
	}
	
	@Length(min=0, max=64, message="团员长度不能超过 64 个字符")
	public String getMemberWangCode() {
		return memberWangCode;
	}

	public void setMemberWangCode(String memberWangCode) {
		this.memberWangCode = memberWangCode;
	}
	
	public Long getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(Long goodsNum) {
		this.goodsNum = goodsNum;
	}
	
	@Length(min=0, max=5, message="折扣长度不能超过 5 个字符")
	public String getRebate() {
		return rebate;
	}

	public void setRebate(String rebate) {
		this.rebate = rebate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}