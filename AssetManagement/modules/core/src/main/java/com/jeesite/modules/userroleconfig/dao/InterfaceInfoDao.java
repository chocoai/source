/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.userroleconfig.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;

import com.jeesite.modules.userroleconfig.entity.InterfaceInfo;

import java.util.List;

/**
 * 接口信息DAO接口
 * @author dwh
 * @version 2018-07-18
 */
@MyBatisDao
public interface InterfaceInfoDao extends CrudDao<InterfaceInfo> {
    int deluserInfterface(String userCode, String interFaceCode);
    int delRoleInfterface(String roleCode, String interFaceCode);
    List<InterfaceInfo> listDataByUserCode(String userCode);
    List<InterfaceInfo> listDataByRoleCode(String roleCode);

    List<InterfaceInfo> getInfoByNotInUser(String userCode);
    List<InterfaceInfo> getInfoByNotInRole(String roleCode);
    int deleteDb(String interFaceCode);

    List<InterfaceInfo> getInfoByUserCodeAndIntCode(String userCode, String interfaceCode);
    List<InterfaceInfo> getInfoByRoleCodeAndIntCode(String roleCode, String interfaceCode);
}