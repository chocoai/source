package com.jeesite.modules.fz.appreciation.entity;

import com.jeesite.common.collect.ListUtils;

import java.util.List;

public class FzGoldRecord {
    private Integer convertibleGold;    // 可兑换梵钻
    private Double expendGold;      // 总支出梵钻
    private Double surplusGold;     // 剩余梵钻
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    private List<FzAccountRecord> fzAccountRecordList = ListUtils.newArrayList();

    public Integer getConvertibleGold() {
        return convertibleGold;
    }

    public void setConvertibleGold(Integer convertibleGold) {
        this.convertibleGold = convertibleGold;
    }

    public Double getExpendGold() {
        return expendGold;
    }

    public void setExpendGold(Double expendGold) {
        this.expendGold = expendGold;
    }

    public Double getSurplusGold() {
        return surplusGold;
    }

    public void setSurplusGold(Double surplusGold) {
        this.surplusGold = surplusGold;
    }

    public List<FzAccountRecord> getFzAccountRecordList() {
        return fzAccountRecordList;
    }

    public void setFzAccountRecordList(List<FzAccountRecord> fzAccountRecordList) {
        this.fzAccountRecordList = fzAccountRecordList;
    }
}
