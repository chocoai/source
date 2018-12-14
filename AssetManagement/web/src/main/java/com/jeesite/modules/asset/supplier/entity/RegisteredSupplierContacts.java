package com.jeesite.modules.asset.supplier.entity;

public class RegisteredSupplierContacts {
    // 职务
    private String Position;
    // 联系人
    private String Contacts;
    // 手机
    private String MobilePhone;
    // 电子邮箱
    private String Email;
    // 微信
    private String WeChat;
    // QQ
    private String TencentQQ;
    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public String getContacts() {
        return Contacts;
    }

    public void setContacts(String contacts) {
        Contacts = contacts;
    }

    public String getMobilePhone() {
        return MobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        MobilePhone = mobilePhone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getWeChat() {
        return WeChat;
    }

    public void setWeChat(String weChat) {
        WeChat = weChat;
    }

    public String getTencentQQ() {
        return TencentQQ;
    }

    public void setTencentQQ(String tencentQQ) {
        TencentQQ = tencentQQ;
    }


}
