package com.jeesite.modules.asset.tianmao.entity;

import java.util.List;

/**
 * 类描述
 *
 * @author Jace Xiong
 */
public class Skus {
    private List<Sku> sku ;

    public void setSku(List<Sku> sku){
        this.sku = sku;
    }
    public List<Sku> getSku(){
        return this.sku;
    }
}
