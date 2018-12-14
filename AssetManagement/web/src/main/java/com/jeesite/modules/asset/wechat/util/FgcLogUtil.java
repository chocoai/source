package com.jeesite.modules.asset.wechat.util;

import com.jeesite.common.lang.DateUtils;
import com.jeesite.modules.asset.wechat.dao.FgcLogDAO;
import com.jeesite.modules.asset.wechat.entity.WechatLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * 类描述
 *
 * @author Jace Xiong
 */
@Component
public class FgcLogUtil {
    private static FgcLogDAO fgcLogDAO;
    @Autowired
    private FgcLogDAO fgcLogDAO1;

    @PostConstruct
    public void beforeInit() {
        fgcLogDAO = fgcLogDAO1;
    }

    public static int insertLog(String wxuser,String sysuser,String k3user,String url,String action,String result){
        String time = DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
        Date date = DateUtils.parseDate(time);

        WechatLog wechatLog = new WechatLog();
        wechatLog.setWxuser(wxuser);
        wechatLog.setSysuser(sysuser);
        wechatLog.setK3user(k3user);
        wechatLog.setUrl(url);
        wechatLog.setAction(action);
        wechatLog.setResult(result);
        wechatLog.setActionTime(date);

        return fgcLogDAO.insertLog(wechatLog);
    }
}
