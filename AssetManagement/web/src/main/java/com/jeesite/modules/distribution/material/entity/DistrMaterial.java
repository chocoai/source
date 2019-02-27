/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.distribution.material.entity;

import com.jeesite.modules.asset.tianmao.entity.TbProduct;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import com.jeesite.common.collect.ListUtils;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 分销素材Entity
 * @author len
 * @version 2019-01-05
 */
@Table(name="distr_material", alias="a", columns={
		@Column(name="material_code", attrName="materialCode", label="素材编码", isPK=true),
		@Column(name="seq", attrName="seq", label="序号"),
		@Column(name="material_name", attrName="materialName", label="素材名", queryType=QueryType.LIKE),
		@Column(name="material_status", attrName="materialStatus", label="状态"),
		@Column(name="introduce", attrName="introduce", label="方案介绍"),
		@Column(name="create_time", attrName="createTime", label="创建时间"),
		@Column(name="create_by", attrName="createBy", label="创建人"),
	}, orderBy="a.seq DESC"
)
public class DistrMaterial extends DataEntity<DistrMaterial> {
	
	private static final long serialVersionUID = 1L;
	private String materialCode;		// 素材编码
	private Integer seq;		// 序号
	private String materialName;		// 素材名
	private String materialStatus;		// 状态
	private String introduce;		// 方案介绍
	private Date createTime;		// 创建时间
	private String createBy;
	private String apiFlag;			// 接口请求
	private String img;
	private List<String> imgList;	// 详情图片
	private List<DistrMaterialDetail> distrMaterialDetailList = ListUtils.newArrayList();		// 子表列表
	private String bannerImg;
	private List<TbProduct> tbProductList;	// 商品信息

	public List<TbProduct> getTbProductList() {
		return tbProductList;
	}

	public void setTbProductList(List<TbProduct> tbProductList) {
		this.tbProductList = tbProductList;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public List<String> getImgList() {
		return imgList;
	}

	public void setImgList(List<String> imgList) {
		this.imgList = imgList;
	}

	public String getBannerImg() {
		return bannerImg;
	}

	public void setBannerImg(String bannerImg) {
		this.bannerImg = bannerImg;
	}

	public String getApiFlag() {
		return apiFlag;
	}

	public void setApiFlag(String apiFlag) {
		this.apiFlag = apiFlag;
	}

	@Override
	public String getCreateBy() {
		return createBy;
	}

	@Override
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public DistrMaterial() {
		this(null);
	}

	public DistrMaterial(String id){
		super(id);
	}
	
	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	@Length(min=0, max=100, message="素材名长度不能超过 100 个字符")
	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	
	@Length(min=0, max=1, message="状态长度不能超过 1 个字符")
	public String getMaterialStatus() {
		return materialStatus;
	}

	public void setMaterialStatus(String materialStatus) {
		this.materialStatus = materialStatus;
	}
	
	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public List<DistrMaterialDetail> getDistrMaterialDetailList() {
		return distrMaterialDetailList;
	}

	public void setDistrMaterialDetailList(List<DistrMaterialDetail> distrMaterialDetailList) {
		this.distrMaterialDetailList = distrMaterialDetailList;
	}
	
}