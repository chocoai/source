/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.tianmao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * tb_itemEntity
 * @author jace
 * @version 2018-07-08
 */
@Table(name="tb_item", alias="a", columns={
		@Column(name="num_iid", attrName="numIid", label="商品数字id", isPK=true),
		@Column(name="detail_url", attrName="detailUrl", label="商品地址URL"),
		@Column(name="title", attrName="title", label="商品标题", queryType=QueryType.LIKE),
		@Column(name="nick", attrName="nick", label="卖家昵称"),
		@Column(name="type", attrName="type", label="商品类型"),
		@Column(name="cid", attrName="cid", label="商品所属的叶子类目 id"),
		@Column(name="seller_cids", attrName="sellerCids", label="商品所属的店铺内卖家自定义类目列表"),
		@Column(name="props", attrName="props", label="商品属性"),
		@Column(name="input_pids", attrName="inputPids", label="用户自行输入的类目属性ID串"),
		@Column(name="input_str", attrName="inputStr", label="用户自行输入的子属性名和属性值"),
		@Column(name="desc", attrName="desc", label="商品描述"),
		@Column(name="pic_url", attrName="picUrl", label="商品主图片地址"),
		@Column(name="num", attrName="num", label="商品数量"),
		@Column(name="valid_thru", attrName="validThru", label="有效期"),
		@Column(name="list_time", attrName="listTime", label="上架时间"),
		@Column(name="delist_time", attrName="delistTime", label="下架时间"),
		@Column(name="price", attrName="price", label="商品价格"),
		@Column(name="post_fee", attrName="postFee", label="平邮费用"),
		@Column(name="express_fee", attrName="expressFee", label="快递费用"),
		@Column(name="ems_fee", attrName="emsFee", label="ems费用"),
		@Column(name="modified", attrName="modified", label="商品修改时间"),
		@Column(name="approve_status", attrName="approveStatus", label="商品上传后的状态。onsale出售中，instock库中"),
		@Column(name="item_weight", attrName="itemWeight", label="商品的重量"),
	}, orderBy="a.num_iid DESC"
)
public class TbItem extends DataEntity<TbItem> {
	
	private static final long serialVersionUID = 1L;
	private Long numIid;		// 商品数字id
	private String detailUrl;		// 商品地址URL
	private String title;		// 商品标题
	private String nick;		// 卖家昵称
	private String type;		// 商品类型
	private Long cid;		// 商品所属的叶子类目 id
	private String sellerCids;		// 商品所属的店铺内卖家自定义类目列表
	private String props;		// 商品属性
	private String inputPids;		// 用户自行输入的类目属性ID串
	private String inputStr;		// 用户自行输入的子属性名和属性值
	private String desc;		// 商品描述
	private String picUrl;		// 商品主图片地址
	private Long num;		// 商品数量
	private Long validThru;		// 有效期
	private Date listTime;		// 上架时间
	private Date delistTime;		// 下架时间
	private String price;		// 商品价格
	private String postFee;		// 平邮费用
	private String expressFee;		// 快递费用
	private String emsFee;		// ems费用
	private String modified;		// 商品修改时间
	private String approveStatus;		// 商品上传后的状态。onsale出售中，instock库中
	private String itemWeight;		// 商品的重量
	
	public TbItem() {
		this(null);
	}

	public TbItem(String id){
		super(id);
	}
	
	public Long getNumIid() {
		return numIid;
	}

	public void setNumIid(Long numIid) {
		this.numIid = numIid;
	}
	
	@Length(min=0, max=255, message="商品地址URL长度不能超过 255 个字符")
	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}
	
	@Length(min=0, max=255, message="商品标题长度不能超过 255 个字符")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=255, message="卖家昵称长度不能超过 255 个字符")
	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}
	
	@Length(min=0, max=255, message="商品类型长度不能超过 255 个字符")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}
	
	@Length(min=0, max=255, message="商品所属的店铺内卖家自定义类目列表长度不能超过 255 个字符")
	public String getSellerCids() {
		return sellerCids;
	}

	public void setSellerCids(String sellerCids) {
		this.sellerCids = sellerCids;
	}
	
	@Length(min=0, max=255, message="商品属性长度不能超过 255 个字符")
	public String getProps() {
		return props;
	}

	public void setProps(String props) {
		this.props = props;
	}
	
	@Length(min=0, max=255, message="用户自行输入的类目属性ID串长度不能超过 255 个字符")
	public String getInputPids() {
		return inputPids;
	}

	public void setInputPids(String inputPids) {
		this.inputPids = inputPids;
	}
	
	@Length(min=0, max=255, message="用户自行输入的子属性名和属性值长度不能超过 255 个字符")
	public String getInputStr() {
		return inputStr;
	}

	public void setInputStr(String inputStr) {
		this.inputStr = inputStr;
	}
	
	@Length(min=0, max=255, message="商品描述长度不能超过 255 个字符")
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	@Length(min=0, max=255, message="商品主图片地址长度不能超过 255 个字符")
	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}
	
	public Long getValidThru() {
		return validThru;
	}

	public void setValidThru(Long validThru) {
		this.validThru = validThru;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getListTime() {
		return listTime;
	}

	public void setListTime(Date listTime) {
		this.listTime = listTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDelistTime() {
		return delistTime;
	}

	public void setDelistTime(Date delistTime) {
		this.delistTime = delistTime;
	}
	
	@Length(min=0, max=255, message="商品价格长度不能超过 255 个字符")
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	@Length(min=0, max=255, message="平邮费用长度不能超过 255 个字符")
	public String getPostFee() {
		return postFee;
	}

	public void setPostFee(String postFee) {
		this.postFee = postFee;
	}
	
	@Length(min=0, max=255, message="快递费用长度不能超过 255 个字符")
	public String getExpressFee() {
		return expressFee;
	}

	public void setExpressFee(String expressFee) {
		this.expressFee = expressFee;
	}
	
	@Length(min=0, max=255, message="ems费用长度不能超过 255 个字符")
	public String getEmsFee() {
		return emsFee;
	}

	public void setEmsFee(String emsFee) {
		this.emsFee = emsFee;
	}
	
	@Length(min=0, max=255, message="商品修改时间长度不能超过 255 个字符")
	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}
	
	@Length(min=0, max=255, message="商品上传后的状态。onsale出售中，instock库中长度不能超过 255 个字符")
	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}
	
	@Length(min=0, max=255, message="商品的重量长度不能超过 255 个字符")
	public String getItemWeight() {
		return itemWeight;
	}

	public void setItemWeight(String itemWeight) {
		this.itemWeight = itemWeight;
	}
	
}