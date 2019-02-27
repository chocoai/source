package com.jeesite.modules.fgc.web;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.util.ParamentUntil;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.asset.wechat.util.FgcLogUtil;
import com.jeesite.modules.fgc.entity.FgcUser;
import com.jeesite.modules.fgc.service.FgcUserService;
import org.springframework.beans.factory.annotation.Autowired;
import com.jeesite.modules.util.redis.RedisUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "${adminPath}/fgc/loginOut")
public class FgcLoginOutController extends BaseController {
    @Autowired
    private FgcUserService fgcUserService;
    @Resource
    private RedisUtil<String, String> redisString;
    @RequestMapping(value = "logOut")
    @ResponseBody
    public ReturnInfo logOut(String token) throws Exception {
        if(!ParamentUntil.isBackString(token)){
            return ReturnDate.error(10002, "参数错误，token为空");
        }
        String openId= redisString.get("uvanfactory_user_"+token);
        if (!ParamentUntil.isBackString(openId)){
            return ReturnDate.error(10002, "缓存找不到openId");
        }
        FgcUser fgcUser = fgcUserService.getFgcUserByOpenId(openId); //是否微信用户信息
        if (fgcUser==null){
            return ReturnDate.error(10001, "系统内部错误，微信用户不存在");
        }
        fgcUser.setSysLoginCode("");
        fgcUser.setSysLoginPas("");

//		String secretKey = Global.getConfig("shiro.loginSubmit.secretKey");
        //解密
//		String sysuser = DesUtils.decode(fgcUser.getSysLoginCode(),secretKey);
        fgcUserService.save(fgcUser);
        //删除缓存
        String dataJson = redisString.get("uvanfactory_user_" + openId);
        if (dataJson != null && dataJson.length() > 0) {
            JSONObject json1 = JSONObject.parseObject(dataJson);
            redisString.delete("uvanfactory_user_" + json1.get("token"));
        }
        redisString.delete("uvanfactory_user_" + openId);

        FgcLogUtil.insertLog(openId,fgcUser.getSysLoginCode(),"","/fgc/wechatLogin","微信注销","注销成功");
        return ReturnDate.success( "注销成功");
    }
}
