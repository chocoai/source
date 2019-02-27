/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.ovopark.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.storevideo.ovopark.entity.SvOvoparkFaceGroup;
import org.apache.ibatis.annotations.Delete;

/**
 * 万店掌人脸分组DAO接口
 * @author Philip Guan
 * @version 2019-02-18
 */
@MyBatisDao
public interface SvOvoparkFaceGroupDao extends CrudDao<SvOvoparkFaceGroup> {

    @Delete("DELETE FROM sv_ovopark_face_group")
    int deleteAll();
}