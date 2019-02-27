/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.softcostume.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 软装方案管理Entity
 * @author len
 * @version 2019-02-26
 */
@Table(name="sv_soft_costume", alias="a", columns={
		@Column(name="soft_costume_code", attrName="softCostumeCode", label="软装编码", isPK=true),
		@Column(name="soft_costume_url", attrName="softCostumeUrl", label="软装方案URL"),
		@Column(name="scheme_name", attrName="schemeName", label="方案名称", queryType=QueryType.LIKE),
		@Column(name="data_sort", attrName="dataSort", label="排序号"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class SvSoftCostume extends DataEntity<SvSoftCostume> {
	
	private static final long serialVersionUID = 1L;
	private String softCostumeCode;		// 软装编码
	private String softCostumeUrl;		// 软装方案URL
	private String schemeName;		// 方案名称
	private Long dataSort;		// 排序号
	
	public SvSoftCostume() {
		this(null);
	}

	public SvSoftCostume(String id){
		super(id);
	}
	
	public String getSoftCostumeCode() {
		return softCostumeCode;
	}

	public void setSoftCostumeCode(String softCostumeCode) {
		this.softCostumeCode = softCostumeCode;
	}
	
	@Length(min=0, max=1000, message="软装方案URL长度不能超过 1000 个字符")
	public String getSoftCostumeUrl() {
		return softCostumeUrl;
	}

	public void setSoftCostumeUrl(String softCostumeUrl) {
		this.softCostumeUrl = softCostumeUrl;
	}
	
	@Length(min=0, max=255, message="方案名称长度不能超过 255 个字符")
	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
	
	@NotNull(message="排序号不能为空")
	public Long getDataSort() {
		return dataSort;
	}

	public void setDataSort(Long dataSort) {
		this.dataSort = dataSort;
	}
	
}