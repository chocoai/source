/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.usermenu.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.achievement.usermenu.entity.AchUserMenu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;

/**
 * 绩效卡用户菜单DAO接口
 * @author Philip Guan
 * @version 2018-11-22
 */
@MyBatisDao
public interface AchUserMenuDao extends CrudDao<AchUserMenu> {
    @Delete("DELETE FROM ach_user_menu WHERE `user_id` = #{arg2}")
    int delete(String userId);
}