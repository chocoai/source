/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.tianmao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;

import java.util.Date;

/**
 * tb_tianmao_itemsEntity
 * @author jace
 * @version 2018-07-04
 */
@Table(name="tb_tianmao_items", alias="a", columns={
		@Column(name="id", attrName="id", label="主键ID", isPK=true),
		@Column(name="body", attrName="body", label="json格式数据"),
		@Column(name="time", attrName="time", label="接收时间"),
	}, orderBy="a.id DESC"
)
public class TbTianmaoItems extends DataEntity<TbTianmaoItems> {
	
	private static final long serialVersionUID = 1L;
	private String body;		// json格式数据
	private Date time;		// 接收时间
	
	public TbTianmaoItems() {
		this(null);
	}

	public TbTianmaoItems(String id){
		super(id);
	}
	
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
}