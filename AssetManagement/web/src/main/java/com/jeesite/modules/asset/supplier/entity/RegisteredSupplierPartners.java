package com.jeesite.modules.asset.supplier.entity;

public class RegisteredSupplierPartners {
    // 客户名称
    private String CustomerName;
    // 年合作份额
    private Long AnnualPartnership;
    // 主要合作产品类型
    private String MainProductType;
    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public Long getAnnualPartnership() {
        return AnnualPartnership;
    }

    public void setAnnualPartnership(Long annualPartnership) {
        AnnualPartnership = annualPartnership;
    }

    public String getMainProductType() {
        return MainProductType;
    }

    public void setMainProductType(String mainProductType) {
        MainProductType = mainProductType;
    }


}
