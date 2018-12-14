/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.wish.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.asset.wish.entity.WishPrimary;
import org.apache.ibatis.annotations.Select;

/**
 * 初选提名记录表DAO接口
 * @author len
 * @version 2018-11-20
 */
@MyBatisDao
public interface WishPrimaryDao extends CrudDao<WishPrimary> {
    @Select("SELECT a.* FROM js_ding_user a WHERE a.userid=#{arg0} and a.left='0'")
    DingUser getDingUser(String userId);
}