package com.jeesite.modules.asset.k3webapi;

import java.util.Date;

public class TrackData {
    private String FID;
    private String FBillNo;  //单据编号
    private String FDocumentStatus;  // 单据状态
    private String F_YF_ReturnPointFDATAVALUE;  // 退回点
    private String F_YF_SBMasterFName;    // 三包师傅
    private String F_YF_MasterTel;       // 师傅电话
    private String F_YF_LogisticsCompany;  // 物流公司
    private String F_YF_LogisticsNo;     // 物流单号
    private String F_YF_StockIdFName;     // 仓库
    private String F_YF_ReturnAddress;   // 退回地址
    private String F_YF_RefundTypeFDATAVALUE;  // 退货类型
    private String F_YF_ShopNameFName;   // 店铺
    private String F_YF_PlatForm;   // 平台
    private String F_YF_ReturnGoodsRemarks; // 退货说明
    private String F_YF_RefundResponsible;  // 退货责任方
    private Date F_YF_EstimateDate;   // 预计到货时间
    private String F_YF_ReturnNumber;  // 退货物流单号
    private String FBillStatus;       // 退货入仓方式
    private String F_YF_ReturnRemarks;  // 退货备注
    private Double F_YF_LogisticsCost;  // 物流费用
    private String F_YF_LogisticsJSModeFDATAVALUE;   // 物流结算方式
    // 售后信息
    private String F_YF_AfterSaleNo;   // 售后单号
    private String F_YF_OutStockNo;    // 出库单号
    private String F_YF_OutOrderStatus;   // 订单状态
    private String FSALESMANIDFname;      // 销售员
    private String F_YF_Customer;      // 客户
    private String F_YF_DeliveryMethod;   // 配送方式
    private String F_YF_LogisticsCompany1; // 物流公司
    private String F_YF_ClientName;   // 客户名称
    private String F_YF_Phone;    // 移动电话
    private String F_YF_DetailsAddres; // 详细地址
    private String F_YF_Question;     // 问题详情
    private String F_YF_Province;     // 省份
    private String F_YF_City;          // 城市
    private String F_YF_Area;           // 区县
    private String F_YF_IMAGE1;     // 图片id1
    private String F_YF_IMAGE2;     // 图片id2
    private String F_YF_IMAGE3;     // 图片id3
    private String F_YF_IMAGE4;     // 图片id4
    private String F_YF_IMAGE5;     // 图片id5
    private String F_YF_IMAGE6;     // 图片id6
    private Date F_YF_ADUITDATETIME;  // 审核时间
    // 明细信息
    private String FEntryID;        // 明细id
    private String F_YF_MATERIALID;  // 物料编码
    private String F_YF_MarterialName;  // 物料名称
    private String F_YF_SpecMode;      // 规格型号
    private String F_YF_ISGIFTS;        // 是否赠品
    private String F_YF_ISFITTING;      // 是否配件
    private String F_YF_FITTINGDESCIPTION;      // 配件说明
    private String F_YF_REALQTY;        // 实发数量
    private String F_YF_RETURNQTY;      // 实退数量
    private String F_YF_PackageNumber;  // 包件数
    private String F_YF_LOGISTICSVOLUMETOTAL;   // 体积
    private String F_YF_REMARK;         // 备注
    // 调货信息
    private String F_YF_TransferGoodsEntityID;  // 调货明细id
    private String F_YF_MaterialsId;            // 物料编码
    private String F_YF_MaterialName;           // 物料名称
    private String F_YF_TRANSFERGOODSQTY;       // 调货数量
    private String F_YF_SALESORDER;             // 销售订单
    private String F_YF_CLIENTIDFNAME;          // 客户
    private String F_YF_SELLERIDFNAME;          // 销售员
    private String F_YF_REMARKS;                // 备注
    private String F_YF_RECEIVEADDRESS;         // 地址
    private String F_YF_ClientsName;            // 客户姓名
    private String F_YF_ClientPhone;            // 移动电话
    private String F_YF_DetailAddress;          // 详细地址

    public String getFID() {
        return FID;
    }

    public void setFID(String FID) {
        this.FID = FID;
    }

    public String getFBillNo() {
        return FBillNo;
    }

    public void setFBillNo(String FBillNo) {
        this.FBillNo = FBillNo;
    }

    public String getFDocumentStatus() {
        return FDocumentStatus;
    }

    public void setFDocumentStatus(String FDocumentStatus) {
        this.FDocumentStatus = FDocumentStatus;
    }

    public String getF_YF_ReturnPointFDATAVALUE() {
        return F_YF_ReturnPointFDATAVALUE;
    }

    public void setF_YF_ReturnPointFDATAVALUE(String f_YF_ReturnPointFDATAVALUE) {
        F_YF_ReturnPointFDATAVALUE = f_YF_ReturnPointFDATAVALUE;
    }

    public String getF_YF_SBMasterFName() {
        return F_YF_SBMasterFName;
    }

    public void setF_YF_SBMasterFName(String f_YF_SBMasterFName) {
        F_YF_SBMasterFName = f_YF_SBMasterFName;
    }

    public String getF_YF_MasterTel() {
        return F_YF_MasterTel;
    }

    public void setF_YF_MasterTel(String f_YF_MasterTel) {
        F_YF_MasterTel = f_YF_MasterTel;
    }

    public String getF_YF_LogisticsCompany() {
        return F_YF_LogisticsCompany;
    }

    public void setF_YF_LogisticsCompany(String f_YF_LogisticsCompany) {
        F_YF_LogisticsCompany = f_YF_LogisticsCompany;
    }

    public String getF_YF_LogisticsNo() {
        return F_YF_LogisticsNo;
    }

    public void setF_YF_LogisticsNo(String f_YF_LogisticsNo) {
        F_YF_LogisticsNo = f_YF_LogisticsNo;
    }

    public String getF_YF_StockIdFName() {
        return F_YF_StockIdFName;
    }

    public void setF_YF_StockIdFName(String f_YF_StockIdFName) {
        F_YF_StockIdFName = f_YF_StockIdFName;
    }

    public String getF_YF_ReturnAddress() {
        return F_YF_ReturnAddress;
    }

    public void setF_YF_ReturnAddress(String f_YF_ReturnAddress) {
        F_YF_ReturnAddress = f_YF_ReturnAddress;
    }

    public String getF_YF_RefundTypeFDATAVALUE() {
        return F_YF_RefundTypeFDATAVALUE;
    }

    public void setF_YF_RefundTypeFDATAVALUE(String f_YF_RefundTypeFDATAVALUE) {
        F_YF_RefundTypeFDATAVALUE = f_YF_RefundTypeFDATAVALUE;
    }

    public String getF_YF_ShopNameFName() {
        return F_YF_ShopNameFName;
    }

    public void setF_YF_ShopNameFName(String f_YF_ShopNameFName) {
        F_YF_ShopNameFName = f_YF_ShopNameFName;
    }

    public String getF_YF_PlatForm() {
        return F_YF_PlatForm;
    }

    public void setF_YF_PlatForm(String f_YF_PlatForm) {
        F_YF_PlatForm = f_YF_PlatForm;
    }

    public String getF_YF_ReturnGoodsRemarks() {
        return F_YF_ReturnGoodsRemarks;
    }

    public void setF_YF_ReturnGoodsRemarks(String f_YF_ReturnGoodsRemarks) {
        F_YF_ReturnGoodsRemarks = f_YF_ReturnGoodsRemarks;
    }

    public String getF_YF_RefundResponsible() {
        return F_YF_RefundResponsible;
    }

    public void setF_YF_RefundResponsible(String f_YF_RefundResponsible) {
        F_YF_RefundResponsible = f_YF_RefundResponsible;
    }

    public Date getF_YF_EstimateDate() {
        return F_YF_EstimateDate;
    }

    public void setF_YF_EstimateDate(Date f_YF_EstimateDate) {
        F_YF_EstimateDate = f_YF_EstimateDate;
    }

    public String getF_YF_ReturnNumber() {
        return F_YF_ReturnNumber;
    }

    public void setF_YF_ReturnNumber(String f_YF_ReturnNumber) {
        F_YF_ReturnNumber = f_YF_ReturnNumber;
    }

    public String getFBillStatus() {
        return FBillStatus;
    }

    public void setFBillStatus(String FBillStatus) {
        this.FBillStatus = FBillStatus;
    }

    public String getF_YF_ReturnRemarks() {
        return F_YF_ReturnRemarks;
    }

    public void setF_YF_ReturnRemarks(String f_YF_ReturnRemarks) {
        F_YF_ReturnRemarks = f_YF_ReturnRemarks;
    }

    public Double getF_YF_LogisticsCost() {
        return F_YF_LogisticsCost;
    }

    public void setF_YF_LogisticsCost(Double f_YF_LogisticsCost) {
        F_YF_LogisticsCost = f_YF_LogisticsCost;
    }

    public String getF_YF_LogisticsJSModeFDATAVALUE() {
        return F_YF_LogisticsJSModeFDATAVALUE;
    }

    public void setF_YF_LogisticsJSModeFDATAVALUE(String f_YF_LogisticsJSModeFDATAVALUE) {
        F_YF_LogisticsJSModeFDATAVALUE = f_YF_LogisticsJSModeFDATAVALUE;
    }

    public String getF_YF_AfterSaleNo() {
        return F_YF_AfterSaleNo;
    }

    public void setF_YF_AfterSaleNo(String f_YF_AfterSaleNo) {
        F_YF_AfterSaleNo = f_YF_AfterSaleNo;
    }

    public String getF_YF_OutStockNo() {
        return F_YF_OutStockNo;
    }

    public void setF_YF_OutStockNo(String f_YF_OutStockNo) {
        F_YF_OutStockNo = f_YF_OutStockNo;
    }

    public String getF_YF_OutOrderStatus() {
        return F_YF_OutOrderStatus;
    }

    public void setF_YF_OutOrderStatus(String f_YF_OutOrderStatus) {
        F_YF_OutOrderStatus = f_YF_OutOrderStatus;
    }

    public String getFSALESMANIDFname() {
        return FSALESMANIDFname;
    }

    public void setFSALESMANIDFname(String FSALESMANIDFname) {
        this.FSALESMANIDFname = FSALESMANIDFname;
    }

    public String getF_YF_Customer() {
        return F_YF_Customer;
    }

    public void setF_YF_Customer(String f_YF_Customer) {
        F_YF_Customer = f_YF_Customer;
    }

    public String getF_YF_DeliveryMethod() {
        return F_YF_DeliveryMethod;
    }

    public void setF_YF_DeliveryMethod(String f_YF_DeliveryMethod) {
        F_YF_DeliveryMethod = f_YF_DeliveryMethod;
    }

    public String getF_YF_LogisticsCompany1() {
        return F_YF_LogisticsCompany1;
    }

    public void setF_YF_LogisticsCompany1(String f_YF_LogisticsCompany1) {
        F_YF_LogisticsCompany1 = f_YF_LogisticsCompany1;
    }

    public String getF_YF_ClientName() {
        return F_YF_ClientName;
    }

    public void setF_YF_ClientName(String f_YF_ClientName) {
        F_YF_ClientName = f_YF_ClientName;
    }

    public String getF_YF_Phone() {
        return F_YF_Phone;
    }

    public void setF_YF_Phone(String f_YF_Phone) {
        F_YF_Phone = f_YF_Phone;
    }

    public String getF_YF_DetailsAddres() {
        return F_YF_DetailsAddres;
    }

    public void setF_YF_DetailsAddres(String f_YF_DetailsAddres) {
        F_YF_DetailsAddres = f_YF_DetailsAddres;
    }

    public String getF_YF_Question() {
        return F_YF_Question;
    }

    public void setF_YF_Question(String f_YF_Question) {
        F_YF_Question = f_YF_Question;
    }

    public String getF_YF_Province() {
        return F_YF_Province;
    }

    public void setF_YF_Province(String f_YF_Province) {
        F_YF_Province = f_YF_Province;
    }

    public String getF_YF_City() {
        return F_YF_City;
    }

    public void setF_YF_City(String f_YF_City) {
        F_YF_City = f_YF_City;
    }

    public String getF_YF_Area() {
        return F_YF_Area;
    }

    public void setF_YF_Area(String f_YF_Area) {
        F_YF_Area = f_YF_Area;
    }

    public String getF_YF_IMAGE1() {
        return F_YF_IMAGE1;
    }

    public void setF_YF_IMAGE1(String f_YF_IMAGE1) {
        F_YF_IMAGE1 = f_YF_IMAGE1;
    }

    public String getF_YF_IMAGE2() {
        return F_YF_IMAGE2;
    }

    public void setF_YF_IMAGE2(String f_YF_IMAGE2) {
        F_YF_IMAGE2 = f_YF_IMAGE2;
    }

    public String getF_YF_IMAGE3() {
        return F_YF_IMAGE3;
    }

    public void setF_YF_IMAGE3(String f_YF_IMAGE3) {
        F_YF_IMAGE3 = f_YF_IMAGE3;
    }

    public String getF_YF_IMAGE4() {
        return F_YF_IMAGE4;
    }

    public void setF_YF_IMAGE4(String f_YF_IMAGE4) {
        F_YF_IMAGE4 = f_YF_IMAGE4;
    }

    public String getF_YF_IMAGE5() {
        return F_YF_IMAGE5;
    }

    public void setF_YF_IMAGE5(String f_YF_IMAGE5) {
        F_YF_IMAGE5 = f_YF_IMAGE5;
    }

    public String getF_YF_IMAGE6() {
        return F_YF_IMAGE6;
    }

    public void setF_YF_IMAGE6(String f_YF_IMAGE6) {
        F_YF_IMAGE6 = f_YF_IMAGE6;
    }

    public String getFEntryID() {
        return FEntryID;
    }

    public void setFEntryID(String FEntryID) {
        this.FEntryID = FEntryID;
    }

    public String getF_YF_MATERIALID() {
        return F_YF_MATERIALID;
    }

    public void setF_YF_MATERIALID(String f_YF_MATERIALID) {
        F_YF_MATERIALID = f_YF_MATERIALID;
    }

    public String getF_YF_MarterialName() {
        return F_YF_MarterialName;
    }

    public void setF_YF_MarterialName(String f_YF_MarterialName) {
        F_YF_MarterialName = f_YF_MarterialName;
    }

    public String getF_YF_SpecMode() {
        return F_YF_SpecMode;
    }

    public void setF_YF_SpecMode(String f_YF_SpecMode) {
        F_YF_SpecMode = f_YF_SpecMode;
    }

    public String getF_YF_ISGIFTS() {
        return F_YF_ISGIFTS;
    }

    public void setF_YF_ISGIFTS(String f_YF_ISGIFTS) {
        F_YF_ISGIFTS = f_YF_ISGIFTS;
    }

    public String getF_YF_ISFITTING() {
        return F_YF_ISFITTING;
    }

    public void setF_YF_ISFITTING(String f_YF_ISFITTING) {
        F_YF_ISFITTING = f_YF_ISFITTING;
    }

    public String getF_YF_FITTINGDESCIPTION() {
        return F_YF_FITTINGDESCIPTION;
    }

    public void setF_YF_FITTINGDESCIPTION(String f_YF_FITTINGDESCIPTION) {
        F_YF_FITTINGDESCIPTION = f_YF_FITTINGDESCIPTION;
    }

    public String getF_YF_REALQTY() {
        return F_YF_REALQTY;
    }

    public void setF_YF_REALQTY(String f_YF_REALQTY) {
        F_YF_REALQTY = f_YF_REALQTY;
    }

    public String getF_YF_RETURNQTY() {
        return F_YF_RETURNQTY;
    }

    public void setF_YF_RETURNQTY(String f_YF_RETURNQTY) {
        F_YF_RETURNQTY = f_YF_RETURNQTY;
    }

    public String getF_YF_PackageNumber() {
        return F_YF_PackageNumber;
    }

    public void setF_YF_PackageNumber(String f_YF_PackageNumber) {
        F_YF_PackageNumber = f_YF_PackageNumber;
    }

    public String getF_YF_LOGISTICSVOLUMETOTAL() {
        return F_YF_LOGISTICSVOLUMETOTAL;
    }

    public void setF_YF_LOGISTICSVOLUMETOTAL(String f_YF_LOGISTICSVOLUMETOTAL) {
        F_YF_LOGISTICSVOLUMETOTAL = f_YF_LOGISTICSVOLUMETOTAL;
    }

    public String getF_YF_REMARK() {
        return F_YF_REMARK;
    }

    public void setF_YF_REMARK(String f_YF_REMARK) {
        F_YF_REMARK = f_YF_REMARK;
    }

    public String getF_YF_TransferGoodsEntityID() {
        return F_YF_TransferGoodsEntityID;
    }

    public void setF_YF_TransferGoodsEntityID(String f_YF_TransferGoodsEntityID) {
        F_YF_TransferGoodsEntityID = f_YF_TransferGoodsEntityID;
    }

    public String getF_YF_MaterialsId() {
        return F_YF_MaterialsId;
    }

    public void setF_YF_MaterialsId(String f_YF_MaterialsId) {
        F_YF_MaterialsId = f_YF_MaterialsId;
    }

    public String getF_YF_MaterialName() {
        return F_YF_MaterialName;
    }

    public void setF_YF_MaterialName(String f_YF_MaterialName) {
        F_YF_MaterialName = f_YF_MaterialName;
    }

    public String getF_YF_TRANSFERGOODSQTY() {
        return F_YF_TRANSFERGOODSQTY;
    }

    public void setF_YF_TRANSFERGOODSQTY(String f_YF_TRANSFERGOODSQTY) {
        F_YF_TRANSFERGOODSQTY = f_YF_TRANSFERGOODSQTY;
    }

    public String getF_YF_SALESORDER() {
        return F_YF_SALESORDER;
    }

    public void setF_YF_SALESORDER(String f_YF_SALESORDER) {
        F_YF_SALESORDER = f_YF_SALESORDER;
    }

    public String getF_YF_CLIENTIDFNAME() {
        return F_YF_CLIENTIDFNAME;
    }

    public void setF_YF_CLIENTIDFNAME(String f_YF_CLIENTIDFNAME) {
        F_YF_CLIENTIDFNAME = f_YF_CLIENTIDFNAME;
    }

    public String getF_YF_SELLERIDFNAME() {
        return F_YF_SELLERIDFNAME;
    }

    public void setF_YF_SELLERIDFNAME(String f_YF_SELLERIDFNAME) {
        F_YF_SELLERIDFNAME = f_YF_SELLERIDFNAME;
    }

    public String getF_YF_REMARKS() {
        return F_YF_REMARKS;
    }

    public void setF_YF_REMARKS(String f_YF_REMARKS) {
        F_YF_REMARKS = f_YF_REMARKS;
    }

    public String getF_YF_RECEIVEADDRESS() {
        return F_YF_RECEIVEADDRESS;
    }

    public void setF_YF_RECEIVEADDRESS(String f_YF_RECEIVEADDRESS) {
        F_YF_RECEIVEADDRESS = f_YF_RECEIVEADDRESS;
    }

    public String getF_YF_ClientsName() {
        return F_YF_ClientsName;
    }

    public void setF_YF_ClientsName(String f_YF_ClientsName) {
        F_YF_ClientsName = f_YF_ClientsName;
    }

    public String getF_YF_ClientPhone() {
        return F_YF_ClientPhone;
    }

    public void setF_YF_ClientPhone(String f_YF_ClientPhone) {
        F_YF_ClientPhone = f_YF_ClientPhone;
    }

    public String getF_YF_DetailAddress() {
        return F_YF_DetailAddress;
    }

    public void setF_YF_DetailAddress(String f_YF_DetailAddress) {
        F_YF_DetailAddress = f_YF_DetailAddress;
    }

    public Date getF_YF_ADUITDATETIME() {
        return F_YF_ADUITDATETIME;
    }

    public void setF_YF_ADUITDATETIME(Date f_YF_ADUITDATETIME) {
        F_YF_ADUITDATETIME = f_YF_ADUITDATETIME;
    }
}
