package com.jeesite.modules.asset.util.service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import com.jeesite.common.web.http.HttpClientUtils;
import com.jeesite.modules.asset.ding.entity.ActionCard;
import com.jeesite.modules.asset.ding.entity.DingMessage;
import com.jeesite.modules.asset.util.RedisHelp;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.RedirectLocations;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

//import com.alibaba.dingtalk.openapi.demo.Exception;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * http请求辅助类（钉钉）
 */
public class HttpHelper {
    private static final  String SEND_ADDRESS="https://oapi.dingtalk.com/message/send?access_token=";

    public static JSONObject httpGet(String url) throws Exception{

        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().
                setSocketTimeout(2000).setConnectTimeout(2000).build();
        httpGet.setConfig(requestConfig);

        try {
            response = httpClient.execute(httpGet, new BasicHttpContext());

            if (response.getStatusLine().getStatusCode() != 200) {

                System.out.println("request url failed, http code=" + response.getStatusLine().getStatusCode()
                        + ", url=" + url);
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String resultStr = EntityUtils.toString(entity, "utf-8");

                JSONObject result = JSON.parseObject(resultStr);
                if (result.getInteger("errcode") == 0) {
//                	result.remove("errcode");
//                	result.remove("errmsg");
                    return result;
                } else {
                    System.out.println("request url=" + url + ",return value=");
                    System.out.println(resultStr);
                    int errCode = result.getInteger("errcode");
                    String errMsg = result.getString("errmsg");
                    //throw new Exception(errCode, errMsg);
                }
            }
        } catch (IOException e) {
            System.out.println("request url=" + url + ", exception, msg=" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (response != null) try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


    public static JSONObject httpPost(String url, Object data) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().
                setSocketTimeout(2000).setConnectTimeout(2000).build();
        httpPost.setConfig(requestConfig);
        httpPost.addHeader("Content-Type", "application/json");

        try {
            StringEntity requestEntity = new StringEntity(JSON.toJSONString(data), "utf-8");
            httpPost.setEntity(requestEntity);

            response = httpClient.execute(httpPost, new BasicHttpContext());

            if (response.getStatusLine().getStatusCode() != 200) {

                System.out.println("request url failed, http code=" + response.getStatusLine().getStatusCode()
                        + ", url=" + url);
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String resultStr = EntityUtils.toString(entity, "utf-8");

                JSONObject result = JSON.parseObject(resultStr);
                if (result.getInteger("errcode") == 0) {
                    result.remove("errcode");
                    result.remove("errmsg");
                    return result;
                } else {
                    System.out.println("request url=" + url + ",return value=");
                    System.out.println(resultStr);
                    int errCode = result.getInteger("errcode");
                    String errMsg = result.getString("errmsg");
                    //throw new Exception(errCode, errMsg);
                }
            }
        } catch (IOException e) {
            System.out.println("request url=" + url + ", exception, msg=" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (response != null) try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


    public static JSONObject uploadMedia(String url, File file) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
        httpPost.setConfig(requestConfig);

        HttpEntity requestEntity = MultipartEntityBuilder.create().addPart("media",
                new FileBody(file, ContentType.APPLICATION_OCTET_STREAM, file.getName())).build();
        httpPost.setEntity(requestEntity);

        try {
            response = httpClient.execute(httpPost, new BasicHttpContext());

            if (response.getStatusLine().getStatusCode() != 200) {

                System.out.println("request url failed, http code=" + response.getStatusLine().getStatusCode()
                        + ", url=" + url);
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String resultStr = EntityUtils.toString(entity, "utf-8");

                JSONObject result = JSON.parseObject(resultStr);
                if (result.getInteger("errcode") == 0) {
                    // 成功
                    result.remove("errcode");
                    result.remove("errmsg");
                    return result;
                } else {
                    System.out.println("request url=" + url + ",return value=");
                    System.out.println(resultStr);
                    int errCode = result.getInteger("errcode");
                    String errMsg = result.getString("errmsg");
//                    throw new Exception(errCode, errMsg);
                }
            }
        } catch (IOException e) {
            System.out.println("request url=" + url + ", exception, msg=" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (response != null) try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


    public static JSONObject downloadMedia(String url, String fileDir) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
        httpGet.setConfig(requestConfig);

        try {
            HttpContext localContext = new BasicHttpContext();

            response = httpClient.execute(httpGet, localContext);

            RedirectLocations locations = (RedirectLocations) localContext.getAttribute(HttpClientContext.REDIRECT_LOCATIONS);
            if (locations != null) {
                URI downloadUrl = locations.getAll().get(0);
                String filename = downloadUrl.toURL().getFile();
                System.out.println("downloadUrl=" + downloadUrl);
                File downloadFile = new File(fileDir + File.separator + filename);
                FileUtils.writeByteArrayToFile(downloadFile, EntityUtils.toByteArray(response.getEntity()));
                JSONObject obj = new JSONObject();
                obj.put("downloadFilePath", downloadFile.getAbsolutePath());
                obj.put("httpcode", response.getStatusLine().getStatusCode());



                return obj;
            } else {
                if (response.getStatusLine().getStatusCode() != 200) {

                    System.out.println("request url failed, http code=" + response.getStatusLine().getStatusCode()
                            + ", url=" + url);
                    return null;
                }
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String resultStr = EntityUtils.toString(entity, "utf-8");

                    JSONObject result = JSON.parseObject(resultStr);
                    if (result.getInteger("errcode") == 0) {
                        // 成功
                        result.remove("errcode");
                        result.remove("errmsg");
                        return result;
                    } else {
                        System.out.println("request url=" + url + ",return value=");
                        System.out.println(resultStr);
                        int errCode = result.getInteger("errcode");
                        String errMsg = result.getString("errmsg");
//                        throw new Exception(errCode, errMsg);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("request url=" + url + ", exception, msg=" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (response != null) try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 发送钉钉图片消息
     * @param filepath 图片地址
     * @param touser 消息接收者
     * @param agendId 发出图片的应用agendid
     * @return
     * @throws Exception
     */
    public static Map<String,String> imageMessage(String filepath, String touser, String agendId) throws Exception {
       // String sendaddress="https://oapi.dingtalk.com/message/send?access_token=";
        String msgtype="image";
        String token=RedisHelp.redisHelp.getDingDingAcessToken();
        String url="https://oapi.dingtalk.com/media/upload?access_token="+token+"&type="+msgtype;
        JSONObject jsonResult=null;
        String info="";
        Map<String,String> map=new HashMap<>();
            jsonResult = uploadMedia(url,new File(filepath));
            if(jsonResult.containsKey("media_id")){
                String mediaId=jsonResult.getString("media_id");
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("touser",touser);
                jsonObject.put("agentid",agendId);
                jsonObject.put("msgtype",msgtype);
                JSONObject jsonObject1=new JSONObject();
                jsonObject1.put("media_id",mediaId);
                jsonObject.put(msgtype,jsonObject1);
                token=RedisHelp.redisHelp.getDingDingAcessToken();
                String sendurl=SEND_ADDRESS+token;
                info= HttpClientUtils.ajaxPostJson(sendurl,jsonObject.toString(),"UTF-8");
                net.sf.json.JSONObject jsonObject2= net.sf.json.JSONObject.fromObject(info);
                if(0==jsonObject2.getInt("errcode")){
                    map.put("success",info);
                }else{
                    map.put("failed",info);
                }
            }else {
                info=jsonResult.toString();
                map.put("failed",info);
            }
            return map;
    }
    public static Map<String,String> markdownMessage(String touser, String agendId,String title,String text) throws Exception {
            String msgtype="markdown";
            String info="";
            Map<String,String> map=new HashMap<>();
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("touser",touser);
            jsonObject.put("agentid",agendId);
            jsonObject.put("msgtype",msgtype);
            JSONObject jsonObject1=new JSONObject();
            jsonObject1.put("title",title);
            //"##### 测试啦啦啦\n* 图片\n![alt 啊](https://after-sales-photo.oss-cn-shanghai.aliyuncs.com/stores/after-ales-photo/data/20187306498305m.jpg)"
            jsonObject1.put("text",text);
            jsonObject.put(msgtype,jsonObject1);
            String token=RedisHelp.redisHelp.getDingDingAcessToken();
            String sendurl=SEND_ADDRESS+token;
            info= HttpClientUtils.ajaxPostJson(sendurl,jsonObject.toString(),"UTF-8");
            net.sf.json.JSONObject jsonObject2= net.sf.json.JSONObject.fromObject(info);
            if(0==jsonObject2.getInt("errcode")){
                map.put("success",info);
            }else{
                map.put("failed",info);
            }
        return map;
        }
    public static Map<String,String> actionCardMessage(String touser, String agendId,String title,String text,String singleUrl,String singleTitle) throws Exception {
        String msgtype="action_card";
        String info="";
        Map<String,String> map=new HashMap<>();
        DingMessage dingMessage=new DingMessage();
        ActionCard actionCard=new ActionCard();
        dingMessage.setMsgtype(msgtype);
        dingMessage.setAgentid(agendId);
        actionCard.setSingle_url(singleUrl);
        //接收用户
        dingMessage.setTouser(touser);
        actionCard.setTitle(title);
        actionCard.setSingle_title(singleTitle);
        actionCard.setMarkdown(text);
        dingMessage.setAction_card(actionCard);
        net.sf.json.JSONObject jsonObject= net.sf.json.JSONObject.fromObject(dingMessage);
        String token=RedisHelp.redisHelp.getDingDingAcessToken();
        String sendurl=SEND_ADDRESS+token;
        info= HttpClientUtils.ajaxPostJson(sendurl,jsonObject.toString(),"UTF-8");
        net.sf.json.JSONObject jsonObject2= net.sf.json.JSONObject.fromObject(info);
        if(0==jsonObject2.getInt("errcode")){
            map.put("success",info);
        }else{
            map.put("failed",info);
        }
        return map;
    }


}
