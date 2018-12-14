package com.jeesite.modules.asset.order.entity.k3Info;

public class VirtualQuotation {
    /**
     * 物料编码
     */
    private String materialNumber;
    /**
     * 数量
     */
    private Long materialQty;

    /**
     * 明细商品店铺
     */
    private String otherShop;

    public Long getMaterialQty() {
        return materialQty;
    }

    public void setMaterialQty(Long materialQty) {
        this.materialQty = materialQty;
    }

    public String getMaterialNumber() {
        return materialNumber;
    }

    public void setMaterialNumber(String materialNumber) {
        this.materialNumber = materialNumber;
    }

    public String getOtherShop() {
        return otherShop;
    }

    public void setOtherShop(String otherShop) {
        this.otherShop = otherShop;
    }
}
