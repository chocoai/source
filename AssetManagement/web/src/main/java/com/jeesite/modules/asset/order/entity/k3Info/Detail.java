package com.jeesite.modules.asset.order.entity.k3Info;

public class Detail {
    /**
     * 物料编码sku
     */
    private String F_MaterialNo;
    /**
     * 数量
     */
    private Long F_Qty;
    /**
     * 单价
     */
    private Double F_UnitPrice;
    /**
     * 标准售价
     */
    private Double F_StandPrice;
    /**
     * 金额
     */
    private Double F_Amount;
    /**
     * 商品店铺
     */
    private String F_GoodsStore;

    public String getF_GoodsStore() {
        return F_GoodsStore;
    }

    public void setF_GoodsStore(String f_GoodsStore) {
        F_GoodsStore = f_GoodsStore;
    }

    public String getF_MaterialNo() {
        return F_MaterialNo;
    }

    public void setF_MaterialNo(String f_MaterialNo) {
        F_MaterialNo = f_MaterialNo;
    }

    public Long getF_Qty() {
        return F_Qty;
    }

    public void setF_Qty(Long f_Qty) {
        F_Qty = f_Qty;
    }

    public Double getF_UnitPrice() {
        return F_UnitPrice;
    }

    public void setF_UnitPrice(Double f_UnitPrice) {
        F_UnitPrice = f_UnitPrice;
    }

    public Double getF_StandPrice() {
        return F_StandPrice;
    }

    public void setF_StandPrice(Double f_StandPrice) {
        F_StandPrice = f_StandPrice;
    }

    public Double getF_Amount() {
        return F_Amount;
    }

    public void setF_Amount(Double f_Amount) {
        F_Amount = f_Amount;
    }
}
