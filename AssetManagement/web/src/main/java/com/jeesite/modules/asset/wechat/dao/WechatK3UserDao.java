/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.wechat.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.wechat.entity.WechatK3User;

import java.util.List;

/**
 * js_wechat_k3_userDAO接口
 * @author jace
 * @version 2018-08-01
 */
@MyBatisDao
public interface WechatK3UserDao extends CrudDao<WechatK3User> {

    WechatK3User getWechatK3UserByOenId(String openId);

    List<String> getUserRoleByLoginCode(String loginCode);
}