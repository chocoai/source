package com.jeesite.modules.fgc.web;

import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.util.RedisHelp;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.fgc.dao.UserDataInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.jeesite.modules.asset.util.result.ReturnDate;

@Controller
@RequestMapping(value = "${adminPath}/fgc/Gettoken")
public class GetIshavetokenController extends BaseController {
    @RequestMapping(value ="GetIshavetoken")
    @ResponseBody
    public ReturnInfo GetIshavetoken(String token){
        UserDataInfo json=RedisHelp.redisHelp.getfgcUserInfo(token);
        if (json==null){
            return ReturnDate.error(10005,"token过期");
        }
        return ReturnDate.success(0,"请求成功","");
    }
}
