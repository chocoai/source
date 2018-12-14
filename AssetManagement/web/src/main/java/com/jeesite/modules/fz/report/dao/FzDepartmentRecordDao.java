/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.report.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.fz.report.entity.FzDepartmentRecord;
import com.jeesite.modules.fz.report.entity.FzDeptData;
import com.jeesite.modules.fz.report.entity.FzDeptType;
import org.apache.ibatis.annotations.Select;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 梵赞部门的赞赏数据DAO接口
 * @author easter
 * @version 2018-11-14
 */
@MyBatisDao
public interface FzDepartmentRecordDao extends CrudDao<FzDepartmentRecord> {

    /**
     * 获得在一定时间内一级部门的各个赞赏数据
     * @param startTime
     * @param endTime
     * @return
     */
    @Select("SELECT \n" +
            "a.name presenterDepartment,\n" +
            "a.department_id preDept,\n" +
            "IFNULL(g.name,'无') praiserDepartment,\n" +
            "IFNULL(g.department_id,'无') praDept,\n" +
            "IFNULL(COUNT(DISTINCT d.`appreciation_code`),0) number \n" +
            "FROM js_ding_department a \n" +
            "LEFT JOIN js_ding_department b ON a.department_id=IF(LENGTH(b.parent_codes)=4,b.department_id,\n" +
            "IF(LEFT(LEFT(RIGHT(b.parent_codes,11),8),1)=',',LEFT(RIGHT(b.parent_codes,10),7),LEFT(RIGHT(b.parent_codes,11),8)))\n" +
            "LEFT JOIN js_ding_user_department c ON c.`department_id`=b.`department_id`  \n" +
            "LEFT JOIN fz_appreciation_record d ON d.`presenter_id`=c.`user_id` AND d.`create_date`>=#{arg0} AND d.`create_date`<=#{arg1}\n" +
            "LEFT JOIN js_ding_user_department e ON d.`praiser_id`=e.`user_id` \n" +
            "LEFT JOIN js_ding_user ju1 ON ju1.`userid`= c.`user_id` AND ju1.`left`='0'\n" +
            "LEFT JOIN js_ding_user ju2 ON ju2.userid =  e.`user_id` AND ju2.left='0'\n" +
            "LEFT JOIN js_ding_department f ON f.`department_id`=e.`department_id`    \n" +
            "LEFT JOIN js_ding_department g ON g.department_id = IF(LENGTH(f.parent_codes)=4,f.department_id,\n" +
            "IF(LEFT(LEFT(RIGHT(f.parent_codes,11),8),1)=',',LEFT(RIGHT(f.parent_codes,10),7),LEFT(RIGHT(f.parent_codes,11),8)))\n" +
            "WHERE a.`parentid` = '1' GROUP BY a.name,g.name;\n")
    List<FzDeptData> findByMonth(Date startTime, Date endTime)throws SQLException;

    /**
     * 得到了一级部门赞赏的各种使用类型以及使用次数
     * @param startTime
     * @param endTime
     * @return
     * @throws SQLException*/
    @Select("SELECT e.name dept,a.`tag` useType,COUNT(1) useTime \n" +
            "FROM js_ding_department e\n" +
            " JOIN js_ding_department d ON e.department_id = IF(LENGTH(d.parent_codes)=4,d.department_id,\n" +
            "IF(LEFT(LEFT(RIGHT(d.parent_codes,11),8),1)=',',LEFT(RIGHT(d.parent_codes,10),7),LEFT(RIGHT(d.parent_codes,11),8)))\n" +
            "JOIN js_ding_user_department c ON d.`department_id`=c.`department_id`\n" +
            "JOIN js_ding_user b ON c.`user_id`=b.`userid` AND b.`left`=0\n" +
            "JOIN fz_appreciation_record a ON a.`presenter_id`=b.`userid` AND a.`create_date`>=#{arg0} AND a.`create_date`<=#{arg1}\n" +
            "WHERE e.`parentid`='1' GROUP BY dept,useType ORDER BY dept,useTime DESC")
    List<FzDeptType> findFzTypeTime(Date startTime, Date endTime)throws SQLException;

    /*@Select("SELECT e.name name,a.`tag` tag\n" +
            "FROM js_ding_department e\n" +
            " JOIN js_ding_department d ON e.department_id = IF(LENGTH(d.parent_codes)=4,d.department_id,\n" +
            "IF(LEFT(LEFT(RIGHT(d.parent_codes,11),8),1)=',',LEFT(RIGHT(d.parent_codes,10),7),LEFT(RIGHT(d.parent_codes,11),8)))\n" +
            " JOIN js_ding_user_department c ON d.`department_id`=c.`department_id` \n" +
            " JOIN js_ding_user b ON c.`user_id`=b.`userid` AND b.`left`=0\n" +
            " JOIN fz_appreciation_record a ON a.`presenter_id`=b.`userid` AND a.`create_date`>=#{arg0} AND a.`create_date`<=#{arg1} \n" +
            "WHERE e.`parentid`='1' GROUP BY e.name ORDER BY COUNT(1) DESC")
    List<Map<String,String>> findFzTypeTime(Date startTime, Date endTime)throws SQLException;*/
}