/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.amspecimen.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.amspecimen.entity.AmSpeciQualifications;
import org.apache.ibatis.annotations.Select;

/**
 * 样品进度单文件表DAO接口
 * @author dwh
 * @version 2018-07-10
 */
@MyBatisDao
public interface AmSpeciQualificationsDao extends CrudDao<AmSpeciQualifications> {


    @Select("delete from js_am_speci_qualifications where code=#{arg0} and type_name=#{arg1}")
    void deleteByCodeAndType(String detailCode, String typeName);
}