/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.fzgoldchangerecord.entity;

import javax.validation.constraints.NotNull;

import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.modules.asset.ding.entity.DingUser;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import java.util.Date;

/**
 * 梵钻变更记录Entity
 * @author dwh
 * @version 2018-09-21
 */
@Table(name="fz_gold_change_record", alias="a", columns={
		@Column(name="record_code", attrName="recordCode", label="梵钻变更记录主键", isPK=true),
		@Column(name="number", attrName="number", label="数量"),
		@Column(name="gold_type", attrName="goldType", label="梵钻类型"),
		@Column(name="in_or_out", attrName="inOrOut", label="收支"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="userid", attrName="userid", label="员工"),
		@Column(name="balance", attrName="balance", label="余额"),
		@Column(name="record_name", attrName="recordName", label="变更名字"),
	}, joinTable={
		@JoinTable(type=JoinTable.Type.LEFT_JOIN, entity=DingUser.class, alias="b",
				on="a.userid=b.userid ",
				columns={	@Column(name="userid", attrName="userid",label="用户id", isPK=true),
						@Column(name="name",attrName="name", label="用户名", isQuery=false,queryType=QueryType.LIKE),})
}, orderBy="a.update_date DESC"
)
public class FzGoldChangeRecord extends DataEntity<FzGoldChangeRecord> {
	
	private static final long serialVersionUID = 1L;
	private String recordCode;		// 梵钻变更记录主键
	private Long number;		// 数量
	private String goldType;		// 梵钻类型 0，兑币，1:部门内币，2：跨部门币'
	private String inOrOut;		// 收支
	private String userid;		// 员工
	private Long balance;          //余额
	private String userName;
	private DingUser dingUser;
	private String inOrOutName;
	private String goldTypeName;
	private String recordName;

	private Integer type;         //判断是跟赞还是赞赏
	private Date msgDate;         //跟赞时间

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getMsgDate() {
		return msgDate;
	}

	public void setMsgDate(Date msgDate) {
		this.msgDate = msgDate;
	}

	public String getRecordName() {
		return recordName;
	}

	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}

	public String getInOrOutName() {
		return inOrOutName;
	}

	public void setInOrOutName(String inOrOutName) {
		this.inOrOutName = inOrOutName;
	}

	public String getGoldTypeName() {
		return goldTypeName;
	}

	public void setGoldTypeName(String goldTypeName) {
		this.goldTypeName = goldTypeName;
	}

	public DingUser getDingUser() {
		return dingUser;
	}

	public void setDingUser(DingUser dingUser) {
		this.dingUser = dingUser;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	//	public String getUserName() {
//		return userName;
//	}
//
//	public void setUserName(String userName) {
//		this.userName = userName;
//	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}

	public FzGoldChangeRecord() {
		this(null);
	}

	public FzGoldChangeRecord(String id){
		super(id);
	}
	
	public String getRecordCode() {
		return recordCode;
	}

	public void setRecordCode(String recordCode) {
		this.recordCode = recordCode;
	}
	
	@NotNull(message="数量不能为空")
	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}
	
	@NotBlank(message="梵钻类型不能为空")
	@Length(min=0, max=20, message="梵钻类型长度不能超过 20 个字符")
	public String getGoldType() {
		return goldType;
	}

	public void setGoldType(String goldType) {
		this.goldType = goldType;
	}
	
	@NotBlank(message="收支不能为空")
	@Length(min=0, max=2, message="收支长度不能超过 2 个字符")
	public String getInOrOut() {
		return inOrOut;
	}

	public void setInOrOut(String inOrOut) {
		this.inOrOut = inOrOut;
	}
	
	@NotBlank(message="员工不能为空")
	@Length(min=0, max=255, message="员工长度不能超过 255 个字符")
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
}