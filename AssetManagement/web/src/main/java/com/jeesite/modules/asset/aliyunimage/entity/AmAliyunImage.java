/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.aliyunimage.entity;

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
 * 阿里云图片Entity
 * @author AlbertFeng
 * @version 2018-08-04
 */
@Table(name="${_prefix}am_aliyun_image", alias="a", columns={
		@Column(name="id", attrName="id", label="主键", isPK=true),
		@Column(name="title", attrName="title", label="标题", queryType=QueryType.LIKE),
		@Column(name="type", attrName="type", label="类型"),
		@Column(name="image_id", attrName="imageId", label="图片Id"),
		@Column(name="file_id", attrName="fileId", label="文件Id"),
		@Column(name="state", attrName="state", label="状态"),
		@Column(name="is_video", attrName="isVideo", label="视频"),
		@Column(name="md5", attrName="md5", label="Md5"),
		@Column(name="taken_at", attrName="takenAt", label="TakenAt"),
		@Column(name="width", attrName="width", label="宽度"),
		@Column(name="height", attrName="height", label="高度"),
		@Column(name="create_time", attrName="createTime", label="创建时间"),
		@Column(name="image_url", attrName="imageUrl", label="图片路径"),
		@Column(name="remark", attrName="remark", label="备注"),
		@Column(name="local_path", attrName="localPath", label="本地保存路径"),
	}, orderBy="a.id DESC"
)
public class AmAliyunImage extends DataEntity<AmAliyunImage> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private String type;		// 类型
	private String imageId;		// 图片Id
	private String fileId;		// 文件Id
	private String state;		// 状态
	private Integer isVideo;		// 视频
	private String md5;		// Md5
	private String takenAt;		// TakenAt
	private String width;		// 宽度
	private String height;		// 高度
	private Date createTime;		// 创建时间
	private String imageUrl;		// 图片路径
	private String remark;		// 备注
	private String localPath;

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public AmAliyunImage() {
		this(null);
	}

	public AmAliyunImage(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="标题长度不能超过 255 个字符")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=100, message="类型长度不能超过 100 个字符")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=100, message="图片Id长度不能超过 100 个字符")
	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	
	@Length(min=0, max=100, message="文件Id长度不能超过 100 个字符")
	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	
	@Length(min=0, max=50, message="状态长度不能超过 50 个字符")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public Integer getIsVideo() {
		return isVideo;
	}

	public void setIsVideo(Integer isVideo) {
		this.isVideo = isVideo;
	}
	
	@Length(min=0, max=100, message="Md5长度不能超过 100 个字符")
	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}
	
	@Length(min=0, max=100, message="TakenAt长度不能超过 100 个字符")
	public String getTakenAt() {
		return takenAt;
	}

	public void setTakenAt(String takenAt) {
		this.takenAt = takenAt;
	}
	
	@Length(min=0, max=50, message="宽度长度不能超过 50 个字符")
	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}
	
	@Length(min=0, max=50, message="高度长度不能超过 50 个字符")
	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=0, max=255, message="图片路径长度不能超过 255 个字符")
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	@Length(min=0, max=500, message="备注长度不能超过 500 个字符")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}