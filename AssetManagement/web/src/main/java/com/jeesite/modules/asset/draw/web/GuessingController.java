package com.jeesite.modules.asset.draw.web;


import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.mapper.JsonMapper;
import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.asset.ding.service.DingUserService;
import com.jeesite.modules.asset.draw.entity.GuesseData;
import com.jeesite.modules.asset.draw.service.LuckDrawService;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.fz.config.IsFileter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * 竞猜业绩
 */
@Controller
@RequestMapping(value = "${adminPath}/fz/guessing")
public class GuessingController {

//    private static final String CONFIG_KEY = "deadline_for_quiz";
    @Autowired
    private LuckDrawService luckDrawService;

    @Autowired
    private DingUserService dingUserService;

    /**
     * 获取后台竞猜业绩接口：
     * @param userId
     * @return
     * @throws ParseException
     */
    @IsFileter(isFile="true")
    @ResponseBody
    @RequestMapping(value = "getAchievement", method = RequestMethod.GET)
    public ReturnInfo getAchievement(String userId) throws ParseException {
        Date date = getConfigVal();
        boolean flag = true;
        // 截止时间与当前时间对比
        if (date.compareTo(new Date()) >= 0) {
            flag = false;
        }
        // 根据userId获取用户信息
        DingUser dingUser = dingUserService.get(userId);
        GuesseData guesseData = new GuesseData();
        guesseData.setUserId(userId);
        if (dingUser.getAchievement() != null) {
//            String achievement = new BigDecimal(dingUser.getAchievement()).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
//            if (achievement.endsWith(".00")) {
//                achievement = achievement.replace(".00", "");
//            } else if (achievement.endsWith(".0")) {
//                achievement = achievement.replace(".0", "");
//            }
            guesseData.setAchievement(dingUser.getAchievement());
        } else {
            guesseData.setAchievement("");
        }
        guesseData.setDate(DateUtils.formatDateTime(date));
        guesseData.setFlag(flag);
        return ReturnDate.success(JSONObject.parseObject(JsonMapper.toJson(guesseData)));
    }

    /**
     * 更新竞猜业绩接口
     * @param userId
     * @param achievement
     * @return
     * @throws ParseException
     */
    @IsFileter(isFile="true")
    @ResponseBody
    @RequestMapping(value = "updateAchievement", method = RequestMethod.GET)
    public ReturnInfo updateAchievement(String userId, String achievement) throws ParseException {
        DingUser dingUser = dingUserService.get(userId);
        if (dingUser == null) {
            return ReturnDate.error(400, "业绩竞猜失败，请联系管理员。");
        } else {
            if (getConfigVal().compareTo(new Date()) >= 0) {
                if (achievement.length() > 4) {
                    return ReturnDate.error(403, "请输入4位数字");
                }
                boolean flag = isInteger(achievement);
                if (flag) {
                    dingUser.setAchievement(achievement);
                    dingUserService.save(dingUser);
                    return ReturnDate.success();
                } else {
                    return ReturnDate.error(402, "请输入数字");
                }
            } else {
                return ReturnDate.error(401, "已超过截止时间,提交竞猜业绩失败。");
            }
        }
    }


    /**
     * 获取设定的精彩时间
     * @return
     * @throws ParseException
     */
    public Date getConfigVal() throws ParseException{
        /** 获取设置的截止时间*/
        String configVal = luckDrawService.selectByKey();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.parse(configVal);
    }

    /*
     * 判断是否为整数
     * @param str 传入的字符串
     * @return 是整数返回true,否则返回false
     */

    public static boolean isInteger(String str) {
        if ("".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}
