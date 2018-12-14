package com.jeesite.modules.asset.fgcqualitycheck.entity;

import com.jeesite.common.entity.DataEntity;

import java.util.Date;

public class SyncQcBillToK3 extends DataEntity<SyncQcBillToK3> {
    private static final long serialVersionUID = 1L;
    private String fid;		// 质检单Id 父类
    private String billno;      //质检单号
    private String fentityid;		// 质检单明细Id
    private String structuralTest;		// 结构检验
    private String appearanceTest;		// 外观检验
    private String packTest;		// 包装检验
    private Long sampleQty;		// 抽检数量
    private Long sampleDisqualifiedQty;		// 抽检不合格数
    private Long sampleQualifiedQty;		// 抽检合格数
    private Double badRatio;		// 抽检不良率
    private Double avgBadRatio;			//平均不良率=抽检不合格数/待检数
    private String rectifyType;		// 整改类型
    private String remarks;		// 备注
    private String isCheck;		// 已检查
    private Date submitTime;			// 提交时间
    private String reverseWrite;        //已反写K3
    private Double qcQualifiedQty;      // 合格数
    private Double qcDisqualifiedQty;   // 不合格数
    private Integer reexaminationNum;	// 复检可接受数
    private Integer structuralDisqualifiedQty;	// 结构不合格数
    private Integer appearanceDisqualifiedQty;	// 外观不合格数
    private Integer packageDisqualifiedQty;		// 包装不合格数

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getBillno() {
        return billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public String getFentityid() {
        return fentityid;
    }

    public void setFentityid(String fentityid) {
        this.fentityid = fentityid;
    }

    public String getStructuralTest() {
        return structuralTest;
    }

    public void setStructuralTest(String structuralTest) {
        this.structuralTest = structuralTest;
    }

    public String getAppearanceTest() {
        return appearanceTest;
    }

    public void setAppearanceTest(String appearanceTest) {
        this.appearanceTest = appearanceTest;
    }

    public String getPackTest() {
        return packTest;
    }

    public void setPackTest(String packTest) {
        this.packTest = packTest;
    }

    public Long getSampleQty() {
        return sampleQty;
    }

    public void setSampleQty(Long sampleQty) {
        this.sampleQty = sampleQty;
    }

    public Long getSampleDisqualifiedQty() {
        return sampleDisqualifiedQty;
    }

    public void setSampleDisqualifiedQty(Long sampleDisqualifiedQty) {
        this.sampleDisqualifiedQty = sampleDisqualifiedQty;
    }

    public Long getSampleQualifiedQty() {
        return sampleQualifiedQty;
    }

    public void setSampleQualifiedQty(Long sampleQualifiedQty) {
        this.sampleQualifiedQty = sampleQualifiedQty;
    }

    public Double getBadRatio() {
        return badRatio;
    }

    public void setBadRatio(Double badRatio) {
        this.badRatio = badRatio;
    }

    public Double getAvgBadRatio() {
        return avgBadRatio;
    }

    public void setAvgBadRatio(Double avgBadRatio) {
        this.avgBadRatio = avgBadRatio;
    }

    public String getRectifyType() {
        return rectifyType;
    }

    public void setRectifyType(String rectifyType) {
        this.rectifyType = rectifyType;
    }

    public String getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(String isCheck) {
        this.isCheck = isCheck;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    public String getReverseWrite() {
        return reverseWrite;
    }

    public void setReverseWrite(String reverseWrite) {
        this.reverseWrite = reverseWrite;
    }

    public Double getQcDisqualifiedQty() {
        return qcDisqualifiedQty;
    }

    public void setQcDisqualifiedQty(Double qcDisqualifiedQty) {
        this.qcDisqualifiedQty = qcDisqualifiedQty;
    }

    public Integer getReexaminationNum() {
        return reexaminationNum;
    }

    public void setReexaminationNum(Integer reexaminationNum) {
        this.reexaminationNum = reexaminationNum;
    }

    public Double getQcQualifiedQty() {
        return qcQualifiedQty;
    }

    public void setQcQualifiedQty(Double qcQualifiedQty) {
        this.qcQualifiedQty = qcQualifiedQty;
    }

    public Integer getStructuralDisqualifiedQty() {
        return structuralDisqualifiedQty;
    }

    public void setStructuralDisqualifiedQty(Integer structuralDisqualifiedQty) {
        this.structuralDisqualifiedQty = structuralDisqualifiedQty;
    }

    public Integer getAppearanceDisqualifiedQty() {
        return appearanceDisqualifiedQty;
    }

    public void setAppearanceDisqualifiedQty(Integer appearanceDisqualifiedQty) {
        this.appearanceDisqualifiedQty = appearanceDisqualifiedQty;
    }

    public Integer getPackageDisqualifiedQty() {
        return packageDisqualifiedQty;
    }

    public void setPackageDisqualifiedQty(Integer packageDisqualifiedQty) {
        this.packageDisqualifiedQty = packageDisqualifiedQty;
    }
}
