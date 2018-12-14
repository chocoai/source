/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.supplier.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.supplier.entity.SupSupplierQualifications;

import java.util.Date;
import java.util.List;

/**
 * 供应商资料DAO接口
 * @author Scarlett
 * @version 2018-07-04
 */
@MyBatisDao
public interface SupSupplierQualificationsDao extends CrudDao<SupSupplierQualifications> {
    void deleteData(String supplierCode);
   /* SupSupplierQualifications isExistedPropifle(String qualifyCode);
    void updateSupSupplierCode(String supplierCode);*/
    void deleteByQualificationCode(String qualifyCode);
    SupSupplierQualifications findByQualificationCode(String qualifyCode);
    List<SupSupplierQualifications> findBySupSupplierCode(String supplierCode);
    void updateDateInfo(Date effectiveDate, Date exipreDate, String qualifyCode, String isNeverExpired);
}