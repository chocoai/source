package com.jeesite.modules.asset.wechat.web;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.codec.DesUtils;
import com.jeesite.common.config.Global;
import com.jeesite.common.web.BaseController;
import com.jeesite.common.web.http.HttpClientUtils;
import com.jeesite.modules.asset.department.entity.DepartmentTok3;
import com.jeesite.modules.asset.k3webapi.InvokeHelper;
import com.jeesite.modules.asset.k3webapi.K3connection;
import com.jeesite.modules.asset.util.ParamentUntil;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.asset.util.service.AmUtilService;
import com.jeesite.modules.asset.wechat.dao.CheckRoleDao;
import com.jeesite.modules.asset.wechat.entity.WechatK3SysUser;
import com.jeesite.modules.asset.wechat.entity.WechatK3User;
import com.jeesite.modules.asset.wechat.service.WechatK3SysUserService;
import com.jeesite.modules.asset.wechat.service.WechatK3UserService;
import com.jeesite.modules.asset.wechat.util.FgcLogUtil;
import com.jeesite.modules.sys.entity.DictData;
import com.jeesite.modules.sys.entity.Office;
import com.jeesite.modules.sys.entity.Role;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.utils.UserUtils;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 类描述
 *
 * @author Jace Xiong
 */
@Controller
@RequestMapping("${adminPath}/wechatFgc")
public class WechatLoginController extends BaseController {
    @Autowired
    private AmUtilService amUtilService;
    @Autowired
    private WechatK3UserService wechatK3UserService;
    @Autowired
    private WxMaService wxService;
    @Autowired
    private CheckRoleDao checkRoleDao;
    @Value("${SYSNOTIFICATION}")
    String SYSNOTIFICATION;  //登录端口
    @Autowired
    private K3connection k3connection;
    @Value("${POST_K3ClOUDRL}")
    private String POST_K3ClOUDRL;  //测试库
    //账套id
    @Value("${dbId}")
    private String dbId;
    private String pwd;
    @Value("${lang}")
    private int lang;
    /**
     * 登录提交信息安全Key，加密用户名、密码、验证码，后再提交（key设置为3个，用逗号分隔）
     */
    private static final String LOGINSUBMIT = "shiro.loginSubmit.secretKey";


    /**
     * 创建系统用户
     */
//    @RequestMapping("sysUser")
    public static String createSysUser(String openId) {
        Map<String, String> sysUser = new HashMap<>(20);
        sysUser.put("op", "add");
        sysUser.put("loginCode", openId);
        sysUser.put("userName", openId);
        sysUser.put("userType", "employee");
        sysUser.put("employee.office.officeCode", "1");
        sysUser.put("employee.office.officeName", "IT发开部");
        Map<String, String> sysLogin = new HashMap<>(20);
        sysLogin.put("loginCode", "system");
        sysLogin.put("passWord", "admin");
        String infologin = HttpClientUtils.ajaxPost("http://am.frp.uvanart.com:9200/guide/login", sysLogin);
        JSONObject jsonObject = JSONObject.parseObject(infologin);
        JSONObject message = (JSONObject) jsonObject.get("message");
        String session = (String) message.get("sessionid");
        String info = HttpClientUtils.ajaxPost("http://am.frp.uvanart.com:9200/a/sys/empUser/save?__sid=" + session, sysUser);
        System.out.println("================" + info);
        JSONObject json = JSON.parseObject(info);
        String result = json.get("result").toString();
        System.out.println(result);
        return result;
    }


    /**
     * K3登录接口
     */
    @RequestMapping(value = "k3Login", method = RequestMethod.POST)
    public Map k3Login(String uid, String pwd) {
        Map<String, Object> map = new HashMap<>(20);
        String dbId = "5ab0d51363d36b";
//        String uid= "denise";
//        String pwd = "uuuu.6668";
        int lang = 2052;
        String postUrl = "http://59.38.255.210:33881/k3cloud/";
        try {
            boolean result = InvokeHelper.Login(dbId, uid, pwd, lang, postUrl);
            if (result) {
                map.put("code", 200);
                map.put("info", "响应成功");
                map.put("data", "登录成功！");
                return map;
            }
            map.put("code", 403);
            map.put("info", "响应失败！");
            map.put("data", "登录失败！");
            return map;
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            Map<String, Object> mapError = new HashMap<>(20);
            mapError.put("info", "failure");
            mapError.put("code", 500);
            mapError.put("data", sw.toString());
            return map;
        }
    }

    @RequestMapping(value = "sysLoginFgc")
    @ResponseBody
    public ReturnInfo sysLoginFgc(String loginCode, String passWord, String openId) throws Exception {
        if (loginCode == null || passWord == null) {
            FgcLogUtil.insertLog(openId,loginCode,"","/a/wechatFgc/sysLoginFgc","优梵信息平台登录","登录失败，用户名或密码为空");
            return ReturnDate.error(901, "登录失败，用户名或密码为空");
        }
        if (!ParamentUntil.isBackString(openId)) {
            FgcLogUtil.insertLog(openId,loginCode,"","/a/wechatFgc/sysLoginFgc","优梵信息平台登录","参数错误，openId为空");
            return ReturnDate.error(900, "参数错误，openId为空");
        }
        WxMaJscode2SessionResult session = null;
        WechatK3User wechatK3User = null;
        String url = "";
//        boolean isLoginSys=false;
        //获得access token
//        session = this.wxService.getUserService().getSessionInfo(wxCode);

//        String openId = "12315";
//        if (session != null) {
//            String openId = session.getOpenid();
            String secretKey = Global.getConfig(LOGINSUBMIT);
            String username = DesUtils.encode(loginCode, secretKey);
            String password = DesUtils.encode(passWord, secretKey);
            wechatK3User = wechatK3UserService.getWechatK3UserByOenId(openId); //是否微信用户信息
            if (wechatK3User == null) {                 //不存在，新增
                FgcLogUtil.insertLog(openId,loginCode,"","/a/wechatFgc/sysLoginFgc","优梵信息平台登录","微信用户不存在");
                return ReturnDate.error(-3, "微信用户不存在");
            }
            if ((!ParamentUntil.isBackString(wechatK3User.getSysLoginCode())) && (!ParamentUntil.isBackString(wechatK3User.getSysLoginPas()))) {
                wechatK3User.setSysLoginCode(username);
                wechatK3User.setSysLoginPas(password);
                wechatK3UserService.save(wechatK3User);
            }

            url = SYSNOTIFICATION + "/a/login?__login=true&__ajax=json&username=" + username + "&password=" + password + "";             //保存完后的登录系统用户
            String info = HttpClientUtils.ajaxGet(url);
            if (ParamentUntil.isBackString(info)) {
                JSONObject json = JSON.parseObject(info);
                String result = json.get("result").toString();
                String sessionId = json.get("sessionid").toString();
                if ("true".equals(result)) {
                    wechatK3User.setSysLoginCode(username);
                    wechatK3User.setSysLoginPas(password);
                    wechatK3UserService.save(wechatK3User);
                    Map<String, String> dictDatasMap = null;
                    //登陆成功查出字典
                    List<DictData> dictDatas = amUtilService.findDictLabels("K3_login_address");
                    if(!ParamentUntil.isBackList(dictDatas)){
                        FgcLogUtil.insertLog(openId,loginCode,"","/a/wechatFgc/sysLoginFgc","优梵信息平台登录","登录失败,该用户没有对应的字典");
                        return ReturnDate.error(-1, "登录失败,该用户没有对应的字典");
                    }
                    if (ParamentUntil.isBackList(dictDatas)) {
                        dictDatasMap = new HashMap();
                        for (int i = 0; i < dictDatas.size(); i++) {
                            String key=checkRoleDao.getKeyByCode(dictDatas.get(i).getDictCode());
                            dictDatasMap.put(key, dictDatas.get(i).getDictValue());
                        }
                    }
                    //查处角色集合，多个取第一个
                    List<Role> roles =checkRoleDao.getRoleByLoginCode(loginCode);
                    //过滤除了字典以外的角色
                    roles = prossesRoles(dictDatasMap,roles);
                    if (!ParamentUntil.isBackList(roles)){
                        FgcLogUtil.insertLog(openId,loginCode,"","/a/wechatFgc/sysLoginFgc","优梵信息平台登录","登录失败,该用户没有对应的角色");
                        return ReturnDate.error(-1, "登录失败,该用户没有对应的角色");
                    }
                    //多个角色只取第一个.得到主页地址
                    String rolesName=roles.get(0).getRoleName();
                    String K3_login_address = "";
                    if (dictDatasMap.get(roles.get(0).getRoleName()) != null) {
                        K3_login_address = dictDatasMap.get(rolesName);
                    }
                    //检查角色是否为供应商员工
//                    User sysUser = checkRoleDao.checkRole(loginCode);
                    Map map = new HashMap();
                    if (rolesName.contains("供应商")) {

                        //查找用户对应的部门，
                        Office office=checkRoleDao.getOfficeByLoginCode(loginCode);
                        if (office!=null){
                        DepartmentTok3 departmentTok3=checkRoleDao.getDepartmentTok3ByOfficeCode(office.getOfficeCode());
                        if (departmentTok3!=null){
                            String k3_username=departmentTok3.getK3Account();
                            String k3_password=departmentTok3.getK3Password();
                            wechatK3User.setK3Username(k3_username);
                            wechatK3User.setK3Password(k3_password);
                        }
                        }
                        wechatK3UserService.save(wechatK3User);
                        map.put("sessionId", sessionId);
                        map.put("K3_login_address", K3_login_address);
                        FgcLogUtil.insertLog(openId,loginCode,"","/a/wechatFgc/sysLoginFgc","优梵信息平台登录","登录成功");
                        return ReturnDate.success(2, "登录成功", map);
                    } else {
                        //判断k3账号是否存在
                        if (ParamentUntil.isBackString(wechatK3User.getK3Username()) && ParamentUntil.isBackString(wechatK3User.getK3Password())) {
                            //登录k3接口
                            String LoginInfo = InvokeHelper.LoginInfo(dbId, wechatK3User.getK3Username(), wechatK3User.getK3Password(), lang, POST_K3ClOUDRL);
                            boolean bResult = LoginInfo.contains("\"LoginResultType\":1");
                            if (ParamentUntil.isBackString(LoginInfo) && bResult) {
                                String Context = com.alibaba.fastjson.JSONObject.parseObject(LoginInfo).get("Context").toString();
                                String dBid = com.alibaba.fastjson.JSONObject.parseObject(Context).get("DBid").toString();
                                String userId = com.alibaba.fastjson.JSONObject.parseObject(Context).get("UserId").toString();
                                String userName = com.alibaba.fastjson.JSONObject.parseObject(Context).get("UserName").toString();
                                String customName = com.alibaba.fastjson.JSONObject.parseObject(Context).get("CustomName").toString();
                                String dataCenterName = com.alibaba.fastjson.JSONObject.parseObject(Context).get("DataCenterName").toString();
                                //登录成功更新k3账号密码
                                wechatK3User.setK3Username(wechatK3User.getK3Username());
                                wechatK3User.setK3Password(wechatK3User.getK3Password());
                                wechatK3User.setK3UserId(userId);
                                wechatK3User.setK3DbId(dBid);
                                wechatK3User.setK3rsUserName(userName);
                                wechatK3User.setK3DataCenterName(dataCenterName);
                                wechatK3User.setK3CustoName(customName);
                                wechatK3UserService.save(wechatK3User);
                                map.put("sessionId", sessionId);
                                map.put("K3_login_address", K3_login_address);
                                FgcLogUtil.insertLog(openId,loginCode,wechatK3User.getK3Username(),"/a/wechatFgc/sysLoginFgc","k3登录","K3登录成功");
                                return ReturnDate.success(3, "K3登录成功", map);
                            } else {
                                FgcLogUtil.insertLog(openId,loginCode,wechatK3User.getK3Username(),"/a/wechatFgc/sysLoginFgc","k3登录","K3登录失败");
                                return ReturnDate.error(-2, "k3登录失败,k3用户名密码错误，请重新输入",openId);
                            }

                        } else {
                            //k3账号不存在返回k3登录
                            FgcLogUtil.insertLog(openId,loginCode,wechatK3User.getK3Username(),"/a/wechatFgc/sysLoginFgc","k3登录","返回k3登录页面");
                            return ReturnDate.success(1, "返回k3登录页面", openId);
                        }
                    }
                } else {
                    FgcLogUtil.insertLog(openId,loginCode,wechatK3User.getK3Username(),"/a/wechatFgc/sysLoginFgc","优梵信息平台登录","系统用户登录失败");
                    return ReturnDate.error(-1, "系统用户登录失败");
                }
            }
        FgcLogUtil.insertLog(openId,loginCode,wechatK3User.getK3Username(),"/a/wechatFgc/sysLoginFgc","微信登录","微信登录失败");
        return ReturnDate.error(-3, "微信登录失败");
    }


    private List<Role> prossesRoles(Map<String,String> dictDatasMap,List<Role> roles) {
        List<Role> roleList=new ArrayList<>();
            for(int i=0;i<roles.size();i++){
                String roleName=roles.get(i).getRoleName();
                String url=dictDatasMap.get(roleName);
                if (ParamentUntil.isBackString(url)){
                    roleList.add(roles.get(i));
                }
            }
            return roleList;

    }


    @RequestMapping(value = "K3LoginFgc")
    @ResponseBody
    public ReturnInfo K3LoginFgc(String loginCode, String passWord,String openId,String sysLoginCode) throws Exception {
        if (loginCode==null||passWord==null){
            FgcLogUtil.insertLog(openId,loginCode,"","/a/wechatFgc/K3LoginFgc","k3登录","登录失败，用户名或密码为空");
            return ReturnDate.error(901, "登录失败，用户名或密码为空");
        }
        if(!ParamentUntil.isBackString(openId)){
            FgcLogUtil.insertLog(openId,loginCode,"","/a/wechatFgc/K3LoginFgc","k3登录","参数错误，openId为空");
            return ReturnDate.error(900, "参数错误，openId为空");
        }
        if(!ParamentUntil.isBackString(sysLoginCode)){
            FgcLogUtil.insertLog(openId,loginCode,"","/a/wechatFgc/K3LoginFgc","k3登录","参数错误，sysLoginCode为空");
            return ReturnDate.error(900, "参数错误，sysLoginCode为空");
        }
            WechatK3User wechatK3User = wechatK3UserService.getWechatK3UserByOenId(openId); //是否微信用户信息
            String url = SYSNOTIFICATION + "/a/login?__login=true&__ajax=json&username=" + wechatK3User.getSysLoginCode() + "&password=" + wechatK3User.getSysLoginPas() + "";             //保存完后的登录系统用户
            String info = HttpClientUtils.ajaxGet(url);
            JSONObject json = JSON.parseObject(info);
            String result = json.get("result").toString();
            String sessionId = json.get("sessionid").toString();
            if ("false".equalsIgnoreCase(result)){
                String sysuser = DesUtils.decode(wechatK3User.getSysLoginCode(),Global.getConfig(LOGINSUBMIT));
                FgcLogUtil.insertLog(openId,sysuser,"","/a/wechatFgc/K3LoginFgc","k3登录","系统用户名密码不正确");
                return ReturnDate.error(901, "系统用户名密码不正确");
            }
            Map map=new HashMap();
        Map<String, String> dictDatasMap = null;
        //登陆成功查出字典
        List<DictData> dictDatas = amUtilService.findDictLabels("K3_login_address");
        if(!ParamentUntil.isBackList(dictDatas)){
            String sysuser = DesUtils.decode(wechatK3User.getSysLoginCode(),Global.getConfig(LOGINSUBMIT));
            FgcLogUtil.insertLog(openId,sysuser,"","/a/wechatFgc/K3LoginFgc","k3登录","登录失败,该用户没有对应的字典");
            return ReturnDate.error(-1, "登录失败,该用户没有对应的字典");
        }
        if (ParamentUntil.isBackList(dictDatas)) {
            dictDatasMap = new HashMap();
            for (int i = 0; i < dictDatas.size(); i++) {
                String key=checkRoleDao.getKeyByCode(dictDatas.get(i).getDictCode());
                dictDatasMap.put(key, dictDatas.get(i).getDictValue());
            }
        }
        //查处角色集合，多个取第一个
        List<Role> roles =checkRoleDao.getRoleByLoginCode(sysLoginCode);
        //过滤除了字典以外的角色
        roles = prossesRoles(dictDatasMap,roles);
        if (!ParamentUntil.isBackList(roles)){
            String sysuser = DesUtils.decode(wechatK3User.getSysLoginCode(),Global.getConfig(LOGINSUBMIT));
            FgcLogUtil.insertLog(openId,sysuser,"","/a/wechatFgc/K3LoginFgc","k3登录","登录失败,该用户没有对应的角色");
            return ReturnDate.error(-1, "登录失败,该用户没有对应的角色");
        }
        //多个角色只取第一个.得到主页地址
        String rolesName=roles.get(0).getRoleName();
        String K3_login_address = "";
        if (dictDatasMap.get(roles.get(0).getRoleName()) != null) {
            K3_login_address = dictDatasMap.get(rolesName);
        }


            String LoginInfo = InvokeHelper.LoginInfo(dbId, loginCode, passWord, lang, POST_K3ClOUDRL);
            boolean bResult = LoginInfo.contains("\"LoginResultType\":1");
            if (ParamentUntil.isBackString(LoginInfo) && bResult &&("true".equals(result))) {
                String Context = com.alibaba.fastjson.JSONObject.parseObject(LoginInfo).get("Context").toString();
                String dBid = com.alibaba.fastjson.JSONObject.parseObject(Context).get("DBid").toString();
                String userId = com.alibaba.fastjson.JSONObject.parseObject(Context).get("UserId").toString();
                String userName = com.alibaba.fastjson.JSONObject.parseObject(Context).get("UserName").toString();
                String customName = com.alibaba.fastjson.JSONObject.parseObject(Context).get("CustomName").toString();
                String dataCenterName = com.alibaba.fastjson.JSONObject.parseObject(Context).get("DataCenterName").toString();
                //登录成功更新k3账号密码
                wechatK3User.setK3Username(loginCode);
                wechatK3User.setK3Password(passWord);
                wechatK3User.setK3UserId(userId);
                wechatK3User.setK3DbId(dBid);
                wechatK3User.setK3rsUserName(userName);
                wechatK3User.setK3DataCenterName(dataCenterName);
                wechatK3User.setK3CustoName(customName);
                wechatK3UserService.save(wechatK3User);
                map.put("sessionId", sessionId);
                map.put("K3_login_address", K3_login_address);
                String sysuser = DesUtils.decode(wechatK3User.getSysLoginCode(),Global.getConfig(LOGINSUBMIT));
                FgcLogUtil.insertLog(openId,sysuser,wechatK3User.getK3Username(),"/a/wechatFgc/K3LoginFgc","k3登录","登录成功");
                return ReturnDate.success(200, "登录成功", map);
            } else {
                String sysuser = DesUtils.decode(wechatK3User.getSysLoginCode(),Global.getConfig(LOGINSUBMIT));
                FgcLogUtil.insertLog(openId,sysuser,wechatK3User.getK3Username(),"/a/wechatFgc/K3LoginFgc","k3登录","k3登录失败");
                return ReturnDate.error(901, "k3登录失败");
            }
    }


    @RequestMapping(value = "logOut")
    @ResponseBody
    public ReturnInfo logOut(String openId) throws Exception {
        if(!ParamentUntil.isBackString(openId)){
            return ReturnDate.error(900, "参数错误，openId为空");
        }
        WechatK3User wechatK3User = wechatK3UserService.getWechatK3UserByOenId(openId); //是否微信用户信息
        if (wechatK3User==null){
            return ReturnDate.error(500, "系统内部错误，微信用户不存在");
        }
        wechatK3User.setK3Password("");
        wechatK3User.setK3Username("");
        wechatK3User.setSysLoginCode("");
        wechatK3User.setSysLoginPas("");
        String secretKey = Global.getConfig("shiro.loginSubmit.secretKey");
        String sysuser = DesUtils.decode(wechatK3User.getSysLoginCode(),secretKey);
        wechatK3UserService.save(wechatK3User);
        FgcLogUtil.insertLog(openId,sysuser,"","/fgc/wechatLogin","微信登录","微信登录成功");
        return ReturnDate.success( "注销成功");
    }


}
