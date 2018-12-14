package com.jeesite.modules.fgc.dao;

public class UserDataInfo {
    private String documentCode;		// 主键
    private String openId;		// OpenID
    private String userName;		// 用户名
    private String verificationCode;		// 绑定的验证码
    private String nickname;		// 微信用户昵称
    private String sex;		// 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
    private String province;		// 用户个人资料填写的省份
    private String city;		// 普通用户个人资料填写的城市
    private String country;		// 国家，如中国为CN
    private String headimgurl;		// 用户头像，最后一个数值代表正方形头像大小
    private String privilege;		// 用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
    private Long unionid;		// 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
    private String sysLoginCode;		// 加密后的系统登录账号
    private String sysLoginPas;		// 加密后的系统登录密码
    private String token;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDocumentCode() {
        return documentCode;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public Long getUnionid() {
        return unionid;
    }

    public void setUnionid(Long unionid) {
        this.unionid = unionid;
    }

    public String getSysLoginCode() {
        return sysLoginCode;
    }

    public void setSysLoginCode(String sysLoginCode) {
        this.sysLoginCode = sysLoginCode;
    }

    public String getSysLoginPas() {
        return sysLoginPas;
    }

    public void setSysLoginPas(String sysLoginPas) {
        this.sysLoginPas = sysLoginPas;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
