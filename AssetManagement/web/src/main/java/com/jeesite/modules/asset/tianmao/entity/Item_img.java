package com.jeesite.modules.asset.tianmao.entity;

/**
 * 类描述
 *
 * @author Jace Xiong
 */
public class Item_img {
    private int id;

    private int position;

    private String url;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setPosition(int position){
        this.position = position;
    }
    public int getPosition(){
        return this.position;
    }
    public void setUrl(String url){
        this.url = url;
    }
    public String getUrl(){
        return this.url;
    }
}
