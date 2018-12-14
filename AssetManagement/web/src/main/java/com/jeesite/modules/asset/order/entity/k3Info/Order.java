package com.jeesite.modules.asset.order.entity.k3Info;

public class Order {
    /**
     * 原始编号
     */
    private String F_BillNo;
    /**
     * 收货人
     */
    private String F_ReceiverName;
    /**
     * 移动电话
     */
    private String F_Tel;
    /**
     * 固定电话
     */
    private String F_Phone;
    /**
     * 省
     */
    private String F_Province;
    /**
     * 市
     */
    private String F_City;
    /**
     * 区
     */
    private String F_District;
    /**
     * 收货地址
     */
    private String F_Address;
    /**
     * 卖家备注
     */
    private String F_Remark;
    /**
     * 业务来源
     */
    private String F_CusFromType;
    /**
     * 特权订单1
     */
    private String F_RealNo1;
    /**
     * 特权订单2
     */
    private String F_RealNo2;
    /**
     * 特权订单3
     */
    private String F_RealNo3;
    /**
     * 提交人
     */
    private String F_SubmitUserName;
    /**
     * 创建时间
     */
    private String F_CreatorTime;
    /**
     * 合计应收
     */
    private Double F_AllTotal;
    /**
     * 优惠金额
     */
    private Double F_FavorableTotal;
    /**
     * 货款合计
     */
    private Double F_GoodsTotal;
    /**
     * 物流费
     */
    private Double F_LogisticsFee;
    /**
     * 三包费
     */
    private Double F_SbFee;
    /**
     * 取价时间
     */
    private String F_PriceofTime;
    /**
     * 邮费（三包费+物流费）
     */
    private Double F_YF_PostageAmount;

    public Double getF_LogisticsFee() {
        return F_LogisticsFee;
    }

    public void setF_LogisticsFee(Double f_LogisticsFee) {
        F_LogisticsFee = f_LogisticsFee;
    }

    public Double getF_SbFee() {
        return F_SbFee;
    }

    public void setF_SbFee(Double f_SbFee) {
        F_SbFee = f_SbFee;
    }

    public Double getF_AllTotal() {
        return F_AllTotal;
    }

    public void setF_AllTotal(Double f_AllTotal) {
        F_AllTotal = f_AllTotal;
    }

    public Double getF_FavorableTotal() {
        return F_FavorableTotal;
    }

    public void setF_FavorableTotal(Double f_FavorableTotal) {
        F_FavorableTotal = f_FavorableTotal;
    }

    public Double getF_GoodsTotal() {
        return F_GoodsTotal;
    }

    public void setF_GoodsTotal(Double f_GoodsTotal) {
        F_GoodsTotal = f_GoodsTotal;
    }

    public String getF_CreatorTime() {
        return F_CreatorTime;
    }

    public void setF_CreatorTime(String f_CreatorTime) {
        F_CreatorTime = f_CreatorTime;
    }

    public String getF_SubmitUserName() {
        return F_SubmitUserName;
    }

    public void setF_SubmitUserName(String f_SubmitUserName) {
        F_SubmitUserName = f_SubmitUserName;
    }

    public String getF_BillNo() {
        return F_BillNo;
    }

    public void setF_BillNo(String f_BillNo) {
        F_BillNo = f_BillNo;
    }

    public String getF_ReceiverName() {
        return F_ReceiverName;
    }

    public void setF_ReceiverName(String f_ReceiverName) {
        F_ReceiverName = f_ReceiverName;
    }

    public String getF_Tel() {
        return F_Tel;
    }

    public void setF_Tel(String f_Tel) {
        F_Tel = f_Tel;
    }

    public String getF_Phone() {
        return F_Phone;
    }

    public void setF_Phone(String f_Phone) {
        F_Phone = f_Phone;
    }

    public String getF_Province() {
        return F_Province;
    }

    public void setF_Province(String f_Province) {
        F_Province = f_Province;
    }

    public String getF_City() {
        return F_City;
    }

    public void setF_City(String f_City) {
        F_City = f_City;
    }

    public String getF_District() {
        return F_District;
    }

    public void setF_District(String f_District) {
        F_District = f_District;
    }

    public String getF_Address() {
        return F_Address;
    }

    public void setF_Address(String f_Address) {
        F_Address = f_Address;
    }

    public String getF_Remark() {
        return F_Remark;
    }

    public void setF_Remark(String f_Remark) {
        F_Remark = f_Remark;
    }

    public String getF_CusFromType() {
        return F_CusFromType;
    }

    public void setF_CusFromType(String f_CusFromType) {
        F_CusFromType = f_CusFromType;
    }

    public String getF_RealNo1() {
        return F_RealNo1;
    }

    public void setF_RealNo1(String f_RealNo1) {
        F_RealNo1 = f_RealNo1;
    }

    public String getF_RealNo2() {
        return F_RealNo2;
    }

    public void setF_RealNo2(String f_RealNo2) {
        F_RealNo2 = f_RealNo2;
    }

    public String getF_RealNo3() {
        return F_RealNo3;
    }

    public void setF_RealNo3(String f_RealNo3) {
        F_RealNo3 = f_RealNo3;
    }

    public String getF_PriceofTime() {
        return F_PriceofTime;
    }

    public void setF_PriceofTime(String f_PriceofTime) {
        F_PriceofTime = f_PriceofTime;
    }

    public Double getF_YF_PostageAmount() {
        return F_YF_PostageAmount;
    }

    public void setF_YF_PostageAmount(Double f_YF_PostageAmount) {
        F_YF_PostageAmount = f_YF_PostageAmount;
    }
}
