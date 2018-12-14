/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.ding.dao;

import com.jeesite.common.dao.TreeDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.ding.data.Node;
import com.jeesite.modules.asset.ding.entity.DepartmentData;
import com.jeesite.modules.asset.ding.entity.DingDepartment;
import com.jeesite.modules.asset.ding.entity.SyncOrganize;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * js_ding_departmentDAO接口
 * @author scarlett
 * @version 2018-09-20
 */
@MyBatisDao
public interface DingDepartmentDao extends TreeDao<DingDepartment> {
    @Select("select a.* from js_ding_department  a left join  js_ding_user_department b on a.department_id=b.department_id where  b.user_id=#{userid}")
    List<DingDepartment> getDingDepartmentByUser(String userid);
    List<DingDepartment> selectAll();
    @Select("select department_id from js_ding_department where parentid=#{arg0}")
    List<String> getDingDepartmentByParentCode(String parentCode);
    @Select("select department_id from js_ding_department where parentid='0'")
    String getRootCode();
    @Select("select department_id from js_ding_department where parent_codes  like #{s}")
    List<String> getIdByParntsId(String s);
    @Select("select name from js_ding_department  a left join  js_ding_user_department b on a.department_id=b.department_id where  b.user_id=#{userid}")
    List<String> getDepartmentNameByUser(String userid);
    @Select("select * from js_ding_department where parent_code=#{arg0} order by tree_sort")
    List<DingDepartment> getChiddeptsByParentCode(String parentCode);
    @Select("SELECT department_id AS departmentId,tree_level AS treeLevel,parent_code AS parentCode,tree_leaf AS treeLeaf,parentid AS parentId," +
            "parent_codes AS parentCodes,dept_manager_userid_list AS managerUser,user_count AS userCount,name FROM js_ding_department ORDER BY tree_sort")
    List<DepartmentData> getDepartment();
    @Update("update js_ding_department set user_count=#{arg0} where department_id=#{arg1}")
    void updateDepartmentUserCount(Long userCount,String departmentId);
    @Select("select a.department_id as id,a.name as name, a.parentid as parentId,a.tree_level as level  from js_ding_department a ORDER BY a.tree_level DESC")
    List<Node> getAll();



    //根据部门名字得到部门id
    @Select("SELECT a.`department_id` FROM js_ding_department a WHERE a.name=#{arg0};")
    String getDeptIdByName(String name);

    @Select("SELECT DISTINCT COUNT(1) FROM js_ding_user a \n" +
            "LEFT JOIN js_ding_user_department b ON a.`userid`=b.`user_id`\n" +
            "LEFT JOIN js_ding_department c ON b.`department_id`=c.`department_id` \n" +
            "WHERE a.`left`=0 AND c.`parent_codes` LIKE #{arg0} or c.department_id like #{arg0}; ")
    int getNumberById(String id);



    //去重查询出部门所有id
    @Select("SELECT DISTINCT department_id FROM js_ding_department")
    List<String> getAllDepartmentId();

    //boolean syncAllDepartment(List<DingDepartment> );
}