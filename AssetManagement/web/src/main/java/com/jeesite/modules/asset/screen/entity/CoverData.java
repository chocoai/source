package com.jeesite.modules.asset.screen.entity;

import java.util.List;

public class CoverData {
    /**
     * 企业编码
     *
     */
    private String enterpriseCode;
    /**
     * 企业名称
     */
    private String enterpriseName;
    /**
     * 封面图片
     */
    private String enterpriseImg;
    /**
     * 产品
     */
    List<ProductData> productDataList;

    public String getEnterpriseCode() {
        return enterpriseCode;
    }

    public void setEnterpriseCode(String enterpriseCode) {
        this.enterpriseCode = enterpriseCode;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getEnterpriseImg() {
        return enterpriseImg;
    }

    public void setEnterpriseImg(String enterpriseImg) {
        this.enterpriseImg = enterpriseImg;
    }

    public List<ProductData> getProductDataList() {
        return productDataList;
    }

    public void setProductDataList(List<ProductData> productDataList) {
        this.productDataList = productDataList;
    }
}
