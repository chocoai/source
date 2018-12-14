package com.jeesite.modules.asset.wechat.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.web.http.HttpClientUtils;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.asset.wechat.dao.WechatK3UserDao;
import com.jeesite.modules.asset.wechat.entity.WechatK3User;
import com.jeesite.modules.asset.wechat.util.FgcLogUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 类描述
 *
 * @author Jace Xiong
 */
@Service
public class WxchatLoginService {
    @Autowired
    private WechatK3UserService wechatK3UserService;
    @Autowired
    private WxMaService wxService;
    @Autowired
    private WechatK3UserDao wechatK3UserDao;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 微信登陆接口
     */
    @Transactional(readOnly = false)
    public ReturnInfo login(String body) throws WxErrorException {
        if (StringUtils.isBlank(body)) {
            return ReturnDate.error(901, "参数为空！");
        }
        JSONObject json = JSONObject.parseObject(body);

        String code = (String) json.get("code");
//        WxMaJscode2SessionResult session = this.wxService.getUserService().getSessionInfo(code);
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=wx9c6eb8c57de10e0c&secret=fa48b7a3be32d55bb25a041bded54337&js_code="+code+"&grant_type=authorization_code";
        String info = HttpClientUtils.ajaxGet(url);
        JSONObject infoJson = JSONObject.parseObject(info);
        String openid = (String) infoJson.get("openid");
//        this.logger.info(session.getSessionKey());
//        this.logger.info(session.getOpenid());

        WechatK3User wechatuser1 = wechatK3UserDao.getWechatK3UserByOenId(openid);
        if(wechatuser1==null){
            WechatK3User wechatK3User = new WechatK3User();
            wechatK3User.setOpenId(openid);
            //新增微信用户
            wechatK3User.setCity((String) json.get("city"));
            wechatK3User.setCountry((String) json.get("country"));
            wechatK3User.setSex(json.get("sex").toString());
            wechatK3User.setNickname((String) json.get("nickname"));
            wechatK3User.setHeadimgurl((String) json.get("headimgurl"));
//            wechatK3User.setOpenId(session.getOpenid());
            wechatK3User.setOpenId(openid);
            wechatK3User.setIsNewRecord(true);
            wechatK3UserService.save(wechatK3User);
            String wxuser = json.get("nickname") + ","+openid;
            FgcLogUtil.insertLog(wxuser,"","","/fgc/wechatLogin","微信登录","微信登录成功");
            return ReturnDate.success(0, "微信登录成功，跳转系统用户登录界面！",openid);
        }
        logger.info("微信用户已存在！返回登录成功");
        String wxuser = json.get("nickname") + ","+openid;
        FgcLogUtil.insertLog(wxuser,"","","/fgc/wechatLogin","微信登录","微信登录成功");
        return ReturnDate.success(0, "微信登录成功，跳转系统用户登录界面！",openid);

    }
}
