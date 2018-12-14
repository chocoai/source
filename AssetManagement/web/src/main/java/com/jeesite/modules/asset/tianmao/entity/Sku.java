package com.jeesite.modules.asset.tianmao.entity;

/**
 * 类描述
 *
 * @author Jace Xiong
 */
public class Sku {
    private String created;

    private String modified;

    private String outer_id;

    private String price;

    private String properties;

    private String properties_name;

    private int quantity;

    private int sku_id;

    private int with_hold_quantity;

    public void setCreated(String created){
        this.created = created;
    }
    public String getCreated(){
        return this.created;
    }
    public void setModified(String modified){
        this.modified = modified;
    }
    public String getModified(){
        return this.modified;
    }
    public void setOuter_id(String outer_id){
        this.outer_id = outer_id;
    }
    public String getOuter_id(){
        return this.outer_id;
    }
    public void setPrice(String price){
        this.price = price;
    }
    public String getPrice(){
        return this.price;
    }
    public void setProperties(String properties){
        this.properties = properties;
    }
    public String getProperties(){
        return this.properties;
    }
    public void setProperties_name(String properties_name){
        this.properties_name = properties_name;
    }
    public String getProperties_name(){
        return this.properties_name;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    public int getQuantity(){
        return this.quantity;
    }
    public void setSku_id(int sku_id){
        this.sku_id = sku_id;
    }
    public int getSku_id(){
        return this.sku_id;
    }
    public void setWith_hold_quantity(int with_hold_quantity){
        this.with_hold_quantity = with_hold_quantity;
    }
    public int getWith_hold_quantity(){
        return this.with_hold_quantity;
    }
}
