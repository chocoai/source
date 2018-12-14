/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.fgcqualitycheck.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import java.util.Date;

/**
 * 质检单Entity
 * @author len
 * @version 2018-08-18
 */
@Table(name="${_prefix}fgc_quality_check_details", alias="a", columns={
		@Column(name="fentityid", attrName="fentityid", label="质检单明细Id", isPK=true),
		@Column(name="fid", attrName="fid.fid", label="质检单Id"),
		@Column(name="billno", attrName="billno", label="单据编号"),
		@Column(name="material_code", attrName="materialCode", label="物料编码"),
		@Column(name="material_name", attrName="materialName", label="物料名称", queryType=QueryType.LIKE),
		@Column(name="source_billno", attrName="sourceBillno", label="采购单号"),
		@Column(name="qc_qty", attrName="qcQty", label="待检数量"),
		@Column(name="qc_qualified_qty", attrName="qcQualifiedQty", label="合格数"),
		@Column(name="qc_disqualified_qty", attrName="qcDisqualifiedQty", label="不合格数"),
		@Column(name="structural_test", attrName="structuralTest", label="结构检验"),
		@Column(name="appearance_test", attrName="appearanceTest", label="外观检验"),
		@Column(name="pack_test", attrName="packTest", label="包装检验"),
		@Column(name="sample_qty", attrName="sampleQty", label="抽检数量"),
		@Column(name="sample_disqualified_qty", attrName="sampleDisqualifiedQty", label="抽检不合格数"),
		@Column(name="sample_qualified_qty", attrName="sampleQualifiedQty", label="抽检合格数"),
		@Column(name="bad_ratio", attrName="badRatio", label="不良比率"),
		@Column(name="disqualified_return", attrName="disqualifiedReturn", label="抽检不合格整批打回"),
		@Column(name="qualified_type", attrName="qualifiedType", label="合格类型"),
		@Column(name="rectify_type", attrName="rectifyType", label="整改类型"),
		@Column(name="rectify_status", attrName="rectifyStatus", label="整改状态"),
		@Column(name="abnormal_problems_desc", attrName="abnormalProblemsDesc", label="异常问题描述"),
		@Column(name = "remarks",attrName = "remarks",label = "备注"),
		@Column(name="is_check", attrName="isCheck", label="已检查"),
		@Column(name = "specification",attrName = "specification",label = "规格型号"),
		@Column(name = "batch_number",attrName = "batchNumber",label = "批号"),
		@Column(name = "avg_bad_ratio",attrName = "avgBadRatio",label = "平均不良率"),
		@Column(name = "submit_time",attrName = "submitTime",label = "提交时间"),
		@Column(name = "reverse_write",attrName = "reverseWrite",label = "已反写k3"),
		@Column(name = "package_volume",attrName = "packageVolume",label = "包装体积"),
		@Column(name = "reexamination_num",attrName = "reexaminationNum",label = "复检可接受数"),
		@Column(name = "is_receive",attrName = "isReceive",label = "是否接受收料"),
		@Column(name = "packing_length",attrName = "packingLength",label = "包装长"),
		@Column(name = "packing_width",attrName = "packingWidth",label = "包装宽"),
		@Column(name = "packing_high",attrName = "packingHigh",label = "包装高"),
		@Column(name = "weight",attrName = "weight",label = "重量"),
		@Column(name = "package_num",attrName = "packageNum",label = "包件数"),
		@Column(name = "color",attrName = "color",label = "颜色"),
		@Column(name = "structural_disqualifiedQty",attrName = "structuralDisqualifiedQty",label = "结构不合格数"),
		@Column(name = "appearance_disqualifiedQty",attrName = "appearanceDisqualifiedQty",label = "外观不合格数"),
		@Column(name = "package_disqualifiedQty",attrName = "packageDisqualifiedQty",label = "包装不合格数"),
	}, orderBy="a.fentityid ASC"
)
public class QualityCheckDetails extends DataEntity<QualityCheckDetails> {
	
	private static final long serialVersionUID = 1L;
	private String fentityid;		// 质检单明细Id
	private QualityCheck fid;		// 质检单Id 父类
	private String billno;		// 单据编号
	private String materialCode;		// 物料编码
	private String materialName;		// 物料名称
	private String sourceBillno;		// 采购单号
	private Double qcQty;		// 待检数量
	private Double qcQualifiedQty;		// 合格数
	private Double qcDisqualifiedQty;		// 不合格数
	private String structuralTest;		// 结构检验
	private String appearanceTest;		// 外观检验
	private String packTest;		// 包装检验
	private Long sampleQty;		// 抽检数量
	private Long sampleDisqualifiedQty;		// 抽检不合格数
	private Long sampleQualifiedQty;		// 抽检合格数
	private Double badRatio;		// 不良比率
	private String disqualifiedReturn;		// 抽检不合格整批打回
	private String qualifiedType;		// 合格类型
	private String rectifyType;		// 整改类型
	private String rectifyStatus;		// 整改状态
	private String abnormalProblemsDesc;		// 异常问题描述
	private String isCheck;		// 已检查
	private String specification;		//规格型号
	private String batchNumber;			//批号
	private Double avgBadRatio;			//平均不良率=抽检不合格数/待检数
	private Date submitTime;			// 提交时间
	private String reverseWrite;		// 已反写k3  1是0否
	private String remarks;
	private Double packageVolume;		//包装体积
	private Integer reexaminationNum;	// 复检可接受数
	private String isReceive;	// 是否接受收料
	private Double packingLength;		// 包装长
	private Double packingWidth;		// 包装宽
	private Double packingHigh;		// 包装高
	private Double weight;			// 重量
	private Integer packageNum;		// 包件数
	private String color;			// 颜色
	private Integer structuralDisqualifiedQty;	// 结构不合格数
	private Integer appearanceDisqualifiedQty;	// 外观不合格数
	private Integer packageDisqualifiedQty;		// 包装不合格数


	public Integer getReexaminationNum() {
		return reexaminationNum;
	}

	public void setReexaminationNum(Integer reexaminationNum) {
		this.reexaminationNum = reexaminationNum;
	}

	public String getIsReceive() {
		return isReceive;
	}

	public void setIsReceive(String isReceive) {
		this.isReceive = isReceive;
	}

	@Override
	public String getRemarks() {
		return remarks;
	}

	@Override
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public QualityCheckDetails() {
		this(null);
	}


	public QualityCheckDetails(QualityCheck fid){
		this.fid = fid;
	}
	
	public String getFentityid() {
		return fentityid;
	}

	public void setFentityid(String fentityid) {
		this.fentityid = fentityid;
	}
	
	public QualityCheck getFid() {
		return fid;
	}

	public void setFid(QualityCheck fid) {
		this.fid = fid;
	}
	
	@NotBlank(message="单据编号不能为空")
	@Length(min=0, max=64, message="单据编号长度不能超过 64 个字符")
	public String getBillno() {
		return billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}
	
	@NotBlank(message="物料编码不能为空")
	@Length(min=0, max=80, message="物料编码长度不能超过 80 个字符")
	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	
	@Length(min=0, max=255, message="物料名称长度不能超过 255 个字符")
	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	
	@Length(min=0, max=64, message="采购单号长度不能超过 64 个字符")
	public String getSourceBillno() {
		return sourceBillno;
	}

	public void setSourceBillno(String sourceBillno) {
		this.sourceBillno = sourceBillno;
	}
	
	public Double getQcQty() {
		return qcQty;
	}

	public void setQcQty(Double qcQty) {
		this.qcQty = qcQty;
	}
	
	public Double getQcQualifiedQty() {
		return qcQualifiedQty;
	}

	public void setQcQualifiedQty(Double qcQualifiedQty) {
		this.qcQualifiedQty = qcQualifiedQty;
	}
	
	public Double getQcDisqualifiedQty() {
		return qcDisqualifiedQty;
	}

	public void setQcDisqualifiedQty(Double qcDisqualifiedQty) {
		this.qcDisqualifiedQty = qcDisqualifiedQty;
	}
	
	@Length(min=0, max=20, message="结构检验长度不能超过 20 个字符")
	public String getStructuralTest() {
		return structuralTest;
	}

	public void setStructuralTest(String structuralTest) {
		this.structuralTest = structuralTest;
	}
	
	@Length(min=0, max=20, message="外观检验长度不能超过 20 个字符")
	public String getAppearanceTest() {
		return appearanceTest;
	}

	public void setAppearanceTest(String appearanceTest) {
		this.appearanceTest = appearanceTest;
	}
	
	@Length(min=0, max=20, message="包装检验长度不能超过 20 个字符")
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
	
	@Length(min=0, max=20, message="抽检不合格整批打回长度不能超过 20 个字符")
	public String getDisqualifiedReturn() {
		return disqualifiedReturn;
	}

	public void setDisqualifiedReturn(String disqualifiedReturn) {
		this.disqualifiedReturn = disqualifiedReturn;
	}
	
	@Length(min=0, max=20, message="合格类型长度不能超过 20 个字符")
	public String getQualifiedType() {
		return qualifiedType;
	}

	public void setQualifiedType(String qualifiedType) {
		this.qualifiedType = qualifiedType;
	}
	
	@Length(min=0, max=20, message="整改类型长度不能超过 20 个字符")
	public String getRectifyType() {
		return rectifyType;
	}

	public void setRectifyType(String rectifyType) {
		this.rectifyType = rectifyType;
	}
	
	@Length(min=0, max=1, message="整改状态长度不能超过 1 个字符")
	public String getRectifyStatus() {
		return rectifyStatus;
	}

	public void setRectifyStatus(String rectifyStatus) {
		this.rectifyStatus = rectifyStatus;
	}
	
	@Length(min=0, max=850, message="异常问题描述长度不能超过 850 个字符")
	public String getAbnormalProblemsDesc() {
		return abnormalProblemsDesc;
	}

	public void setAbnormalProblemsDesc(String abnormalProblemsDesc) {
		this.abnormalProblemsDesc = abnormalProblemsDesc;
	}
	
	@Length(min=0, max=1, message="已检查长度不能超过 1 个字符")
	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public Double getAvgBadRatio() {
		return avgBadRatio;
	}

	public void setAvgBadRatio(Double avgBadRatio) {
		this.avgBadRatio = avgBadRatio;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public String getReverseWrite() {
		return reverseWrite;
	}

	public void setReverseWrite(String reverseWrite) {
		this.reverseWrite = reverseWrite;
	}

	public Double getPackageVolume() {
		return packageVolume;
	}

	public void setPackageVolume(Double packageVolume) {
		this.packageVolume = packageVolume;
	}

	public Double getPackingLength() {
		return packingLength;
	}

	public void setPackingLength(Double packingLength) {
		this.packingLength = packingLength;
	}

	public Double getPackingWidth() {
		return packingWidth;
	}

	public void setPackingWidth(Double packingWidth) {
		this.packingWidth = packingWidth;
	}

	public Double getPackingHigh() {
		return packingHigh;
	}

	public void setPackingHigh(Double packingHigh) {
		this.packingHigh = packingHigh;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Integer getPackageNum() {
		return packageNum;
	}

	public void setPackageNum(Integer packageNum) {
		this.packageNum = packageNum;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
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