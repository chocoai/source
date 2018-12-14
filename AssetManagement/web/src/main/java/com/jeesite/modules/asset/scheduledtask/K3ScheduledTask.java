package com.jeesite.modules.asset.scheduledtask;

import com.alibaba.fastjson.JSONArray;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.modules.asset.amlogistics.entity.AmLogistics;
import com.jeesite.modules.asset.amlogistics.entity.AmLogisticsDetail;
import com.jeesite.modules.asset.amlogistics.service.AmLogisticsService;
import com.jeesite.modules.asset.k3webapi.InvokeHelper;
import com.jeesite.modules.asset.k3webapi.LogisticsData;
import com.jeesite.modules.asset.util.TimeUtils;
import com.jeesite.modules.sys.utils.UserUtils;
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
//@RequestMapping("logistics")
//@Configuration
//@EnableScheduling
@Component
public class K3ScheduledTask {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(K3ScheduledTask.class);

    @Autowired
    private AmLogisticsService amLogisticsService;

    private long startRow =0;

    private static K3ScheduledTask k3ScheduledTask;
    @PostConstruct
    public void init() {
        k3ScheduledTask = this;
    }
//    @RequestMapping("info")
//    @Async
//    @Scheduled(fixedRate = 5000 * 60)
//    @Scheduled(cron = "0 0/5 * * * ?")// 每5分钟同步一次
//    @Scheduled(cron = "0/10 * * * * ?")
    public static void getK3InfoUpAmLogistics() throws Exception {
        long totil = 0;
        if (InvokeHelper.Login(K3Config.DBID, K3Config.UID, K3Config.PWD, K3Config.LANG, K3Config.K3ClOUDRL)) {
            // 销售订单保存测试
            //物流查询单
            String sFormId = "YF_SAL_LogisticsQuery";
            if (k3ScheduledTask.startRow == 0){
                List<AmLogistics> loList= k3ScheduledTask.amLogisticsService.findList(new AmLogistics());
                if (loList!=null&&loList.size()>0){
                    k3ScheduledTask.startRow = Integer.parseInt(loList.get(0).getFdId()) ;
                }
            }
            //需要保存的数据
            // 如下字段可能需要根据自己实际值做修改
            //物流查询单
            String F_YF_LogisticsCompany = "菜鸟物流";
            String s5 = "{\"FormId\":\"YF_SAL_LogisticsQuery\",\"FieldKeys\":\"" +
                    "FBILLNO,FDOCUMENTSTATUS,F_YF_SHOPNAME.FName,F_YF_OUTSTOCKNO,FCARRIAGENO,F_YF_THREESF,F_YF_THREETEL,F_YF_TYPE.FDATAVALUE,FCUSTOMERID." +
                    "FName,F_YF_Recipient,F_YF_Phone,FSalesManID.FName,F_YF_Province,F_YF_City,F_YF_Area,F_YF_LogisticsCompany," +
                    "F_YF_DeliveryMethod,F_YF_DetailsAddress,F_YF_ONCARDATETIME,F_YF_PROBLEMBEWRITE,F_YF_PROCESSRESULT," +
                    "F_YF_HANDLEUSERID.FName,FCREATEDATE,FMODIFIERID.FName," +
                    "FMODIFYDATE,F_YF_DETERMINEUSERID.FName,F_YF_DETERMINEDATETIME,F_YF_MATERIALID," +
                    "F_YF_MATERIALSNAME,F_YF_SPECIFICATION,FREALQTY,F_YF_PACKAGEQTY,FISFREE,F_YF_SHOP.FName,FSOORDERNO," +
                    "F_YF_SHOPNAME,FCUSTOMERID,FSalesManID,F_YF_HANDLEUSERID,FMODIFIERID,F_YF_DETERMINEUSERID,F_YF_SHOP,F_YF_DeliveryLogistic.FName,FEntity_FEntryID,fid\",\"FilterString\":\"fid>=" + k3ScheduledTask.startRow+" and (F_YF_DeliveryLogistic.FName='菜鸟物流' or F_YF_DeliveryLogistic.FName='菜鸟易宅配') \",\"OrderString\":\"\"," +
                    "\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"1000\"}";
            String a = InvokeHelper.ExecuteBillQuery(sFormId, s5, K3Config.K3ClOUDRL);
            String b = new String(a.getBytes());
            String[] c = b.split(",");
            if (c.length == 1) {
                return;
            }
            JSONArray jsonList = (JSONArray) JSONArray.parse(String.valueOf(b));
            List<LogisticsData> logisticsDataList = new ArrayList<>();
                for (int i = 0; i < jsonList.size(); i++) {
                    LogisticsData logisticsData = new LogisticsData();
                    prossed(logisticsData, (List<Object>) jsonList.get(i), logisticsDataList);
                }
                String index = logisticsDataList.get((logisticsDataList.size() - 1)).getF_ID();
                Integer startRow_int = Integer.parseInt(index);
                if (!(startRow_int == null)) {
                    k3ScheduledTask.startRow = startRow_int;
                }
                if (logisticsDataList != null && logisticsDataList.size() > 0) {
                    for (int i = 0; i < logisticsDataList.size(); i++) {
                        LogisticsData data = logisticsDataList.get(i);

                        List<AmLogisticsDetail> isHaveDetail = k3ScheduledTask.amLogisticsService.getDetail(data.getFEntity_FEntryID(), data.getF_YF_MATERIALID());
                        //先更新主表
                        AmLogistics amLogistics = new AmLogistics();
                        amLogistics.setDocumentCode(data.getFBILLNO());        //单据编号
                        AmLogistics isHaveamLogistics = k3ScheduledTask.amLogisticsService.get(amLogistics);
                        if (isHaveamLogistics != null && isHaveDetail != null && isHaveDetail.size() > 0) {
                            continue;
                        }
                        if (isHaveamLogistics == null) {
//                    //如果有就更新
//                    if (isHaveamLogistics!=null){
//                        amLogistics.setIsNewRecord(false);
//                    }else {
                            amLogistics.setIsNewRecord(true);
//                    }
                            String status = "创建";
//                    if ("Z".equals(data.getFDOCUMENTSTATUS())){
//                        status="暂存";
//                    }
//                    if ("A".equals(data.getFDOCUMENTSTATUS())){
//                        status="创建";
//                    }
//                    if ("B".equals(data.getFDOCUMENTSTATUS())){
//                        status="审核中";
//                    }
//                    if ("C".equals(data.getFDOCUMENTSTATUS())){
//                        status="已审核";
//                    }
//                    if ("D".equals(data.getFDOCUMENTSTATUS())){
//                        status="重新审核";
//                    }
                            amLogistics.setDocumentStatus(status);   //单据状态
                            amLogistics.setStores(data.getF_YF_SHOPNAMEFName());       //店铺
                            amLogistics.setSalesLibraryCode(data.getF_YF_OUTSTOCKNO());      //销售出库单号
                            amLogistics.setSingleTranportCode(data.getFCARRIAGENO());                          //运输单号
                            amLogistics.setMaintenanceUser(data.getF_YF_THREESF());             //三包师傅
                            amLogistics.setMaintenancePhone(data.getF_YF_THREETEL());           //三包电话
                            amLogistics.setType(data.getF_YF_TYPE());                       //类型
                            amLogistics.setClient(data.getFCUSTOMERIDFName());                  //客户
                            amLogistics.setRecipient(data.getF_YF_Recipient());                       //收件人
                            amLogistics.setMobilePhone(data.getF_YF_Phone());           //移动电话
                            amLogistics.setSeller(data.getFSalesManIDFName());            //销售员
                            amLogistics.setProvince(data.getF_YF_Province());               //省份
                            amLogistics.setCity(data.getF_YF_City());                     //城市
                            amLogistics.setCounty(data.getF_YF_Area());                   //区域
                            amLogistics.setLogisticsCompany(data.getF_YF_DeliveryLogistic());       //物流公司
                            amLogistics.setDeliveryMethod(data.getF_YF_DeliveryMethod());         //配送方式
                            amLogistics.setAddress(data.getF_YF_DetailsAddress());           //详细地址
                            amLogistics.setLoadingTime(data.getF_YF_ONCARDATETIME());           //装车时间
                            amLogistics.setProblemDescription(data.getF_YF_PROBLEMBEWRITE());       //问题描述
                            amLogistics.setProcessResult(data.getF_YF_PROCESSRESULT());          //处理结果
                            // 任务1729 修改创建时间 start
                         //   String isTimeOut = isTimeOut(data.getFCREATEDATE(), data.getF_YF_PROCESSRESULT());
                            String isTimeOut = isTimeOut(new Date(), data.getF_YF_PROCESSRESULT());
                            amLogistics.setCreateDate(new Date());              //创建时间
                            // 任务1729 end
                            amLogistics.setIsTimedOut(isTimeOut);                    //是否超时
                            amLogistics.setProcessPerson(data.getF_YF_HANDLEUSERIDFNAME());                          //处理人
                        //    amLogistics.setCreateDate(data.getFCREATEDATE());              //创建时间
                            amLogistics.setModifyPerson(data.getFMODIFIERIDFNAME());     //修改人
                            amLogistics.setModifyDate(data.getFMODIFYDATE());             //修改时间
                            amLogistics.setCompletionPerson(data.getF_YF_DETERMINEUSERIDFNAME());      //完成人名字
                            amLogistics.setCompletionDate(data.getF_YF_DETERMINEDATETIME());            //完成时间
                            amLogistics.setFdId(data.getF_ID());
//                            amLogistics.setShippingLogistics(data.getF_YF_DeliveryLogistic());
                            k3ScheduledTask.amLogisticsService.save(amLogistics);
                        }
                        if (isHaveDetail == null || isHaveDetail.size() <= 0) {
                            //更新明细表
                            AmLogisticsDetail detail = new AmLogisticsDetail();
                            detail.setDocumentCode(amLogistics);                     //单据编号
                            detail.setConsumablesCode(data.getF_YF_MATERIALID());     //物料编码
//                    List<AmLogisticsDetail> isHaveDetail=  amLogisticsService.getDetail(detail.getDocumentCode().getDocumentCode(),detail.getConsumablesCode());
//                    if (isHaveDetail==null||isHaveDetail.size()<=0){
                            detail.setIsNewRecord(true);
//                    }else {
//                        detail.setIsNewRecord(false);
//                        detail.setDetailCode(isHaveDetail.get(0).getDetailCode());
//                    }
                            detail.setConsumablesName(data.getF_YF_MATERIALSNAME());   //物料名称
                            detail.setSpecifications(data.getF_YF_SPECIFICATION());     //规格型号
                            detail.setActualNumber(data.getFREALQTY()); //实发数量
                            detail.setPackageNumber(data.getF_YF_PACKAGEQTY());     //包件数
                            String isGifts = null;
                            if ("false".equals(data.getFISFREE())) {
                                isGifts = "否";
                            }
                            if ("true".equals(data.getFISFREE())) {
                                isGifts = "是";
                            }
                            detail.setIsGifts(isGifts);                 //是否赠品
                            detail.setStores(data.getF_YF_SHOPFNAME());         //店铺名字
                            detail.setSalesOrderCode(data.getFSOORDERNO());    //销售订单号
                            detail.setEntryId(data.getFEntity_FEntryID());
                            k3ScheduledTask.amLogisticsService.saveDetail(detail);

                        }

                    }
                    totil++;
                }

                String master = UserUtils.getUser().getLoginCode();
            logger.info("同步物流查询单==================================================================================================================================");
            logger.info("物流查询单同步条数" + totil);
            logger.info("物流查询单同步时间" + DateUtils.getDateTime());
            }


    }

    private static String isTimeOut(Date createTime, String processResult) {
        String rst = "否";
        //是否超时
        if (createTime == null) {
            return "未知创建时间";
        }
        Date date = new Date();
//        AmLogistics amLogistics=new AmLogistics();
        String wekData = TimeUtils.getWeek(createTime);//得到创建时间是周几
        Date etiveStaTime = TimeUtils.getTimeFromData(createTime, 8, 30);  //得到创建时间当天的8.30
        Date noonStaTime = TimeUtils.getTimeFromData(createTime, 11, 30);  //得到创建时间当天的11.30
        Date afternoonStaTime = TimeUtils.getTimeFromData(createTime, 13, 30);  //得到创建时间当天的13.30
        Date etiveEndTime = TimeUtils.getTimeFromDeforData(createTime, 18, 30);  //得到创建时间前一天的18.30
        Date etTime = TimeUtils.getTimeFromDeforData(createTime, 18, 00);  //得到创建时间前一天的18.00
        if (K3Config.SUNDAY.equals(wekData)) {
            //如果创建时间小于8.30大于昨天的18.00 ,创建时间修改为8.30
            if (createTime.getTime() <= etiveStaTime.getTime() && createTime.getTime() >= etTime.getTime()) {
                createTime = etiveStaTime;
            }
        } else {
            //如果是周一到周六
            if (createTime.getTime() <= etiveStaTime.getTime() && createTime.getTime() >= etiveEndTime.getTime()) {
                createTime = etiveStaTime;
            }
        }
        //如果创建时间大于等于当天时间11.30,小于13.30
        if (createTime.getTime() >= noonStaTime.getTime() && createTime.getTime() <= afternoonStaTime.getTime()) {
            createTime = afternoonStaTime;
        }
        boolean isProcessResult = false;
        if (processResult == null || processResult.length() <= 0) {
            isProcessResult = true;
        }
        if (date.getTime() - createTime.getTime() > 1800000 && isProcessResult) {
            rst = "是";
        }
        return rst;
    }


    private static void prossed(LogisticsData logisticsData, List<Object> objects, List<LogisticsData> rst) throws ParseException {
        if (objects == null) {
            return;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
        logisticsData.setFBILLNO(objects.get(0).toString());
        logisticsData.setFDOCUMENTSTATUS(objects.get(1).toString());
        logisticsData.setF_YF_SHOPNAMEFName(objects.get(2).toString());
        logisticsData.setF_YF_OUTSTOCKNO(objects.get(3).toString());
        logisticsData.setFCARRIAGENO(objects.get(4).toString());
        logisticsData.setF_YF_THREESF(objects.get(5).toString());
        logisticsData.setF_YF_THREETEL(objects.get(6).toString());
        logisticsData.setF_YF_TYPE(objects.get(7).toString());
        logisticsData.setFCUSTOMERIDFName(objects.get(8).toString());
        logisticsData.setF_YF_Recipient(objects.get(9).toString());
        logisticsData.setF_YF_Phone(objects.get(10).toString());
        logisticsData.setFSalesManIDFName(objects.get(11).toString());
        logisticsData.setF_YF_Province(objects.get(12).toString());
        logisticsData.setF_YF_City(objects.get(13).toString());
        logisticsData.setF_YF_Area(objects.get(14).toString());
        logisticsData.setF_YF_LogisticsCompany(objects.get(15).toString());
        logisticsData.setF_YF_DeliveryMethod(objects.get(16).toString());
        logisticsData.setF_YF_DetailsAddress(objects.get(17).toString());
        String oncardateTime = objects.get(18).toString();
        Date oncardateTime_Date = null;
        if (oncardateTime != null && oncardateTime.length() > 0) {
            oncardateTime = oncardateTime.replace("T", " ");
            oncardateTime_Date = format.parse(oncardateTime);
        }
        logisticsData.setF_YF_ONCARDATETIME(oncardateTime_Date);
        logisticsData.setF_YF_PROBLEMBEWRITE(objects.get(19).toString());
        logisticsData.setF_YF_PROCESSRESULT(objects.get(20).toString());
        logisticsData.setF_YF_HANDLEUSERIDFNAME(objects.get(21).toString());
        String fcreatDate = null;
        try {
            fcreatDate = objects.get(22).toString();
            fcreatDate = fcreatDate.replace("T", " ");
        } catch (Exception e) {
//            e.printStackTrace();
        }
        Date fcreatDate_Date = null;
        if (fcreatDate != null && fcreatDate.length() > 0) {
            fcreatDate_Date = format.parse(fcreatDate);
        }
        logisticsData.setFCREATEDATE(fcreatDate_Date);
        logisticsData.setFMODIFIERIDFNAME(objects.get(23).toString());
        String modifyDate = null;
        try {
            modifyDate = objects.get(24).toString();
            modifyDate = modifyDate.replace("T", " ");
        } catch (Exception e) {
//            e.printStackTrace();
        }
        Date modifyDate_Date = null;
        if (modifyDate != null && modifyDate.length() > 0) {
            modifyDate_Date = format.parse(modifyDate);
        }
        logisticsData.setFMODIFYDATE(modifyDate_Date);
        logisticsData.setF_YF_DETERMINEUSERIDFNAME(objects.get(25).toString());
        String determinendDate = null;
        try {
            determinendDate = objects.get(26).toString();
            determinendDate = determinendDate.replace("T", " ");
        } catch (Exception e) {
//            e.printStackTrace();
        }
        Date determinendDate_Date = null;
        if (determinendDate != null && determinendDate.length() > 0) {
            determinendDate_Date = format.parse(determinendDate);
        }
        logisticsData.setF_YF_DETERMINEDATETIME(determinendDate_Date);    //完成时间
        logisticsData.setF_YF_MATERIALID(objects.get(27).toString());
        logisticsData.setF_YF_MATERIALSNAME(objects.get(28).toString());
        logisticsData.setF_YF_SPECIFICATION(objects.get(29).toString());
        logisticsData.setFREALQTY(objects.get(30).toString());
        logisticsData.setF_YF_PACKAGEQTY(objects.get(30).toString());
        logisticsData.setFISFREE(objects.get(32).toString());
        logisticsData.setF_YF_SHOPFNAME(objects.get(33).toString());
        logisticsData.setFSOORDERNO(objects.get(34).toString());
        logisticsData.setFEntity_FEntryID(objects.get(42).toString());
        logisticsData.setF_ID(objects.get(44).toString());
        logisticsData.setF_YF_DeliveryLogistic(objects.get(43).toString());
        rst.add(logisticsData);
    }


}


