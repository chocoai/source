package com.jeesite.modules.storevideo.ovopark.api;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class BaseResponse<T> implements Serializable {
    public static Stat stat;
    public String result;
    public T data;
    @JSONField(serialize = false)
    private String errorMsg;

    public Stat getStat() {
        return stat;
    }

    public void setStat(Stat stat) {
        this.stat = stat;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMsg(){
        if(errorMsg == null){
            if(stat.getCode().equals(0)) errorMsg = "0";
            else errorMsg = result;
        }
        return errorMsg;
    }
}
