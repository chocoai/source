/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.tianmao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * tb_item_imgsEntity
 * @author jace
 * @version 2018-07-08
 */
@Table(name="tb_item_imgs", alias="a", columns={
		@Column(name="id", attrName="id", label="商品图片的id，和商品相对应", comment="商品图片的id，和商品相对应（主图id默认为0）", isPK=true),
		@Column(name="url", attrName="url", label="图片链接地址"),
		@Column(name="position", attrName="position", label="图片放在第几张", comment="图片放在第几张（多图时可设置）"),
		@Column(name="created", attrName="created", label="图片创建时间"),
		@Column(name="item_id", attrName="itemId", label="图片所属商品ID"),
	}, orderBy="a.id DESC"
)
public class TbItemImgs extends DataEntity<TbItemImgs> {
	
	private static final long serialVersionUID = 1L;
	private String url;		// 图片链接地址
	private Long position;		// 图片放在第几张（多图时可设置）
	private Date created;		// 图片创建时间
	private Long itemId;		// 图片所属商品ID
	
	public TbItemImgs() {
		this(null);
	}

	public TbItemImgs(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="图片链接地址长度不能超过 255 个字符")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public Long getPosition() {
		return position;
	}

	public void setPosition(Long position) {
		this.position = position;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	
}