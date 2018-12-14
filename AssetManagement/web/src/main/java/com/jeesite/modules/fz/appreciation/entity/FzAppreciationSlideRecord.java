package com.jeesite.modules.fz.appreciation.entity;


import java.io.Serializable;
import java.util.Objects;

/**
 * @author easter
 * @data 2018/10/29 11:20
 */
public class FzAppreciationSlideRecord implements Serializable {
    private String praiserId;		// 获赞者Id
    private String praiserName;		// 获赞者名字
    private String presenterId;		// 赠送者id
    private String presenterName;		// 赠送者名字
    private String praiserAvatar;      //获赞者头像url
    private String presenterAvatar;      //赠送者头像url

    public FzAppreciationSlideRecord() {
    }

    public FzAppreciationSlideRecord(String praiserId, String praiserName, String presenterId, String presenterName, String praiserAvatar, String presenterAvatar) {
        this.praiserId = praiserId;
        this.praiserName = praiserName;
        this.presenterId = presenterId;
        this.presenterName = presenterName;
        this.praiserAvatar = praiserAvatar;
        this.presenterAvatar = presenterAvatar;
    }

    public String getPraiserId() {
        return praiserId;
    }

    public void setPraiserId(String praiserId) {
        this.praiserId = praiserId;
    }

    public String getPraiserName() {
        return praiserName;
    }

    public void setPraiserName(String praiserName) {
        this.praiserName = praiserName;
    }

    public String getPresenterId() {
        return presenterId;
    }

    public void setPresenterId(String presenterId) {
        this.presenterId = presenterId;
    }

    public String getPresenterName() {
        return presenterName;
    }

    public void setPresenterName(String presenterName) {
        this.presenterName = presenterName;
    }

    public String getPraiserAvatar() {
        return praiserAvatar;
    }

    public void setPraiserAvatar(String praiserAvatar) {
        this.praiserAvatar = praiserAvatar;
    }

    public String getPresenterAvatar() {
        return presenterAvatar;
    }

    public void setPresenterAvatar(String presenterAvatar) {
        this.presenterAvatar = presenterAvatar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FzAppreciationSlideRecord that = (FzAppreciationSlideRecord) o;
        return Objects.equals(praiserId, that.praiserId) &&
                Objects.equals(praiserName, that.praiserName) &&
                Objects.equals(presenterId, that.presenterId) &&
                Objects.equals(presenterName, that.presenterName) &&
                Objects.equals(praiserAvatar, that.praiserAvatar) &&
                Objects.equals(presenterAvatar, that.presenterAvatar);
    }

    @Override
    public int hashCode() {

        return Objects.hash(praiserId, praiserName, presenterId, presenterName, praiserAvatar, presenterAvatar);
    }
}
