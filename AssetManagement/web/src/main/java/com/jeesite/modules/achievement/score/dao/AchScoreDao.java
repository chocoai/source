/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.score.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.achievement.score.entity.AchScore;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 加减分管理DAO接口
 * @author len
 * @version 2018-11-16
 */
@MyBatisDao
public interface AchScoreDao extends CrudDao<AchScore> {
    @Delete("DELETE FROM ach_score WHERE bill_code=#{arg0}")
    void deleteDb(String billCode);

    @Select("SELECT * FROM ach_score limit 1")
    List<AchScore> selectTemp();
}