/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.taobao.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 图片评价表Entity
 * @author len
 * @version 2018-11-01
 */
@Table(name="taobao_evaluate", alias="a", columns={
		@Column(name="product_id", attrName="productId", label="商品id", isPK=true),
		@Column(name="publish_time", attrName="publishTime", label="发布时间"),
		@Column(name="content", attrName="content", label="内容"),
		@Column(name="author_name", attrName="authorName", label="发布人", queryType=QueryType.LIKE),
		@Column(name="author_level", attrName="authorLevel", label="发布级别", queryType=QueryType.LIKE),
		@Column(name="product_sku", attrName="productSku", label="商品规格", queryType=QueryType.LIKE),
		@Column(name="image_url1", attrName="imageUrl1", label="图片1"),
		@Column(name="image_url2", attrName="imageUrl2", label="图片2"),
		@Column(name="image_url3", attrName="imageUrl3", label="图片3"),
		@Column(name="image_url4", attrName="imageUrl4", label="图片4"),
		@Column(name="image_url5", attrName="imageUrl5", label="图片5"),
		@Column(name="seller_reply_content", attrName="sellerReplyContent", label="卖家评价内容", queryType=QueryType.LIKE),
		@Column(name="append_publish_time", attrName="appendPublishTime", label="评价发布时间"),
		@Column(name="append_interval", attrName="appendInterval", label="评价间隔"),
		@Column(name="append_content", attrName="appendContent", label="评价内容", queryType=QueryType.LIKE),
		@Column(name="append_images1", attrName="appendImages1", label="评价图片1"),
		@Column(name="append_images2", attrName="appendImages2", label="评价图片2"),
		@Column(name="append_images3", attrName="appendImages3", label="评价图片3"),
		@Column(name="append_images4", attrName="appendImages4", label="评价图片4"),
		@Column(name="append_images5", attrName="appendImages5", label="评价图片5"),
		@Column(name="update_time", attrName="updateTime", label="更新时间"),
		@Column(name="publish_date", attrName="publishDate", label="发布时间(时间戳)"),
	}, orderBy="a.update_time DESC"
)
public class TaobaoEvaluate extends DataEntity<TaobaoEvaluate> {
	
	private static final long serialVersionUID = 1L;
//	private String pkey;		// 主键
	private String productId;		// 商品id
	private Date publishTime;		// 发布时间
	private String content;		// 内容
	private String authorName;		// 发布人
	private String authorLevel;		// 发布级别
	private String productSku;		// 商品规格
	private String imageUrl1;		// 图片1
	private String imageUrl2;		// 图片2
	private String imageUrl3;		// 图片3
	private String imageUrl4;		// 图片4
	private String imageUrl5;		// 图片5
	private String sellerReplyContent;		// 卖家评价内容
	private Date appendPublishTime;		// 评价发布时间
	private String appendInterval;		// 评价间隔
	private String appendContent;		// 评价内容
	private String appendImages1;		// 评价图片1
	private String appendImages2;		// 评价图片2
	private String appendImages3;		// 评价图片3
	private String appendImages4;		// 评价图片4
	private String appendImages5;		// 评价图片5
	private Date updateTime;		// 更新时间
	private Long publishDate;
	
	public TaobaoEvaluate() {
		this(null);
	}

	public TaobaoEvaluate(String id){
		super(id);
	}
	
//	public String getPkey() {
//		return pkey;
//	}
//
//	public void setPkey(String pkey) {
//		this.pkey = pkey;
//	}
	
	@Length(min=0, max=64, message="商品id长度不能超过 64 个字符")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=64, message="发布人长度不能超过 64 个字符")
	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	
	@Length(min=0, max=10, message="发布级别长度不能超过 10 个字符")
	public String getAuthorLevel() {
		return authorLevel;
	}

	public void setAuthorLevel(String authorLevel) {
		this.authorLevel = authorLevel;
	}
	
	@Length(min=0, max=255, message="商品规格长度不能超过 255 个字符")
	public String getProductSku() {
		return productSku;
	}

	public void setProductSku(String productSku) {
		this.productSku = productSku;
	}
	
	@Length(min=0, max=500, message="图片1长度不能超过 500 个字符")
	public String getImageUrl1() {
		return imageUrl1;
	}

	public void setImageUrl1(String imageUrl1) {
		this.imageUrl1 = imageUrl1;
	}
	
	@Length(min=0, max=500, message="图片2长度不能超过 500 个字符")
	public String getImageUrl2() {
		return imageUrl2;
	}

	public void setImageUrl2(String imageUrl2) {
		this.imageUrl2 = imageUrl2;
	}
	
	@Length(min=0, max=500, message="图片3长度不能超过 500 个字符")
	public String getImageUrl3() {
		return imageUrl3;
	}

	public void setImageUrl3(String imageUrl3) {
		this.imageUrl3 = imageUrl3;
	}
	
	@Length(min=0, max=500, message="图片4长度不能超过 500 个字符")
	public String getImageUrl4() {
		return imageUrl4;
	}

	public void setImageUrl4(String imageUrl4) {
		this.imageUrl4 = imageUrl4;
	}
	
	@Length(min=0, max=500, message="图片5长度不能超过 500 个字符")
	public String getImageUrl5() {
		return imageUrl5;
	}

	public void setImageUrl5(String imageUrl5) {
		this.imageUrl5 = imageUrl5;
	}
	
	public String getSellerReplyContent() {
		return sellerReplyContent;
	}

	public void setSellerReplyContent(String sellerReplyContent) {
		this.sellerReplyContent = sellerReplyContent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAppendPublishTime() {
		return appendPublishTime;
	}

	public void setAppendPublishTime(Date appendPublishTime) {
		this.appendPublishTime = appendPublishTime;
	}
	
	@Length(min=0, max=100, message="评价间隔长度不能超过 100 个字符")
	public String getAppendInterval() {
		return appendInterval;
	}

	public void setAppendInterval(String appendInterval) {
		this.appendInterval = appendInterval;
	}
	
	public String getAppendContent() {
		return appendContent;
	}

	public void setAppendContent(String appendContent) {
		this.appendContent = appendContent;
	}
	
	@Length(min=0, max=500, message="评价图片1长度不能超过 500 个字符")
	public String getAppendImages1() {
		return appendImages1;
	}

	public void setAppendImages1(String appendImages1) {
		this.appendImages1 = appendImages1;
	}
	
	@Length(min=0, max=500, message="评价图片2长度不能超过 500 个字符")
	public String getAppendImages2() {
		return appendImages2;
	}

	public void setAppendImages2(String appendImages2) {
		this.appendImages2 = appendImages2;
	}
	
	@Length(min=0, max=500, message="评价图片3长度不能超过 500 个字符")
	public String getAppendImages3() {
		return appendImages3;
	}

	public void setAppendImages3(String appendImages3) {
		this.appendImages3 = appendImages3;
	}
	
	@Length(min=0, max=500, message="评价图片4长度不能超过 500 个字符")
	public String getAppendImages4() {
		return appendImages4;
	}

	public void setAppendImages4(String appendImages4) {
		this.appendImages4 = appendImages4;
	}
	
	@Length(min=0, max=500, message="评价图片5长度不能超过 500 个字符")
	public String getAppendImages5() {
		return appendImages5;
	}

	public void setAppendImages5(String appendImages5) {
		this.appendImages5 = appendImages5;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Long publishDate) {
		this.publishDate = publishDate;
	}
}