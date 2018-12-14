package com.jeesite.modules.fz.fzlogin.web;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.codec.Md5Utils;
import com.jeesite.common.web.BaseController;
import com.jeesite.common.web.http.HttpClientUtils;
import com.jeesite.modules.asset.ding.FzTask;
import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.asset.ding.service.DingDepartmentService;
import com.jeesite.modules.asset.ding.service.DingUserService;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.fz.config.IsFileter;
import com.jeesite.modules.fz.fzlogin.entity.FzNeigouLoginLog;
import com.jeesite.modules.fz.fzlogin.service.FzLoginRecordService;
import com.jeesite.modules.fz.fzlogin.service.FzLoginService;
import com.jeesite.modules.fz.utils.AuthHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping(value = "${adminPath}/fz/fzLogin")
public class FzLoginContoller extends BaseController {

    @Value("${FZ_EXPRIED_TIME}")
    Long FZ_EXPRIED_TIME;  //token过期时间
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private RedisTemplate<String, List> redisTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private AmqpTemplate rabbitTemplate;
    @Autowired
    private DingUserService dingUserService;
    @Autowired
    private DingDepartmentService dingDepartmentService;
    @Autowired
    private FzLoginRecordService fzLoginRecordService;
    @Autowired
    private FzLoginService fzLoginService;
    @Value("${SMSNOTIFICATION}")
    String SMSNOTIFICATION;  //短信验证

    private static final String userName = "uvanapi";

    private static final String password = "123456";

    public static String typeName = "梵赞";

    @Value("${file.baseDir}")
    String baseDir;
    @Value("${neigou.getUvanTokenUser}")
    private String getUvanTokenUser;
    @Value("${neigou.getUvanTokenPass}")
    private String getUvanTokenPass;

    /**
     * 登录接口
     *
     * @param ddCode
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/fzLogin", method = RequestMethod.POST)
    @ResponseBody
    public ReturnInfo fzLogin(String ddCode, HttpServletResponse response) throws Exception {
        String GET_DING_ACCESS_TOKEN_ADDRESS = "https://oapi.dingtalk.com/gettoken?corpid=dingde55314a8e20f3f6&corpsecret=YDj118xRNB5CyG_s0uWdZvvi7DOueWS9RmUN_HFiSaTjjGb9c42jCPWO-vQ1jpht";
        String result = HttpClientUtils.get(GET_DING_ACCESS_TOKEN_ADDRESS);
        net.sf.json.JSONObject jsonObject1 = net.sf.json.JSONObject.fromObject(result);
        if (jsonObject1 != null) {
            if (jsonObject1.containsKey("errcode")) {
                int count = jsonObject1.getInt("errcode");
                if (0 == count) {
                    result = jsonObject1.getString("access_token");
                }
            }
        }
        String url = "https://oapi.dingtalk.com/user/getuserinfo?access_token=" + result + "&code=" + ddCode;
        String info = HttpClientUtils.ajaxGet(url);
        JSONObject jsonObject = JSONObject.parseObject(info);
        DingUser dingUser = null;
        if (jsonObject.containsKey("userid")) {
            String userid = jsonObject.get("userid").toString();
            dingUser = dingUserService.getDingUser(userid);
            setResponse(response, dingUser);
        } else {
            logger.error(info);

            return ReturnDate.error(400, "钉钉登录失败");
        }
        return ReturnDate.success(dingUser);
    }



    private void setResponse(HttpServletResponse response, DingUser dingUser){
        response.setHeader("Access-Control-Expose-Headers", "*");
        response.setHeader("uvan_token", dingUser.getUvan_token());
    }


    /**
     * 获取签名接口
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/fzSign", method = RequestMethod.POST)
    @ResponseBody
    public ReturnInfo fzSign(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String result = null;
        try {
            result = AuthHelper.getConfig(request, baseDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnDate.success(JSONObject.parseObject(result));
    }

    /**
     * 发送短信到用户，返回验证码到前台
     *
     * @param
     * @return
     */
    @ResponseBody
//    @RequestMapping(value = "sendMsg", method = RequestMethod.GET)
    public String sendMsg(String mobile) {
        /** 登录短信验证*/
        String loginUrl = SMSNOTIFICATION.trim() + "/api/account/login?userName=" + userName + "&password=" + password;
        String resp = HttpClientUtils.ajaxGet(loginUrl);
        String msg = JSONObject.parseObject(resp).get("message").toString();
        int randomNo = (int) ((Math.random() * 9 + 1) * 100000);
        HttpClientUtils.ajaxGet(SMSNOTIFICATION.trim() + "/api/alibabaapi/SendSmsByVerifyCode?tel=" + mobile + "&code=" + randomNo + "&name=" + typeName + "&sessionKey=" + msg);
        String rst = Md5Utils.md5(String.valueOf(randomNo));
        return rst;
    }

    /**
     * 前台验证验证码通过后登录
     *
     * @param mobile
     * @param response
     * @return
     */
    @ResponseBody
//    @RequestMapping(value = "smsLogin", method = RequestMethod.GET)
    public ReturnInfo smsLogin(String mobile, HttpServletResponse response) {
        List<DingUser> dingUserList = dingUserService.getUserByMobile(mobile);
        if (dingUserList != null && dingUserList.size() > 0) {
            DingUser dingUser = dingUserList.get(0);
            dingUserService.saveToken(dingUser);
            setResponse(response, dingUser);
            return ReturnDate.success(dingUser);
        } else {
            return ReturnDate.error(400, "钉钉登录失败");
        }
    }

    @Value("${neigou.surl}")
    private String surl;
    @Value("${neigou.furl}")
    private String furl;
    @Value("${neigou.login_url}")
    private String login_url;
    /**
     * 获取login_token并且登陆
     *
     * @param userid
     * @return
     */
    @IsFileter(isFile = "true")
    @RequestMapping(value = "/fzNeigouLogin", method = RequestMethod.GET)
    public ReturnInfo fzNeigouLogin(String userid) {
        FzNeigouLoginLog fzNeigouLoginLog = new FzNeigouLoginLog();
        try { 
            fzNeigouLoginLog.setLoginTime(new Date());
            if (userid == null || "".equals(userid)) {
                return ReturnDate.success(15003, "没有userid", null);
            }
            DingUser dingUser = dingUserService.get(userid);
            fzNeigouLoginLog.setUserId(userid);
            if (dingUser == null) {
                fzNeigouLoginLog.setResult("userid错误");
                rabbitTemplate.convertAndSend(FzTask.fzNeigouLoginLogsP,fzNeigouLoginLog);
                return ReturnDate.success(15009, "userid错误", null);
            }
            String login_token = "";
            //获取login_token
            ReturnInfo info = fzLoginService.getLoginToken(userid,true);
            if(info == null){
                return null;
            } 
            Integer code = info.getCode();
            if (code == 15028) {
                fzNeigouLoginLog.setLoginSuccess("2");
                rabbitTemplate.convertAndSend(FzTask.fzNeigouLoginLogsP,fzNeigouLoginLog);
                return ReturnDate.success(code,info.getMsg(),null);
            }
            login_token = (String) info.getData();

            String neigou_login_url = login_url + "?furl=" + furl + "&surl=" + surl + "&login_token=" + login_token + "";
            fzNeigouLoginLog.setLoginSuccess("1");
            fzNeigouLoginLog.setUserName(dingUser.getName());
            rabbitTemplate.convertAndSend(FzTask.fzNeigouLoginLogsP,fzNeigouLoginLog);
            return ReturnDate.success(neigou_login_url);
        } catch (Exception e) {
            e.printStackTrace();
            fzNeigouLoginLog.setLoginSuccess("0");
            fzNeigouLoginLog.setResult(e.getMessage());
            rabbitTemplate.convertAndSend(FzTask.fzNeigouLoginLogsP,fzNeigouLoginLog);
            return ReturnDate.error(-100, "服务器忙", e.getMessage());
        }
    }

    /**
     * 获取uvan_token
     * @param getTokenUser
     * @param getTokenPass
     * @return
     */
    @RequestMapping(value = "/getUvanToken", method = RequestMethod.GET)
    public ReturnInfo getUvanToken(String getTokenUser,String getTokenPass) {
        if(getUvanTokenUser.equals(getTokenUser) || getUvanTokenPass.equals(getTokenPass)){
            String uvan_neigou_token = stringRedisTemplate.opsForValue().get("uvan_neigou_token");
            if(uvan_neigou_token == null || "".equals(uvan_neigou_token)){
                //根据uuid生产随机token,把"-"去掉,不然在跟前端交互的时候会出现问题
                uvan_neigou_token = UUID.randomUUID().toString().replaceAll("-", "");
                stringRedisTemplate.opsForValue().set("uvan_neigou_token",uvan_neigou_token,5,TimeUnit.MINUTES);
            }
            return ReturnDate.success(15031,"获取uvan_token成功",uvan_neigou_token);
        }
        return null;
    }

}