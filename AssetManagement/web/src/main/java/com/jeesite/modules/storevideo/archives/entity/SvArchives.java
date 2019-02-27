/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.archives.entity;

import com.jeesite.modules.asset.tianmao.entity.TbProduct;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 产品推送基础档案Entity
 * @author len
 * @version 2019-02-26
 */
@Table(name="sv_archives", alias="a", columns={
		@Column(name="archives_code", attrName="archivesCode", label="档案资料编码", isPK=true),
		@Column(name="num_iid", attrName="numIid", label="天猫id", queryType=QueryType.LIKE),
		@Column(name="goods_name", attrName="goodsName", label="商品名称", queryType=QueryType.LIKE),
		@Column(name="cid", attrName="cid", label="商品类目名称"),
		@Column(name="procategory_code", attrName="procategoryCode", label="商品分类"),
		@Column(name="procategory_name", attrName="procategoryName", label="分类名称"),
		@Column(name="proseries_code", attrName="proseriesCode", label="商品系列"),
		@Column(name="proseries", attrName="proseries", label="系列名称"),
	}, orderBy="a.archives_code DESC"
)
public class SvArchives extends DataEntity<SvArchives> {
	
	private static final long serialVersionUID = 1L;
	private String archivesCode;		// 档案资料编码
	private String numIid;		// 天猫id
	private String goodsName;		// 商品名称
	private Long cid;		// 商品类目名称
	private String procategoryCode;		// 商品分类
	private String procategoryName;		// 分类名称
	private String proseriesCode;		// 商品系列
	private String proseries;		// 系列名称
	private TbProduct tbProduct;

	public TbProduct getTbProduct() {
		return tbProduct;
	}

	public void setTbProduct(TbProduct tbProduct) {
		this.tbProduct = tbProduct;
	}

	public SvArchives() {
		this(null);
	}

	public SvArchives(String id){
		super(id);
	}
	
	public String getArchivesCode() {
		return archivesCode;
	}

	public void setArchivesCode(String archivesCode) {
		this.archivesCode = archivesCode;
	}
	
	@NotBlank(message="天猫id不能为空")
	@Length(min=0, max=64, message="天猫id长度不能超过 64 个字符")
	public String getNumIid() {
		return numIid;
	}

	public void setNumIid(String numIid) {
		this.numIid = numIid;
	}
	
	@Length(min=0, max=255, message="商品名称长度不能超过 255 个字符")
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}
	
	@Length(min=0, max=64, message="商品分类长度不能超过 64 个字符")
	public String getProcategoryCode() {
		return procategoryCode;
	}

	public void setProcategoryCode(String procategoryCode) {
		this.procategoryCode = procategoryCode;
	}
	
	@Length(min=0, max=64, message="分类名称长度不能超过 64 个字符")
	public String getProcategoryName() {
		return procategoryName;
	}

	public void setProcategoryName(String procategoryName) {
		this.procategoryName = procategoryName;
	}
	
	@Length(min=0, max=64, message="商品系列长度不能超过 64 个字符")
	public String getProseriesCode() {
		return proseriesCode;
	}

	public void setProseriesCode(String proseriesCode) {
		this.proseriesCode = proseriesCode;
	}
	
	@Length(min=0, max=64, message="系列名称长度不能超过 64 个字符")
	public String getProseries() {
		return proseries;
	}

	public void setProseries(String proseries) {
		this.proseries = proseries;
	}
	
}