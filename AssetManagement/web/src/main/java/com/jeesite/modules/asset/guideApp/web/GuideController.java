package com.jeesite.modules.asset.guideApp.web;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.codec.DesUtils;
import com.jeesite.common.config.Global;
import com.jeesite.common.web.BaseController;
import com.jeesite.common.web.http.HttpClientUtils;
import com.jeesite.modules.asset.guideApp.entity.AddressConfig;
import com.jeesite.modules.asset.guideApp.service.GuideService;
import com.jeesite.modules.asset.wechat.config.WxMaProperties;
import com.jeesite.modules.sys.entity.User;

import com.jeesite.modules.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "guide")
public class GuideController extends BaseController {
    @Autowired
    private AddressConfig addressConfig;
    @Autowired
    private GuideService guideService;
    /**
     * 登录提交信息安全Key，加密用户名、密码、验证码，后再提交（key设置为3个，用逗号分隔）
     */
    private static final String LOGINSUBMIT = "shiro.loginSubmit.secretKey";

    /**
     * 登录接口
     * @param loginCode
     * @param passWord
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Map login(String loginCode, String passWord, HttpServletRequest request) {

        Map<String,Object> map = new HashMap<>(20);
        User user = guideService.getLoginCode(loginCode);
        if (user == null) {
            map.put("code","901");
            map.put("msg","账号不存在，请联系管理员!");
            map.put("data","");
            return map;
        } else {
            if (!"0".equals(user.getStatus())) {
                map.put("code","903");
                map.put("msg","账号禁用，请联系管理员!");
                map.put("data","");
                return map;
            } else {
               boolean isLogin = UserService.validatePassword(passWord, user.getPassword());
               if (!isLogin) {
                   map.put("code","901");
                   map.put("msg","密码校验错误!");
                   map.put("data","");
                   return map;
               }
            }
        }
        String secretKey = Global.getConfig(LOGINSUBMIT);
        String username = DesUtils.encode(loginCode, secretKey);
        String password = DesUtils.encode(passWord, secretKey);
        // 访问域名前缀
//        StringBuffer domainUrl = request.getRequestURL();
//        String contextPath = request.getContextPath();
//        String tempContextUrl = domainUrl.delete(domainUrl.length() - request.getRequestURI().length(), domainUrl.length()).toString();
//        String url = "http://am.frp.uvanart.com:9200/a/login?__login=true&__ajax=json&username="+username+"&password="+password+"";
        String url = addressConfig.getAddress() +  "/a/login?__login=true&__ajax=json&username="+username+"&password="+password+"";
        String info = HttpClientUtils.ajaxGet(url);
        JSONObject json = JSONObject.parseObject(info);
        String result = (String) json.get("result");
        if("false".equals(result)){
            map.put("code","901");
            map.put("msg","登录失败");
            map.put("data",json);
            return map;
        }
        map.put("code","200");
        map.put("msg","登录成功");
        map.put("data",json);
        return map;
    }
}
