package com.jeesite.modules.storevideo.video.entity;

import java.util.List;

public class SvVideoDto {

    private SvVideo svVideo;

    private List<SvVideoRlat> svVideoRlatList;

    public SvVideo getSvVideo() {
        return svVideo;
    }

    public void setSvVideo(SvVideo svVideo) {
        this.svVideo = svVideo;
    }

    public List<SvVideoRlat> getSvVideoRlatList() {
        return svVideoRlatList;
    }

    public void setSvVideoRlatList(List<SvVideoRlat> svVideoRlatList) {
        this.svVideoRlatList = svVideoRlatList;
    }
}
