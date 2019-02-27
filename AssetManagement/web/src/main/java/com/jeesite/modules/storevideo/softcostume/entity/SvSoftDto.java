package com.jeesite.modules.storevideo.softcostume.entity;

import java.util.List;

public class SvSoftDto {

    private SvSoftCostume svSoftCostume;

    private List<SvSoftRlat> svSoftRlatList;

    public SvSoftCostume getSvSoftCostume() {
        return svSoftCostume;
    }

    public void setSvSoftCostume(SvSoftCostume svSoftCostume) {
        this.svSoftCostume = svSoftCostume;
    }

    public List<SvSoftRlat> getSvSoftRlatList() {
        return svSoftRlatList;
    }

    public void setSvSoftRlatList(List<SvSoftRlat> svSoftRlatList) {
        this.svSoftRlatList = svSoftRlatList;
    }
}
