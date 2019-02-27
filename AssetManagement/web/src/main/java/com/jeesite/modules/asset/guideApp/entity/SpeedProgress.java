package com.jeesite.modules.asset.guideApp.entity;

public class SpeedProgress {
    /**
     * 单号
     */
    private String FBillNo;
    /**
     * 跟进时间
     */
    private String FTrackDatetime;
    /**
     * 进度
     */
    private String FSchedule;
    /**
     * 跟进人
     */
    private String FTrackUserName;
    /**
     * 跟进人id
     */
    private String FTrackUserId;

    public String getFBillNo() {
        return FBillNo;
    }

    public void setFBillNo(String FBillNo) {
        this.FBillNo = FBillNo;
    }

    public String getFTrackDatetime() {
        return FTrackDatetime;
    }

    public void setFTrackDatetime(String FTrackDatetime) {
        this.FTrackDatetime = FTrackDatetime;
    }

    public String getFSchedule() {
        return FSchedule;
    }

    public void setFSchedule(String FSchedule) {
        this.FSchedule = FSchedule;
    }

    public String getFTrackUserName() {
        return FTrackUserName;
    }

    public void setFTrackUserName(String FTrackUserName) {
        this.FTrackUserName = FTrackUserName;
    }

    public String getFTrackUserId() {
        return FTrackUserId;
    }

    public void setFTrackUserId(String FTrackUserId) {
        this.FTrackUserId = FTrackUserId;
    }
}
