package com.jeesite.modules.asset.scheduledtask;

import com.alibaba.fastjson.JSONArray;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.modules.asset.dispute.entity.AmDispute;
import com.jeesite.modules.asset.dispute.entity.AmDisputeDetail;
import com.jeesite.modules.asset.dispute.entity.AmDisputeImg;
import com.jeesite.modules.asset.dispute.service.AmDisputeImgService;
import com.jeesite.modules.asset.dispute.service.AmDisputeService;
import com.jeesite.modules.asset.k3webapi.InvokeHelper;
import com.jeesite.modules.asset.k3webapi.dispute.DisputeData;
import com.jeesite.modules.asset.util.TimeUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@Controller
//@RequestMapping("dispute")
//@Configuration
//@EnableScheduling
@Component
public class K3Scheduled {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(K3Scheduled.class);
    private long startRow = 0;


    @Autowired
    private AmDisputeService amDisputeService;
    @Autowired
    private AmDisputeImgService amDisputeImgService;

    private static K3Scheduled k3Scheduled;
    @PostConstruct
    public void init () {
        k3Scheduled = this;
    }
//    @RequestMapping("info")
//    @Async
//    @Scheduled(cron = "0 0/5 * * * ?") // 每5分钟同步一次
//    @Scheduled(fixedRate = 5000 * 60) // 每5分钟同步一次
//    @Scheduled(cron = "0/10 * * * * ?")
    public static void getInfo() throws Exception {
        int total = 0;
        if (InvokeHelper.Login(K3Config.DBID, K3Config.UID, K3Config.PWD, K3Config.LANG, K3Config.K3ClOUDRL)) {
            // 销售订单保存测试
            //物流纠纷单
            String sFormId = "YF_SC_LogisDisputes";
            if (k3Scheduled.startRow == 0) {
                List<AmDispute> list = k3Scheduled.amDisputeService.findList(new AmDispute());
                if (list != null && list.size() > 0) {
                    k3Scheduled.startRow = Integer.parseInt(list.get(0).getFid());
                }
            }
            //需要保存的数据
            // 如下字段可能需要根据自己实际值做修改
            // 物流纠纷单
            String s5 = "{\"FormId\":\"YF_SC_LogisDisputes\",\"FieldKeys\":\"" +
                    "FBILLNO,F_YF_CARRIAGENO,F_YF_SALESMANID.FName,FDOCUMENTSTATUS,F_YF_OUTSTOCKNO," +
                    "F_YF_ONCARDATETIME1,F_YF_CUSTOMER.FName,F_YF_DISPUTETYPE.FDATAVALUE,F_YF_RESPONSTYPE.FDATAVALUE," +
                    "F_YF_COMPENSATIONAMOUNT,F_YF_REMARKS,F_YF_PROCESSRESULT,F_YF_TURNREMARKS,F_YF_SHOPNAME.FName," +
                    "F_YF_PlatForm,F_YF_ClientName,F_YF_DeliveryMethod,F_YF_Province,F_YF_City,F_YF_Area," +
                    "F_YF_LogisticsCompany,F_YF_Phone,F_YF_DetailsAddress,F_YF_MATERIALID,F_YF_MaterialName,F_YF_MateriaModel," +
                    "F_YF_REALQTY,F_YF_DISPUTEREASON,F_YF_SIGNSTATUS,FCREATEDATE,FEntity_FEntryID,F_YF_DELIVERYLOGISTIC.FName,F_YF_IMG1,F_YF_IMG2,F_YF_IMG3,F_YF_IMG4,F_YF_IMG5,F_YF_IMG6,FID\",\"FilterString\":\"FID>="+k3Scheduled.startRow+" and (F_YF_DELIVERYLOGISTIC.FName='菜鸟物流' or F_YF_DeliveryLogistic.FName='菜鸟易宅配')\",\"OrderString\":\"\"," +
                    "\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"50\"}";
            // F_YF_LogisticsCompany='菜鸟物流'
            String a = InvokeHelper.ExecuteBillQuery(sFormId, s5, K3Config.K3ClOUDRL);
            String b = new String(a.getBytes());
            String[] c = b.split(",");
            if (c.length == 1) {
                return;
            }
            JSONArray jsonList = (JSONArray) JSONArray.parse(String.valueOf(b));
            List<DisputeData> disputeDataList = new ArrayList<>();
            for (int i = 0; i < jsonList.size(); i++) {
                DisputeData disputeData = new DisputeData();
                prossed(disputeData, (List<Object>) jsonList.get(i), disputeDataList);
            }
            String index = disputeDataList.get((disputeDataList.size() - 1)).getFID();
            k3Scheduled.startRow = Integer.parseInt(index);
            if (disputeDataList != null && disputeDataList.size() > 0) {
                for (DisputeData disputeData : disputeDataList) {
                    AmDispute amDispute = new AmDispute();
                    amDispute.setDocumentCode(disputeData.getFBILLNO());
                    AmDispute amDispute1 = k3Scheduled.amDisputeService.get(amDispute);
                    List<AmDisputeDetail> isHaveDetail = k3Scheduled.amDisputeService.getDetail(disputeData.getFEntity_FEntryID(), disputeData.getF_YF_MATERIALID());
                    if (amDispute1 != null && isHaveDetail != null && isHaveDetail.size() > 0) {
                        continue;
                    }
                    if (amDispute1 == null) {
//                        amDispute.setIsNewRecord(false);
//                    } else {
                        amDispute.setIsNewRecord(true);
                        amDispute.setFid(disputeData.getFID());
                        amDispute.setSingleTranportCode(disputeData.getF_YF_CARRIAGENO());  // 运输单号
                        amDispute.setSeller(disputeData.getF_YF_SALESMANIDFName());   // 销售员
                        amDispute.setDocumentStatus("创建");  // 单据状态
                        amDispute.setSalesLibraryCode(disputeData.getF_YF_OUTSTOCKNO()); // 销售出库单号
                        amDispute.setLoadingTime(disputeData.getF_YF_ONCARDATETIME1());  // 装车时间
                        amDispute.setClient(disputeData.getF_YF_CUSTOMERFName());        // 客户
                        amDispute.setDisputeType(disputeData.getF_YF_DISPUTETYPEFDATAVALUE()); // 纠纷类型
                        if ("物流公司".equals(disputeData.getF_YF_RESPONSTYPEFDATAVALUE())) {  // 责任类型
                            amDispute.setDutyType("0");
                            amDispute.setDutyValue("物流公司");
                        } else if ("三包师傅".equals(disputeData.getF_YF_RESPONSTYPEFDATAVALUE())) {
                            amDispute.setDutyType("1");
                            amDispute.setDutyValue("三包师傅");
                        } else {
                            amDispute.setDutyType("2");
                            amDispute.setDutyValue("其他");
                        }
                        amDispute.setDamages(disputeData.getF_YF_COMPENSATIONAMOUNT());   // 赔偿金额
                        amDispute.setProblemDescription(disputeData.getF_YF_REMARKS());   // 问题描述
                        amDispute.setProcessResult(disputeData.getF_YF_PROCESSRESULT());  // 处理结果
                        amDispute.setRejectRemarks(disputeData.getF_YF_TURNREMARKS());    // 驳回备注
                        amDispute.setStores(disputeData.getF_YF_SHOPNAMEFName());         // 店铺
                        amDispute.setPlatform(disputeData.getF_YF_PlatForm());            // 平台
                        // 任务1729 修改创建时间 start
                    //    amDispute.setCreateDate(disputeData.getFCREATEDATE());            // 创建时间
                        amDispute.setCreateDate(new Date());
                     //   String isTimeOut = isTimeOut(disputeData.getFCREATEDATE(), disputeData.getF_YF_PROCESSRESULT());
                        String isTimeOut = isTimeOut(new Date(), disputeData.getF_YF_PROCESSRESULT());
                        // 任务1729 end
                        amDispute.setIsTimedOut(isTimeOut);                               // 是否超时
                        amDispute.setClientName(disputeData.getF_YF_ClientName());        // 客户姓名
                        amDispute.setDeliveryMethod(disputeData.getF_YF_DeliveryMethod());// 配送方式
                        amDispute.setProvince(disputeData.getF_YF_Province());            // 省份
                        amDispute.setCity(disputeData.getF_YF_City());                    // 城市
                        amDispute.setCounty(disputeData.getF_YF_Area());                  // 区县
                        //amDispute.setLogisticsCompany(disputeData.getF_YF_LogisticsCompany());   // 物流公司
                        amDispute.setLogisticsCompany(disputeData.getF_YF_DELIVERYLOGISTIC());   // 发货物流
                        amDispute.setMobilePhone(disputeData.getF_YF_Phone());            // 移动电话
                        amDispute.setAddress(disputeData.getF_YF_DetailsAddress());       // 详细地址
                        k3Scheduled.amDisputeService.saveData(amDispute);
                    }
                    AmDisputeImg amDisputeImg = new AmDisputeImg();
                    amDisputeImg.setDocumentCode(disputeData.getFBILLNO());
                    AmDisputeImg amDisputeImg1 = k3Scheduled.amDisputeImgService.get(amDisputeImg);
                    if (amDisputeImg1 == null) {
                        System.out.println(disputeData.getF_YF_IMG1());
                        amDisputeImg.setFileid1(disputeData.getF_YF_IMG1().trim());
                        amDisputeImg.setFileid2(disputeData.getF_YF_IMG2().trim());
                        amDisputeImg.setFileid3(disputeData.getF_YF_IMG3().trim());
                        amDisputeImg.setFileid4(disputeData.getF_YF_IMG4().trim());
                        amDisputeImg.setFileid5(disputeData.getF_YF_IMG5().trim());
                        amDisputeImg.setFileid6(disputeData.getF_YF_IMG6().trim());
                        amDisputeImg.setIsNewRecord(true);
                        k3Scheduled.amDisputeImgService.save(amDisputeImg);
                    }
                    if (isHaveDetail == null || isHaveDetail.size() <= 0) {
                        AmDisputeDetail amDisputeDetail = new AmDisputeDetail();
                        // AmDisputeDetail amDisputeDetail1 =  amDisputeService.findDetil(disputeData.getFBILLNO(), disputeData.getF_YF_MATERIALID());
                        // amDisputeService.findDetil(disputeData.getFBILLNO(), disputeData.getF_YF_MATERIALID());

                        amDisputeDetail.setIsNewRecord(true);
                        amDisputeDetail.setDocumentCode(amDispute);                            // 单据编号
                        amDisputeDetail.setConsumablesCode(disputeData.getF_YF_MATERIALID());  // 物料编号
                        amDisputeDetail.setConsumablesName(disputeData.getF_YF_MaterialName());// 物料名称
                        amDisputeDetail.setSpecifications(disputeData.getF_YF_MateriaModel()); // 规格型号
                        amDisputeDetail.setAmount(disputeData.getF_YF_REALQTY());              // 数量
                        amDisputeDetail.setDisputeReason(disputeData.getF_YF_DISPUTEREASON()); // 纠纷原因
                        amDisputeDetail.setSignStatus(disputeData.getF_YF_SIGNSTATUS());       // 签收状态
                        amDisputeDetail.setEntryId(disputeData.getFEntity_FEntryID());
                        k3Scheduled.amDisputeService.saveDetil(amDisputeDetail);
                    }
                    total++;
                }
            }
            logger.info("同步物流纠纷单==================================================================================================================================");
            logger.info("物流纠纷单同步条数" + total);
            logger.info("物流纠纷单同步时间" + DateUtils.getDateTime());
        }
    }

    private static String isTimeOut(Date createTime, String result) {
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

        boolean flag = true;
        if (!"".equals(result) && result != null) {
            flag = false;
        }
        if (date.getTime() - createTime.getTime() > (60000 * 2160) && flag) {
            rst = "是";
        }
        return rst;
    }

    private static void prossed(DisputeData disputeData, List<Object> objects, List<DisputeData> rst) throws ParseException {
        if (objects == null) {
            return;
        }
        disputeData.setFBILLNO(objects.get(0).toString());
        disputeData.setF_YF_CARRIAGENO(objects.get(1).toString());
        disputeData.setF_YF_SALESMANIDFName(objects.get(2).toString());
        disputeData.setFDOCUMENTSTATUS(objects.get(3).toString());
        disputeData.setF_YF_OUTSTOCKNO(objects.get(4).toString());
        disputeData.setF_YF_ONCARDATETIME1(getTime(objects, 5));
        disputeData.setF_YF_CUSTOMERFName(objects.get(6).toString());
        disputeData.setF_YF_DISPUTETYPEFDATAVALUE(objects.get(7).toString());
        disputeData.setF_YF_RESPONSTYPEFDATAVALUE(objects.get(8).toString());
        disputeData.setF_YF_COMPENSATIONAMOUNT(getNum(objects, 9));
        disputeData.setF_YF_REMARKS(objects.get(10).toString());
        disputeData.setF_YF_PROCESSRESULT(objects.get(11).toString());
        disputeData.setF_YF_TURNREMARKS(objects.get(12).toString());
        disputeData.setF_YF_SHOPNAMEFName(objects.get(13).toString());
        disputeData.setF_YF_PlatForm(objects.get(14).toString());
        disputeData.setF_YF_ClientName(objects.get(15).toString());
        disputeData.setF_YF_DeliveryMethod(objects.get(16).toString());
        disputeData.setF_YF_Province(objects.get(17).toString());
        disputeData.setF_YF_City(objects.get(18).toString());
        disputeData.setF_YF_Area(objects.get(19).toString());
        disputeData.setF_YF_LogisticsCompany(objects.get(20).toString());
        disputeData.setF_YF_Phone(objects.get(21).toString());
        disputeData.setF_YF_DetailsAddress(objects.get(22).toString());

        disputeData.setF_YF_MATERIALID(objects.get(23).toString());
        disputeData.setF_YF_MaterialName(objects.get(24).toString());
        disputeData.setF_YF_MateriaModel(objects.get(25).toString());
        Long realqty = null;
        try {
            realqty = (Long) objects.get(26);
        } catch (Exception e) {
        }
        disputeData.setF_YF_REALQTY(realqty);
        disputeData.setF_YF_DISPUTEREASON(objects.get(27).toString());
        disputeData.setF_YF_SIGNSTATUS(objects.get(28).toString());
        disputeData.setFCREATEDATE(getTime(objects, 29));
        disputeData.setFEntity_FEntryID(objects.get(30).toString());
        disputeData.setF_YF_DELIVERYLOGISTIC(objects.get(31).toString());
        disputeData.setF_YF_IMG1(objects.get(32).toString().trim());
        disputeData.setF_YF_IMG2(objects.get(33).toString().trim());
        disputeData.setF_YF_IMG3(objects.get(34).toString().trim());
        disputeData.setF_YF_IMG4(objects.get(35).toString().trim());
        disputeData.setF_YF_IMG5(objects.get(36).toString().trim());
        disputeData.setF_YF_IMG6(objects.get(37).toString().trim());
        String userToken = InvokeHelper.UserToken;
        for (int i = 32; i <= 37; i++) {             // 图片取得
            String img = objects.get(i).toString().trim();
            if (img != null && !"".equals(img)) {
//                String url = "http://uvan.ik3cloud.com/K3cloud/FileUpLoadServices/download.aspx?fileId=" + img + "&dbId=20161121155638&t=636601727451676767&token=" + userToken + "";
                String url = K3Config.K3ClOUDRL + "FileUpLoadServices/download.aspx?fileId=" + img + "&dbId="+ K3Config.DBID +"&token=" + userToken + "";
                try {
                    String filePath = K3Config.BASEDIR + "/dispute/"+ img +".jpg";
                    saveToImgByStr(filePath, url);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        disputeData.setFID(objects.get(38).toString());
        rst.add(disputeData);
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

    /**
     * 图片二进制
     * @param filePath
     * @param url
     * @return
     */
    public static boolean saveToImgByStr(String filePath, String url){
        boolean flag = false;
            try {
                // 将字符串转换成二进制，用于显示图片
                // 将上面生成的图片格式字符串 imgStr，还原成图片显示
               // byte[] imgByte = hex2byte( imgStr );

                byte[] imgByte = getImageFromNetByUrl(url);
                InputStream in = new ByteArrayInputStream(imgByte);

                File file = new File(filePath);//可以是任何图片格式.jpg,.png等
                if (!file.exists()) {


                    FileOutputStream fos = new FileOutputStream(file);

                    byte[] b = new byte[1024];
                    int nRead = 0;
                    while ((nRead = in.read(b)) != -1) {
                        fos.write(b, 0, nRead);
                    }
                    fos.flush();
                    fos.close();

                }
                in.close();
                flag = true;
            } catch (Exception e) {
                flag = false;
                e.printStackTrace();
            } finally {

            }
        return flag;
    }

    /**
     * 根据地址获得数据的字节流
     *
     * @param strUrl
     *            网络连接地址
     * @return
     */
    public static byte[] getImageFromNetByUrl(String strUrl) {
        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            InputStream inStream = conn.getInputStream();// 通过输入流获取图片数据
            byte[] btImg = readInputStream(inStream);// 得到图片的二进制数据
            return btImg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从输入流中获取数据
     *
     * @param inStream
     *            输入流
     * @return
     * @throws IOException
     * @throws Exception
     */
    private static byte[] readInputStream(InputStream inStream) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[10240];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();

    }
}