package com.jeesite.modules.storevideo.ovopark.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jeesite.common.collect.MapUtils;
import com.jeesite.modules.storevideo.ovopark.entity.*;
import com.jeesite.modules.util.ObjectUtils;
import com.ovopark.gw.GwInitRequestHandler;
import com.ovopark.utils.OvoParkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApiService {


    private final static String orgid = "118";
    private final static String apigwUrl="http://openapi.ovopark.com/m.api";
    private final static String _akey="S107-00000066";
    private final static String _asid="3bbe816ba91c6026747818fd4624b14c";

    private final static TypeReference<BaseResponse<List<SvOvoparkFaceGroup>>> TYPE_GROUP_LIST = new TypeReference<BaseResponse<List<SvOvoparkFaceGroup>>>(){};
    private final static TypeReference<BaseResponse<SvOvoparkFaceGroup>> TYPE_GROUP = new TypeReference<BaseResponse<SvOvoparkFaceGroup>>(){};
    private final static TypeReference<BaseResponse<List<SvOvoparkDevice>>> TYPE_DEVICE_LIST = new TypeReference<BaseResponse<List<SvOvoparkDevice>>>(){};
    private final static TypeReference<BaseResponse<SvOvoparkDevice>> TYPE_DEVICE = new TypeReference<BaseResponse<SvOvoparkDevice>>(){};

    private static Logger log = LoggerFactory.getLogger(ApiService.class);


    public static final Long orgidLong = Long.valueOf(orgid);


    /**
     * 查询人脸分组
     * @return 分组列表
     */
    public static List<SvOvoparkFaceGroup> getGroups(){

        GwInitRequestHandler reqHandler = getRequestParameters("open.face.queryGroup", null);

        String responseContent = getResponseContent(reqHandler);
        if(responseContent != null){
            BaseResponse<List<SvOvoparkFaceGroup>> result = JSONObject.parseObject(responseContent, TYPE_GROUP_LIST);
            if(result != null && result.getStat().getCode().equals(0)){
                return result.getData();
            }
        }
        return null;
    }

    /**
     * 添加人脸分组
     * @param groupData
     * @return
     */
    public static SvOvoparkFaceGroup addGroup(Map groupData){
        GwInitRequestHandler reqHandler = getRequestParameters("open.face.addGroup", groupData);
        String responseContent = getResponseContent(reqHandler);
        BaseResponse<SvOvoparkFaceGroup> tt = JSONObject.parseObject(responseContent, TYPE_GROUP);
        return tt == null || !tt.getStat().getCode().equals(0) ? null : tt.getData();
    }

    /**
     * 查询人脸设备
     * @return 列表
     */
    public static List<SvOvoparkDevice> getDevices(){

        GwInitRequestHandler reqHandler = getRequestParameters("open.face.queryDevice", null);

        String responseContent = getResponseContent(reqHandler);
        if(responseContent != null){
            BaseResponse<List<SvOvoparkDevice>> result = JSONObject.parseObject(responseContent, TYPE_DEVICE_LIST);
            if(result != null && result.getStat().getCode().equals(0)){
                return result.getData();
            }
        }
        return null;
    }

    /**
     * 查询人脸列表
     * @return 列表
     */
    public static List<SvOvoparkUser> getUsers(Map<String, String> data){

        GwInitRequestHandler reqHandler = getRequestParameters("open.openplatform.base.getUsers", data);

        String responseContent = getResponseContent(reqHandler);
        if(responseContent != null){
            BaseResponse<List<SvOvoparkUser>> result = JSONObject.parseObject(responseContent, new TypeReference<BaseResponse<List<SvOvoparkUser>>>(){});
            if(result != null && result.getStat().getCode().equals(0)){
                return result.getData();
            }
        }
        return null;
    }

    /**
     * 绑定人脸设备
     * @param data
     * @return
     */
    public static SvOvoparkDevice bindDevice(Map data){
        GwInitRequestHandler reqHandler = getRequestParameters("open.face.bindingDevice", data);
        String responseContent = getResponseContent(reqHandler);
        BaseResponse<SvOvoparkDevice> tt = JSONObject.parseObject(responseContent, TYPE_DEVICE);
        return tt == null || !tt.getStat().getCode().equals(0) ? null : tt.getData();
    }

    /**
     * 添加用户
     * @param svOvoparkUser
     * @return
     */
    public static BaseResponse addUser(SvOvoparkUser svOvoparkUser){

        List<SvOvoparkUserDTO> list = new ArrayList<>(1);


        SvOvoparkUserDTO dto = new SvOvoparkUserDTO();
        svOvoparkUser.copyTo(dto);
        list.add(dto);

        String json = JSONObject.toJSONString(list);

        Map<String, String> map = MapUtils.newHashMap();
        //map.put("departno", svOvoparkUser.getDepartNo().toString());
        //map.put("username", svOvoparkUser.getUserName());
        //map.put("userid", svOvoparkUser.getUserId());
        //map.put("memberType", svOvoparkUser.getMemberType());
        //map.put("mobilephone", svOvoparkUser.getMobilePhone());
        //map.put("gender", svOvoparkUser.getGender());
        //map.put("thirdpicurl", svOvoparkUser.getThirdpicurl());
        //map.put("checkrepeat", svOvoparkUser.getCheckrepeat().toString());

        map.put("DataUser", json);

        GwInitRequestHandler reqHandler = getRequestParameters("open.face.addUser", map);

        //GwInitRequestHandler reqHandler = getRequestParameters("open.face.addUser", map);
        String responseContent = getResponseContent(reqHandler);
        BaseResponse tt = JSONObject.parseObject(responseContent, new TypeReference<BaseResponse>(){});
        return tt;
    }

    /**
     * 更新用户
     * @param data
     * @return
     */
    public static BaseResponse updateUser(Map data){
        GwInitRequestHandler reqHandler = getRequestParameters("open.face.updateUser", data);
        String responseContent = getResponseContent(reqHandler);
        BaseResponse tt = JSONObject.parseObject(responseContent, new TypeReference<BaseResponse>(){});
        return tt;
    }


    private static GwInitRequestHandler getRequestParameters(String method, Map<String, String> params){
        GwInitRequestHandler reqHandler = new  GwInitRequestHandler();
        reqHandler.init();
        reqHandler.setApplicationKey(_akey);
        reqHandler.setApplicationSecret(_asid);
        reqHandler.setMethod(method);//open
        reqHandler.setGateUrl(apigwUrl);
        reqHandler.setParameter("orgid", orgid);
        if(params != null){
            for (Map.Entry<String, String> entry :params.entrySet()){
                reqHandler.setParameter(entry.getKey(), entry.getValue());
            }
        }
        reqHandler.createSign();

        return reqHandler;
    }

    private static GwInitRequestHandler getRequestPojo(String method, Object params){
        GwInitRequestHandler reqHandler = new  GwInitRequestHandler();
        reqHandler.init();
        reqHandler.setApplicationKey(_akey);
        reqHandler.setApplicationSecret(_asid);
        reqHandler.setMethod(method);//open
        reqHandler.setGateUrl(apigwUrl);
        reqHandler.setParameter("orgid", orgid);
        if(ObjectUtils.isNotEmpty(params)){
            reqHandler.setPoJo(params);
        }
        reqHandler.createSign();

        return reqHandler;
    }

    private static String getResponseContent(GwInitRequestHandler reqHandler){
        return getResponseContent(reqHandler, "POST");
    }
    private static String getResponseContent(GwInitRequestHandler reqHandler, String method){
        OvoParkHttpClient httpClient = new OvoParkHttpClient();
        //获取请求带参数的url
        String requestUrl = reqHandler.getRequestURL();
        log.debug(requestUrl);
        //获取debug信息
        String debuginfo = reqHandler.getDebugInfo();
        log.debug("debuginfo:" + debuginfo);
        //设置请求内容
        httpClient.setReqContent(requestUrl);
        httpClient.setMethod(method);
        if(httpClient.call()){
            String resContent = httpClient.getResContent();
            log.debug("responseContent:" + resContent);
            return resContent;
        }
        return null;
    }

    private static <T> T getContentData(String responseContent){
        T t = null;
        try {
            t = JSONObject.parseObject(responseContent, new TypeReference<T>() {});
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return t;
    }

    public static Map<?, ?> objectToMap(Object obj) {
        if(obj == null)
            return null;

        return new org.apache.commons.beanutils.BeanMap(obj);
    }

}
