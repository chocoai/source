package com.jeesite.modules.asset.scheduledtask;

import com.alibaba.fastjson.JSONArray;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.modules.asset.k3webapi.InvokeHelper;
import com.jeesite.modules.asset.k3webapi.TrackData;
import com.jeesite.modules.asset.track.entity.AmTrack;
import com.jeesite.modules.asset.track.entity.AmTrackDetail;
import com.jeesite.modules.asset.track.entity.AmTrackTransfer;
import com.jeesite.modules.asset.track.service.AmTrackService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@Controller
//@RequestMapping("track")
//@Configuration
//@EnableScheduling
@Component
public class K3Track {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(K3Track.class);

    @Autowired
    private AmTrackService amTrackService;

    private Date auditTime = null;
    private String auditDate = null;

    private static K3Track k3Track;
    @PostConstruct
    public void init() {
        k3Track = this;
    }
//    @RequestMapping("info")
//    @Async
//    @Scheduled(cron = "0 0/5 * * * ?") // 每5分钟同步一次
//    @Scheduled(fixedRate = 5000 * 60)
    public static void getInfo() throws Exception {
        if (InvokeHelper.Login(K3Config.DBID, K3Config.UID, K3Config.PWD, K3Config.LANG, K3Config.K3ClOUDRL)) {
            int total = 0;
            // 销售订单保存测试
            // 退货跟踪单
            String sFormId = "YF_SC_ReturnApplyTrack";
            if (k3Track.auditTime == null) {
                List<AmTrack> list = k3Track.amTrackService.findList(new AmTrack());
                if (list != null && list.size() > 0) {
                    k3Track.auditTime = list.get(0).getAuditTime();
                }
                if (k3Track.auditTime == null) {
                    k3Track.auditTime = DateUtils.parseDate("2018-07-17 17:30:00");
                }
                k3Track.auditDate = DateUtils.formatDateTime(k3Track.auditTime);
            }
            //需要保存的数据
            // 如下字段可能需要根据自己实际值做修改
            // 物流纠纷单
            String s5 = "{\"FormId\":\"YF_SC_ReturnApplyTrack\",\"FieldKeys\":\"" +
                    "FBillNo,FDocumentStatus,F_YF_ReturnPoint.FDATAVALUE,F_YF_SBMaster.FName,F_YF_MasterTel,F_YF_LogisticsCompany.FName,F_YF_LogisticsNo," +
                    "F_YF_StockId.FName,F_YF_ReturnAddress,F_YF_RefundType.FDATAVALUE,F_YF_ShopName.FName,F_YF_PlatForm,F_YF_ReturnGoodsRemarks,F_YF_RefundResponsible," +
                    "F_YF_EstimateDate,F_YF_ReturnNumber,FBillStatus,F_YF_ReturnRemarks,F_YF_LogisticsCost,F_YF_LogisticsJSMode.FDATAVALUE,F_YF_AfterSaleNo,F_YF_OutStockNo," +
                    "F_YF_OutOrderStatus,FSALESMANID.FName,F_YF_Customer.FName,F_YF_DeliveryMethod,F_YF_LogisticsCompany1,F_YF_ClientName,F_YF_Phone,F_YF_DetailsAddres," +
                    "F_YF_Question,F_YF_Province,F_YF_City,F_YF_Area,F_YF_IMAGE1,F_YF_IMAGE2,F_YF_IMAGE3,F_YF_IMAGE4,F_YF_IMAGE5,F_YF_IMAGE6,FEntity_FEntryID,F_YF_MATERIALID," +
                    "F_YF_MarterialName,F_YF_SpecMode,F_YF_ISGIFTS,F_YF_ISFITTING,F_YF_FITTINGDESCIPTION,F_YF_REALQTY,F_YF_RETURNQTY,F_YF_PackageNumber,F_YF_LOGISTICSVOLUMETOTAL," +
                    "F_YF_REMARK,F_YF_TransferGoodsEntity_FEntryID,F_YF_MaterialsId,F_YF_MaterialName,F_YF_TRANSFERGOODSQTY,F_YF_SALESORDER,F_YF_CLIENTID.FNAME,F_YF_SELLERID.FNAME," +
                    "F_YF_REMARKS,F_YF_RECEIVEADDRESS.F_YF_DetailedAddress,F_YF_ClientsName,F_YF_ClientPhone,F_YF_DetailAddress,F_YF_ADUITDATETIME,FID\",\"FilterString\":\"F_YF_ADUITDATETIME>'"+k3Track.auditDate+"' AND FDocumentStatus='C' AND F_YF_TrackStatus='A'\",\"OrderString\":\"F_YF_ADUITDATETIME DESC\"," +
                    "\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"100\"}";
            String a = InvokeHelper.ExecuteBillQuery(sFormId, s5, K3Config.K3ClOUDRL);
            String b = new String(a.getBytes());
            String[] c = b.split(",");
            if (c.length == 1) {
                return;
            }
            JSONArray jsonList = (JSONArray) JSONArray.parse(String.valueOf(b));
            List<TrackData> trackDataList = new ArrayList<>();
            for (int i = 0; i < jsonList.size(); i++) {
                TrackData trackData = new TrackData();
                prossed(trackData, (List<Object>) jsonList.get(i), trackDataList);
            }
         //   String index = trackDataList.get((trackDataList.size() - 1)).getFID();
            k3Track.auditDate = DateUtils.formatDateTime(trackDataList.get(0).getF_YF_ADUITDATETIME());
            if (trackDataList != null && trackDataList.size() > 0) {
                for (TrackData trackData : trackDataList) {
                    AmTrack amTrack = new AmTrack();
                    amTrack.setDocumentCode(trackData.getFBillNo());
                    AmTrack amTrack1 = k3Track.amTrackService.get(amTrack);
                    List<AmTrackDetail> isHaveDetail = k3Track.amTrackService.getDetail(trackData.getFEntryID(), trackData.getF_YF_MATERIALID());
                    List<AmTrackTransfer> isTransfer = k3Track.amTrackService.getTransfer(trackData.getF_YF_TransferGoodsEntityID(), trackData.getF_YF_MaterialsId());
                    if (amTrack1 != null && isHaveDetail != null && isHaveDetail.size() > 0 && isTransfer != null && isTransfer.size() > 0) {
                        continue;
                    }
                    if (amTrack1 == null) {
                        amTrack.setIsNewRecord(true);
                        amTrack.setFid(trackData.getFID());
                        amTrack.setDocumentCode(trackData.getFBillNo());    // 单据编码
                        amTrack.setDocumentStatus("创建");        // 单据状态
                        amTrack.setRetreat(trackData.getF_YF_ReturnPointFDATAVALUE());    // 退回点
                        amTrack.setThreeWorker(trackData.getF_YF_SBMasterFName());      // 三包师傅
                        amTrack.setWorkerPhone(trackData.getF_YF_MasterTel());          // 师傅电话
                        amTrack.setLogisticsCompany(trackData.getF_YF_LogisticsCompany());  // 物流公司
                        amTrack.setLogisticsCode(trackData.getF_YF_LogisticsNo());          // 物流单号
                        amTrack.setWarehouse(trackData.getF_YF_StockIdFName());             // 仓库
                        amTrack.setReturnAddress(trackData.getF_YF_ReturnAddress());        // 退回地址
                        amTrack.setDeliveryType(trackData.getF_YF_RefundTypeFDATAVALUE());  // 退回类型
                        amTrack.setStores(trackData.getF_YF_ShopNameFName());               // 店铺
                        amTrack.setPlatform(trackData.getF_YF_PlatForm());                  // 平台
                        amTrack.setReturnPolicy(trackData.getF_YF_ReturnGoodsRemarks());    // 退货说明
                        amTrack.setResponsibility(trackData.getF_YF_RefundResponsible());   // 退货责任方
                        amTrack.setEstimateDate(trackData.getF_YF_EstimateDate());          // 预计到货时间
                        amTrack.setReturnLogisticsCode(trackData.getF_YF_ReturnNumber());   // 退货物流单号
                        if ("A".equals(trackData.getFBillStatus())) {
                            amTrack.setReturnMethod("送货上门");                // 退货入仓方式
                        } else if ("B".equals(trackData.getFBillStatus())) {
                            amTrack.setReturnMethod("仓库自提");
                        } else if ("C".equals(trackData.getFBillStatus())) {
                            amTrack.setReturnMethod("货物已到仓");
                        }
                        amTrack.setReturnRemarks(trackData.getF_YF_ReturnRemarks());        // 退货备注
                        amTrack.setLogisticsCost(trackData.getF_YF_LogisticsCost());        // 物流费用
                        amTrack.setSettlementMode(trackData.getF_YF_LogisticsJSModeFDATAVALUE());   // 物流结算方式
                        amTrack.setCreateDate(new Date());                                  // 创建时间

                        amTrack.setAfterSaleCode(trackData.getF_YF_AfterSaleNo());          // 售后单号
                        amTrack.setOutstorageCode(trackData.getF_YF_OutStockNo());          // 出库单号
                        if ("O".equals(trackData.getF_YF_OutOrderStatus())) {
                            amTrack.setOrderStatus("出库");                                   // 订单状态
                        } else if ("S".equals(trackData.getF_YF_OutOrderStatus())) {
                            amTrack.setOrderStatus("发货");
                        } else if ("R".equals(trackData.getF_YF_OutOrderStatus())) {
                            amTrack.setOrderStatus("回访");
                        }
                        amTrack.setSeller(trackData.getFSALESMANIDFname());                 // 销售员
                        amTrack.setClient(trackData.getF_YF_Customer());                    // 客户
                        amTrack.setDeliveryMethod(trackData.getF_YF_DeliveryMethod());      // 配送方式
                        amTrack.setLogisticsCompany1(trackData.getF_YF_LogisticsCompany1());// 物流公司
                        amTrack.setClientName(trackData.getF_YF_ClientName());              // 客户名称
                        amTrack.setMobilePhone(trackData.getF_YF_Phone());                  // 移动电话
                        amTrack.setAddress(trackData.getF_YF_DetailsAddres());              // 详细地址
                        amTrack.setProblemDescription(trackData.getF_YF_Question());        // 问题描述
                        amTrack.setProvince(trackData.getF_YF_Province());                  // 省份
                        amTrack.setCity(trackData.getF_YF_City());                          // 城市
                        amTrack.setRegion(trackData.getF_YF_Area());                        // 区县
                        amTrack.setFileid1(trackData.getF_YF_IMAGE1().trim());              // 图片1
                        amTrack.setFileid2(trackData.getF_YF_IMAGE2().trim());              // 图片2
                        amTrack.setFileid3(trackData.getF_YF_IMAGE3().trim());              // 图片3
                        amTrack.setFileid4(trackData.getF_YF_IMAGE4().trim());              // 图片4
                        amTrack.setFileid5(trackData.getF_YF_IMAGE5().trim());              // 图片5
                        amTrack.setFileid6(trackData.getF_YF_IMAGE6().trim());              // 图片6
                        amTrack.setAuditTime(trackData.getF_YF_ADUITDATETIME());  // 审核时间
                        k3Track.amTrackService.saveData(amTrack);
                    }
                    if (isHaveDetail == null || isHaveDetail.size() <= 0) {
                        AmTrackDetail amTrackDetail = new AmTrackDetail();
                        amTrackDetail.setIsNewRecord(true);
                        amTrackDetail.setEntryId(trackData.getFEntryID());
                        amTrackDetail.setDocumentCode(amTrack);                         // 单据编码
                        amTrackDetail.setConsumablesCode(trackData.getF_YF_MATERIALID());// 物料编码
                        amTrackDetail.setConsumablesName(trackData.getF_YF_MarterialName());    // 物料名称
                        amTrackDetail.setSpecifications(trackData.getF_YF_SpecMode());  // 规格型号
                        if ("true".equals(trackData.getF_YF_ISGIFTS())) {
                            amTrackDetail.setIsGifts("是");                              // 是否赠品
                        } else if ("false".equals(trackData.getF_YF_ISGIFTS())) {
                            amTrackDetail.setIsGifts("否");
                        }
                        if ("true".equals(trackData.getF_YF_ISFITTING())) {
                            amTrackDetail.setIsParts("是");                              // 是否配件
                        } else if ("false".equals(trackData.getF_YF_ISFITTING())) {
                            amTrackDetail.setIsParts("否");
                        }
                        amTrackDetail.setPartsExplain(trackData.getF_YF_FITTINGDESCIPTION());   // 配件说明
                        amTrackDetail.setActualNumber(trackData.getF_YF_REALQTY());             // 实发数量
                        amTrackDetail.setRetreatNumber(trackData.getF_YF_RETURNQTY());          // 实退数量
                        amTrackDetail.setPackageNumber(trackData.getF_YF_PackageNumber());      // 包件数
                        amTrackDetail.setVolume(trackData.getF_YF_LOGISTICSVOLUMETOTAL());      // 体积
                        amTrackDetail.setRemarks(trackData.getF_YF_REMARK());                   // 备注
                        k3Track.amTrackService.saveDetail(amTrackDetail);
                    }
                    if (isTransfer == null || isTransfer.size() <= 0) {
                        if ("0".equals(trackData.getF_YF_TransferGoodsEntityID())) {
                            continue;
                        }
                        AmTrackTransfer amTrackTransfer = new AmTrackTransfer();
                        amTrackTransfer.setIsNewRecord(true);
                        amTrackTransfer.setEntityId(trackData.getF_YF_TransferGoodsEntityID());
                        amTrackTransfer.setDocumentCode(amTrack);                               // 单据编号
                        amTrackTransfer.setConsumablesCode(trackData.getF_YF_MaterialsId());    // 物料编码
                        amTrackTransfer.setConsumablesName(trackData.getF_YF_MaterialName());   // 物料名称
                        amTrackTransfer.setTransferNumber(trackData.getF_YF_TRANSFERGOODSQTY());// 调货数量
                        amTrackTransfer.setSalesOrder(trackData.getF_YF_SALESORDER());          // 销售订单
                        amTrackTransfer.setClient(trackData.getF_YF_CLIENTIDFNAME());           // 客户
                        amTrackTransfer.setSeller(trackData.getF_YF_SELLERIDFNAME());           // 销售员
                        amTrackTransfer.setRemarks(trackData.getF_YF_REMARKS());                // 备注
                        amTrackTransfer.setAddress(trackData.getF_YF_RECEIVEADDRESS());         // 地址
                        amTrackTransfer.setClientName(trackData.getF_YF_ClientsName());         // 客户姓名
                        amTrackTransfer.setMobilePhone(trackData.getF_YF_ClientPhone());        // 移动电话
                        amTrackTransfer.setDetailAddress(trackData.getF_YF_DetailAddress());    // 详细地址
                        k3Track.amTrackService.saveTransfer(amTrackTransfer);

                    }
                    total ++;
                }
                logger.info("同步退货追踪单==================================================================================================================================");
                logger.info("退货追踪单同步条数" + total);
                logger.info("退货追踪单同步时间" + DateUtils.getDateTime());
            }
        }
    }

    private static void prossed(TrackData trackData, List<Object> objects, List<TrackData> rst) throws ParseException {
        if (objects == null) {
            return;
        }
        trackData.setFID(objects.get(65).toString());
        trackData.setFBillNo(objects.get(0).toString());
        trackData.setFDocumentStatus(objects.get(1).toString());
        trackData.setF_YF_ReturnPointFDATAVALUE(objects.get(2).toString());
        trackData.setF_YF_SBMasterFName(objects.get(3).toString());
        trackData.setF_YF_MasterTel(objects.get(4).toString());
        trackData.setF_YF_LogisticsCompany(objects.get(5).toString());
        trackData.setF_YF_LogisticsNo(objects.get(6).toString());
        trackData.setF_YF_StockIdFName(objects.get(7).toString());
        trackData.setF_YF_ReturnAddress(objects.get(8).toString());
        trackData.setF_YF_RefundTypeFDATAVALUE(objects.get(9).toString());
        trackData.setF_YF_ShopNameFName(objects.get(10).toString());
        trackData.setF_YF_PlatForm(objects.get(11).toString());
        trackData.setF_YF_ReturnGoodsRemarks(objects.get(12).toString());
        trackData.setF_YF_RefundResponsible(objects.get(13).toString());
        trackData.setF_YF_EstimateDate(getTime(objects, 14));
        trackData.setF_YF_ReturnNumber(objects.get(15).toString());
        trackData.setFBillStatus(objects.get(16).toString());
        trackData.setF_YF_ReturnRemarks(objects.get(17).toString());
        trackData.setF_YF_LogisticsCost(getNum(objects,18));
        trackData.setF_YF_LogisticsJSModeFDATAVALUE(objects.get(19).toString());
        // 售后信息
        trackData.setF_YF_AfterSaleNo(objects.get(20).toString());
        trackData.setF_YF_OutStockNo(objects.get(21).toString());
        trackData.setF_YF_OutOrderStatus(objects.get(22).toString());
        trackData.setFSALESMANIDFname(objects.get(23).toString());
        trackData.setF_YF_Customer(objects.get(24).toString());
        trackData.setF_YF_DeliveryMethod(objects.get(25).toString());
        trackData.setF_YF_LogisticsCompany1(objects.get(26).toString());
        trackData.setF_YF_ClientName(objects.get(27).toString());
        trackData.setF_YF_Phone(objects.get(28).toString());
        trackData.setF_YF_DetailsAddres(objects.get(29).toString());
        trackData.setF_YF_Question(objects.get(30).toString());
        trackData.setF_YF_Province(objects.get(31).toString());
        trackData.setF_YF_City(objects.get(32).toString());
        trackData.setF_YF_Area(objects.get(33).toString());
        trackData.setF_YF_IMAGE1(objects.get(34).toString().trim());
        trackData.setF_YF_IMAGE2(objects.get(35).toString().trim());
        trackData.setF_YF_IMAGE3(objects.get(36).toString().trim());
        trackData.setF_YF_IMAGE4(objects.get(37).toString().trim());
        trackData.setF_YF_IMAGE5(objects.get(38).toString().trim());
        trackData.setF_YF_IMAGE6(objects.get(39).toString().trim());
        String userToken = InvokeHelper.UserToken;
        for (int i = 34; i <= 39; i++) {             // 图片取得
            String img = objects.get(i).toString().trim();
            if (img != null && !"".equals(img)) {
//                String url = "http://uvan.ik3cloud.com/K3cloud/FileUpLoadServices/download.aspx?fileId=" + img + "&dbId=20161121155638&t=636601727451676767&token=" + userToken + "";
                String url = K3Config.K3ClOUDRL + "FileUpLoadServices/download.aspx?fileId=" + img + "&dbId="+ K3Config.DBID +"&token=" + userToken + "";
                try {
                    String filePath = K3Config.BASEDIR + "/track/" + img +".jpg";
                    K3Scheduled.saveToImgByStr(filePath, url);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        trackData.setFEntryID(objects.get(40).toString());
        trackData.setF_YF_MATERIALID(objects.get(41).toString());
        trackData.setF_YF_MarterialName(objects.get(42).toString());
        trackData.setF_YF_SpecMode(objects.get(43).toString());
        trackData.setF_YF_ISGIFTS(objects.get(44).toString());
        trackData.setF_YF_ISFITTING(objects.get(45).toString());
        trackData.setF_YF_FITTINGDESCIPTION(objects.get(46).toString());
        trackData.setF_YF_REALQTY(objects.get(47).toString());
        trackData.setF_YF_RETURNQTY(objects.get(48).toString());
        trackData.setF_YF_PackageNumber(objects.get(49).toString());
        trackData.setF_YF_LOGISTICSVOLUMETOTAL(objects.get(50).toString());
        trackData.setF_YF_REMARK(objects.get(51).toString());
        // 调货信息
        trackData.setF_YF_TransferGoodsEntityID(objects.get(52).toString());
        trackData.setF_YF_MaterialsId(objects.get(53).toString());
        trackData.setF_YF_MaterialName(objects.get(54).toString());
        trackData.setF_YF_TRANSFERGOODSQTY(objects.get(55).toString());
        trackData.setF_YF_SALESORDER(objects.get(56).toString());
        trackData.setF_YF_CLIENTIDFNAME(objects.get(57).toString());
        trackData.setF_YF_SELLERIDFNAME(objects.get(58).toString());
        trackData.setF_YF_REMARKS(objects.get(59).toString());
        trackData.setF_YF_RECEIVEADDRESS(objects.get(60).toString());
        trackData.setF_YF_ClientsName(objects.get(61).toString());
        trackData.setF_YF_ClientPhone(objects.get(62).toString());
        trackData.setF_YF_DetailAddress(objects.get(63).toString());
        trackData.setF_YF_ADUITDATETIME(getTime(objects,64));
        rst.add(trackData);
    }


    public static Date getTime(List<Object> objects, int index) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String oncardateTime = null;
        Date oncardateTime_Date = null;
        try {
            oncardateTime = objects.get(index).toString();
            oncardateTime = oncardateTime.replace("T", " ");
            if (oncardateTime != null && !"".equals(oncardateTime)) {
                oncardateTime_Date = format.parse(oncardateTime);
            }
        } catch (Exception e) {

        }
        return oncardateTime_Date;
    }

    //    public String getStr (List<Object> objects, int index) {
//        String str = null;
//        try {
//            str = objects.get(index).toString();
//        } catch (Exception e) {
//
//        }
//        return str;
//    }
    public static Double getNum(List<Object> objects, int index) {
        Double aDouble = null;
        try {
            aDouble = (double) objects.get(index);
        } catch (Exception e) {

        }
        return aDouble;
    }
}