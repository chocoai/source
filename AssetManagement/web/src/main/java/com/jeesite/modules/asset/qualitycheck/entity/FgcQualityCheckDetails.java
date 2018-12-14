/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.qualitycheck.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 质检单Entity
 * @author Albert
 * @version 2018-07-25
 */
@Table(name="${_prefix}fgc_quality_check_details", alias="a", columns={
		@Column(name="id", attrName="id", label="主键", isPK=true),
		@Column(name="quality_id", attrName="id.qualityId", label="质检单Id"),
		@Column(name="billno", attrName="billno.billno", label="单据编号"),
		@Column(name="material_code", attrName="materialCode", label="物料编码"),
		@Column(name="material_name", attrName="materialName", label="物料名称", queryType=QueryType.LIKE),
		@Column(name="source_billno", attrName="sourceBillno", label="源单编号"),
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
//		@Column(includeEntity=DataEntity.class),
		@Column(name="is_check", attrName="isCheck", label="已检查"),
	}, orderBy="a.id ASC"
)
public class FgcQualityCheckDetails extends DataEntity<FgcQualityCheckDetails> {
	
	private static final long serialVersionUID = 1L;
	private String id;				//质检单明细Id
	private String billno;		// 单据编号 父类
	private FgcQualityCheck qualityId;	//质检单Id 父类
	private String materialCode;		// 物料编码
	private String materialName;		// 物料名称
	private String sourceBillno;		// 源单编号
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
	private String isCheck;						//已检查
	private String fid;				//
	private String billNo;


	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public FgcQualityCheckDetails() {
		this(null);
	}


	public FgcQualityCheckDetails(FgcQualityCheck qualityId){
		this.qualityId = qualityId;
	}

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
	
	@NotBlank(message="物料名称不能为空")
	@Length(min=0, max=255, message="物料名称长度不能超过 255 个字符")
	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	
	@Length(min=0, max=64, message="源单编号长度不能超过 64 个字符")
	public String getSourceBillno() {
		return sourceBillno;
	}

	public void setSourceBillno(String sourceBillno) {
		this.sourceBillno = sourceBillno;
	}
	
	@NotNull(message="待检数量不能为空")
	public Double getQcQty() {
		return qcQty;
	}

	public void setQcQty(Double qcQty) {
		this.qcQty = qcQty;
	}
	
	@NotNull(message="合格数不能为空")
	public Double getQcQualifiedQty() {
		return qcQualifiedQty;
	}

	public void setQcQualifiedQty(Double qcQualifiedQty) {
		this.qcQualifiedQty = qcQualifiedQty;
	}
	
	@NotNull(message="不合格数不能为空")
	public Double getQcDisqualifiedQty() {
		return qcDisqualifiedQty;
	}

	public void setQcDisqualifiedQty(Double qcDisqualifiedQty) {
		this.qcDisqualifiedQty = qcDisqualifiedQty;
	}
	
	@NotBlank(message="结构检验不能为空")
	@Length(min=0, max=20, message="结构检验长度不能超过 20 个字符")
	public String getStructuralTest() {
		return structuralTest;
	}

	public void setStructuralTest(String structuralTest) {
		this.structuralTest = structuralTest;
	}
	
	@NotBlank(message="外观检验不能为空")
	@Length(min=0, max=20, message="外观检验长度不能超过 20 个字符")
	public String getAppearanceTest() {
		return appearanceTest;
	}

	public void setAppearanceTest(String appearanceTest) {
		this.appearanceTest = appearanceTest;
	}
	
	@NotBlank(message="包装检验不能为空")
	@Length(min=0, max=20, message="包装检验长度不能超过 20 个字符")
	public String getPackTest() {
		return packTest;
	}

	public void setPackTest(String packTest) {
		this.packTest = packTest;
	}
	
	@NotNull(message="抽检数量不能为空")
	public Long getSampleQty() {
		return sampleQty;
	}

	public void setSampleQty(Long sampleQty) {
		this.sampleQty = sampleQty;
	}
	
	@NotNull(message="抽检不合格数不能为空")
	public Long getSampleDisqualifiedQty() {
		return sampleDisqualifiedQty;
	}

	public void setSampleDisqualifiedQty(Long sampleDisqualifiedQty) {
		this.sampleDisqualifiedQty = sampleDisqualifiedQty;
	}
	
	@NotNull(message="抽检合格数不能为空")
	public Long getSampleQualifiedQty() {
		return sampleQualifiedQty;
	}

	public void setSampleQualifiedQty(Long sampleQualifiedQty) {
		this.sampleQualifiedQty = sampleQualifiedQty;
	}
	
	@NotNull(message="不良比率不能为空")
	public Double getBadRatio() {
		return badRatio;
	}

	public void setBadRatio(Double badRatio) {
		this.badRatio = badRatio;
	}
	
	@NotBlank(message="抽检不合格整批打回不能为空")
	@Length(min=0, max=20, message="抽检不合格整批打回长度不能超过 20 个字符")
	public String getDisqualifiedReturn() {
		return disqualifiedReturn;
	}

	public void setDisqualifiedReturn(String disqualifiedReturn) {
		this.disqualifiedReturn = disqualifiedReturn;
	}
	
	@NotBlank(message="合格类型不能为空")
	@Length(min=0, max=20, message="合格类型长度不能超过 20 个字符")
	public String getQualifiedType() {
		return qualifiedType;
	}

	public void setQualifiedType(String qualifiedType) {
		this.qualifiedType = qualifiedType;
	}
	
	@NotBlank(message="整改类型不能为空")
	@Length(min=0, max=20, message="整改类型长度不能超过 20 个字符")
	public String getRectifyType() {
		return rectifyType;
	}

	public void setRectifyType(String rectifyType) {
		this.rectifyType = rectifyType;
	}
	
	@NotBlank(message="整改状态不能为空")
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


	public FgcQualityCheck getQualityId() {
		return qualityId;
	}

	public void setQualityId(FgcQualityCheck qualityId) {
		this.qualityId = qualityId;
	}

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}
}