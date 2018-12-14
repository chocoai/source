/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.appreciation.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.fz.appreciation.entity.FzAppreciationFollow;
import com.jeesite.modules.fz.appreciation.entity.FzAppreciationRecord;
import com.jeesite.modules.fz.appreciation.returnData.LeaderboardData;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 赞赏记录表DAO接口
 * @author dwh
 * @version 2018-09-19
 */
@MyBatisDao
public interface FzAppreciationFollowDao extends CrudDao<FzAppreciationFollow> {
    @Select("select * from js_ding_user a where userid=#{praiserId} and a.left='0'")
    LeaderboardData getUserInfo(String praiserId);
    @Select("SELECT re.praiser_id, COUNT(appreciation_follow_code) AS praiserNumber from  fz_appreciation_record re LEFT JOIN fz_appreciation_follow af ON re.appreciation_code=af.appreciation_code" +
            " where af.create_date >= #{arg0} and af.create_date <= #{arg1}" +
            " GROUP BY re.praiser_id ORDER BY praiserNumber DESC")
    List<FzAppreciationRecord> getListByPraiserNumber(Date startTime, Date endTime);
    @Select("select a.* ,b.name as presenterName from fz_appreciation_follow a left join js_ding_user b on a.presenter_id=b.userid where a.appreciation_code=#{appreciationCode}")
    List<FzAppreciationFollow> getFollowsByCode(String appreciationCode);
    @Delete("delete from fz_appreciation_follow where appreciation_code=#{appreciationCode}")
    void deleteDbByRecCode(String appreciationCode);
    @Select("SELECT re.praiser_id, COUNT(appreciation_follow_code) AS praiserNumber from  fz_appreciation_record re LEFT JOIN fz_appreciation_follow af ON re.appreciation_code=af.appreciation_code" +
            " where af.create_date >= #{arg0} and af.create_date <= #{arg1} and re.tag=#{arg2} " +
            " GROUP BY re.praiser_id ORDER BY praiserNumber DESC")
    List<FzAppreciationRecord> getListByTimeAndType(Date startTime, Date endTime, String type);
    @Select("SELECT * FROM fz_appreciation_record  a WHERE a.create_date >= #{arg0}" +
            "and  a.create_date <= #{arg1} " +
            " and a.tag =#{arg2} " +
            "  limit #{arg3},#{arg4}")
    List<FzAppreciationRecord> getRecodeByTimeAndType(Date startTime, Date endTime, String type, int pageSize, int pageNo);
    @Select("SELECT * FROM fz_appreciation_record  a WHERE a.create_date >= #{arg0}" +
            " and  a.create_date <= #{arg1} " +
            " limit #{arg2},#{arg3}")
    List<FzAppreciationRecord> getRecodeByTime(Date startTime, Date endTime, int pageSize, int pageNo);

    @Select("select * from js_ding_user where userid=#{userId}")
    LeaderboardData getUserInfo2(String userId);
    @Select("select appreciation_code from fz_appreciation_follow where presenter_id=#{userId}")
    List<String> getFollowsByUserId(String userId);

    /**
     * 根据时间查询赞赏记录
     * @param startTime
     * @param endTime
     * @return
     */
    @Select("SELECT a.*,sum(a.coin_count) AS coinCounts FROM fz_appreciation_record  a WHERE a.create_date >= #{arg0}" +
            " and  a.create_date <= #{arg1} group by a.praiser_id")
    List<FzAppreciationRecord> getRecordByTime(Date startTime, Date endTime);

    // 根据获赞记录获取跟赞记录
    List<FzAppreciationFollow> getFollows(List<String> appreciation);

    @Select("SELECT a.*,sum(a.coin_count) AS coinCounts FROM fz_appreciation_record  a WHERE a.create_date >= #{arg0}" +
            " and  a.create_date <= #{arg1} group by a.praiser_id,a.tag order by a.coin_count DESC")
    List<FzAppreciationRecord> getTypeRecordByTime(Date startTime, Date endTime);

    /**
     * 得到当天的跟赞数量
     * @param dayStartTime
     * @param dayEndTime
     * @param presenterId
     * @param praiserId
     * @return
     */
    @Select("SELECT IFNULL(SUM(ff.`coin_number`),0) FROM `fz_appreciation_follow` ff,`fz_appreciation_record` fr \n" +
            "WHERE ff.`appreciation_code`=fr.`appreciation_code` AND ff.`presenter_id`=#{arg2} AND fr.`praiser_id`=#{arg3} \n" +
            "AND ff.`create_date`>=#{arg0} AND ff.`create_date`<=#{arg1} ")
    int countFollowThisDay(Timestamp dayStartTime, Timestamp dayEndTime, String presenterId, String praiserId)throws SQLException;
}