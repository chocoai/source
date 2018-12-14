/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fgc.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.codec.DesUtils;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.common.web.http.HttpClientUtils;
import com.jeesite.modules.asset.util.ParamentUntil;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.asset.wechat.util.FgcLogUtil;
import com.jeesite.modules.fgc.dao.FgcUserDao;
import com.jeesite.modules.fgc.dao.UserDataInfo;
import com.jeesite.modules.fgc.entity.FgcUser;
import com.jeesite.modules.fgc.token.TokenProccessor;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 梵工厂用户表Service
 *
 * @author dwh
 * @version 2018-08-14
 */
@Service
@Transactional(readOnly = true)
public class FgcUserService extends CrudService<FgcUserDao, FgcUser> {
    @Autowired
    private FgcUserDao fgcUserDao;
    @Value("${FGC_EXPRIED_TIME}")
    Long FGC_EXPRIED_TIME;  //token过期时间
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Value("${SYSNOTIFICATION}")
    String SYSNOTIFICATION;  //登录端口
    /**
     * 登录提交信息安全Key，加密用户名、密码、验证码，后再提交（key设置为3个，用逗号分隔）
     */
    private static final String LOGINSUBMIT = "shiro.loginSubmit.secretKey";
    /**
     * 获取单条数据
     *
     * @param fgcUser
     * @return
     */
    @Override
    public FgcUser get(FgcUser fgcUser) {
        return super.get(fgcUser);
    }

    /**
     * 查询分页数据
     *
     * @param page    分页对象
     * @param fgcUser
     * @return
     */
    @Override
    public Page<FgcUser> findPage(Page<FgcUser> page, FgcUser fgcUser) {
        return super.findPage(page, fgcUser);
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param fgcUser
     */
    @Override
    @Transactional(readOnly = false)
    public void save(FgcUser fgcUser) {
        super.save(fgcUser);
    }

    /**
     * 更新状态
     *
     * @param fgcUser
     */
    @Override
    @Transactional(readOnly = false)
    public void updateStatus(FgcUser fgcUser) {
        super.updateStatus(fgcUser);
    }

    /**
     * 删除数据
     *
     * @param fgcUser
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(FgcUser fgcUser) {
        super.delete(fgcUser);
    }

    public FgcUser getFgcUserByOpenId(String openId) {
        return fgcUserDao.getFgcUserByOpenId(openId);
    }

    public FgcUser getFgcUserByNameAndCode(String userName, String verificationCode) {
        return fgcUserDao.getFgcUserByNameAndCode(userName, verificationCode);
    }

    /**
     * 微信登陆接口
     */
    @Transactional(readOnly = false)
    public ReturnInfo login(String wxCode, HttpServletResponse response) throws WxErrorException {

        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=wx9c6eb8c57de10e0c&secret=fa48b7a3be32d55bb25a041bded54337&js_code=" + wxCode + "&grant_type=authorization_code";
        String info = HttpClientUtils.ajaxGet(url);
        JSONObject infoJson = JSONObject.parseObject(info);
        String openid = (String) infoJson.get("openid");
        if (openid==null||openid.length()<=0){
            return ReturnDate.error(10001,"登陆失败,微信code错误", "");
        }
        //得到缓存的用户信息
        String dataJson = redisTemplate.opsForValue().get("uvanfactory_user_" + openid);
        //缓存不存在，就判断数据库中是否存在
        if (dataJson != null && dataJson.length() > 0) {
            JSONObject json1 = JSONObject.parseObject(dataJson);
            FgcUser user = JSONObject.toJavaObject(json1, FgcUser.class);
//            if (user != null) {
            String username = user.getSysLoginCode();
            String password = user.getSysLoginPas();
            String token = user.getToken();
            String status=user.getStatus();
            if ("1".equals(status)){
                return ReturnDate.error(10003, "账号被禁用，请联系管理员", "");
            }
            //检查是否有系统用户
            if (ParamentUntil.isBackString(username) && ParamentUntil.isBackString(password)) {
                String secretKey = Global.getConfig(LOGINSUBMIT);
                String uName = DesUtils.encode(username, secretKey);
                String pwd = DesUtils.encode(password, secretKey);
                String result = isSysLogin(uName, pwd);
                if ("true".equals(result)) {
                    response.setHeader("Token", token);
                    redisTemplate.opsForValue().set("uvanfactory_user_" + openid, dataJson,FGC_EXPRIED_TIME,TimeUnit.SECONDS);
                    redisTemplate.opsForValue().set("uvanfactory_user_" + token,openid ,FGC_EXPRIED_TIME,TimeUnit.SECONDS);
                    return ReturnDate.success(0, "登陆成功", "");
                } else {
                    return ReturnDate.error(10001,"登陆失败，系统用户密码错误", "");
                }
            } else {
                UserDataInfo userDataInfo= getData(user,"");
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(userDataInfo);
                redisTemplate.opsForValue().set("uvanfactory_user_" + openid, jsonObject.toString(),FGC_EXPRIED_TIME,TimeUnit.SECONDS);
                return ReturnDate.error(-300, "跳转到系统用户登录页面", "");
            }
//            }else {
//                return prossesone(openid,json);
//            }
//            return ReturnDate.error(-11001, "微信登录失败");
        } else {
            //缓存中没有，去数据库中查找
           FgcUser fgcUser=this.getFgcUserByOpenId(openid);
            //生成token
            String token =UUID.randomUUID().toString().replaceAll("-","");
            if (fgcUser==null){
                fgcUser = new FgcUser();
                fgcUser.setOpenId(openid);
                fgcUser.setIsNewRecord(true);
                this.save(fgcUser);

                UserDataInfo userDataInfo= getData(fgcUser,"");
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(userDataInfo);
                redisTemplate.opsForValue().set("uvanfactory_user_" + openid, jsonObject.toString(),FGC_EXPRIED_TIME,TimeUnit.SECONDS);
                return ReturnDate.error(-300, "跳转到系统用户登录页面", "");
            }else {
                if ("1".equals(fgcUser.getStatus())){
                    return ReturnDate.error(10003, "账号被禁用，请联系管理员", "");
                }
                //检查是否有系统用户
                String username = fgcUser.getSysLoginCode();
                String password = fgcUser.getSysLoginPas();
                if (ParamentUntil.isBackString(username) && ParamentUntil.isBackString(password)) {
                    String secretKey = Global.getConfig(LOGINSUBMIT);
                    String uName = DesUtils.encode(username, secretKey);
                    String pwd = DesUtils.encode(password, secretKey);
                    String result = isSysLogin(uName, pwd);
                    if ("true".equals(result)) {
                        UserDataInfo userDataInfo= getData(fgcUser,token);
                        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(userDataInfo);
                        redisTemplate.opsForValue().set("uvanfactory_user_" +openid ,jsonObject.toString() ,FGC_EXPRIED_TIME,TimeUnit.SECONDS);
                        redisTemplate.opsForValue().set("uvanfactory_user_" + token,openid ,FGC_EXPRIED_TIME,TimeUnit.SECONDS);
                        response.setHeader("Token", token);
                        return ReturnDate.success(0, "登陆成功", "");
                    } else {
                        return ReturnDate.error(10001, "登陆失败，系统用户密码错误", "");
                    }
                }else {
                    UserDataInfo userDataInfo= getData(fgcUser,"");
                    JSONObject jsonObject = (JSONObject) JSONObject.toJSON(userDataInfo);
                    redisTemplate.opsForValue().set("uvanfactory_user_" + openid, jsonObject.toString());
                    return ReturnDate.error(-300, "跳转到系统用户登录页面", "");
                }
            }
        }
    }

    public UserDataInfo getData(FgcUser user, String token) {
        UserDataInfo info=new UserDataInfo();
        info.setCity(user.getCity());
        info.setCountry(user.getCountry());
        info.setDocumentCode(user.getDocumentCode());
        info.setHeadimgurl(user.getHeadimgurl());
        info.setNickname(user.getNickname());
        info.setOpenId(user.getOpenId());
        info.setPrivilege(user.getPrivilege());
        info.setProvince(user.getProvince());
        info.setSex(user.getSex());
        info.setSysLoginCode(user.getSysLoginCode());
        info.setSysLoginPas(user.getSysLoginPas());
        info.setToken(token);
        info.setStatus(user.getStatus());
        info.setUnionid(user.getUnionid());
        info.setUserName(user.getUserName());
        info.setVerificationCode(user.getVerificationCode());
        return info;
    }

    @Transactional(readOnly = false)
    public String isSysLogin(String username, String password) {
        String sysUrl = SYSNOTIFICATION + "/a/login?__login=true&__ajax=json&username=" + username + "&password=" + password + "";
        String result = "";
        String sysInfo = HttpClientUtils.ajaxGet(sysUrl);
        if (ParamentUntil.isBackString(sysInfo)) {
            JSONObject sysJson = JSON.parseObject(sysInfo);
            result = sysJson.get("result").toString();
            String sessionId = sysJson.get("sessionid").toString();
        }
        return result;
    }

    @Transactional(readOnly = false)
    public ReturnInfo prossesone(String openid, JSONObject json) {
        String token = UUID.randomUUID().toString().replaceAll("-","");
        FgcUser fgcUser = fgcUserDao.getFgcUserByOpenId(openid);
        if (fgcUser == null) {
            fgcUser = new FgcUser();
            fgcUser.setOpenId(openid);
            //新增微信用户
            fgcUser.setCity((String) json.get("city"));
            fgcUser.setCountry((String) json.get("country"));
            fgcUser.setSex(json.get("sex").toString());
            fgcUser.setNickname((String) json.get("nickname"));
            fgcUser.setHeadimgurl((String) json.get("headimgurl"));
//            wechatK3User.setOpenId(session.getOpenid());
            fgcUser.setOpenId(openid);
            fgcUser.setIsNewRecord(true);
            this.save(fgcUser);
        }
        fgcUser.setToken(token);
        //写入redis
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(fgcUser);
        redisTemplate.opsForValue().set("uvanfactory_user_" + openid, token);
        redisTemplate.opsForValue().set("uvanfactory_user_" + token, jsonObject.toString());
        String wxuser = json.get("nickname") + "," + openid;
        FgcLogUtil.insertLog(wxuser, "", "", "/fgc/wechatLogin", "微信登录", "微信登录成功");
        return ReturnDate.success(0, "微信登录成功，跳转系统用户登录界面！", token);
    }
    @Transactional(readOnly = false)
    public ReturnInfo sysLoginFgc(String openid, String userName, String password, HttpServletResponse response,FgcUser wxUserInfo) {
        //保存微信信息
        this.save(wxUserInfo);
        //得到缓存的用户信息
        String dataJson = redisTemplate.opsForValue().get("uvanfactory_user_" + openid);
        if (dataJson != null && dataJson.length() > 0) {
            String secretKey = Global.getConfig(LOGINSUBMIT);
            String uName = DesUtils.encode(userName, secretKey);
            String pwd = DesUtils.encode(password, secretKey);
            String result = isSysLogin(uName, pwd);
            if ("true".equals(result)) {
                JSONObject json1 = JSONObject.parseObject(dataJson);
                UserDataInfo user = JSONObject.toJavaObject(json1, UserDataInfo.class);
                if ("1".equals(user.getStatus())){
                    return ReturnDate.error(10003, "账号被禁用，请联系管理员", "");
                }
                String document=user.getDocumentCode();
                FgcUser user1=new FgcUser();
                user1.setDocumentCode(document);
                FgcUser user2=this.get(user1);
                user2.setSysLoginCode(userName);
                user2.setSysLoginPas(password);
                this.save(user2);
                String token=json1.get("token").toString();
                if (!ParamentUntil.isBackString(token)){
                    token= UUID.randomUUID().toString().replaceAll("-","");
                }
                UserDataInfo userDataInfo= getData(user2,token);
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(userDataInfo);
                redisTemplate.opsForValue().set("uvanfactory_user_" +openid ,jsonObject.toString() ,FGC_EXPRIED_TIME,TimeUnit.SECONDS);
                redisTemplate.opsForValue().set("uvanfactory_user_" + token,openid ,FGC_EXPRIED_TIME,TimeUnit.SECONDS);
                response.setHeader("token", token);
                return ReturnDate.success(0, "登陆成功", "");
            } else {
                return ReturnDate.error(10001, "登陆失败，系统用户密码错误", "");
            }
        }else {
            return ReturnDate.error(-1, "系统异常，请重新登录", "");
        }
    }

    public FgcUser getUserByloginName(String userName) {
        return fgcUserDao.getUserByloginName(userName);
    }

    @Transactional
    public int updataStatus(String documentCode, String status) {
       return fgcUserDao.updataStatus(documentCode,status);
    }
}