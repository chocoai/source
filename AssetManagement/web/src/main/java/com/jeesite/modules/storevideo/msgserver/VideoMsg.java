package com.jeesite.modules.storevideo.msgserver;

import java.util.List;

public class VideoMsg {
    /**
     * clear,add
     */
    private String flag;
    /**
     * default,customer
     */
    private String type;
    private List<String> videoId;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getVideoId() {
        return videoId;
    }

    public void setVideoId(List<String> videoId) {
        this.videoId = videoId;
    }
}
