/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.tianmao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.modules.asset.product.entity.ProductCategory;
import com.jeesite.modules.asset.product.entity.ProductSeries;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

/**
 * 同步淘宝商品列表Entity
 * @author Jace
 * @version 2018-07-07
 */
@Table(name="tb_product", alias="a", columns={
		@Column(name="num_iid", attrName="numIid", label="商品数字id", isPK=true, queryType=QueryType.LIKE),
		@Column(name="title", attrName="title", label="商品标题", queryType=QueryType.LIKE),
		@Column(name="procategory_code", attrName="procategoryCode", label="分类编码"),
		@Column(name="proseries_code", attrName="proseriesCode", label="风格编码"),
		@Column(name="pic_url", attrName="picUrl", label="商品主图片地址"),
		@Column(name="cid", attrName="cid", label="cid"),
		@Column(name="approve_status", attrName="approveStatus", label="商品上传后的状态。onsale出售中，instock库中"),
		@Column(name="price", attrName="price", label="价格"),
		@Column(name="detail_url", attrName="detailUrl", label="商品url"),
		@Column(name="list_time", attrName="listTime", label="上架时间"),
		@Column(name="delist_tim", attrName="delistTim", label="下架时间"),
		@Column(name="modified", attrName="modified", label="商品修改时间"),
		@Column(name="nick", attrName="nick", label="卖家昵称"),
		@Column(name="outer_code", attrName="outerCode", label="商家外部编码",queryType=QueryType.LIKE),
		@Column(name="interactive_name", attrName="interactiveName", label="交互屏名称"),
	}, joinTable={
		@JoinTable(type=JoinTable.Type.LEFT_JOIN, entity=ProductCategory.class, alias="b",
				on="b.procategory_code=a.procategory_code ",
				columns={@Column(includeEntity=ProductCategory.class)}),
		@JoinTable(type=JoinTable.Type.LEFT_JOIN, entity=ProductSeries.class, alias="c",
				on="c.proseries_code=a.proseries_code ",
				columns={@Column(includeEntity=ProductSeries.class)})
}
)

public class TbProduct extends DataEntity<TbProduct> {
	
	private static final long serialVersionUID = 1L;
	private String numIid;		// 商品数字id
	private String title;		// 商品标题
	private String picUrl;		// 商品主图片地址
	private Long cid;		// cid
	private String approveStatus;		// 商品上传后的状态。onsale出售中，instock库中
	private String price;		// 价格
	private String detailUrl;		// 商品url
	private Date listTime;		// 上架时间
	private Date delistTim;		// 下架时间
	private Date modified;		// 商品修改时间
	private String outerId;		// sku
	private String nick;		// 卖家昵称
	private String outerCode;	// 商家外部编码
	private String proseriesCode;
	private String procategoryCode;
	private String queryNick;	// 根据昵称过滤数据
	private Double lowerPrice;	// 商品最低价
	private String interactiveName;	// 交互屏名称
	private Double lowerDistrPrice;	// 分销商商品最低价
	private String distrPicUrl;		// 分销商品主图

	public String getDistrPicUrl() {
		return distrPicUrl;
	}

	public void setDistrPicUrl(String distrPicUrl) {
		this.distrPicUrl = distrPicUrl;
	}

	public Double getLowerDistrPrice() {
		return lowerDistrPrice;
	}

	public void setLowerDistrPrice(Double lowerDistrPrice) {
		this.lowerDistrPrice = lowerDistrPrice;
	}

	public String getInteractiveName() {
		return interactiveName;
	}

	public void setInteractiveName(String interactiveName) {
		this.interactiveName = interactiveName;
	}

	public Double getLowerPrice() {
		return lowerPrice;
	}

	public void setLowerPrice(Double lowerPrice) {
		this.lowerPrice = lowerPrice;
	}

	public String getQueryNick() {
		return queryNick;
	}

	public void setQueryNick(String queryNick) {
		this.queryNick = queryNick;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public ProductSeries getProductSeries() {
		return productSeries;
	}

	public void setProductSeries(ProductSeries productSeries) {
		this.productSeries = productSeries;
	}

	private ProductCategory productCategory;
	private ProductSeries productSeries;

	public String getProseriesCode() {
		return proseriesCode;
	}

	public void setProseriesCode(String proseriesCode) {
		this.proseriesCode = proseriesCode;
	}

	public String getProcategoryCode() {
		return procategoryCode;
	}

	public void setProcategoryCode(String procategoryCode) {
		this.procategoryCode = procategoryCode;
	}



	public String getOuterCode() {
		return outerCode;
	}

	public void setOuterCode(String outerCode) {
		this.outerCode = outerCode;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	List<TbSku> tbSkuList = ListUtils.newArrayList();

	public List<TbSku> getTbSkuList() {
		return tbSkuList;
	}

	public void setTbSkuList(List<TbSku> tbSkuList) {
		this.tbSkuList = tbSkuList;
	}

	public String getOuterId() {
		return outerId;
	}

	public void setOuterId(String outerId) {
		this.outerId = outerId;
	}

	public TbProduct() {
		this(null);
	}

	public TbProduct(String id){
		super(id);
	}
	
	public String getNumIid() {
		return numIid;
	}

	public void setNumIid(String numIid) {
		this.numIid = numIid;
	}
	
	@Length(min=0, max=128, message="商品标题长度不能超过 128 个字符")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=255, message="商品主图片地址长度不能超过 255 个字符")
	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}
	
	@Length(min=0, max=32, message="商品上传后的状态。onsale出售中，instock库中长度不能超过 32 个字符")
	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}
	
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	@Length(min=0, max=255, message="商品url长度不能超过 255 个字符")
	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getListTime() {
		return listTime;
	}

	public void setListTime(Date listTime) {
		this.listTime = listTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDelistTim() {
		return delistTim;
	}

	public void setDelistTim(Date delistTim) {
		this.delistTim = delistTim;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}
	
}