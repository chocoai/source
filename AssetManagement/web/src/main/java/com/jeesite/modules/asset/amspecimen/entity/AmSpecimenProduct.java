/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.amspecimen.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 构建样品进度Entity
 * @author mclaran
 * @version 2018-06-29
 */
@Table(name="${_prefix}am_specimen_product", alias="a", columns={
		@Column(name="code", attrName="code", label="code", isPK=true),
		@Column(name="product_code", attrName="productCode", label="产品编号"),
		@Column(name="product_name", attrName="productName", label="产品名称", queryType=QueryType.LIKE),
		@Column(name="count", attrName="count", label="数量"),
		@Column(name="material", attrName="material", label="材质"),
		@Column(name="es", attrName="es", label="材料"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="specimen_code", attrName="specimenCode.specimenCode", label="样品进度code"),
		@Column(name="photo", attrName="photo", label="图片"),
	}, orderBy="a.create_date ASC"
)
public class AmSpecimenProduct extends DataEntity<AmSpecimenProduct> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// code
	private String productCode;		// 产品编号
	private String productName;		// 产品名称
	private Integer count;		// 数量
	private String material;		// 材质
	private String es;		// 材料
	private AmSpecimen specimenCode;		// 样品进度code 父类
	private String photo;		// 图片
	private String file;      //附件
	private String imgUrl;
	private String fileUrl;
	private String imgPath; //阿里云截取过后的图片路径
	private String filePath; //阿里云截取过后的附件路径


	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public AmSpecimenProduct() {
		this(null);
	}


	public AmSpecimenProduct(AmSpecimen specimenCode){
		this.specimenCode = specimenCode;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=32, message="产品编号长度不能超过 32 个字符")
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	@Length(min=0, max=32, message="产品名称长度不能超过 32 个字符")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
    @Length(min=0, max=32, message="数量只能是整数")
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }





	@Length(min=0, max=128, message="材质长度不能超过 128 个字符")
	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}
	
	@Length(min=0, max=128, message="材料长度不能超过 128 个字符")
	public String getEs() {
		return es;
	}

	public void setEs(String es) {
		this.es = es;
	}
	
	@Length(min=0, max=32, message="样品进度code长度不能超过 32 个字符")
	public AmSpecimen getSpecimenCode() {
		return specimenCode;
	}

	public void setSpecimenCode(AmSpecimen specimenCode) {
		this.specimenCode = specimenCode;
	}
	
	@Length(min=0, max=100, message="图片长度不能超过 100 个字符")
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
}