package com.jeesite.modules.storevideo.ovopark.api;

public class Stat {
    public String cid;
    public Integer code;
    public String codename;
    private String EMPTY_FACESET;
    public Long systime;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getCodename() {
        return codename;
    }

    public void setCodename(String codename) {
        this.codename = codename;
    }

    public String getEMPTY_FACESET() {
        return EMPTY_FACESET;
    }

    public void setEMPTY_FACESET(String EMPTY_FACESET) {
        this.EMPTY_FACESET = EMPTY_FACESET;
    }

    public Long getSystime() {
        return systime;
    }

    public void setSystime(Long systime) {
        this.systime = systime;
    }
}
