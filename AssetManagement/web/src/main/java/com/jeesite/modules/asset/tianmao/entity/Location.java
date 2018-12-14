package com.jeesite.modules.asset.tianmao.entity;

/**
 * 类描述
 *
 * @author Jace Xiong
 */
public class Location {
    private String city;

    private String state;

    public void setCity(String city){
        this.city = city;
    }
    public String getCity(){
        return this.city;
    }
    public void setState(String state){
        this.state = state;
    }
    public String getState(){
        return this.state;
    }

}
