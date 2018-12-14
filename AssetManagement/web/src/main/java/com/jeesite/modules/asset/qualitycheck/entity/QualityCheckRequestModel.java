package com.jeesite.modules.asset.qualitycheck.entity;

/**
 * @program: AssetManagement
 * @description: 质检单请求参数
 * @author: Albert Feng
 * @create: 2018-08-01 16:22
 **/
public class QualityCheckRequestModel {
    private String id;
    private String keyword;
    private  Integer limit;
    private  Integer page;
    private  String status;
    private  Object extend;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getExtend() {
        return extend;
    }

    public void setExtend(Object extend) {
        this.extend = extend;
    }
}
