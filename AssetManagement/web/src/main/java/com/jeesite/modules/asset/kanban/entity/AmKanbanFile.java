/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.kanban.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.entity.TreeEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 看板档案Entity
 * @author dwh
 * @version 2018-07-24
 */
@Table(name="${_prefix}am_kanban_file", alias="a", columns={
		@Column(name="kanban_code", attrName="kanbanCode", label="看板编码", isPK=true),
		@Column(name="kanban_name", attrName="kanbanName", label="看板名称", queryType=QueryType.LIKE, isTreeName=true),
		@Column(name="office_name", attrName="officeName", label="需求部门名称", queryType=QueryType.LIKE),
		@Column(name="url", attrName="url", label="网址"),
		@Column(name="adaptation_url", attrName="adaptationUrl", label="适配网址"),

		@Column(includeEntity=TreeEntity.class),
		@Column(includeEntity=DataEntity.class),
		@Column(name="office_code", attrName="officeCode", label="部门编码"),
	}, orderBy="a.tree_sorts, a.kanban_code"
)
public class AmKanbanFile extends TreeEntity<AmKanbanFile> {
	
	private static final long serialVersionUID = 1L;
	private String kanbanCode;		// 看板编码
	private String kanbanName;		// 看板名称
	private String officeName;		// 需求部门名称
	private String url;		// 网址
	private String officeCode;		// 部门编码
	private String adaptationUrl;      //适配网址

	public String getAdaptationUrl() {
		return adaptationUrl;
	}

	public void setAdaptationUrl(String adaptationUrl) {
		this.adaptationUrl = adaptationUrl;
	}

	public AmKanbanFile() {
		this(null);
	}

	public AmKanbanFile(String id){
		super(id);
	}
	
	@Override
	public AmKanbanFile getParent() {
		return parent;
	}

	@Override
	public void setParent(AmKanbanFile parent) {
		this.parent = parent;
	}
	
	public String getKanbanCode() {
		return kanbanCode;
	}

	public void setKanbanCode(String kanbanCode) {
		this.kanbanCode = kanbanCode;
	}
	
	@NotBlank(message="看板名称不能为空")
	@Length(min=0, max=100, message="看板名称长度不能超过 100 个字符")
	public String getKanbanName() {
		return kanbanName;
	}

	public void setKanbanName(String kanbanName) {
		this.kanbanName = kanbanName;
	}
	
	@Length(min=0, max=100, message="需求部门名称长度不能超过 100 个字符")
	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	
	@Length(min=0, max=200, message="网址长度不能超过 200 个字符")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Length(min=0, max=64, message="部门编码长度不能超过 64 个字符")
	public String getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}
	
}