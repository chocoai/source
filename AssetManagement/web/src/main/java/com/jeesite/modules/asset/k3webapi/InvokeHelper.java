package com.jeesite.modules.asset.k3webapi;

import com.alibaba.fastjson.serializer.ValueFilter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class InvokeHelper {
    @Value("${POST_K3ClOUDRL}")
    String POST_K3ClOUDRLS;  //测试库
//    public static String POST_K3ClOUDRL = "http://uvan.ik3cloud.com/K3cloud/";     //正式库
    public static String UserToken = null;
    // Cookie 值
    private static String CookieVal = null;

    private static String K3_LOGIN_URL = "";

    private static Map<String,String> map = new HashMap<>();
    static {
        map.put("Save",
                "Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.Save.common.kdsvc");
        map.put("View",
                "Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.View.common.kdsvc");
        map.put("Submit",
                "Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.Submit.common.kdsvc");
        map.put("Audit",
                "Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.Audit.common.kdsvc");
        map.put("UnAudit",
                "Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.UnAudit.common.kdsvc");
        map.put("StatusConvert",
                "Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.StatusConvert.common.kdsvc");
        map.put("ExecuteBillQuery",
                "Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.ExecuteBillQuery.common.kdsvc");
        map.put("confirm",
                "YF.K3.App.Core.ServicesStub.PUR.InStock.InStockConfirm,YF.K3.App.Core.ServicesStub.common.kdsvc");
        map.put("ExcuteOperation",
                "Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.ExcuteOperation.common.kdsvc");
        map.put("calculateShippingCosts",
                "YF.K3.OA.App.Core.ServicesStub.Common.CalculateShippingCosts,YF.K3.OA.App.Core.common.kdsvc");    // 计算运费
        map.put("virtualQuotation",
                "YF.K3.App.Core.ServicesStub.SCM.SaleOrder.VirtualQuotation,YF.K3.App.Core.ServicesStub.common.kdsvc");    // 计算优惠
        map.put("saveSupplierInfo",
                "YF.K3.BD.App.Core.ServicesStub.Common.RegisteredSupplier,YF.K3.BD.App.Core.common.kdsvc");
        map.put("QueryBySql",
                "YF.K3.SCM.App.Core.SystemService.QueryCommonService.QueryByDynamicObjectCollection,YF.K3.SCM.App.Core.common.kdsvc");   //查询
        map.put("getInventoryStockQueryReport",
                "YF.K3.App.Core.ServicesStub.SCM.SaleOrder.GetInventoryStockQueryReport,YF.K3.App.Core.ServicesStub.common.kdsvc");    // 仓库库存查询
        map.put("materialGetBomSonMaterial",
                "YF.K3.App.Core.ServicesStub.SCM.SaleOrder.MaterialGetBomSonMaterial,YF.K3.App.Core.ServicesStub.common.kdsvc");    // 查询bom组合表中的子项物料
        map.put("getInventoryStockQueryReport2",
                "YF.K3.App.Core.ServicesStub.SCM.SaleOrder.getInventoryStockQueryReport2,YF.K3.App.Core.ServicesStub.common.kdsvc");    // 查询货期 多个sku
    }

    public static StringBuffer concession( String deal, Object content, String POST_K3ClOUDRLS) throws Exception {
        StringBuffer a = Invoke(deal, content, POST_K3ClOUDRLS);
        return a;
    }


    private static HttpURLConnection initUrlConn(String url, JSONArray paras, String POST_K3ClOUDRL)
            throws Exception {
        paras.add(UserToken);
        K3_LOGIN_URL = POST_K3ClOUDRL;
        URL postUrl = new URL(POST_K3ClOUDRL.concat(url));
        HttpURLConnection connection = (HttpURLConnection) postUrl
                .openConnection();
        if (CookieVal != null) {
            connection.setRequestProperty("Cookie", CookieVal);
        }
        if (!connection.getDoOutput()) {
            connection.setDoOutput(true);
        }
        connection.setRequestMethod("POST");
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Content-Type", "application/json");
        DataOutputStream out = new DataOutputStream(
                connection.getOutputStream());

        UUID uuid = UUID.randomUUID();
        int hashCode = uuid.toString().hashCode();

        JSONObject jObj = new JSONObject();

        jObj.put("format", 1);
        jObj.put("useragent", "ApiClient");
        jObj.put("rid", hashCode);
        jObj.put("parameters", paras.toString());
        jObj.put("timestamp", new Date().toString());
        jObj.put("v", "1.0");

//        out.writeBytes(jObj.toString().getBytes("GBK"));
        out.write(jObj.toString().getBytes("utf-8"));
        out.flush();
        out.close();

        return connection;
    }

    // Login
    public static boolean Login(String dbId, String user, String pwd, int lang, String POST_K3ClOUDRL)
            throws Exception {

        boolean bResult = false;

        String sUrl = "Kingdee.BOS.WebApi.ServicesStub.AuthService.ValidateUser.common.kdsvc";

        JSONArray jParas = new JSONArray();
        jParas.add(dbId);// 帐套Id
        jParas.add(user);// 用户名
        jParas.add(pwd);// 密码
        jParas.add(lang);// 语言

        HttpURLConnection connection = initUrlConn(sUrl, jParas,POST_K3ClOUDRL);
        // 获取Cookie
        String key = null;
        for (int i = 1; (key = connection.getHeaderFieldKey(i)) != null; i++) {
            if (key.equalsIgnoreCase("Set-Cookie")) {
                String tempCookieVal = connection.getHeaderField(i);
                if (tempCookieVal.startsWith("kdservice-sessionid")) {
                    CookieVal = tempCookieVal;
                    break;
                }
            }
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        String line;
        System.out.println(" ============================= ");
        System.out.println(" Contents of post request ");
        System.out.println(" ============================= ");
        while ((line = reader.readLine()) != null) {
            String sResult = new String(line.getBytes(), "utf-8");
            String a=null;
            try {
                 a = com.alibaba.fastjson.JSONObject.parseObject(sResult).get("Context").toString();
                UserToken = com.alibaba.fastjson.JSONObject.parseObject(a).get("UserToken").toString();
                System.out.println(sResult);
                bResult = line.contains("\"LoginResultType\":1");
            }catch (Exception e){
                bResult=false;
            }

        }
        System.out.println(" ============================= ");
        System.out.println(" Contents of post request ends ");
        System.out.println(" ============================= ");
        reader.close();

        connection.disconnect();
        System.out.println("*****************|登录结果："+bResult+"|******************");
        return bResult;
    }
    //增加flag
    private static StringBuffer Invoke3(String deal, String formId, String flag, String content, String POST_K3ClOUDRL)
            throws Exception {
        StringBuffer stringBuffer=new StringBuffer();
        String sUrl = map.get(deal).toString();
        JSONArray jParas = new JSONArray();
        jParas.add(formId);
        jParas.add(flag);
        jParas.add(content);

        HttpURLConnection connectionInvoke = initUrlConn(sUrl, jParas,POST_K3ClOUDRL);

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                connectionInvoke.getInputStream(),"utf-8"));

        String line;
        System.out.println(" ============================= ");
        System.out.println(" Contents of post request ");
        System.out.println(" ============================= ");
        while ((line = reader.readLine()) != null) {
            String sResult = new String(line.getBytes());
            stringBuffer.append(sResult);
        }
        System.out.println(stringBuffer);
        System.out.println(" ============================= ");
        System.out.println(" Contents of post request ends ");
        System.out.println(" ============================= ");
        reader.close();

        connectionInvoke.disconnect();
        return stringBuffer;
    }

    public static StringBuffer ExcuteOperation(String formId, String flag, String content,String POST_K3ClOUDRL)
            throws Exception {
        return Invoke3("ExcuteOperation", formId, flag, content,POST_K3ClOUDRL);
    }
    // Save
    public static StringBuffer Save(String formId, String content,String POST_K3ClOUDRL) throws Exception {
        StringBuffer a=  Invoke("Save", formId, content,POST_K3ClOUDRL);
        return a;
    }

    // View
    public static StringBuffer View(String formId, String content,String POST_K3ClOUDRL) throws Exception {
       StringBuffer a= Invoke("View", formId, content, POST_K3ClOUDRL);
       return a;
    }
    // View
    public static String ExecuteBillQuery(String formId, String content,String POST_K3ClOUDRL) throws Exception {
       String a= Invoke2("ExecuteBillQuery", formId, content, POST_K3ClOUDRL);
        return a;
    }
    // Submit
    public static void Submit(String formId, String content,String POST_K3ClOUDRL) throws Exception {
        Invoke("Submit", formId, content, POST_K3ClOUDRL);
    }
    // View
    public static String confirm( String content,String POST_K3ClOUDRL) throws Exception {
        String a= Invoke2("confirm","", content, POST_K3ClOUDRL);
        return a;
    }
    // Audit
    public static StringBuffer Audit(String formId, String content,String POST_K3ClOUDRL) throws Exception {
       return Invoke("Audit", formId, content, POST_K3ClOUDRL);
    }

    // UnAudit
    public static StringBuffer UnAudit(String formId, String content,String POST_K3ClOUDRL) throws Exception {
        return Invoke("UnAudit", formId, content, POST_K3ClOUDRL);
    }

    // StatusConvert
    public static void StatusConvert(String formId, String content,String POST_K3ClOUDRL)
            throws Exception {
        Invoke("StatusConvert", formId, content, POST_K3ClOUDRL);
    }
    // 供应商
    public static String saveSupplierInfo( String content,String POST_K3ClOUDRL) throws Exception {
        String a= Invoke2("saveSupplierInfo", "", content, POST_K3ClOUDRL);
        return a;
    }

    /**
     * @desc 值过滤器
     * @author AlbertFeng
     * @date 2018-08-16 10:28
     */
    private static ValueFilter filter = new ValueFilter() {
        @Override
        public Object process(Object obj, String s, Object v) {
            if (v == null)
                return "";
            return v;
        }
    };

    /**
     * @desc: sql查询K3数据
     * @author: AlbertFeng
     * @date: 2018-08-16 11:40
     * @param: [serverName, parameter]
     * @return: net.sf.json.JSONArray
     */
    public static JSONArray Execute(String serverName, Object parameter,String POST_K3ClOUDRL) throws Exception {
        String sUrl = map.get(serverName);
        JSONArray jParas = new JSONArray();
        jParas.add(parameter);

        HttpURLConnection connectionInvoke = initUrlConn(sUrl, jParas,POST_K3ClOUDRL);

        BufferedReader streamReader = new BufferedReader(new InputStreamReader(
                connectionInvoke.getInputStream(),"UTF-8"));
        StringBuffer strBuffer = new StringBuffer();

        String inputStr;
        while ((inputStr = streamReader.readLine()) != null)
            strBuffer.append(inputStr);
        streamReader.close();
        //TODO:需要把数据中为null的值替换成空字符串
        JSONArray jsonArray = JSONArray.fromObject(strBuffer.toString().replace("null","''"));
        connectionInvoke.disconnect();
        return jsonArray;
    }
    //调用k3接口没有formid并且把返回值为null改成''
    private static String Invoke2(String deal, String formId, String content,String POST_K3ClOUDRL)
            throws Exception {
        String rst=new String();
        String sUrl = map.get(deal).toString();
        JSONArray jParas = new JSONArray();
        jParas.add(content);

        HttpURLConnection connectionInvoke = initUrlConn(sUrl, jParas,POST_K3ClOUDRL);

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                connectionInvoke.getInputStream(),"UTF-8"));

        String line;
        System.out.println(" ============================= ");
        System.out.println(" Contents of post request ");
        System.out.println(" ============================= ");
        while ((line = reader.readLine()) != null) {
            String sResult = new String(line.getBytes());

            List<String> rst1=new ArrayList<>();
            String [] list=sResult.split(",");
            list[0]=list[0].replace("[[","");
            list[list.length-1]=list[list.length-1].replace("]]","");

            for (int i=0;i<list.length;i++){
                if ("null".equals(list[i])){
                    list[i]="''";
                }
//                if (i==list.length-1){
//                   System.out.println( list[i]);
//                }
                rst1.add(list[i]);
            }
            sResult="["+rst1.toString()+"]";
            rst+=sResult;
        }
        System.out.println(" ============================= ");
        System.out.println(" Contents of post request ends ");
        System.out.println(" ============================= ");
        reader.close();

        connectionInvoke.disconnect();
        return rst;
    }



    //正常调用金蝶云接口
    private static StringBuffer Invoke(String deal, String formId, String content,String POST_K3ClOUDRL)
            throws Exception {
        StringBuffer stringBuffer=new StringBuffer();
        String sUrl = map.get(deal).toString();
        JSONArray jParas = new JSONArray();
        jParas.add(formId);
        jParas.add(content);

        HttpURLConnection connectionInvoke = initUrlConn(sUrl, jParas,POST_K3ClOUDRL);

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                connectionInvoke.getInputStream(),"utf-8"));

        String line;
        System.out.println(" ============================= ");
        System.out.println(" Contents of post request ");
        System.out.println(" ============================= ");
        while ((line = reader.readLine()) != null) {
            String sResult = new String(line.getBytes());
            stringBuffer.append(sResult);
        }
        System.out.println(stringBuffer);
        System.out.println(" ============================= ");
        System.out.println(" Contents of post request ends ");
        System.out.println(" ============================= ");
        reader.close();

        connectionInvoke.disconnect();
        return stringBuffer;
    }

    /**
     * 把中文转成Unicode码
     *
     * @param str
     * @return
     */
    public static String chinaToUnicode(String str) {
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            int chr1 = (char) str.charAt(i);
            if (chr1 >= 19968 && chr1 <= 171941) {// 汉字范围 \u4e00-\u9fa5 (中文)
                result += "\\u" + Integer.toHexString(chr1);
            } else {
                result += str.charAt(i);
            }
        }
        return result;
    }
    //没有formId
    private static StringBuffer Invoke(String deal,Object content,String POST_K3ClOUDRLS)
            throws Exception {
        StringBuffer stringBuffer=new StringBuffer();
        String sUrl = map.get(deal).toString();
        JSONArray jParas = new JSONArray();
        jParas.add(content);

        HttpURLConnection connectionInvoke = initUrlConn(sUrl, jParas, POST_K3ClOUDRLS);
        System.out.println(sUrl);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                connectionInvoke.getInputStream(),"utf-8"));

        String line;
        System.out.println(" ============================= ");
        System.out.println(" Contents of post request ");
        System.out.println(" ============================= ");
        while ((line = reader.readLine()) != null) {
            String sResult = new String(line.getBytes());
            stringBuffer.append(sResult);
        }
        System.out.println(stringBuffer);
        System.out.println(" ============================= ");
        System.out.println(" Contents of post request ends ");
        System.out.println(" ============================= ");
        reader.close();

        connectionInvoke.disconnect();
        return stringBuffer;
    }


    // Login
    public static String LoginInfo(String dbId, String user, String pwd, int lang, String POST_K3ClOUDRL)
            throws Exception {

        String sResult="";

        String sUrl = "Kingdee.BOS.WebApi.ServicesStub.AuthService.ValidateUser.common.kdsvc";

        JSONArray jParas = new JSONArray();
        jParas.add(dbId);// 帐套Id
        jParas.add(user);// 用户名
        jParas.add(pwd);// 密码
        jParas.add(lang);// 语言

        HttpURLConnection connection = initUrlConn(sUrl, jParas,POST_K3ClOUDRL);
        // 获取Cookie
        String key = null;
        for (int i = 1; (key = connection.getHeaderFieldKey(i)) != null; i++) {
            if (key.equalsIgnoreCase("Set-Cookie")) {
                String tempCookieVal = connection.getHeaderField(i);
                if (tempCookieVal.startsWith("kdservice-sessionid")) {
                    CookieVal = tempCookieVal;
                    break;
                }
            }
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        String line;
        System.out.println(" ============================= ");
        System.out.println(" Contents of post request ");
        System.out.println(" ============================= ");
        while ((line = reader.readLine()) != null) {
            sResult= new String(line.getBytes(), "utf-8");
            System.out.println(sResult);
        }
        reader.close();

        connection.disconnect();
        return sResult;
    }
}
