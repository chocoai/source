/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.group.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import com.jeesite.common.collect.ListUtils;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 团购信息表Entity
 * @author len
 * @version 2018-10-23
 */
@Table(name="${_prefix}group_purchase", alias="a", columns={
		@Column(name="purchase_code", attrName="purchaseCode", label="团购编码", isPK=true),
		@Column(name="wang_code", attrName="wangCode", label="团长", queryType=QueryType.LIKE),
		@Column(name="group_phone", attrName="groupPhone", label="团长手机号", queryType=QueryType.LIKE),
		@Column(name="goods_num", attrName="goodsNum", label="购买件数"),
		@Column(name="rebate", attrName="rebate", label="购买折扣"),
		@Column(name="create_time", attrName="createTime", label="创建时间"),
		@Column(name="synch_status", attrName="synchStatus", label="同步状态"),
		@Column(name="update_time", attrName="updateTime", label="更新时间"),
		@Column(name="total_num", attrName="totalNum", label="购买总件数"),
	}, orderBy="a.purchase_code DESC"
)
public class GroupPurchase extends DataEntity<GroupPurchase> {
	
	private static final long serialVersionUID = 1L;
	private String purchaseCode;		// 团购编码
	private String wangCode;		// 团长
	private String groupPhone;		// 团长手机号
	private Long goodsNum;		// 购买件数
	private String rebate;		// 购买折扣
	private Date createTime;		// 创建时间
	private String synchStatus;		// 同步状态
	private Date updateTime;		// 更新时间
	private Long totalNum;			// 购买总件数

	public Long getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Long totalNum) {
		this.totalNum = totalNum;
	}

	private List<GroupDetail> groupDetailList = ListUtils.newArrayList();		// 子表列表
	
	public GroupPurchase() {
		this(null);
	}

	public GroupPurchase(String id){
		super(id);
	}
	
	public String getPurchaseCode() {
		return purchaseCode;
	}

	public void setPurchaseCode(String purchaseCode) {
		this.purchaseCode = purchaseCode;
	}
	
	@Length(min=0, max=64, message="团长长度不能超过 64 个字符")
	public String getWangCode() {
		return wangCode;
	}

	public void setWangCode(String wangCode) {
		this.wangCode = wangCode;
	}
	
	@Length(min=0, max=64, message="团长手机号长度不能超过 64 个字符")
	public String getGroupPhone() {
		return groupPhone;
	}

	public void setGroupPhone(String groupPhone) {
		this.groupPhone = groupPhone;
	}
	
	public Long getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(Long goodsNum) {
		this.goodsNum = goodsNum;
	}
	
	@Length(min=0, max=5, message="购买折扣长度不能超过 5 个字符")
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
	
	@Length(min=0, max=10, message="同步状态长度不能超过 10 个字符")
	public String getSynchStatus() {
		return synchStatus;
	}

	public void setSynchStatus(String synchStatus) {
		this.synchStatus = synchStatus;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public List<GroupDetail> getGroupDetailList() {
		return groupDetailList;
	}

	public void setGroupDetailList(List<GroupDetail> groupDetailList) {
		this.groupDetailList = groupDetailList;
	}
	
}