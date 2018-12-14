package com.jeesite.modules.asset.tianmao.entity;

public class InventoryStockQueryModel {

    /// <summary>
    /// 物料编码
    /// </summary>
    public String F_YF_MaterialNumber;

    /// <summary>
    /// 物料名称
    /// </summary>
    public String F_YF_MaterialName;

    /// <summary>
    /// 规格型号
    /// </summary>
    public String F_YF_SpecMode;

    /// <summary>
    /// 仓库
    /// </summary>
    public String F_YF_StockName;

    /// <summary>
    /// 库存数
    /// </summary>
    public Double F_YF_StockQty;

    /// <summary>
    /// 采购在途数
    /// </summary>
    public Double  F_YF_POQty;

    /// <summary>
    /// 可用数
    /// </summary>
    public Double  F_YF_AvailableQty;

    /// <summary>
    /// 订单数
    /// </summary>
    public Double  F_YF_TradeQty;

    /// <summary>
    /// 可销售数
    /// </summary>
    public Double  F_YF_CanSaleQty;
    /// <summary>
    /// 预计交期
    /// </summary>
    public String F_YF_ExpectedDate;
    /// <summary>
    /// 采购周期
    /// </summary>
    public String F_YF_PurchaseCycle;
    /// <summary>
    /// 现货清货数
    /// </summary>
    public Double  F_YF_SpotClearanceQty;
    /// <summary>
    /// 调拨在途
    /// </summary>
    public Double  FTransferQty;
    /// <summary>
    /// 物料分组
    /// </summary>
    public String FMaterialGroup;

    public String getF_YF_MaterialNumber() {
        return F_YF_MaterialNumber;
    }

    public void setF_YF_MaterialNumber(String f_YF_MaterialNumber) {
        F_YF_MaterialNumber = f_YF_MaterialNumber;
    }

    public String getF_YF_MaterialName() {
        return F_YF_MaterialName;
    }

    public void setF_YF_MaterialName(String f_YF_MaterialName) {
        F_YF_MaterialName = f_YF_MaterialName;
    }

    public String getF_YF_SpecMode() {
        return F_YF_SpecMode;
    }

    public void setF_YF_SpecMode(String f_YF_SpecMode) {
        F_YF_SpecMode = f_YF_SpecMode;
    }

    public String getF_YF_StockName() {
        return F_YF_StockName;
    }

    public void setF_YF_StockName(String f_YF_StockName) {
        F_YF_StockName = f_YF_StockName;
    }

    public Double getF_YF_StockQty() {
        return F_YF_StockQty;
    }

    public void setF_YF_StockQty(Double f_YF_StockQty) {
        F_YF_StockQty = f_YF_StockQty;
    }

    public Double getF_YF_POQty() {
        return F_YF_POQty;
    }

    public void setF_YF_POQty(Double f_YF_POQty) {
        F_YF_POQty = f_YF_POQty;
    }

    public Double getF_YF_AvailableQty() {
        return F_YF_AvailableQty;
    }

    public void setF_YF_AvailableQty(Double f_YF_AvailableQty) {
        F_YF_AvailableQty = f_YF_AvailableQty;
    }

    public Double getF_YF_TradeQty() {
        return F_YF_TradeQty;
    }

    public void setF_YF_TradeQty(Double f_YF_TradeQty) {
        F_YF_TradeQty = f_YF_TradeQty;
    }

    public Double getF_YF_CanSaleQty() {
        return F_YF_CanSaleQty;
    }

    public void setF_YF_CanSaleQty(Double f_YF_CanSaleQty) {
        F_YF_CanSaleQty = f_YF_CanSaleQty;
    }

    public String getF_YF_ExpectedDate() {
        return F_YF_ExpectedDate;
    }

    public void setF_YF_ExpectedDate(String f_YF_ExpectedDate) {
        F_YF_ExpectedDate = f_YF_ExpectedDate;
    }

    public String getF_YF_PurchaseCycle() {
        return F_YF_PurchaseCycle;
    }

    public void setF_YF_PurchaseCycle(String f_YF_PurchaseCycle) {
        F_YF_PurchaseCycle = f_YF_PurchaseCycle;
    }

    public Double getF_YF_SpotClearanceQty() {
        return F_YF_SpotClearanceQty;
    }

    public void setF_YF_SpotClearanceQty(Double f_YF_SpotClearanceQty) {
        F_YF_SpotClearanceQty = f_YF_SpotClearanceQty;
    }

    public Double getFTransferQty() {
        return FTransferQty;
    }

    public void setFTransferQty(Double FTransferQty) {
        this.FTransferQty = FTransferQty;
    }

    public String getFMaterialGroup() {
        return FMaterialGroup;
    }

    public void setFMaterialGroup(String FMaterialGroup) {
        this.FMaterialGroup = FMaterialGroup;
    }
}
