/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.fault.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.fault.entity.BugRegistrationPicture;
import org.apache.ibatis.annotations.Delete;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 线上bug登记图片DAO接口
 * @author scarlett
 * @version 2018-10-25
 */
@MyBatisDao
public interface BugRegistrationPictureDao extends CrudDao<BugRegistrationPicture> {
    List<BugRegistrationPicture> findPicsByBugCode(String bugCode);
    void updateBugCode(@Param("bugCode") String bugCode,String picCode);
    @Delete("delete from js_bug_registration_picture where bugpic_code=#{arg0}")
    void deleteData(String bugpicCode);
}