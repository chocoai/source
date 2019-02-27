package com.jeesite.modules.asset.screen.entity;

public class ProductData {
    /**
     * 企业编码
     */
    private String enterpriseCode;
    /**
     * 产品编码
     */
    private String productCode;		// 产品编码
    /**
     * 产品名称
     */
    private String productName;		// 产品名称
    /**
     * 产品图片
     */
    private String productImg;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getEnterpriseCode() {
        return enterpriseCode;
    }

    public void setEnterpriseCode(String enterpriseCode) {
        this.enterpriseCode = enterpriseCode;
    }
}
