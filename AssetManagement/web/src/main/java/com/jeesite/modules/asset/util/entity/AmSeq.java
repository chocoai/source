/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.util.entity;

import javax.validation.constraints.NotNull;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * js_am_seqEntity
 * @author czy
 * @version 2018-04-24
 */
@Table(name="${_prefix}am_seq", alias="a", columns={
		@Column(name="perfix", attrName="perfix", label="perfix", isPK=true),
		@Column(name="seq", attrName="seq", label="seq"),
	}, orderBy="a.perfix DESC"
)
public class AmSeq extends DataEntity<AmSeq> {
	
	private static final long serialVersionUID = 1L;
	private String perfix;		// perfix
	private Long seq;		// seq
	public AmSeq() {
		this(null);
	}

	public AmSeq(String id){
		super(id);
	}
	
	public String getPerfix() {
		return perfix;
	}

	public void setPerfix(String perfix) {
		this.perfix = perfix;
	}
	
	@NotNull(message="seq不能为空")
	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}
	
}