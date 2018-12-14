/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.synthetical.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.achievement.synthetical.entity.AchSynthetical;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;

/**
 * 绩效综合管理DAO接口
 * @author len
 * @version 2018-11-16
 */
@MyBatisDao
public interface AchSyntheticalDao extends CrudDao<AchSynthetical> {

    @Update("update ach_synthetical set disable_by=null,disable_date=null,disable_status='0' where synthetical_code= #{arg0}")
    void enable(String syntheticalCode);

    /**
     * 根据编码删除数据
     * @param syntheticalCode
     */
    @Delete("DELETE FROM ach_synthetical WHERE synthetical_code=#{arg0}")
    void deleteDb(String syntheticalCode);
}