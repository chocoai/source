package com.jeesite.modules.asset.k3webapi;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvokeTest {
   @Value("${POST_K3ClOUDRL}")
      String POST_K3ClOUDRL;  //测试库
    public void main(String[] args) throws Exception {

        //账套id
        String dbId = "5ab0d51363d36b";
        //Administrator  denise
        String uid = "Administrator";
        String pwd = "iiii.1234";
        int lang = 2052;
        if (InvokeHelper.Login(dbId, uid, pwd, lang,POST_K3ClOUDRL)) {
            // 销售订单保存测试
            // 业务对象Id
//            String sFormId = "YF_YF_PicTureManage";
            //物流查询单
            String sFormId = "YF_SAL_LogisticsQuery";
            //需要保存的数据
            // 如下字段可能需要根据自己实际值做修改
            // FCustId FSalerId FMaterialId FUnitID
            String s1 = "{\"Creator\":\"\",\"NeedUpDateFields\":[],\"NeedReturnFields\":[],\"IsDeleteEntry\":\"True\",\"SubSystemId\":\"\",\"IsVerifyBaseDataField\":\"false\",\"IsEntryBatchFill\":\"True\",\"Model\":{\"FID\":\"0\",\"FBillNo\":\"\",\"FCreatorId\":{\"FUserID\":\"0\"},\"FCreateDate\":\"1900-01-01\",\"FModifierId\":{\"FUserID\":\"0\"},\"FModifyDate\":\"1900-01-01\",\"F_YF_AduitDatetime\":\"1900-01-01\",\"F_YF_AduitUserId\":{\"FUserID\":\"0\"},\"F_YF_Date\":\"1900-01-01\",\"F_YF_Department\":{\"FNumber\":\"123456\"},\"F_YF_DemandStaffId\":{\"FUserID\":\"0\"},\"F_YF_DemandStaffNo\":\"\",\"FEntity\":[{\"FEntryID\":\"0\",\"F_YF_EntryDate\":\"2018-06-01\",\"F_YF_AfternoonBenTime\":\"2018-05-31\",\"F_YF_AfternoonEndTime\":\"2018-06-01\",\"F_YF_MornBenTime\":\"2018-05-31\",\"F_YF_MornEndTime\":\"2018-06-01\"}]}}";
            String s2 = "{\"Creator\":\"\",\"NeedUpDateFields\":[],\"NeedReturnFields\":[],\"IsDeleteEntry\":\"True\",\"SubSystemId\":\"\",\"IsVerifyBaseDataField\":\"false\",\"IsEntryBatchFill\":\"True\",\"Model\":{\"FID\":\"0\",\"FBillNo\":\"\",\"FCreatorId\":{},\"FCreateDate\":\"1900-01-01\",\"FModifierId\":{},\"FModifyDate\":\"1900-01-01\",\"F_YF_AduitUserId\":{},\"F_YF_AduitDatetime\":\"1900-01-01\",\"FBillTypeID\":{\"FNumber\":\"123456\"},\"F_YF_ProductsBelong\":{\"FNumber\":\"\"},\"F_YF_ModuleBelong\":{\"FNumber\":\"\"},\"F_YF_DemandName\":\"\",\"F_YF_DemandDescribe\":\"\",\"F_YF_DemandStaff\":{},\"F_YF_DevelopStaff\":{},\"F_YF_OnlineDate\":\"1900-01-01\",\"F_YF_WeekPlan\":\"0\",\"F_YF_TestStaff\":{},\"F_YF_TestRemark\":\"\",\"F_YF_WantAchieveDate\":\"1900-01-01\",\"F_YF_DevelopRemark\":\"\",\"F_YF_ProblemDescribe\":\"test\",\"F_YF_ExpectResult\":\"test\",\"F_YF_SourceBillNo\":\"\",\"F_YF_DemandRaiseDate\":\"1900-01-01\",\"F_YF_DemandManageNo\":\"\",\"F_YF_AttachmentName\":\"\",\"F_YF_Handler\":{}}}";
            String sContent = "{\"Creator\":\"String\",\"NeedUpDateFields\":[\"FBillTypeID\",\"FDate\",\"FBusinessType\",\"FSaleOrgId\",\"FCustId\",\"FSettleCurrId\",\"FSalerId\",\"SAL_SaleOrder__FSaleOrderEntry\",\"FMaterialId\",\"FSettleOrgIds\",\"FUnitID\",\"FQty\",\"SAL_SaleOrder__FSaleOrderFinance\",\"FSettleCurrId\",\"FLocalCurrId\",\"FIsIncludedTax\",\"FBillTaxAmount\",\"FBillAmount\",\"FBillAllAmount\",\"FExchangeTypeId\",\"FExchangeRate\"],\"Model\":{\"FID\":0,\"FBillTypeID\":{\"FNumber\":\"XSDD01_SYS\"},\"FBusinessType\":\"NORMAL\",\"FSaleOrgId\":{\"FNUMBER\":\"100\"},\"FCustId\":{\"FNUMBER\":\"CUST0001\"},\"FSettleCurrId\":{\"FNUMBER\":\"PRE001\"},\"FSalerId\":{\"FNUMBER\":\"0002\"},\"SAL_SaleOrder__FSaleOrderFinance\":{\"FExchangeRate\":6.8123},\"SAL_SaleOrder__FSaleOrderEntry\":[{\"FMaterialId\":{\"FNUMBER\":\"001\"},\"FSettleOrgIds\":{\"FNUMBER\":\"100\"},\"FUnitID\":{\"FNumber\":\"个\"},\"FQty\":10}]}}";
            String s3 = "{\"Creator\":\"\",\"NeedUpDateFields\":[],\"NeedReturnFields\":[],\"IsDeleteEntry\":\"True\",\"SubSystemId\":\"\",\"IsVerifyBaseDataField\":\"false\",\"IsEntryBatchFill\":\"True\",\"Model\":{\"FID\":\"0\",\"FBillNo\":\"\",\"FCreatorId\":{\"FUserID\":\"0\"},\"FCreateDate\":\"1900-01-01\",\"FModifierId\":{\"FUserID\":\"0\"},\"FModifyDate\":\"1900-01-01\",\"F_YF_Url\":\"\",\"F_YF_AduitUserId\":{\"FUserID\":\"0\"},\"F_YF_AduitDatetime\":\"1900-01-01\",\"F_YF_OrgId\":{\"FNumber\":\"\"},\"F_YF_ObjectId\":{\"FID\":\"\"},\"F_YF_SourceFID\":\"\"}}";
            String s4 = "{\"CreateOrgId\":\"0\",\"Number\":\"WLCX20170224002\",\"Id\":\"\"}";
            //物流查询单
            String s5 = "{\"FormId\":\"YF_SAL_LogisticsQuery\",\"FieldKeys\":\"" +
                    "FBILLNO,FDOCUMENTSTATUS,F_YF_SHOPNAME.FName,F_YF_OUTSTOCKNO,FCARRIAGENO,F_YF_THREESF,F_YF_THREETEL,F_YF_TYPE,FCUSTOMERID." +
                    "FName,F_YF_Recipient,F_YF_Phone,FSalesManID.FName,F_YF_Province,F_YF_City,F_YF_Area,F_YF_LogisticsCompany," +
                    "F_YF_DeliveryMethod,F_YF_DetailsAddress,F_YF_ONCARDATETIME,F_YF_PROBLEMBEWRITE,F_YF_PROCESSRESULT," +
                    "F_YF_HANDLEUSERID.FName,FCREATEDATE,FMODIFIERID.FName," +
                    "FMODIFYDATE,F_YF_DETERMINEUSERID.FName,F_YF_DETERMINEDATETIME,F_YF_MATERIALID," +
                    "F_YF_MATERIALSNAME,F_YF_SPECIFICATION,FREALQTY,F_YF_PACKAGEQTY,FISFREE,F_YF_SHOP.FName,FSOORDERNO," +
                    "F_YF_SHOPNAME,FCUSTOMERID,FSalesManID,F_YF_HANDLEUSERID,FMODIFIERID,F_YF_DETERMINEUSERID,F_YF_SHOP\",\"FilterString\":\"\",\"OrderString\":\"\"," +
                    "\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"1000\"}";
            String params = "{\n" +
                    "\t\"entryOrder\":{\n" +
                    "\t\t\"entryOrderCode\":\"XDCE0004\",\n" +
                    "\t\t\"ownerCode\":\"HZ0004\",\n" +
                    "\t\t\"warehouseCode\":\"WH0004\"\n" +
                    "\t\t},\n" +
                    "\t\"orderLines\":[{\n" +
                    "\t\t\"ownerCode\":\"HZ0004\",\n" +
                    "\t\t\"itemCode\":\"SP000\",\n" +
                    "\t\t\"planQty\":13\n" +
                    "\t}],\n" +
                    "\t\"extendProps\":{\n" +
                    "\t\t\"fgsdfg\":\n" +
                    "\t\t{\n" +
                    "\t\t\"test\":\"abc\",\n" +
                    "\t\t\"test1\":\"123\"\n" +
                    "\t\t},\n" +
                    "\t\t\"abv\":\n" +
                    "\t\t{\n" +
                    "\t\t\"test\":\"test\",\n" +
                    "\t\t\"test1\":\"6666\"\n" +
                    "\t\t}\n" +
                    "\t}\n" +
                    "}";
            String d = null;
            try {
                d = InvokeHelper.confirm( params,POST_K3ClOUDRL);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String a = InvokeHelper.ExecuteBillQuery(sFormId, s5,POST_K3ClOUDRL);
            JSONArray jsonList = (JSONArray) JSONArray.parse(String.valueOf(a));
            List<LogisticsData> logisticsDataList = new ArrayList<>();
            for (int i = 0; i < jsonList.size(); i++) {
                LogisticsData logisticsData = new LogisticsData();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
                List<Object> objects = (List<Object>) jsonList.get(i);
//                    System.out.println((objects.get(j))[0]);
                logisticsData.setFBILLNO(objects.get(0).toString());
                logisticsData.setFDOCUMENTSTATUS(objects.get(1).toString());
//                if (!(objects.get(35).toString().equals("0"))){
                logisticsData.setF_YF_SHOPNAMEFName(objects.get(2).toString());
//                }
                logisticsData.setF_YF_OUTSTOCKNO(objects.get(3).toString());
                logisticsData.setFCARRIAGENO(objects.get(4).toString());
                String F_YF_THREESF = null;
                try {
                    F_YF_THREESF = objects.get(5).toString();
                } catch (Exception e) {
//                    e.printStackTrace();
                }
                logisticsData.setF_YF_THREESF(F_YF_THREESF);
                logisticsData.setF_YF_THREETEL(objects.get(6).toString());

                logisticsData.setF_YF_TYPE(objects.get(7).toString());
//                if (!(objects.get(36).toString().equals("0"))){
                logisticsData.setFCUSTOMERIDFName(objects.get(8).toString());
//                }
                String recipient=null;
                try {
                    recipient= objects.get(9).toString();
                }catch (Exception e){
//                    e.printStackTrace();
                }
                logisticsData.setF_YF_Recipient(recipient);
                String phone=null;
                try {
                    phone= objects.get(10).toString();
                }catch (Exception e){
//                    e.printStackTrace();
                }
                logisticsData.setF_YF_Phone(phone);
                if (!(objects.get(38).toString().equals("0"))){
                logisticsData.setFSalesManIDFName(objects.get(11).toString());
                }
                String province = null;
                try {
                    province = objects.get(12).toString();
                } catch (Exception e) {
//                    e.printStackTrace();
                }
                logisticsData.setF_YF_Province(province);
                String city = null;
                try {
                    city = objects.get(13).toString();
                } catch (Exception e) {
//                    e.printStackTrace();
                }
                logisticsData.setF_YF_City(city);
                String F_YF_Area = null;
                try {
                    F_YF_Area = objects.get(14).toString();
                } catch (Exception e) {
//                    e.printStackTrace();
                }
                logisticsData.setF_YF_Area(F_YF_Area);
                String LogisticsCompany = null;
                try {
                    LogisticsCompany = objects.get(15).toString();
                } catch (Exception e) {
//                    e.printStackTrace();
                }
                logisticsData.setF_YF_LogisticsCompany(LogisticsCompany);
                String deliveryMethod = null;
                try {
                    deliveryMethod = objects.get(16).toString();
                } catch (Exception e) {
//                    e.printStackTrace();
                }
                logisticsData.setF_YF_DeliveryMethod(deliveryMethod);
                logisticsData.setF_YF_LogisticsCompany(LogisticsCompany);
                String detailsAddress = null;
                try {
                    detailsAddress = objects.get(17).toString();
                } catch (Exception e) {
//                    e.printStackTrace();
                }
                logisticsData.setF_YF_DetailsAddress(detailsAddress);
                String oncardateTime = null;
                try {
                    oncardateTime = objects.get(18).toString();
                    oncardateTime = oncardateTime.replace("T", " ");
                } catch (Exception e) {
//                    e.printStackTrace();
                }
                Date oncardateTime_Date = null;
                if (oncardateTime != null && oncardateTime.length() > 0) {
                    oncardateTime_Date = format.parse(oncardateTime);
                }
                logisticsData.setF_YF_ONCARDATETIME(oncardateTime_Date);

                logisticsData.setF_YF_PROBLEMBEWRITE(objects.get(19).toString());
                logisticsData.setF_YF_PROCESSRESULT(objects.get(20).toString());
                if (!(objects.get(38).toString().equals("0"))) {
                    logisticsData.setF_YF_HANDLEUSERIDFNAME(objects.get(21).toString());
                }

                String fcreatDate = null;
                try {
                    fcreatDate = objects.get(22).toString();
                    fcreatDate = fcreatDate.replace("T", " ");
                } catch (Exception e) {
//                    e.printStackTrace();
                }
                Date fcreatDate_Date = null;
                if (fcreatDate != null && fcreatDate.length() > 0) {
                    fcreatDate_Date = format.parse(fcreatDate);
                }
                logisticsData.setFCREATEDATE(fcreatDate_Date);
                if (!(objects.get(39).toString().equals("0"))) {
                    logisticsData.setFMODIFIERIDFNAME(objects.get(23).toString());
                }
                String modifyDate = null;
                try {
                    modifyDate = objects.get(24).toString();
                    modifyDate = modifyDate.replace("T", " ");
                } catch (Exception e) {
//                    e.printStackTrace();
                }
                Date modifyDate_Date = null;
                if (modifyDate != null && modifyDate.length() > 0) {
                    modifyDate_Date = format.parse(modifyDate);
                }
                logisticsData.setFMODIFYDATE(modifyDate_Date);
                if (!(objects.get(40).toString().equals("0"))) {
                    logisticsData.setF_YF_DETERMINEUSERIDFNAME(objects.get(25).toString());
                }
                String determinendDate = null;
                try {
                    determinendDate = objects.get(26).toString();
                    determinendDate = determinendDate.replace("T", " ");
                } catch (Exception e) {
//                    e.printStackTrace();
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
                if (!(objects.get(40).toString()).equals("0"))
                    logisticsData.setF_YF_SHOPFNAME(objects.get(33).toString());
                logisticsData.setFSOORDERNO(objects.get(34).toString());
                logisticsDataList.add(logisticsData);
            }

            System.out.println("----------"+logisticsDataList);


//                prossed(logisticsData,(List<Object>)jsonList.get(i));
//                for(int j = 0; j < ((List<Object>)jsonList.get(i)).size(); j++){
//                    System.out.println(((List<Object>)jsonList.get(i)).get(j));
//
//                }
        }

//            JSONObject json = (JSONObject)JSONObject.parse(String.valueOf(a));
//            JSONObject json1 = (JSONObject) json.get("Result");
//            ResultData jb = (ResultData)JSONObject.toBean(obj,ResultData.class);//将建json对象转换为Person对象
//            System.out.println(jb);
//            String str = String.valueOf((JSONObject) json.get("Result"));
//            ResultData jb = JSONObject.parseObject(String.valueOf(a),ResultData.class);//将建json对象转换为Person对象

//            System.out.println(jb);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
        String time = "2017-02-27T16:19:58.427";
        time = time.replace("T", " ");
        Date date = format.parse(time);
        System.out.println(date);


    }

    private void prossed(LogisticsData logisticsData, List<Object> objects) throws ParseException {
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
        oncardateTime = oncardateTime.replace("T", " ");
        Date oncardateTime_Date = format.parse(oncardateTime);
        logisticsData.setF_YF_ONCARDATETIME(oncardateTime_Date);

        logisticsData.setF_YF_PROBLEMBEWRITE(objects.get(19).toString());
        logisticsData.setF_YF_PROCESSRESULT(objects.get(20).toString());
        logisticsData.setF_YF_HANDLEUSERIDFNAME(objects.get(21).toString());
        String fcreatDate = objects.get(22).toString();
        fcreatDate = fcreatDate.replace("T", " ");
        Date fcreatDate_Date = format.parse(fcreatDate);
        logisticsData.setFCREATEDATE(fcreatDate_Date);

        logisticsData.setFMODIFIERIDFNAME(objects.get(23).toString());
        String modifyDate = objects.get(24).toString();
        modifyDate = modifyDate.replace("T", " ");
        Date modifyDate_Date = format.parse(modifyDate);
        logisticsData.setFMODIFYDATE(modifyDate_Date);

        logisticsData.setF_YF_DETERMINEUSERIDFNAME(objects.get(25).toString());

        String determinendDate = objects.get(26).toString();
        determinendDate = determinendDate.replace("T", " ");
        Date determinendDate_Date = format.parse(determinendDate);
        logisticsData.setF_YF_DETERMINEDATETIME(determinendDate_Date);    //完成时间
//
//        logisticsData.setF_YF_MATERIALID(objects.get(27).toString());
//        logisticsData.setF_YF_MATERIALSNAME(objects.get(28).toString());
//        logisticsData.setF_YF_SPECIFICATION(objects.get(29).toString());
//        logisticsData.setFREALQTY((Long) objects.get(30));
//        logisticsData.setF_YF_PACKAGEQTY((Long) objects.get(31));
//        logisticsData.setFISFREE((Boolean) objects.get(32));
//        logisticsData.setF_YF_SHOPFNAME(objects.get(33).toString());
//        logisticsData.setFSOORDERNO(objects.get(34).toString());

    }
}
