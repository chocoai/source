package com.jeesite.modules.asset.tianmao.entity;

/**
 * 类描述
 *
 * @author Jace Xiong
 */
public class Delivery_time {
    private String delivery_time_type;

    private String need_delivery_time;

    public void setDelivery_time_type(String delivery_time_type){
        this.delivery_time_type = delivery_time_type;
    }
    public String getDelivery_time_type(){
        return this.delivery_time_type;
    }
    public void setNeed_delivery_time(String need_delivery_time){
        this.need_delivery_time = need_delivery_time;
    }
    public String getNeed_delivery_time(){
        return this.need_delivery_time;
    }

}
