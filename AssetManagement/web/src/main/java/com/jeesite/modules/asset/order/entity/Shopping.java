package com.jeesite.modules.asset.order.entity;

public class Shopping {
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品id
     */
    private String id;
    /**
     * 商品skuid
     */
    private String skuid;
    /**
     *  规格名称
     */
    private String property;
    /**
     * 商品图片
     */
    private String pic;
    /**
     * sku库存
     */
    private Integer quantity;
    /**
     * 商品sku编码
     */
    private String outerCode;
    /**
     * 商品店铺
     */
    private String storename;
    /**
     * 商品sku真实价格
     */
    private String price;
    /**
     * 件数
     */
    private Integer count;

    /**
     * sku图片
     */
    private String skuUrl;
    /**
     * 分销价
     */
    private Double distributionPrice;

    public String getSkuUrl() {
        return skuUrl;
    }

    public void setSkuUrl(String skuUrl) {
        this.skuUrl = skuUrl;
    }

    public Double getDistributionPrice() {
        return distributionPrice;
    }

    public void setDistributionPrice(Double distributionPrice) {
        this.distributionPrice = distributionPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSkuid() {
        return skuid;
    }

    public void setSkuid(String skuid) {
        this.skuid = skuid;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getOuterCode() {
        return outerCode;
    }

    public void setOuterCode(String outerCode) {
        this.outerCode = outerCode;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
