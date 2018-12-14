package com.jeesite.modules.asset.wechat.dao;

import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.wechat.entity.WechatLog;
import org.apache.ibatis.annotations.Insert;

/**
 * 梵工厂日志DAO
 *
 * @author Jace Xiong
 */
@MyBatisDao
public interface FgcLogDAO {
    @Insert("insert into js_wechat_log(wxuser,sysuser,k3user,url,action,result,action_time) values(#{wxuser},#{sysuser},#{k3user},#{url},#{action},#{result},#{actionTime})")
    int insertLog(WechatLog wechatLog);
}
