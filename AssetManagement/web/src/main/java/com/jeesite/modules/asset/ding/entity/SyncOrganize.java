package com.jeesite.modules.asset.ding.entity;
//
public class SyncOrganize {

    private String organizeCode;
    private String organizeName;
    private String parentCode;
    private String corpCode;

    public SyncOrganize() {
    }

    public SyncOrganize(String organizeCode, String organizeName, String parentCode, String corpCode) {
        this.organizeCode = organizeCode;
        this.organizeName = organizeName;
        this.parentCode = parentCode;
        this.corpCode = corpCode;
    }

    public String getOrganizeCode() {
        return organizeCode;
    }

    public void setOrganizeCode(String organizeCode) {
        this.organizeCode = organizeCode;
    }

    public String getOrganizeName() {
        return organizeName;
    }

    public void setOrganizeName(String organizeName) {
        this.organizeName = organizeName;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getCorpCode() {
        return corpCode;
    }

    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode;
    }

}
