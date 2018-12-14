package com.jeesite.modules.asset.order.entity.print;

/**
 * 订单商品明细
 */
public class ItemDetail {
    /**
     * 订单商品明细SKU
     */
    private String sku;     // 物料编码
    /**
     * 订单商品明细商品名称
     */
    private String goodsName;    // 商品名称
    /**
     * 订单商品规格
     */
    private String spec;          // 规格
    /**
     * 订单商品明细该商品的单价
     */
    private String price;       // 单价
    /**
     * 订单商品明细该商品的数量
     */
    private Long count;      // 数量
    /**
     * =单价*（1-优惠金额/商品总额）
     */
    private String discountAmt; // 折扣后单价
    /**
     * 表体金额
     */
    private String amount;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getDiscountAmt() {
        return discountAmt;
    }

    public void setDiscountAmt(String discountAmt) {
        this.discountAmt = discountAmt;
    }
}
