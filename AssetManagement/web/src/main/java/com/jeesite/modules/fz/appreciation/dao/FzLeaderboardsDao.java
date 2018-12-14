/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.appreciation.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.fz.appreciation.entity.FzLeaderboards;
import com.jeesite.modules.fz.appreciation.returnData.LeaderboardData;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;

import java.util.Date;
import java.util.List;

/**
 * fz_leaderboardsDAO接口
 * @author dwh
 * @version 2018-09-26
 */
@MyBatisDao
public interface FzLeaderboardsDao extends CrudDao<FzLeaderboards> {
    // 先删除临时表数据
    @Delete("TRUNCATE TABLE fz_appreciation_sum;")
    void truncateTemp();
    // 重新插入数据
    @Insert("INSERT INTO fz_appreciation_sum SELECT t.praiser_id,t.tag,SUM(t.coin_count) coin_count,t.create_date,t.appreciation_code FROM (\n" +
            "SELECT a.praiser_id,a.tag,a.coin_count,date_format(a.create_date, '%Y%m%d') AS create_date,a.appreciation_code FROM fz_appreciation_record  a \n" +
            "WHERE a.create_date >= #{arg0} AND a.create_date <= #{arg1}) t GROUP BY t.praiser_id,t.create_date")
    void insertTemp(Date startDate, Date endDate);

    // 根据时间获取年或者月排行榜信息
    List<LeaderboardData> getYearMonthList(String yearMonth);

    // 根据时间获取周或者季度排行榜信息
    List<LeaderboardData> getWeekQuarterList(String start, String end);

    // 根据时间和标签获取数据
    List<LeaderboardData> getLeaderboardByType(Date startTime, Date endTime, String type);
}