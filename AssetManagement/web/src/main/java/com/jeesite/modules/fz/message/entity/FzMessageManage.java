/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.message.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 梵赞消息推送Entity
 * @author scarlett
 * @version 2018-10-24
 */
@Table(name="fz_message_manage", alias="a", columns={
		@Column(name="pkey", attrName="pkey", label="单据号", isPK=true),
		@Column(name="touser", attrName="touser", label="接收用户",queryType=QueryType.LIKE),
		@Column(name="agend_id", attrName="agendId", label="应用id",queryType=QueryType.LIKE),
		@Column(name="title", attrName="title", label="消息提示文案", queryType=QueryType.LIKE),
		@Column(name="text", attrName="text", label="会话框文案"),
		@Column(name="singleurl", attrName="singleurl", label="跳转页面路径"),
		@Column(name="singletitle", attrName="singletitle", label="页面跳转显示标题"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="result", attrName="result", label="是否推送成功"),
	}, orderBy="a.update_date DESC"
)
public class FzMessageManage extends DataEntity<FzMessageManage> {
	
	private static final long serialVersionUID = 1L;
	private String pkey;		// 单据号
	private String touser;		// touser
	private String agendId;		// 应用id
	private String title;		// 消息提示文案
	private String text;		// 会话框文案
	private String singleurl;		// 跳转页面路径
	private String singletitle;		// 页面跳转显示标题
	private String result;		// 推送状态

	public String getTouserName() {
		return touserName;
	}

	public void setTouserName(String touserName) {
		this.touserName = touserName;
	}

	private String touserName;//接收用户名
	
	public FzMessageManage() {
		this(null);
	}

	public FzMessageManage(String id){
		super(id);
	}
	
	public String getPkey() {
		return pkey;
	}

	public void setPkey(String pkey) {
		this.pkey = pkey;
	}
	
	@NotBlank(message="touser不能为空")
	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}
	
	@NotBlank(message="应用id不能为空")
	@Length(min=0, max=64, message="应用id长度不能超过 64 个字符")
	public String getAgendId() {
		return agendId;
	}

	public void setAgendId(String agendId) {
		this.agendId = agendId;
	}
	
	@NotBlank(message="消息提示文案不能为空")
	@Length(min=0, max=500, message="消息提示文案长度不能超过 500 个字符")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@NotBlank(message="会话框文案不能为空")
	@Length(min=0, max=1000, message="会话框文案长度不能超过 1000 个字符")
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	@NotBlank(message="跳转页面路径不能为空")
	@Length(min=0, max=400, message="跳转页面路径长度不能超过 400 个字符")
	public String getSingleurl() {
		return singleurl;
	}

	public void setSingleurl(String singleurl) {
		this.singleurl = singleurl;
	}
	
	@NotBlank(message="页面跳转显示标题不能为空")
	@Length(min=0, max=500, message="页面跳转显示标题长度不能超过 500 个字符")
	public String getSingletitle() {
		return singletitle;
	}

	public void setSingletitle(String singletitle) {
		this.singletitle = singletitle;
	}
	
	@Length(min=0, max=1, message="推送状态长度不能超过 1 个字符")
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
}