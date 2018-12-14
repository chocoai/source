/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.appreciation.entity;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.modules.asset.ding.entity.DingUser;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 赞赏记录表Entity
 * @author dwh
 * @version 2018-09-19
 */
@Table(name="fz_appreciation_record", alias="a", columns={
		@Column(name="appreciation_code", attrName="appreciationCode", label="赞赏记录主键", isPK=true),
		@Column(name="praiser_id", attrName="praiserId", label="获赞者Id"),
		@Column(name="presenter_id", attrName="presenterId", label="赠送者id"),
		@Column(name="presenter_department", attrName="presenterDepartment", label="赠送者部门id"),
		@Column(name="praiser_department", attrName="praiserDepartment", label="获赠者部门id"),
		@Column(name="coin_number", attrName="coinNumber", label="赠币数量"),
		@Column(name="content", attrName="content",  label="理由" ,queryType=QueryType.LIKE),
		@Column(name="tag", attrName="tag", label="标签"),
		@Column(name="praiser_number", attrName="praiserNumber", label="获赞次数"),
		@Column(name="img_url", attrName="imgUrl", label="照片路径,以，分割"),
		@Column(name="full_path", attrName="fullPath", label="照片全路径，以，分割"),
		@Column(name="coin_count", attrName="coinCount", label="赞币总数量"),
		@Column(name="anonymous", attrName="anonymous", label="匿名赞赏"),
		@Column(includeEntity=DataEntity.class),
}, joinTable = {
		@JoinTable(type=JoinTable.Type.LEFT_JOIN, entity=DingUser.class, alias="b",
				on="a.praiser_id = b.userid",
				columns={@Column(name="name", attrName="praiserName",label="获赞者名称"),
				}),
		@JoinTable(type=JoinTable.Type.LEFT_JOIN, entity=DingUser.class, alias="c",
				on="a.presenter_id = c.userid",
				columns={@Column(name="name", attrName="presenterName",label="赠送者名称"),
				}),
	}, orderBy="a.create_date DESC"
)
public class FzAppreciationRecord extends DataEntity<FzAppreciationRecord> {
	
	private static final long serialVersionUID = 1L;
	private String appreciationCode;		// 赞赏记录主键
	private String praiserId;		// 获赞者Id
	private String presenterId;		// 赠送者id
	private String presenterDepartment;		// 赠送者部门id
	private String praiserDepartment;		// 获赠者部门id
	private Long coinNumber;		// 赠币数量
	private String content;		// 理由
	private String tag;		// 标签
	private Long praiserNumber;		// 获赞次数
	private String imgUrl;		// 照片路径,以，分割
	private String fullPath;		// 照片全路径，以，分割
	private Long coinCount;		// 赞币总数量
	private List<FzAppreciationFollow> fzAppreciationFollowList = ListUtils.newArrayList();		// 子表列表
	private List<String> imgList;   //返回给前端的照片集合
	private String praiserName;         //前端获赞者名字
	private String presenterName;        //前端赠送者名字
	private boolean packup=true;            //jy需要的字段，后台没用到
	private Long createTime;                //时间秒
	private Long updateTime;               //时间秒
	private String user_img;                //用户头像
	private String userId;                  //该字段用来判断前端有没有传用户进来
	private String praiserInfo;                //条件获赞
	private String presenterInfo;           //条件赠赞
	private DingUser dingUser;
	private Long coinCounts;  				// 获赞币总数
	private Date newDate;                         //获取的最新时间
	private String anonymous;				// 匿名赞赏

	public String getAnonymous() {
		return anonymous;
	}

	public void setAnonymous(String anonymous) {
		this.anonymous = anonymous;
	}

	public Date getNewDate() {
		return newDate;
	}

	public void setNewDate(Date newDate) {
		this.newDate = newDate;
	}

	public Long getCoinCounts() {
		return coinCounts;
	}

	public void setCoinCounts(Long coinCounts) {
		this.coinCounts = coinCounts;
	}

	public DingUser getDingUser() {
		return dingUser;
	}

	public void setDingUser(DingUser dingUser) {
		this.dingUser = dingUser;
	}

	public String getPraiserInfo() {
		return praiserInfo;
	}

	public void setPraiserInfo(String praiserInfo) {
		this.praiserInfo = praiserInfo;
	}

	public String getPresenterInfo() {
		return presenterInfo;
	}

	public void setPresenterInfo(String presenterInfo) {
		this.presenterInfo = presenterInfo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUser_img() {
		return user_img;
	}

	public void setUser_img(String user_img) {
		this.user_img = user_img;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public boolean isPackup() {
		return packup;
	}

	public void setPackup(boolean packup) {
		this.packup = packup;
	}

	public String getPraiserName() {
		return praiserName;
	}

	public void setPraiserName(String praiserName) {
		this.praiserName = praiserName;
	}

	public String getPresenterName() {
		return presenterName;
	}

	public void setPresenterName(String presenterName) {
		this.presenterName = presenterName;
	}

	public List<String> getImgList() {
		return imgList;
	}

	public void setImgList(List<String> imgList) {
		this.imgList = imgList;
	}

	public FzAppreciationRecord() {
		this(null);
	}

	public FzAppreciationRecord(String id){
		super(id);
	}
	
	public String getAppreciationCode() {
		return appreciationCode;
	}

	public void setAppreciationCode(String appreciationCode) {
		this.appreciationCode = appreciationCode;
	}
	
	@NotBlank(message="获赞者Id不能为空")
	@Length(min=0, max=255, message="获赞者Id长度不能超过 255 个字符")
	public String getPraiserId() {
		return praiserId;
	}

	public void setPraiserId(String praiserId) {
		this.praiserId = praiserId;
	}
	
	@NotBlank(message="赠送者id不能为空")
	@Length(min=0, max=255, message="赠送者id长度不能超过 255 个字符")
	public String getPresenterId() {
		return presenterId;
	}

	public void setPresenterId(String presenterId) {
		this.presenterId = presenterId;
	}
	
	@Length(min=0, max=255, message="赠送者部门id长度不能超过 255 个字符")
	public String getPresenterDepartment() {
		return presenterDepartment;
	}

	public void setPresenterDepartment(String presenterDepartment) {
		this.presenterDepartment = presenterDepartment;
	}
	
	@Length(min=0, max=255, message="获赠者部门id长度不能超过 255 个字符")
	public String getPraiserDepartment() {
		return praiserDepartment;
	}

	public void setPraiserDepartment(String praiserDepartment) {
		this.praiserDepartment = praiserDepartment;
	}
	
	@NotNull(message="赠币数量不能为空")
	public Long getCoinNumber() {
		return coinNumber;
	}

	public void setCoinNumber(Long coinNumber) {
		this.coinNumber = coinNumber;
	}
	
	@Length(min=0, max=1000, message="理由长度不能超过 1000 个字符")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=500, message="标签长度不能超过 500 个字符")
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public Long getPraiserNumber() {
		return praiserNumber;
	}

	public void setPraiserNumber(Long praiserNumber) {
		this.praiserNumber = praiserNumber;
	}
	
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}
	
	public Long getCoinCount() {
		return coinCount;
	}

	public void setCoinCount(Long coinCount) {
		this.coinCount = coinCount;
	}
	
	public List<FzAppreciationFollow> getFzAppreciationFollowList() {
		return fzAppreciationFollowList;
	}

	public void setFzAppreciationFollowList(List<FzAppreciationFollow> fzAppreciationFollowList) {
		this.fzAppreciationFollowList = fzAppreciationFollowList;
	}
	
}