/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.ding.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.ding.entity.DingDepartment;
import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.asset.ding.entity.SyncUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;

import java.sql.SQLException;
import java.util.List;

/**
 * 人员管理DAO接口
 * @author scarlett
 * @version 2018-09-19
 */
@MyBatisDao
public interface DingUserDao extends CrudDao<DingUser> {
    void insertUserRole(String userid, String roleid);

    int selectUserRole(String userid, String roleid);

    void deleteUserDepartment(String userid);

    void deleteUserRole(String userid);

    void insertUserDepartment(String userid, String departmentId);

    @Select("SELECT distinct u.userid AS userid, u.name AS name, u.email AS email,u.tel AS tel,u.work_place AS workPlace,u.remark AS remark,u.mobile AS mobile,u.email AS email,\n" +
            "u.org_email AS orgEmail,u.department AS department,u.position AS position,u.avatar AS avatar,u.hired_date AS hiredDate,u.jobnumber AS jobnumber,u.extattr AS extattr,u.state_code AS stateCode,\n" +
            "u.is_senior AS isSenior,u.left AS 'left',u.convertible_gold AS convertibleCold,u.in_department_gold AS inDepartmentGold,u.out_department_gold AS outDepartmentGold\n" +
            "from js_ding_user u where u.left='0' and u.userid in (SELECT user_id from js_ding_user_department d where d.user_id=u.userid and d.department_id=#{arg0})")
    List<DingUser> getUserListByDepartmentId(String DepartmentId);

    @Select("select u.userid AS userid, u.name AS name, u.email AS email,u.tel AS tel,u.work_place AS workPlace,u.remark AS remark,u.mobile AS mobile,u.email AS email,\n" +
            "u.org_email AS orgEmail,u.department AS department,u.position AS position,u.avatar AS avatar,u.hired_date AS hiredDate,u.jobnumber AS jobnumber,u.extattr AS extattr,u.state_code AS stateCode,\n" +
            "u.is_senior AS isSenior,u.left AS 'left',u.convertible_gold AS convertibleCold,u.in_department_gold AS inDepartmentGold,u.out_department_gold AS outDepartmentGold\n" +
            " from js_ding_user u where u.userid=#{praiserId} and u.left='0'")
    DingUser getUserById(String praiserId);

    @Select("select * from js_ding_user a where a.userid=#{arg0} and a.left='0'")
    DingUser getUserWithId(String userid);

    @Select("select b.* from js_ding_user a join js_ding_user b on a.direct_superior = b.userid  where a.userid=#{arg0} and a.left='0'")
    DingUser getMyBoss(String userid);

    @Select("select userid from js_ding_user")
    List<String> getUserIdList();

    void updateLeftStatus(List<String> idList);

    List<DingUser> selectUserCountByDepartment(String departmentId);

    @Update("update js_ding_user a set in_department_gold=#{arg0},out_department_gold=#{arg1} where a.left='0'")
    void updateGold(long inDepartmentGold, long outDepartmentGold);

    @Update("update js_ding_user a set in_department_gold=#{arg0},out_department_gold=#{arg1} where a.left='0' AND  " +
            " a.userid in (SELECT b.user_id from js_ding_user_department b where b.department_id=#{arg2})")
    void updateGoldBydepartmentId(long inDepartmentGold, long outDepartmentGold, String id);

    @Select("SELECT sum(coin_number) FROM `fz_appreciation_record` a where a.praiser_id=#{arg0} ")
    Integer getPraiserCount(String userId);

    @Select("SELECT sum(coin_number) FROM `fz_appreciation_record` a where a.presenter_id=#{arg0} ")
    Integer getPresenterCount(String userId);

    List<DingDepartment> getDepartName(@Param("praiser") List<String> praiser);

    //获得跟赞数量
    @Select("SELECT sum(fd.coin_number) from fz_appreciation_follow fd INNER JOIN fz_appreciation_record rd on rd.appreciation_code=fd.appreciation_code where rd.praiser_id=#{arg0} \n" +
            "and fd.presenter_id<>#{arg0}")
    Integer getPraiserFollowCount(String userId);

    //送出的跟赞数量
    @Select("SELECT sum(rd.coin_number) from fz_appreciation_follow rd where rd.presenter_id=#{arg0}")
    Integer getPresenterFollowCount(String userId);

    //月末部门内和跨部门梵钻清零
    @Update("update js_ding_user set in_department_gold=0,out_department_gold=0")
    void updateGoldNumber();

    @Select("SELECT * FROM js_ding_user WHERE mobile= #{arg0}")
    List<DingUser> getUserByMobile(String mobile);

    List<DingUser> getUserByIds(@Param("userIdList") List<String> userIdList);

    @Update("UPDATE js_ding_user j SET j.convertible_gold = j.convertible_gold - #{arg1} WHERE j.left='0' AND j.userid = #{arg0} ")
    void updateConvertibleCold(String userid, String nums);
    List<String> getNamesByUserIds(@Param("userIdList") List<String> userIdList);

    // 在职人员
    @Select("SELECT a.* FROM js_ding_user a where a.left='0'")
    List<DingUser> selectByLeft();

    //更新用户的特权用户值
    @Update("UPDATE js_ding_user j SET j.user_is_vip=#{arg1} WHERE j.userid=#{arg0}")
    void updateUserIsVip(String userid, String status);

    void updateBatch(List<DingUser> dingUserList);

    //从数据库删除加盟商数据
    @Delete("delete from js_ding_user where (extattr is null or extattr = '')")
    void deleteAllianceUser();

    //批量插入用户
    void insertAllUsers(List<DingUser> dingUsers);
    //批量更新用户
    void updateAllUsers(List<DingUser> list);
    void deleteBatch(List<String> list);

    @Update("UPDATE js_ding_user SET customized=true WHERE userid=#{arg0}")
    void updateCustomized(String userId);
}
