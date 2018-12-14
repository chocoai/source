///**
// * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
// */
//package com.jeesite.modules.asset.userroleconfig.dao;
//
//import com.jeesite.common.dao.CrudDao;
//import com.jeesite.common.mybatis.annotation.MyBatisDao;
//import com.jeesite.modules.asset.userroleconfig.entity.InterfaceInfo;
//
//import java.util.List;
//
///**
// * 接口信息DAO接口
// * @author dwh
// * @version 2018-07-18
// */
//@MyBatisDao
//public interface InterfaceInfoDao extends CrudDao<InterfaceInfo> {
//    int deluserInfterface(String userCode, String interFaceCode);
//    List<InterfaceInfo> listDataByUserCode(String userCode);
//
//    List<InterfaceInfo> getInfoByNotInUser(String userCode);
//
//    int deleteDb(String interFaceCode);
//
//    List<InterfaceInfo> getInfoByUserCodeAndIntCode(String userCode, String interfaceCode);
//}