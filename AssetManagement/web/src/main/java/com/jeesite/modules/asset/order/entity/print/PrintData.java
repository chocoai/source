package com.jeesite.modules.asset.order.entity.print;

import java.util.Date;
import java.util.List;

public class PrintData {

    /**
     * 订单基本信息-客户姓名
     */
    private String customerName;    // 客户ID
    /**
     * 订单创建时间 取年月日
     */
    private String createDate;  // 下单日期
    /**
     * 订单基本信息-客户来源
     */
    private String customerSource; // 来源
    /**
     * 订单基本信息-商品总额
     */
    private String totalPrice;      // 商品总额
    /**
     * 订单基本信息-优惠金额
     */
    private String preferential;    // 优惠金额
    /**
     * 订单基本信息-物流费+三包费
     */
    private String installFee;      // 物流/安装费
    /**
     * 订单基本信息-备注
     */
    private String remarks;         // 备注
    /**
     * 订单基本信息-客户姓名
     */
    private String collectBy;       // 收货人
    /**
     * 订单基本信息-移动电话
     */
    private String mobile;          // 联系电话
    /**
     * 订单基本信息-省+市+区+详细地址
     */
    private String receiveAddress;  // 收货地址
    /**
     * 订单商品明细
     */
    List<ItemDetail> item;          // 商品明细

    /**
     * 订单商品明细条数
     */
    private Integer size;

    /**
     * 订单基本信息-合计应收
     */
    private String totalFee;  // 合计应收

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCustomerSource() {
        return customerSource;
    }

    public void setCustomerSource(String customerSource) {
        this.customerSource = customerSource;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCollectBy() {
        return collectBy;
    }

    public void setCollectBy(String collectBy) {
        this.collectBy = collectBy;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public List<ItemDetail> getItem() {
        return item;
    }

    public void setItem(List<ItemDetail> item) {
        this.item = item;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPreferential() {
        return preferential;
    }

    public void setPreferential(String preferential) {
        this.preferential = preferential;
    }

    public String getInstallFee() {
        return installFee;
    }

    public void setInstallFee(String installFee) {
        this.installFee = installFee;
    }
}
