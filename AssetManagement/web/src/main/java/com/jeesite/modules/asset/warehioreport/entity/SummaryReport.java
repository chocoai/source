/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.warehioreport.entity;

import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.jeesite.modules.asset.consumables.entity.Consumables;
import com.jeesite.modules.asset.warehouse.entity.AmWarehouse;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 耗材仓库进出明细表Entity
 * @author czy
 * @version 2018-05-26
 */
@Table(name="${_prefix}am_wareh_iodetails", alias="a", columns={
		@Column(name="id", attrName="id", label="编码", isPK=true),
		@Column(name="operation_date", attrName="operationDate", label="操作时间"),
		@Column(name="operation_by", attrName="operationBy", label="操作人"),
		@Column(name="sign", attrName="sign", label="进出标志"),
		@Column(name="bill_code", attrName="billCode", label="单据编号"),
		@Column(name="bill_date", attrName="billDate", label="单据日期"),
		@Column(name="bill_type", attrName="billType", label="单据类型"),
		@Column(name="warehouse_code", attrName="warehouseCode", label="仓库编码"),
		@Column(name="consumables_code", attrName="consumablesCode", label="耗材编码"),
		@Column(name="instorage_count", attrName="instorageCount", label="入库数量"),
		@Column(name="instorage_amount", attrName="instorageAmount", label="入库金额"),
		@Column(name="outstorage_count", attrName="outstorageCount", label="出库数量"),
		@Column(name="outstorage_amount", attrName="outstorageAmount", label="出库金额"),
		@Column(name="price", attrName="price", label="单价"),
	}, joinTable={
		@JoinTable(type=JoinTable.Type.LEFT_JOIN, entity=Consumables.class, alias="b",
				on="a.consumables_code=b.consumables_code ",
				columns={
//				@Column(name="consumables_code", label="档案编码", isPK=true),
						@Column(includeEntity=Consumables.class)}),
		@JoinTable(type=JoinTable.Type.LEFT_JOIN, entity=AmWarehouse.class, alias="d",
				on="d.warehouse_code = a.warehouse_code", attrName="amWarehouse",
				columns={@Column(name="warehouse_code", label="仓库编码"),
						@Column(name="warehouse_name", label="仓库名称")}),
}, orderBy="a.id DESC"
)
public class SummaryReport extends DataEntity<SummaryReport> {
	
	private static final long serialVersionUID = 1L;
	private Date operationDate;		// 操作时间
	private String operationBy;		// 操作人
	private String sign;		// 进出标志
	private String billCode;		// 单据编号
	private Date billDate;		// 单据日期
	private String billType;		// 单据类型
	private String warehouseCode;		// 仓库编码
	private String consumablesCode;		// 耗材编码
	private Long instorageCount;		// 入库数量
	private Double instorageAmount;		// 入库金额
	private Long outstorageCount;		// 出库数量
	private Double outstorageAmount;		// 出库金额
	private Double price;		// 单价

	private Long beginningCount;    //期初数量
	private Double beginningAmount;		//期初金额
	private Double beginningPrice;		//期初单价
	private Double instoragePrice;		// 入库单价
	private Double outstoragePrice;		// 出库单价
	private Long balanceCount;  //结余数量
	private Double balanceAmount;		//结余金额
	private Double balancePrice;		//结余单价

	private Date beginDate;
	private Date endDate;

	private Long beginningOutCount;  //期初出库数量
	private Long beginningInCount;	//期初入库总数量
	private Double beginningOutAmount;  //期初出库数量
	private Double beginningInAmount;	//期初入库总数量

	private AmWarehouse amWarehouse;

	private Consumables consumables;

	private String warehouseName;
	private String imgPath;
	private String codeORname;


	//合计显示
	private long allbgingCount;
	private Double allbgingAmount;
	private long allinstorageCount;
	private Double allinstorageAmount;
	private long alloutCount;
	private Double alloutAmount;
	private long allbalanceCount;
	private Double allbalanceAmount;


	public String getCodeORname() {
		return codeORname;
	}

	public void setCodeORname(String codeORname) {
		this.codeORname = codeORname;
	}

	public long getAllbgingCount() {
		return allbgingCount;
	}


	public AmWarehouse getAmWarehouse() {
		return amWarehouse;
	}

	public void setAmWarehouse(AmWarehouse amWarehouse) {
		this.amWarehouse = amWarehouse;
	}

	public void setAllbgingCount(long allbgingCount) {
		this.allbgingCount = allbgingCount;
	}

	public Double getAllbgingAmount() {
		return allbgingAmount;
	}

	public void setAllbgingAmount(Double allbgingAmount) {
		this.allbgingAmount = allbgingAmount;
	}

	public long getAllinstorageCount() {
		return allinstorageCount;
	}

	public void setAllinstorageCount(long allinstorageCount) {
		this.allinstorageCount = allinstorageCount;
	}

	public Double getAllinstorageAmount() {
		return allinstorageAmount;
	}

	public void setAllinstorageAmount(Double allinstorageAmount) {
		this.allinstorageAmount = allinstorageAmount;
	}

	public long getAlloutCount() {
		return alloutCount;
	}

	public void setAlloutCount(long alloutCount) {
		this.alloutCount = alloutCount;
	}

	public Double getAlloutAmount() {
		return alloutAmount;
	}

	public void setAlloutAmount(Double alloutAmount) {
		this.alloutAmount = alloutAmount;
	}

	public long getAllbalanceCount() {
		return allbalanceCount;
	}

	public void setAllbalanceCount(long allbalanceCount) {
		this.allbalanceCount = allbalanceCount;
	}

	public Double getAllbalanceAmount() {
		return allbalanceAmount;
	}

	public void setAllbalanceAmount(Double allbalanceAmount) {
		this.allbalanceAmount = allbalanceAmount;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}


	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public Consumables getConsumables() {
		return consumables;
	}

	public void setConsumables(Consumables consumables) {
		this.consumables = consumables;
	}

	public Long getBeginningOutCount() {
		return beginningOutCount;
	}

	public void setBeginningOutCount(Long beginningOutCount) {
		this.beginningOutCount = beginningOutCount;
	}

	public Long getBeginningInCount() {
		return beginningInCount;
	}

	public void setBeginningInCount(Long beginningInCount) {
		this.beginningInCount = beginningInCount;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public SummaryReport() {
		this(null);
	}

	public SummaryReport(String id){
		super(id);
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="操作时间不能为空")
	public Date getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}
	
	@NotBlank(message="操作人不能为空")
	@Length(min=0, max=100, message="操作人长度不能超过 100 个字符")
	public String getOperationBy() {
		return operationBy;
	}

	public void setOperationBy(String operationBy) {
		this.operationBy = operationBy;
	}
	
	@NotBlank(message="进出标志不能为空")
	@Length(min=0, max=1, message="进出标志长度不能超过 1 个字符")
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
	@NotBlank(message="单据编号不能为空")
	@Length(min=0, max=64, message="单据编号长度不能超过 64 个字符")
	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="单据日期不能为空")
	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	
	@NotBlank(message="单据类型不能为空")
	@Length(min=0, max=1, message="单据类型长度不能超过 1 个字符")
	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}
	
	@NotBlank(message="仓库编码不能为空")
	@Length(min=0, max=64, message="仓库编码长度不能超过 64 个字符")
	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	
	@NotBlank(message="耗材编码不能为空")
	@Length(min=0, max=64, message="耗材编码长度不能超过 64 个字符")
	public String getConsumablesCode() {
		return consumablesCode;
	}

	public void setConsumablesCode(String consumablesCode) {
		this.consumablesCode = consumablesCode;
	}
	
	public Long getInstorageCount() {
		return instorageCount;
	}

	public void setInstorageCount(Long instorageCount) {
		this.instorageCount = instorageCount;
	}
	
	public Double getInstorageAmount() {
		return instorageAmount;
	}

	public void setInstorageAmount(Double instorageAmount) {
		this.instorageAmount = instorageAmount;
	}
	
	public Long getOutstorageCount() {
		return outstorageCount;
	}

	public void setOutstorageCount(Long outstorageCount) {
		this.outstorageCount = outstorageCount;
	}
	
	public Double getOutstorageAmount() {
		return outstorageAmount;
	}

	public void setOutstorageAmount(Double outstorageAmount) {
		this.outstorageAmount = outstorageAmount;
	}
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getBeginningCount() {
		return beginningCount;
	}

	public void setBeginningCount(Long beginningCount) {
		this.beginningCount = beginningCount;
	}

	public Double getBeginningAmount() {
		return beginningAmount;
	}

	public void setBeginningAmount(Double beginningAmount) {
		this.beginningAmount = beginningAmount;
	}

	public Double getBeginningPrice() {
		return beginningPrice;
	}

	public void setBeginningPrice(Double beginningPrice) {
		this.beginningPrice = beginningPrice;
	}

	public Double getInstoragePrice() {
		return instoragePrice;
	}

	public void setInstoragePrice(Double instoragePrice) {
		this.instoragePrice = instoragePrice;
	}

	public Double getOutstoragePrice() {
		return outstoragePrice;
	}

	public void setOutstoragePrice(Double outstoragePrice) {
		this.outstoragePrice = outstoragePrice;
	}

	public Long getBalanceCount() {
		return balanceCount;
	}

	public void setBalanceCount(Long balanceCount) {
		this.balanceCount = balanceCount;
	}

	public Double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public Double getBalancePrice() {
		return balancePrice;
	}

	public void setBalancePrice(Double balancePrice) {
		this.balancePrice = balancePrice;
	}

	public Double getBeginningOutAmount() {
		return beginningOutAmount;
	}

	public void setBeginningOutAmount(Double beginningOutAmount) {
		this.beginningOutAmount = beginningOutAmount;
	}

	public Double getBeginningInAmount() {
		return beginningInAmount;
	}

	public void setBeginningInAmount(Double beginningInAmount) {
		this.beginningInAmount = beginningInAmount;
	}
}