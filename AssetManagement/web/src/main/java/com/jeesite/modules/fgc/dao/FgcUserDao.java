/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fgc.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.fgc.entity.FgcUser;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 梵工厂用户表DAO接口
 * @author dwh
 * @version 2018-08-14
 */
@MyBatisDao
public interface FgcUserDao extends CrudDao<FgcUser> {
    @Select("select * from js_fgc_user where open_id=#{openId}")
    FgcUser getFgcUserByOpenId(String openId);
    @Select("select * from js_fgc_user where user_name=#{arg0} and verification_code=#{arg1}")
    FgcUser getFgcUserByNameAndCode(String userName, String verificationCode);

    @Select("select * from js_fgc_user where sys_login_code=#{userName}")
    FgcUser getUserByloginName(String userName);
    @Update("update js_fgc_user set status=#{arg1} where document_code=#{arg0}")
    int updataStatus(String documentCode, String status);
}