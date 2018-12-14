/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.classify.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.entity.TreeEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 资产分类表Entity
 * @author czy
 * @version 2018-04-24
 */
@Table(name="${_prefix}am_classify", alias="a", columns={
		@Column(includeEntity=TreeEntity.class),
		@Column(name="classify_code", attrName="classifyCode", label="分类编码", isPK=true),
		@Column(name="classify_name", attrName="classifyName", label="分类名称", queryType=QueryType.LIKE, isTreeName=true),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.tree_sorts, a.classify_code"
)
public class AmClassify extends TreeEntity<AmClassify> {
	
	private static final long serialVersionUID = 1L;
	private String classifyCode;		// 分类编码
	private String classifyName;		// 分类名称
	
	public AmClassify() {
		this(null);
	}

	public AmClassify(String id){
		super(id);
	}
	
	@Override
	public AmClassify getParent() {
		return parent;
	}

	@Override
	public void setParent(AmClassify parent) {
		this.parent = parent;
	}
	
	public String getClassifyCode() {
		return classifyCode;
	}

	public void setClassifyCode(String classifyCode) {
		this.classifyCode = classifyCode;
	}
	
	@NotBlank(message="分类名称不能为空")
	@Length(min=0, max=100, message="分类名称长度不能超过 100 个字符")
	public String getClassifyName() {
		return classifyName;
	}

	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
	}
	
}