/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.draw.entity;

import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import java.util.List;
import com.jeesite.common.collect.ListUtils;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 抽奖竞猜Entity
 * @author len
 * @version 2018-10-05
 */
@Table(name="${_prefix}am_luck_draw", alias="a", columns={
		@Column(name="document_code", attrName="documentCode", label="单据编号", isPK=true),
		@Column(name="bill_time", attrName="billTime", label="单据时间"),
		@Column(name="winning_code", attrName="winningCode", label="中奖码", queryType=QueryType.LIKE),
		@Column(name="winners_num", attrName="winnersNum", label="中奖人数"),
		@Column(name="push_status", attrName="pushStatus", label="推送状态"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.create_date DESC"
)
public class LuckDraw extends DataEntity<LuckDraw> {
	
	private static final long serialVersionUID = 1L;
	private String documentCode;		// 单据编号
	private Date billTime;		// 单据时间
	private String winningCode;		// 中奖码
	private Integer winnersNum;		// 中奖人数
	private String pushStatus;		// 推送状态
	private boolean isPushed;			// 是否已推送
	private List<LuckDetail> luckDetailList = ListUtils.newArrayList();		// 子表列表
	
	public LuckDraw() {
		this(null);
	}

	public LuckDraw(String id){
		super(id);
	}
	
	public String getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBillTime() {
		return billTime;
	}

	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}
	
	@Length(min=0, max=64, message="中奖码长度不能超过 64 个字符")
	public String getWinningCode() {
		return winningCode;
	}

	public void setWinningCode(String winningCode) {
		this.winningCode = winningCode;
	}
	
	public Integer getWinnersNum() {
		return winnersNum;
	}

	public void setWinnersNum(Integer winnersNum) {
		this.winnersNum = winnersNum;
	}
	
	@Length(min=0, max=1, message="推送状态长度不能超过 1 个字符")
	public String getPushStatus() {
		return pushStatus;
	}

	public void setPushStatus(String pushStatus) {
		this.pushStatus = pushStatus;
	}
	
	public List<LuckDetail> getLuckDetailList() {
		return luckDetailList;
	}

	public void setLuckDetailList(List<LuckDetail> luckDetailList) {
		this.luckDetailList = luckDetailList;
	}

	public boolean isPushed() {
		return isPushed;
	}

	public void setPushed(boolean pushed) {
		isPushed = pushed;
	}
}