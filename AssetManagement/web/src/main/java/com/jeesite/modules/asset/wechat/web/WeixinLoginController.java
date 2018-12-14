package com.jeesite.modules.asset.wechat.web;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.asset.wechat.service.WxchatLoginService;
import com.jeesite.modules.asset.wechat.util.FgcLogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类描述
 *
 * @author Jace Xiong
 */
@RestController
@RequestMapping("fgc")
public class WeixinLoginController {
    @Autowired
    private WxchatLoginService wxchatLoginService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 微信登陆接口
     */
    @RequestMapping(value = "/wechatLogin",method = RequestMethod.POST)
    public ReturnInfo login2(@RequestBody String body) {
        JSONObject json = JSONObject.parseObject(body);
        try{
            return wxchatLoginService.login(body);
        }catch (Exception e){
            this.logger.error(e.getMessage(), e);
            String wxuser = json.get("nickname") + ",";
            FgcLogUtil.insertLog(wxuser,"","","/fgc/wechatLogin","微信登录","微信登录失败");
            return ReturnDate.error(901, "系统用户登录失败:"+e.toString());
        }
    }



//    /**
//     * 微信登陆接口
//     */
//    @RequestMapping(value = "/wechatLogin",method = RequestMethod.POST)
//    public ReturnInfo login2(@RequestBody String body) {
//        if (StringUtils.isBlank(body)) {
//            return ReturnDate.error(901, "参数为空！");
//        }
//        JSONObject json = JSONObject.parseObject(body);
//        try {
//            String code = (String) json.get("code");
//            WxMaJscode2SessionResult session = this.wxService.getUserService().getSessionInfo(code);
//            this.logger.info(session.getSessionKey());
//            this.logger.info(session.getOpenid());
//
//            WechatK3User wechatuser1 = wechatK3UserDao.getWechatK3UserByOenId(session.getOpenid());
//            if(wechatuser1==null){
//                WechatK3User wechatK3User = new WechatK3User();
//                wechatK3User.setOpenId(session.getOpenid());
//                //新增微信用户
//                wechatK3User.setCity((String) json.get("city"));
//                wechatK3User.setCountry((String) json.get("country"));
//                wechatK3User.setSex(json.get("sex").toString());
//                wechatK3User.setNickname((String) json.get("nickname"));
//                wechatK3User.setHeadimgurl((String) json.get("headimgurl"));
//                wechatK3User.setOpenId(session.getOpenid());
//                wechatK3User.setIsNewRecord(true);
//                wechatK3UserService.save(wechatK3User);
//                String wxuser = json.get("nickname") + ","+session.getOpenid();
//                FgcLogUtil.insertLog(wxuser,"","","/fgc/wechatLogin","微信登录","微信登录成功");
//                return ReturnDate.success(0, "微信登录成功，跳转系统用户登录界面！",session.getOpenid());
//            }
//            logger.info("微信用户已存在！返回登录成功");
//            String wxuser = json.get("nickname") + ","+session.getOpenid();
//            FgcLogUtil.insertLog(wxuser,"","","/fgc/wechatLogin","微信登录","微信登录成功");
//            return ReturnDate.success(0, "微信登录成功，跳转系统用户登录界面！",session.getOpenid());
//        } catch (WxErrorException e) {
//            this.logger.error(e.getMessage(), e);
//            String wxuser = json.get("nickname") + ",";
//            FgcLogUtil.insertLog(wxuser,"","","/fgc/wechatLogin","微信登录","微信登录失败");
//            return ReturnDate.error(901, "系统用户登录失败:"+e.toString());
//        }
//    }
}
