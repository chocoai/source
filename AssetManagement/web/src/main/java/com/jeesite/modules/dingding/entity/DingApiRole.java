package com.jeesite.modules.dingding.entity;

public class DingApiRole {

    private Long id;
    private String name;
    private String groupName;
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public String getGroupName() {
        return groupName;
    }

}