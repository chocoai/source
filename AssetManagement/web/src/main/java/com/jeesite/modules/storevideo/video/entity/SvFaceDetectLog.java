/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.video.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;

/**
 * 人脸识别记录Entity
 * @author Philip Guan
 * @version 2019-01-18
 */
@Table(name="sv_face_detect_log", alias="a", columns={
		@Column(name="log_code", attrName="logCode", label="编号", isPK=true),
		@Column(name="result", attrName="result", label="接口请求结果"),
		@Column(name="timestamp", attrName="_timestamp", label="时间戳"),
		@Column(name="age", attrName="age", label="年龄"),
		@Column(name="arrivetime", attrName="arrivetime", label="到店时间"),
		@Column(name="deviceid", attrName="deviceid", label="设备id"),
		@Column(name="devicemac", attrName="devicemac", label="设备mac"),
		@Column(name="devicename", attrName="devicename", label="设备名称"),
		@Column(name="emotion", attrName="emotion", label="anger", comment="anger:愤怒,disgust:厌恶,fear:恐惧,happiness:高兴,neutral:平静,sadness:伤心,surprise:惊讶"),
		@Column(name="ethnicity", attrName="ethnicity", label="Asian", comment="Asian:亚洲人,White:白人,Black:黑人"),
		@Column(name="faceid", attrName="faceid", label="faceId"),
		@Column(name="gender", attrName="gender", label="性别", comment="性别(false:男,true:女)"),
		@Column(name="glass", attrName="glass", label="Sunglasses", comment="Sunglasses:墨镜, None:不戴眼镜, Normal:普通眼镜"),
		@Column(name="groupid", attrName="groupid", label="人脸分组id"),
		@Column(name="membertype", attrName="membertype", label="1", comment="1:顾客,2:会员,3:店员"),
		@Column(name="mobilephone", attrName="mobilephone", label="手机号码"),
		@Column(name="orgid", attrName="orgid", label="企业id"),
		@Column(name="skinstatus", attrName="skinstatus", label="health", comment="health:健康, stain:色斑,acne:青春痘,dark_circle:黑眼圈"),
		@Column(name="thirdpicurl", attrName="thirdpicurl", label="访问地址"),
		@Column(name="userid", attrName="userid", label="用户id"),
		@Column(name="username", attrName="username", label="用户姓名"),
		@Column(name="wdzpicurl", attrName="wdzpicurl", label="图片url地址"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class SvFaceDetectLog extends DataEntity<SvFaceDetectLog> {
	
	private static final long serialVersionUID = 1L;
	private String logCode;		// 编号
	private String _timestamp;		// 时间戳
	private String result;		// 接口请求结果
	private String age;		// 年龄
	private String arrivetime;		// 到店时间
	private String deviceid;		// 设备id
	private String devicemac;		// 设备mac
	private String devicename;		// 设备名称
	private String emotion;		// anger:愤怒,disgust:厌恶,fear:恐惧,happiness:高兴,neutral:平静,sadness:伤心,surprise:惊讶
	private String ethnicity;		// Asian:亚洲人,White:白人,Black:黑人
	private String faceid;		// faceId
	private String gender;		// 性别(false:男,true:女)
	private String glass;		// Sunglasses:墨镜, None:不戴眼镜, Normal:普通眼镜
	private String groupid;		// 人脸分组id
	private String membertype;		// 1:顾客,2:会员,3:店员
	private String mobilephone;		// 手机号码
	private String orgid;		// 企业id
	private String skinstatus;		// health:健康, stain:色斑,acne:青春痘,dark_circle:黑眼圈
	private String thirdpicurl;		// 访问地址
	private String userid;		// 用户id
	private String username;		// 用户姓名
	private String wdzpicurl;		// 图片url地址
	
	public SvFaceDetectLog() {
		this(null);
	}

	public SvFaceDetectLog(String id){
		super(id);
	}
	
	public String getLogCode() {
		return logCode;
	}

	public void setLogCode(String logCode) {
		this.logCode = logCode;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Length(min=0, max=64, message="时间戳长度不能超过 64 个字符")
	public String get_timestamp() {
		return _timestamp;
	}

	public void set_timestamp(String _timestamp) {
		this._timestamp = _timestamp;
	}
	
	@Length(min=0, max=64, message="年龄长度不能超过 64 个字符")
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	
	@Length(min=0, max=64, message="到店时间长度不能超过 64 个字符")
	public String getArrivetime() {
		return arrivetime;
	}

	public void setArrivetime(String arrivetime) {
		this.arrivetime = arrivetime;
	}
	
	@Length(min=0, max=64, message="设备id长度不能超过 64 个字符")
	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	
	@Length(min=0, max=64, message="设备mac长度不能超过 64 个字符")
	public String getDevicemac() {
		return devicemac;
	}

	public void setDevicemac(String devicemac) {
		this.devicemac = devicemac;
	}
	
	@Length(min=0, max=64, message="设备名称长度不能超过 64 个字符")
	public String getDevicename() {
		return devicename;
	}

	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}
	
	@Length(min=0, max=64, message="anger长度不能超过 64 个字符")
	public String getEmotion() {
		return emotion;
	}

	public void setEmotion(String emotion) {
		this.emotion = emotion;
	}
	
	@Length(min=0, max=64, message="Asian长度不能超过 64 个字符")
	public String getEthnicity() {
		return ethnicity;
	}

	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}
	
	@Length(min=0, max=64, message="faceId长度不能超过 64 个字符")
	public String getFaceid() {
		return faceid;
	}

	public void setFaceid(String faceid) {
		this.faceid = faceid;
	}
	
	@Length(min=0, max=64, message="性别长度不能超过 64 个字符")
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	@Length(min=0, max=64, message="Sunglasses长度不能超过 64 个字符")
	public String getGlass() {
		return glass;
	}

	public void setGlass(String glass) {
		this.glass = glass;
	}
	
	@Length(min=0, max=500, message="人脸分组id长度不能超过 500 个字符")
	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	
	@Length(min=0, max=64, message="1长度不能超过 64 个字符")
	public String getMembertype() {
		return membertype;
	}

	public void setMembertype(String membertype) {
		this.membertype = membertype;
	}
	
	@Length(min=0, max=64, message="手机号码长度不能超过 64 个字符")
	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	
	@Length(min=0, max=64, message="企业id长度不能超过 64 个字符")
	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	
	@Length(min=0, max=64, message="health长度不能超过 64 个字符")
	public String getSkinstatus() {
		return skinstatus;
	}

	public void setSkinstatus(String skinstatus) {
		this.skinstatus = skinstatus;
	}
	
	@Length(min=0, max=1000, message="访问地址长度不能超过 1000 个字符")
	public String getThirdpicurl() {
		return thirdpicurl;
	}

	public void setThirdpicurl(String thirdpicurl) {
		this.thirdpicurl = thirdpicurl;
	}
	
	@Length(min=0, max=64, message="用户id长度不能超过 64 个字符")
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	@Length(min=0, max=64, message="用户姓名长度不能超过 64 个字符")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Length(min=0, max=1000, message="图片url地址长度不能超过 1000 个字符")
	public String getWdzpicurl() {
		return wdzpicurl;
	}

	public void setWdzpicurl(String wdzpicurl) {
		this.wdzpicurl = wdzpicurl;
	}
	
}