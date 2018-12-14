package com.jeesite.modules.asset.tianmao.entity;

import java.util.List;

public class PriceTag {
    /**
     * 模板数据
     */
    List<GoodsTag> goodsTagList;

    /**
     * K3物料表不存在的SKU
     */
    List<String> skuList;

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 打印类型
     */
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<GoodsTag> getGoodsTagList() {
        return goodsTagList;
    }

    public void setGoodsTagList(List<GoodsTag> goodsTagList) {
        this.goodsTagList = goodsTagList;
    }

    public List<String> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<String> skuList) {
        this.skuList = skuList;
    }
}
