/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.screen.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.screen.entity.ScreenConfigDetail;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 屏幕配置DAO接口
 * @author len
 * @version 2018-12-21
 */
@MyBatisDao
public interface ScreenConfigDetailDao extends CrudDao<ScreenConfigDetail> {
    @Select("DELETE FROM js_screen_config_detail WHERE configure_code=#{arg0}")
    void deleteDb(String configureCode);

}