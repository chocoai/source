package com.jeesite.modules.asset.ding.web;


import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.web.BaseController;
import com.jeesite.common.web.http.HttpClientUtils;
import com.jeesite.modules.asset.util.RedisHelp;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.asset.util.service.HttpHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 发送钉钉图片消息
 */
@Controller
@RequestMapping(value = "${adminPath}/fz/ding/image")
public class DingImageMessage extends BaseController {

    //private static final String AGENT_ID="5110142";
    @PostMapping(value = "sendImage")
    @ResponseBody
    public ReturnInfo sendImage(HttpServletRequest request) {
        String filepath=request.getParameter("filepath");
        String touser=request.getParameter("touser");
        String agendId=request.getParameter("agendId");
        String info="";
        Map<String,String> map=new HashMap<>();
        try {
            map= HttpHelper.imageMessage(filepath,touser,agendId);
          if(map.containsKey("success")){
              info=map.get("success");
              return ReturnDate.success(info);
          }else {
              info=map.get("failed");
          }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnDate.error(1999,"钉钉图片信息发送失败"+info);
    }
    //发送markdown形式信息
    @PostMapping(value = "sendMarkDown")
    @ResponseBody
    public ReturnInfo sendMarkDown(HttpServletRequest request) {
        String touser=request.getParameter("touser");
        String agendId=request.getParameter("agendId");
        String title=request.getParameter("title");
        String text=request.getParameter("text");
        String info="";
        Map<String,String> map=new HashMap<>();
        try {
            map= HttpHelper.markdownMessage(touser,agendId,title,text);
            if(map.containsKey("success")){
                info=map.get("success");
                return ReturnDate.success(info);
            }else {
                info=map.get("failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnDate.error(1999,"钉钉图片信息发送失败"+info);
    }
}
