/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.supplier.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.supplier.entity.SupSupplier;
import org.apache.ibatis.annotations.Update;


/**
 * 供应商资料DAO接口
 * @author Scarlett
 * @version 2018-07-04
 */
@MyBatisDao
public interface SupSupplierDao extends CrudDao<SupSupplier> {
    void deleteData(String supplierCode);
    int findSupSupplier(String companyName);
    void updatePartialInfo(String supplierStatus,Double score,String savePath,String relativePath,String abbreviationName,String supplierCode);
}