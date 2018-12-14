/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.guideApp.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.guideApp.entity.GuideActivity;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * 导购活动表DAO接口
 * @author len
 * @version 2018-12-07
 */
@MyBatisDao
public interface GuideActivityDao extends CrudDao<GuideActivity> {
    /**
     * 查询所有状态是有效的活动
     * @param
     * @return
     */
    List<GuideActivity> selectList();

    /**
     * 获取最新的创建时间
     * @return
     */
    @Select("SELECT create_time from js_guide_activity ORDER BY create_time desc LIMIT 1")
    Date getNewTime();
}