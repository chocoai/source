/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.report.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 一级部门赞赏统计项目Entity
 * @author easter
 * @version 2018-11-14
 */
@Table(name="fz_statistical_item", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="item", attrName="item", label="统计类型"),
	}, orderBy="a.id DESC"
)
public class FzStatisticalItem extends DataEntity<FzStatisticalItem> {
	
	private static final long serialVersionUID = 1L;
	private String item;		// 统计类型
	
	public FzStatisticalItem() {
		this(null);
	}

	public FzStatisticalItem(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="统计类型长度不能超过 64 个字符")
	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
	
}