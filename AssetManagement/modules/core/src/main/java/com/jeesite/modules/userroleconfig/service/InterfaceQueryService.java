/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.userroleconfig.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.userroleconfig.dao.InterfaceFieldDao;
import com.jeesite.modules.userroleconfig.dao.InterfaceQueryDao;
import com.jeesite.modules.userroleconfig.entity.InterfaceField;
import com.jeesite.modules.userroleconfig.entity.InterfaceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 接口查询条件Service
 *
 * @author dwh
 * @version 2018-07-18
 */
@Service
@Transactional(readOnly = true)
public class InterfaceQueryService extends CrudService<InterfaceQueryDao, InterfaceQuery> {

    @Autowired
    private InterfaceFieldDao interfaceFieldDao;

    /**
     * 获取单条数据
     *
     * @param interfaceQuery
     * @return
     */
    @Override
    public InterfaceQuery get(InterfaceQuery interfaceQuery) {
        return super.get(interfaceQuery);
    }

    /**
     * 查询分页数据
     *
     * @param page           分页对象
     * @param interfaceQuery
     * @return
     */
    @Override
    public Page<InterfaceQuery> findPage(Page<InterfaceQuery> page, InterfaceQuery interfaceQuery) {
        return super.findPage(page, interfaceQuery);
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param interfaceQuery
     */
    @Override
    @Transactional(readOnly = false)
    public void save(InterfaceQuery interfaceQuery) {
        super.save(interfaceQuery);
    }

    /**
     * 更新状态
     *
     * @param interfaceQuery
     */
    @Override
    @Transactional(readOnly = false)
    public void updateStatus(InterfaceQuery interfaceQuery) {
        super.updateStatus(interfaceQuery);
    }

    /**
     * 删除数据
     *
     * @param interfaceQuery
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(InterfaceQuery interfaceQuery) {
        super.delete(interfaceQuery);
    }

    public List<InterfaceQuery> getInterfaceQueryList(String interfaceCode, String userCode, String fileCode) {

        InterfaceQuery interfaceQuery = new InterfaceQuery();
        interfaceQuery.setUserCode(userCode);
        interfaceQuery.setInterfaceCode(interfaceCode);
        List<InterfaceQuery> list = super.findList(interfaceQuery);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                InterfaceQuery query = list.get(i);
                InterfaceField field = new InterfaceField();
                field.setFieldCode(query.getFieldCode());

                field = interfaceFieldDao.get(field);
                if (field!=null) {
                    String sql = field.getFieldName() + " " + list.get(i).getSymbol() + " " + list.get(i).getFieldValue();
                    list.get(i).setQueryListName(sql);
                }

            }
        }

        return list;
    }

    public List<InterfaceQuery> getQueryListByRole(String interfaceCode, String roleCode, String fileCode) {

        InterfaceQuery interfaceQuery = new InterfaceQuery();
        interfaceQuery.setRoleCode(roleCode);
        interfaceQuery.setInterfaceCode(interfaceCode);
        List<InterfaceQuery> list = super.findList(interfaceQuery);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                InterfaceQuery query = list.get(i);
                InterfaceField field = new InterfaceField();
                field.setFieldCode(query.getFieldCode());

                field = interfaceFieldDao.get(field);
                if (field!=null) {
                    String sql = field.getFieldName() + " " + list.get(i).getSymbol() + " " + list.get(i).getFieldValue();
                    list.get(i).setQueryListName(sql);
                }

            }
        }

        return list;
    }

    public InterfaceQuery getQueryByCode(InterfaceQuery interfaceQuery) {
        return super.get(interfaceQuery);
    }
}