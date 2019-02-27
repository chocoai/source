/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.guideApp.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;
import java.util.List;
import com.jeesite.common.collect.ListUtils;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 导购活动表Entity
 * @author len
 * @version 2018-12-07
 */
@Table(name="${_prefix}guide_activity", alias="a", columns={
		@Column(name="activity_code", attrName="activityCode", label="活动编号", isPK=true),
		@Column(name="activity_name", attrName="activityName", label="活动名称", queryType=QueryType.LIKE),
		@Column(name="activity_status", attrName="activityStatus", label="状态"),
		@Column(name="start_time", attrName="startTime", label="开始时间"),
		@Column(name="end_time", attrName="endTime", label="结束时间"),
		@Column(name="activity_remarks", attrName="activityRemarks", label="活动备注"),
		@Column(name="activity_introduce", attrName="activityIntroduce", label="活动介绍"),
		@Column(name="create_time", attrName="createTime", label="创建时间"),
		@Column(name="create_by", attrName="createBy", label="创建人"),
		@Column(name="update_by", attrName="updateBy", label="更新人"),
		@Column(name="update_time", attrName="updateTime", label="更新时间"),
	}, orderBy="a.create_time DESC"
)
public class GuideActivity extends DataEntity<GuideActivity> {
	
	private static final long serialVersionUID = 1L;
	private String activityCode;		// 活动编号
	private String activityName;		// 活动名称
	private String activityStatus;		// 状态
	private Date startTime;		// 开始时间
	private Date endTime;		// 结束时间
	private String activityRemarks;		// 活动备注
	private String activityIntroduce;		// 活动介绍
	private String bannerImage;		// 列表banner
	private Date createTime;		// 创建时间
	private String createBy;			// 创建人
	private String updateBy;			// 更新人
	private Date updateTime;		// 更新时间
	private List<GuideComment> guideCommentList = ListUtils.newArrayList();		// 子表列表
	private List<GuideFaq> guideFaqList = ListUtils.newArrayList();		// 子表列表
	private Integer commentNum;		// 评论数
	private boolean activityGroup;	// 是否是活动组
	private boolean canComment;		// 是否可以评论

	public boolean isCanComment() {
		return canComment;
	}

	public void setCanComment(boolean canComment) {
		this.canComment = canComment;
	}

	public boolean isActivityGroup() {
		return activityGroup;
	}

	public void setActivityGroup(boolean activityGroup) {
		this.activityGroup = activityGroup;
	}

	public Integer getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	@Override
	public String getCreateBy() {
		return createBy;
	}

	@Override
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	@Override
	public String getUpdateBy() {
		return updateBy;
	}

	@Override
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public GuideActivity() {
		this(null);
	}

	public GuideActivity(String id){
		super(id);
	}
	
	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}
	
	@NotBlank(message="活动名称不能为空")
	@Length(min=0, max=255, message="活动名称长度不能超过 255 个字符")
	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	
	@Length(min=0, max=1, message="状态长度不能超过 1 个字符")
	public String getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="开始时间不能为空")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="结束时间不能为空")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@Length(min=0, max=255, message="活动备注长度不能超过 255 个字符")
	public String getActivityRemarks() {
		return activityRemarks;
	}

	public void setActivityRemarks(String activityRemarks) {
		this.activityRemarks = activityRemarks;
	}
	
	public String getActivityIntroduce() {
		return activityIntroduce;
	}

	public void setActivityIntroduce(String activityIntroduce) {
		this.activityIntroduce = activityIntroduce;
	}
	
	@Length(min=0, max=500, message="列表banner长度不能超过 500 个字符")
	public String getBannerImage() {
		return bannerImage;
	}

	public void setBannerImage(String bannerImage) {
		this.bannerImage = bannerImage;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public List<GuideComment> getGuideCommentList() {
		return guideCommentList;
	}

	public void setGuideCommentList(List<GuideComment> guideCommentList) {
		this.guideCommentList = guideCommentList;
	}
	
	public List<GuideFaq> getGuideFaqList() {
		return guideFaqList;
	}

	public void setGuideFaqList(List<GuideFaq> guideFaqList) {
		this.guideFaqList = guideFaqList;
	}
	
}