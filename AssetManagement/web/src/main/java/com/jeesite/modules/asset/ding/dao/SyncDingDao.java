package com.jeesite.modules.asset.ding.dao;

import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.ding.entity.SyncOrganize;
import com.jeesite.modules.asset.ding.entity.SyncPosition;
import com.jeesite.modules.asset.ding.entity.SyncUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@MyBatisDao
public interface SyncDingDao {

    //e-learing同步数据用，取除了父部门剩下所有
    @Select("select a.department_id as organizeCode,a.name as organizeName, a.parentid as parentCode from js_ding_department a where parentid != '0' order by a.department_id DESC")
    List<SyncOrganize> getOrganizes();
    //e-learing同步数据用，只取父部门
    @Select("select a.department_id as organizeCode,a.name as organizeName, a.parentid as parentCode from js_ding_department a where parentid = '0'")
    SyncOrganize getParentOrganize();

    //e-learing同步数据用，获得岗位信息用于同步
    @Select("select (@n:=@n+1) positionCode, b.position positionName from (select DISTINCT case o.position when null then o.name when '' then o.name else o.position end position from js_ding_user o) b,(select @n:=0) as num")
    List<SyncPosition> getSyncPosition();
/*

    //编好岗位代码重新存入数据库中
    @Update("update js_ding_user j set j.positionCode=#{arg0} where j.position =#{arg1}")
    void savePositionResult(String positionCode,String position);
*/

    //e-learing同步数据用，获得用户信息用于同步
    @Select("SELECT a.userName userName, a.employeeCode employeeCode, a.loginName loginName, a.accountStatus accountStatus, b.department_id organizeCode, d.positionCode FROM (SELECT o.`name` userName, o.jobnumber employeeCode, o.userid userid, o.mobile loginName, o.user_status accountStatus, CASE o.position WHEN NULL THEN o.name WHEN '' THEN o.name ELSE o.position END `position` FROM js_ding_user o GROUP BY o.mobile) a LEFT JOIN js_ding_user_department b ON a.userid=b.user_id LEFT JOIN (SELECT (@i:=@i+1) positionCode, b.position positionName FROM (SELECT DISTINCT CASE c.position WHEN NULL THEN c.name WHEN '' THEN c.name ELSE c.position END `position` FROM js_ding_user c ) b,(SELECT @i:=0) AS i) d ON a.position=d.positionName WHERE employeeCode IS NOT NULL && employeeCode != '' GROUP BY a.loginName")
    List<SyncUser> getSyncUsers();

    //保存调用同步接口后返回的结果
    @Insert("insert into js_ding_sync_elearing(create_time, sync_name, message) values(#{arg0}, #{arg1}, #{arg2})")
    void saveSyncResult(String date, String method, String result);




/*
*
* 隔开
*
* */
}
