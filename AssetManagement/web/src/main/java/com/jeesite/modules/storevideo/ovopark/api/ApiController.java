/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.ovopark.api;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.collect.MapUtils;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.storevideo.config.SVRabbitConfig;
import com.jeesite.modules.storevideo.member.entity.CrmMemberInfo;
import com.jeesite.modules.storevideo.member.service.CrmMemberInfoService;
import com.jeesite.modules.storevideo.ovopark.entity.SvOvoparkFaceGroup;
import com.jeesite.modules.storevideo.ovopark.entity.SvOvoparkUser;
import com.jeesite.modules.storevideo.ovopark.service.SvOvoparkUserService;
import com.jeesite.modules.storevideo.tvclient.entity.ClientLogType;
import com.jeesite.modules.storevideo.tvclient.entity.SvTvClientLog;
import com.jeesite.modules.storevideo.tvclient.service.SvTvClientLogService;
import com.jeesite.modules.storevideo.util.DetectEngine;
import com.jeesite.modules.storevideo.video.entity.*;
import com.jeesite.modules.storevideo.video.service.SvFaceDetectLogService;
import com.jeesite.modules.storevideo.video.service.SvVideoRlatService;
import com.jeesite.modules.storevideo.video.service.SvVideoService;
import com.jeesite.modules.util.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * 店铺视频Controller
 * @author Philip Guan
 * @version 2019-01-16
 */
@Controller
@RequestMapping(value = "${adminPath}/sv/api")
public class ApiController extends BaseController {

	@Autowired
	private SvVideoService svVideoService;
	@Autowired
    private SvVideoRlatService svVideoRlatService;
    @Autowired
    private SvFaceDetectLogService svFaceDetectLogService;
    @Autowired
    private SvTvClientLogService svTvClientLogService;
    @Autowired
    private SvOvoparkUserService svOvoparkUserService;
    @Autowired
    private CrmMemberInfoService crmMemberInfoService;
    @Autowired
    private DetectEngine detectEngine;
    @Autowired
    private AmqpTemplate rabbitTemplate;


    ///**
    // * 保存绩效卡
    // */
    @PostMapping(value = "detected")
    @ResponseBody
    public ReturnInfo detected(HttpServletRequest request) throws IOException {

        String responseResult = getParamString("result", request);
        String responseData = getParamString("data", request);
        SvFaceDetectLog data = JSONObject.parseObject(responseData, SvFaceDetectLog.class);

        if(data != null){
            data.setIsNewRecord(true);
            data.setResult(responseResult);
            svFaceDetectLogService.save(data);

            if((data.getResult().contains("空人脸集") || data.getResult().contains("无匹配的人脸")) && !StringUtils.isBlank(data.getWdzpicurl())){


                //发送视频到电视机
                Map param = MapUtils.newHashMap();
                param.put("age", detectEngine.getAgeLabel(data.getAge()));
                param.put("sex", data.getGender().equals("true") ? "女" : "男");
                param.put("cameraMac", data.getDevicemac());
                param.put("userId", param.get("sex").toString()+param.get("age").toString());
                //detectEngine.pushVideo(param);
                this.rabbitTemplate.convertAndSend(SVRabbitConfig.QUEUE_PUSH_VIDEO, param);

                //String faceToken = detectEngine.getFaceTokenByImageUrl(data.getWdzpicurl());
                //if(faceToken != null){
                //    //String facePlusApiUrl = "https://s2bapi.uvanart.com/xc/faceimage/GetPersonInfoBy?faceToken=" + a.getFace_token();
                //    //String result = HttpClientUtils.ajaxGet(facePlusApiUrl);
                //    //S2bPhotoDatum s2bPhotoDatum = JSONObject.parseObject(result, S2bPhotoDatum.class);
                //    System.out.println("请求梵店");
                //
                //
                //} else {
                //    logger.debug("找不到facetoken");
                //}
            } else {
                svTvClientLogService.addLog(ClientLogType.INFO, null, "图片不符合要求:"+data.getResult());
            }


        }

        return ReturnDate.success();
    }

    private String getParamString(String key, HttpServletRequest request){
        String[] results = request.getParameterValues(key);
        if(results == null) return null;
        return results[0];
    }

    @PostMapping(value = "sendMsg")
    @ResponseBody
    public ReturnInfo sendMsg(@RequestParam Map<String, String> param, HttpServletRequest request) {
        //Map param = MapUtils.newHashMap();
        //param.put("age", data.getAge());
        //param.put("sex", data.getGender().equals("true") ? "女" : "男");
        //param.put("cameraMac", data.getDevicemac());
        List<SvOvoparkFaceGroup> list = ApiService.getGroups();
        //this.rabbitTemplate.convertAndSend(SVRabbitConfig.QUEUE_PUSH_VIDEO, param);

        return ReturnDate.success();
    }

    @PostMapping(value = "regUser")
    @ResponseBody
    public ReturnInfo regUser(SvOvoparkUser svOvoparkUser, HttpServletRequest request) {

        svOvoparkUser.setOrgId(ApiService.orgidLong);
        svOvoparkUser.setCheckrepeat(1L);
        svOvoparkUser.setDepartNo(9751L);
        //svOvoparkUser.set


        CrmMemberInfo memberInfo = crmMemberInfoService.getMemberInfoByMobile(svOvoparkUser.getMobilePhone());
        if(memberInfo != null){
            svOvoparkUser.setUserName(memberInfo.getMemberName());
        }

        svOvoparkUserService.save(svOvoparkUser);

        ApiService.addUser(svOvoparkUser);


        return ReturnDate.success();
    }
}