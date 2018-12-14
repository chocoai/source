/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.wish.entity;

import com.jeesite.common.utils.excel.annotation.ExcelField;
import com.jeesite.common.utils.excel.annotation.ExcelFields;
import org.hibernate.validator.constraints.Length;
import java.util.List;
import com.jeesite.common.collect.ListUtils;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import javax.validation.Valid;

/**
 * 入围名单Entity
 * @author len
 * @version 2018-11-20
 */
@Table(name="${_prefix}wish_shortlist", alias="a", columns={
		@Column(name="user_id", attrName="userId", label="用户id", isPK=true),
		@Column(name="user_name", attrName="userName", label="用户名", queryType=QueryType.LIKE),
		@Column(name="job_number", attrName="jobNumber", label="工号"),
		@Column(name="position", attrName="position", label="岗位"),
		@Column(name="department_id", attrName="departmentId", label="部门id"),
		@Column(name="department", attrName="department", label="部门"),
		@Column(name="nominate_num", attrName="nominateNum", label="提名次数"),
		@Column(name="user_status", attrName="userStatus", label="状态"),
		@Column(name="votes_num", attrName="votesNum", label="得票数"),
		@Column(name="score", attrName="score", label="分数"),
		@Column(name="personal_profile", attrName="personalProfile", label="个人简介"),
		@Column(name="avatar", attrName="avatar", label="头像"),
		@Column(name="img1", attrName="img1", label="图片1"),
		@Column(name="img2", attrName="img2", label="图片2"),
		@Column(name="img3", attrName="img3", label="图片3"),
		@Column(name="img4", attrName="img4", label="图片4"),
		@Column(name="img5", attrName="img5", label="图片5"),
		@Column(name="img6", attrName="img6", label="图片6"),
	}, orderBy="a.nominate_num DESC"
)
public class WishShortlist extends DataEntity<WishShortlist> {

	@Valid
	@ExcelFields({
			@ExcelField(title="用户id", attrName="userId", align=ExcelField.Align.CENTER, type = ExcelField.Type.IMPORT,sort=10),
			@ExcelField(title="用户名", attrName="userName", align=ExcelField.Align.CENTER, sort=15),
			@ExcelField(title="工号", attrName="jobNumber", align = ExcelField.Align.CENTER, sort=20),
			@ExcelField(title="岗位", attrName="position",align = ExcelField.Align.CENTER, sort=30),
			@ExcelField(title="部门id", attrName="departmentId",align = ExcelField.Align.CENTER,type = ExcelField.Type.IMPORT, sort=40),
			@ExcelField(title="部门 ", attrName="department",align = ExcelField.Align.CENTER, sort=45),
			@ExcelField(title="提名次数 ", attrName="nominateNum",align = ExcelField.Align.CENTER, sort=45),
			@ExcelField(title="状态 ", attrName="userStatus",align = ExcelField.Align.CENTER, dictType="sys_yes_no", sort=45),
			@ExcelField(title="得票数 ", attrName="votesNum",align = ExcelField.Align.CENTER,type = ExcelField.Type.EXPORT, sort=45),
			@ExcelField(title="分数 ", attrName="score",align = ExcelField.Align.CENTER,type = ExcelField.Type.EXPORT, sort=45),
	})
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户id
	private String userName;		// 用户名
	private String jobNumber;		// 工号
	private String position;		// 岗位
	private String departmentId;		// 部门id
	private String department;		// 部门
	private Integer nominateNum;		// 提名次数
	private String userStatus;		// 状态
	private Integer votesNum;		// 得票数
	private Double score;		// 分数
	private String personalProfile;		// 个人简介
	private List<WishVotedDetail> wishVotedDetailList = ListUtils.newArrayList();		// 子表列表
	private String avatar;		// 头像
	private String name; 		// 用于查询部门/名称
	private String img1;
	private String img2;
	private String img3;
	private String img4;
	private String img5;
	private String img6;
	private List<ShortlistImage> photoList = ListUtils.newArrayList();		// 图片列表
	private boolean isChecked;
	private boolean packup;


	public boolean isPackup() {
		return packup;
	}

	public void setPackup(boolean packup) {
		this.packup = packup;
	}

	public Integer getVotesNum() {
		return votesNum;
	}

	public void setVotesNum(Integer votesNum) {
		this.votesNum = votesNum;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean checked) {
		isChecked = checked;
	}

	public List<ShortlistImage> getPhotoList() {
		return photoList;
	}

	public void setPhotoList(List<ShortlistImage> photoList) {
		this.photoList = photoList;
	}

	public String getImg1() {
		return img1;
	}

	public void setImg1(String img1) {
		this.img1 = img1;
	}

	public String getImg2() {
		return img2;
	}

	public void setImg2(String img2) {
		this.img2 = img2;
	}

	public String getImg3() {
		return img3;
	}

	public void setImg3(String img3) {
		this.img3 = img3;
	}

	public String getImg4() {
		return img4;
	}

	public void setImg4(String img4) {
		this.img4 = img4;
	}

	public String getImg5() {
		return img5;
	}

	public void setImg5(String img5) {
		this.img5 = img5;
	}

	public String getImg6() {
		return img6;
	}

	public void setImg6(String img6) {
		this.img6 = img6;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public WishShortlist() {
		this(null);
	}

	public WishShortlist(String id){
		super(id);
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=64, message="用户名长度不能超过 64 个字符")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Length(min=0, max=10, message="工号长度不能超过 10 个字符")
	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}
	
	@Length(min=0, max=64, message="岗位长度不能超过 64 个字符")
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	@Length(min=0, max=128, message="部门id长度不能超过 64 个字符")
	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	
	@Length(min=0, max=64, message="部门长度不能超过 64 个字符")
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	public Integer getNominateNum() {
		return nominateNum;
	}

	public void setNominateNum(Integer nominateNum) {
		this.nominateNum = nominateNum;
	}
	
	@Length(min=0, max=1, message="状态长度不能超过 1 个字符")
	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	@Length(min=0, max=1000, message="个人简介长度不能超过 1000 个字符")
	public String getPersonalProfile() {
		return personalProfile;
	}

	public void setPersonalProfile(String personalProfile) {
		this.personalProfile = personalProfile;
	}
	
	public List<WishVotedDetail> getWishVotedDetailList() {
		return wishVotedDetailList;
	}

	public void setWishVotedDetailList(List<WishVotedDetail> wishVotedDetailList) {
		this.wishVotedDetailList = wishVotedDetailList;
	}
	
}