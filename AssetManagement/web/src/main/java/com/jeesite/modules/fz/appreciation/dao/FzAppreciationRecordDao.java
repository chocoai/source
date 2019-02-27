/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.appreciation.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.asset.ding.entity.ExportAppreciationData;
import com.jeesite.modules.fz.appreciation.entity.FzAccountRecord;
import com.jeesite.modules.fz.appreciation.entity.FzAppreciationDate;
import com.jeesite.modules.fz.appreciation.entity.FzAppreciationRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
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
public interface FzAppreciationRecordDao extends CrudDao<FzAppreciationRecord> {
    @Delete("delete from fz_appreciation_record where appreciation_code=#{appreciationCode}")
    void deleteDb(String appreciationCode);
    @Select("SELECT count(DISTINCT a.praiser_id) FROM `fz_appreciation_record` a " +
            " WHERE a.create_date >= #{arg0} and a.create_date <= #{arg1} and a.tag = #{arg2}  ")
    int getTotal(Date startTime, Date endTime, String type);

    /**
     * 获取数据库名字
     * @return
     */
    @Select("select database() from DUAL;")
    String getDataBase();
    //获赞者获赞数量
    @Select("SELECT b.userid as userid,b.`name` as name,SUM(a.coin_count) as coinNum,b.position as position FROM fz_appreciation_record a LEFT JOIN js_ding_user b ON a.praiser_id=b.userid where b.`left`=0\n" +
            " GROUP BY b.`name`")
    List <ExportAppreciationData> getExportData();
    //赠送者赞赏数量+跟赞数量
    @Select("SELECT c.userid as userid,c.name as name,c.position as position,sum(c.coin_number) as coinNum FROM ((SELECT b.userid,b.`name`,b.position,sum(a.coin_number) AS coin_number FROM fz_appreciation_follow a\n" +
            "LEFT JOIN js_ding_user b ON a.presenter_id = b.userid WHERE b.`left` = '0' GROUP BY b.name) UNION ALL (SELECT b.userid,b.`name`,b.position,sum(a.coin_number) \n" +
            "AS coin_number FROM fz_appreciation_record a LEFT JOIN js_ding_user b ON a.presenter_id = b.userid WHERE b.`left` = '0' GROUP BY \tb.name)) c GROUP BY\n" +
            "c.`name`")
    List <ExportAppreciationData> getExportData1();


    /**
     * 获取梵赞记录里面前ranking名中在规定时间里面的排行中获赞者钉钉信息
     * @param ranking
     * @return
     */
    @Select("SELECT * FROM `js_ding_user` dd RIGHT JOIN(\n" +
            "SELECT * FROM fz_appreciation_record fze WHERE fze.`praiser_id` IN(\n" +
            "SELECT t.praiser_id FROM(\n" +
            "SELECT * FROM `fz_appreciation_record` fz \n" +
            "WHERE fz.`create_date`>=#{arg0} AND fz.`create_date` <#{arg1} GROUP BY fz.praiser_id ORDER BY IFNULL(SUM(fz.`coin_count`),0)\n" +
            "DESC LIMIT 0,#{arg2})\n" +
            "AS t))AS b  ON b.praiser_id=dd.userid WHERE b.`create_date`>=#{arg0} AND b.`create_date` <#{arg1};")
    List<DingUser> getSlidePraiserDate (Date startTime, Date endTime, int ranking) throws SQLException;

    /**
     * 获取梵赞记录里面前ranking名中在规定时间里面的排行中赞他人者钉钉信息
     * @param ranking
     * @return
     */
    @Select("SELECT * FROM `js_ding_user` dd RIGHT JOIN(\n" +
            "SELECT * FROM fz_appreciation_record fze WHERE fze.`praiser_id` IN(\n" +
            "SELECT t.praiser_id FROM(\n" +
            "SELECT * FROM `fz_appreciation_record` fz \n" +
            "WHERE fz.`create_date`>=#{arg0} AND fz.`create_date` <#{arg1} GROUP BY fz.praiser_id ORDER BY IFNULL(SUM(fz.`coin_count`),0)\n" +
            "DESC LIMIT 0,#{arg2})\n" +
            "AS t))AS b  ON b.presenter_id=dd.userid WHERE b.`create_date`>=#{arg0} AND b.`create_date` <#{arg1};")
    List<DingUser> getSlidePresenterDate (Date startTime, Date endTime,int ranking) throws SQLException;

    /**
     * 根据部门在一定时间内梵赞数据的的排行中赞他人者钉钉信息
     * @param startTime
     * @param endTime
     * @param departmentId
     * @return
     */
    @Select("SELECT * FROM js_ding_user jdu RIGHT JOIN(\n" +
            "SELECT * FROM fz_appreciation_record fa WHERE fa.praiser_id IN(\n" +
            "SELECT t.praiser_id FROM (\n" +
            "SELECT * FROM fz_appreciation_record fz WHERE fz.`presenter_id` IN (\n" +
            "SELECT jdu1.`userid` FROM js_ding_user jdu1,js_ding_department jdd1,js_ding_user_department jdud1 WHERE\n" +
            " jdu1.userid=jdud1.user_id AND jdd1.department_id=jdud1.department_id AND jdd1.parent_codes LIKE #{arg2}\n" +
            ") AND fz.`praiser_id` IN(\n" +
            "SELECT jdu2.userid FROM js_ding_user jdu2,js_ding_department jdd2,js_ding_user_department jdud2 WHERE \n" +
            "jdu2.userid=jdud2.user_id AND jdd2.department_id=jdud2.department_id AND jdd2.parent_codes LIKE #{arg2}\n" +
            ") AND fz.create_date >=#{arg0} AND fz.create_date <#{arg1} GROUP BY fz.praiser_id ORDER BY IFNULL(SUM(fz.`coin_count`),0) DESC LIMIT 0,#{arg3})\n" +
            "AS t) AND fa.create_date >=#{arg0} AND fa.create_date <#{arg1}\n" +
            ")AS b ON jdu.userid=b.presenter_id")
    List<DingUser> getPresenterDateByDapartmentId(Date startTime, Date endTime, String departmentId,int ranking)throws SQLException;

    /**
     * 根据部门在一定时间内梵赞数据的的排行中获赞者钉钉信息
     * @param startTime
     * @param endTime
     * @param departmentId
     * @return
     */
    @Select("SELECT * FROM js_ding_user jdu RIGHT JOIN(\n" +
            "SELECT * FROM fz_appreciation_record fa WHERE fa.praiser_id IN(\n" +
            "SELECT t.praiser_id FROM (\n" +
            "SELECT * FROM fz_appreciation_record fz WHERE fz.`presenter_id` IN (\n" +
            "SELECT jdu1.`userid` FROM js_ding_user jdu1,js_ding_department jdd1,js_ding_user_department jdud1 WHERE\n" +
            " jdu1.userid=jdud1.user_id AND jdd1.department_id=jdud1.department_id AND jdd1.parent_codes LIKE #{arg2}\n" +
            ") AND fz.`praiser_id` IN(\n" +
            "SELECT jdu2.userid FROM js_ding_user jdu2,js_ding_department jdd2,js_ding_user_department jdud2 WHERE \n" +
            "jdu2.userid=jdud2.user_id AND jdd2.department_id=jdud2.department_id AND jdd2.parent_codes LIKE #{arg2}\n" +
            ") AND fz.create_date >=#{arg0} AND fz.create_date <#{arg1} GROUP BY fz.praiser_id ORDER BY IFNULL(SUM(fz.`coin_count`),0) DESC LIMIT 0,#{arg3})\n" +
            "AS t) AND fa.create_date >=#{arg0} AND fa.create_date <#{arg1}\n" +
            ")AS b ON jdu.userid=b.praiser_id")
    List<DingUser> getPraiserDateByDapartmentId(Date startTime, Date endTime, String departmentId,int ranking)throws SQLException;
    /**
     * 在一定时间内获赞的总数和赞员工的多少人和排名
     * @param startTime
     * @param endTime
     * @param userId 钉钉id
     * @return
     */
    @Select("SELECT * FROM (\n" +
            "SELECT @rowno:=@rowno+1 AS leaderboards,a.* FROM(SELECT praiser_id , IFNULL(SUM(coin_number),0) coinCounts ,COUNT(DISTINCT presenter_id) praiserNum\n" +
            "FROM fz_appreciation_record WHERE create_date>=#{arg0} AND create_date<#{arg1} GROUP BY praiser_id  ORDER BY SUM(coin_number) DESC) a,\n" +
            "(SELECT @rowno:=0) b )c WHERE c.praiser_id=#{arg2};")
    FzAppreciationDate getPraisDate(Date startTime, Date endTime, String userId) throws SQLException;

    /**
     * 在一定时间内赠送多少梵赞和赞了多少人
     * @param startTime
     * @param endTime
     * @param userId 钉钉id
     * @return
     */
    @Select("SELECT IFNULL(SUM(a.coin_number),0) coinNumber,COUNT(DISTINCT a.praiser_id) presenterNum FROM fz_appreciation_record a WHERE a.presenter_id =#{arg2} AND a.create_date>=#{arg0} AND a.create_date<#{arg1}")
    FzAppreciationDate getPresentDate(Date startTime, Date endTime, String userId)throws SQLException;

    /**
     * 获取当天送了多少梵赞,不包括跟赞
     * @param dayStartTime
     * @param dayEndTime
     * @param presenterId
     * @param praiserId
     * @return
     * @throws SQLException
     */
    @Select("SELECT IFNULL(SUM(f.`coin_number`),0) SUM FROM `fz_appreciation_record` f WHERE f.`praiser_id`=#{arg3} AND f.`presenter_id`=#{arg2} \n" +
            "AND f.`create_date`>=#{arg0} AND f.`create_date`<=#{arg1}")
    int countThidDay(Timestamp dayStartTime, Timestamp dayEndTime, String presenterId, String praiserId)throws SQLException;

    /**
     * 梵赞收入记录
     * @param userId
     */
    List<FzAccountRecord> getAccountRecord(@Param("userId") String userId, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize,@Param("flag") String flag);

    Integer selectCount(@Param("userId") String userId, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize,@Param("flag") String flag);
}