package com.jeesite.modules.asset.guideApp.entity;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;

import java.util.List;

/**
 * @Auther: len
 * @Date: 2018/8/1 09:54
 * @Description:
 */
@Table(name="${_prefix}guide", alias="a", columns={
        @Column(name="documentCode", attrName="documentCode", label="订单编号", isPK=true),
        @Column(name="totalFee", attrName="totalFee", label="订单类型"),
        @Column(name="documentStatus", attrName="documentStatus", label="订单状态"),
        @Column(name="customerName", attrName="customerName", label="客户姓名"),
        @Column(name="createTime", attrName="createTime", label="客户昵称"),
})
public class GuideOrder extends DataEntity<GuideOrder> {
    /**
     * 订单号
     */
    private String documentCode;
    /**
     * 订单类型
     */
    private String documentType;
    /**
     * 移动电话
     */
    private String mobilePhone;
    /**
     * 订单状态
     */
    private String documentStatus;
    /**
     * 合计应收
     */
    private Double totalFee;
    /**
     * 客户姓名
     */
    private String customerName;
    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 商品明细
     */
    private List<GuideGoods> goods;

    public String getDocumentCode() {
        return documentCode;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

    public Double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public List<GuideGoods> getGoods() {
        return goods;
    }

    public void setGoods(List<GuideGoods> goods) {
        this.goods = goods;
    }
    private String officeCode;
    private String userCode;
    private String userName;
    private String nameOrPhone;

    public String getNameOrPhone() {
        return nameOrPhone;
    }

    public void setNameOrPhone(String nameOrPhone) {
        this.nameOrPhone = nameOrPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
}
