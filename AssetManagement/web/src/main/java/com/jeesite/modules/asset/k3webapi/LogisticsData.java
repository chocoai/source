package com.jeesite.modules.asset.k3webapi;

import java.util.Date;

public class LogisticsData {
    private String FBILLNO;                //单据编号
    private String FDOCUMENTSTATUS;                       //单据状态
    private String F_YF_SHOPNAMEFName;           //店铺
     private String F_YF_OUTSTOCKNO;                     //销售出库单号
    private String   FCARRIAGENO;             //运输单号
    private String   F_YF_THREESF;           //三包师傅
    private String F_YF_THREETEL;       //三包电话
    private String  F_YF_TYPE;      //类型
    private String FCUSTOMERIDFName;           //客户
    private String F_YF_Recipient;        //收件人
    private String F_YF_Phone;             //移动电话
    private String FSalesManIDFName;    //销售员
     private String F_YF_Province;                     //省份
    private String F_YF_City;        //城市
    private String F_YF_Area;                    //区域
    private String F_YF_LogisticsCompany;           //物流公司
    private String F_YF_DeliveryMethod;          //配送方式
    private String F_YF_DetailsAddress;                //详细地址
    private Date F_YF_ONCARDATETIME;             //装车时间
    private String F_YF_PROBLEMBEWRITE;                    //问题描述
    private String F_YF_PROCESSRESULT;                   //处理结果
    private String F_YF_HANDLEUSERIDFNAME;  //处理人名字
    private Date   FCREATEDATE;           //创建时间
    private String FMODIFIERIDFNAME;         //修改人名字
    private Date FMODIFYDATE;       //最近修改时间
    private String F_YF_DETERMINEUSERIDFNAME;      //完成人名字
    private Date  F_YF_DETERMINEDATETIME;       //完成时间
    private String F_YF_MATERIALID;             //物料编码
    private String  F_YF_MATERIALSNAME;                      //物料名称
     private String F_YF_SPECIFICATION;           //规格型号
    private  String  FREALQTY;                 //实发数量
    private  String F_YF_PACKAGEQTY;          //包件数
    private  String FISFREE;             //是否赠品
    private   String      F_YF_SHOPFNAME;    //店铺名字
    private  String FSOORDERNO;     //销售订单号   34
    private  String FEntity_FEntryID; //明细表uuid
    private String F_ID;//单据uuid
    private  String F_YF_DeliveryLogistic;  //发货物流

    public String getF_YF_DeliveryLogistic() {
        return F_YF_DeliveryLogistic;
    }

    public void setF_YF_DeliveryLogistic(String f_YF_DeliveryLogistic) {
        F_YF_DeliveryLogistic = f_YF_DeliveryLogistic;
    }

    public String getF_ID() {
        return F_ID;
    }

    public void setF_ID(String f_ID) {
        F_ID = f_ID;
    }

    public String getFEntity_FEntryID() {
        return FEntity_FEntryID;
    }

    public void setFEntity_FEntryID(String FEntity_FEntryID) {
        this.FEntity_FEntryID = FEntity_FEntryID;
    }

    public String getFBILLNO() {
        return FBILLNO;
    }

    public void setFBILLNO(String FBILLNO) {
        this.FBILLNO = FBILLNO;
    }

    public String getFISFREE() {
        return FISFREE;
    }

    public void setFISFREE(String FISFREE) {
        this.FISFREE = FISFREE;
    }

    public String getFDOCUMENTSTATUS() {
        return FDOCUMENTSTATUS;
    }

    public void setFDOCUMENTSTATUS(String FDOCUMENTSTATUS) {
        this.FDOCUMENTSTATUS = FDOCUMENTSTATUS;
    }

    public String getF_YF_SHOPNAMEFName() {
        return F_YF_SHOPNAMEFName;
    }

    public void setF_YF_SHOPNAMEFName(String f_YF_SHOPNAMEFName) {
        F_YF_SHOPNAMEFName = f_YF_SHOPNAMEFName;
    }

    public String getF_YF_OUTSTOCKNO() {
        return F_YF_OUTSTOCKNO;
    }

    public void setF_YF_OUTSTOCKNO(String f_YF_OUTSTOCKNO) {
        F_YF_OUTSTOCKNO = f_YF_OUTSTOCKNO;
    }

    public String getFCARRIAGENO() {
        return FCARRIAGENO;
    }

    public void setFCARRIAGENO(String FCARRIAGENO) {
        this.FCARRIAGENO = FCARRIAGENO;
    }

    public String getF_YF_THREESF() {
        return F_YF_THREESF;
    }

    public void setF_YF_THREESF(String f_YF_THREESF) {
        F_YF_THREESF = f_YF_THREESF;
    }

    public String getF_YF_THREETEL() {
        return F_YF_THREETEL;
    }

    public void setF_YF_THREETEL(String f_YF_THREETEL) {
        F_YF_THREETEL = f_YF_THREETEL;
    }

    public String getF_YF_TYPE() {
        return F_YF_TYPE;
    }

    public void setF_YF_TYPE(String f_YF_TYPE) {
        F_YF_TYPE = f_YF_TYPE;
    }

    public String getFCUSTOMERIDFName() {
        return FCUSTOMERIDFName;
    }

    public void setFCUSTOMERIDFName(String FCUSTOMERIDFName) {
        this.FCUSTOMERIDFName = FCUSTOMERIDFName;
    }

    public String getF_YF_Recipient() {
        return F_YF_Recipient;
    }

    public void setF_YF_Recipient(String f_YF_Recipient) {
        F_YF_Recipient = f_YF_Recipient;
    }

    public String getF_YF_Phone() {
        return F_YF_Phone;
    }

    public void setF_YF_Phone(String f_YF_Phone) {
        F_YF_Phone = f_YF_Phone;
    }

    public String getFSalesManIDFName() {
        return FSalesManIDFName;
    }

    public void setFSalesManIDFName(String FSalesManIDFName) {
        this.FSalesManIDFName = FSalesManIDFName;
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

    public String getF_YF_LogisticsCompany() {
        return F_YF_LogisticsCompany;
    }

    public void setF_YF_LogisticsCompany(String f_YF_LogisticsCompany) {
        F_YF_LogisticsCompany = f_YF_LogisticsCompany;
    }

    public String getF_YF_DeliveryMethod() {
        return F_YF_DeliveryMethod;
    }

    public void setF_YF_DeliveryMethod(String f_YF_DeliveryMethod) {
        F_YF_DeliveryMethod = f_YF_DeliveryMethod;
    }

    public String getF_YF_DetailsAddress() {
        return F_YF_DetailsAddress;
    }

    public void setF_YF_DetailsAddress(String f_YF_DetailsAddress) {
        F_YF_DetailsAddress = f_YF_DetailsAddress;
    }

    public Date getF_YF_ONCARDATETIME() {
        return F_YF_ONCARDATETIME;
    }

    public void setF_YF_ONCARDATETIME(Date f_YF_ONCARDATETIME) {
        F_YF_ONCARDATETIME = f_YF_ONCARDATETIME;
    }

    public String getF_YF_PROBLEMBEWRITE() {
        return F_YF_PROBLEMBEWRITE;
    }

    public void setF_YF_PROBLEMBEWRITE(String f_YF_PROBLEMBEWRITE) {
        F_YF_PROBLEMBEWRITE = f_YF_PROBLEMBEWRITE;
    }

    public String getF_YF_PROCESSRESULT() {
        return F_YF_PROCESSRESULT;
    }

    public void setF_YF_PROCESSRESULT(String f_YF_PROCESSRESULT) {
        F_YF_PROCESSRESULT = f_YF_PROCESSRESULT;
    }

    public String getF_YF_HANDLEUSERIDFNAME() {
        return F_YF_HANDLEUSERIDFNAME;
    }

    public void setF_YF_HANDLEUSERIDFNAME(String f_YF_HANDLEUSERIDFNAME) {
        F_YF_HANDLEUSERIDFNAME = f_YF_HANDLEUSERIDFNAME;
    }

    public Date getFCREATEDATE() {
        return FCREATEDATE;
    }

    public void setFCREATEDATE(Date FCREATEDATE) {
        this.FCREATEDATE = FCREATEDATE;
    }

    public String getFMODIFIERIDFNAME() {
        return FMODIFIERIDFNAME;
    }

    public void setFMODIFIERIDFNAME(String FMODIFIERIDFNAME) {
        this.FMODIFIERIDFNAME = FMODIFIERIDFNAME;
    }

    public Date getFMODIFYDATE() {
        return FMODIFYDATE;
    }

    public void setFMODIFYDATE(Date FMODIFYDATE) {
        this.FMODIFYDATE = FMODIFYDATE;
    }

    public String getF_YF_DETERMINEUSERIDFNAME() {
        return F_YF_DETERMINEUSERIDFNAME;
    }

    public void setF_YF_DETERMINEUSERIDFNAME(String f_YF_DETERMINEUSERIDFNAME) {
        F_YF_DETERMINEUSERIDFNAME = f_YF_DETERMINEUSERIDFNAME;
    }

    public Date getF_YF_DETERMINEDATETIME() {
        return F_YF_DETERMINEDATETIME;
    }

    public void setF_YF_DETERMINEDATETIME(Date f_YF_DETERMINEDATETIME) {
        F_YF_DETERMINEDATETIME = f_YF_DETERMINEDATETIME;
    }

    public String getF_YF_MATERIALID() {
        return F_YF_MATERIALID;
    }

    public void setF_YF_MATERIALID(String f_YF_MATERIALID) {
        F_YF_MATERIALID = f_YF_MATERIALID;
    }

    public String getF_YF_MATERIALSNAME() {
        return F_YF_MATERIALSNAME;
    }

    public void setF_YF_MATERIALSNAME(String f_YF_MATERIALSNAME) {
        F_YF_MATERIALSNAME = f_YF_MATERIALSNAME;
    }

    public String getF_YF_SPECIFICATION() {
        return F_YF_SPECIFICATION;
    }

    public void setF_YF_SPECIFICATION(String f_YF_SPECIFICATION) {
        F_YF_SPECIFICATION = f_YF_SPECIFICATION;
    }

    public String getFREALQTY() {
        return FREALQTY;
    }

    public void setFREALQTY(String FREALQTY) {
        this.FREALQTY = FREALQTY;
    }

    public String getF_YF_PACKAGEQTY() {
        return F_YF_PACKAGEQTY;
    }

    public void setF_YF_PACKAGEQTY(String f_YF_PACKAGEQTY) {
        F_YF_PACKAGEQTY = f_YF_PACKAGEQTY;
    }

    public String getF_YF_SHOPFNAME() {
        return F_YF_SHOPFNAME;
    }

    public void setF_YF_SHOPFNAME(String f_YF_SHOPFNAME) {
        F_YF_SHOPFNAME = f_YF_SHOPFNAME;
    }

    public String getFSOORDERNO() {
        return FSOORDERNO;
    }

    public void setFSOORDERNO(String FSOORDERNO) {
        this.FSOORDERNO = FSOORDERNO;
    }
}
